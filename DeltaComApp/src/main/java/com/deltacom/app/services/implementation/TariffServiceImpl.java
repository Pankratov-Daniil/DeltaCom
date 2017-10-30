package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.exceptions.TariffException;
import com.deltacom.app.repository.api.OptionRepository;
import com.deltacom.app.repository.api.TariffRepository;
import com.deltacom.app.repository.implementation.OptionRepositoryImpl;
import com.deltacom.app.repository.implementation.TariffRepositoryImpl;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operations with repository for Tariff entities.
 */
@Service("TariffService")
public class TariffServiceImpl implements TariffService {
    @Autowired
    private TariffRepository tariffRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ContractService contractService;

    /**
     * Gets Tariff entity by its id from database.
     * @param id id of Tariff entity to be found
     * @return founded Tariff entity
     */
    @Override
    @Transactional
    public Tariff getTariffById(int id) {
        try {
            return tariffRepository.getById(id);
        } catch (PersistenceException ex) {
            throw new TariffException("Tariff wasn't gotten by id: ", ex);
        }
    }

    /**
     * Gets all Tariff entities from database.
     * @return List of Tariff entities from database
     */
    @Override
    @Transactional
    public List<Tariff> getAllTariffs() {
        try {
            return tariffRepository.getAll();
        } catch (PersistenceException ex) {
            throw new TariffException("Tariffs wasn't gotten: ", ex);
        }
    }

    /**
     * Creates new tariff
     * @param tariff tariff without options
     * @param tariffOptionsIds tariff options ids
     */
    @Override
    @Transactional
    public void addTariff(Tariff tariff, String[] tariffOptionsIds) {
        try {
            if(tariff == null) {
                throw new PersistenceException("Tariff cannot be null.");
            }
            if(tariffOptionsIds == null || tariffOptionsIds.length == 0){
                throw new PersistenceException("Options ids cannot be empty.");
            }
            tariff.setOptions(createOptionsListFromIds(tariffOptionsIds));
            tariffRepository.add(tariff);
        } catch (PersistenceException ex) {
            throw new TariffException("Tariff wasn't added: ", ex);
        }
    }

    /**
     * Updates tariff
     * @param tariff tariff without options
     * @param tariffOptionsIds tariff options ids
     */
    @Override
    @Transactional
    public void updateTariff(Tariff tariff, String[] tariffOptionsIds) {
        try {
            if(tariff == null) {
                throw new PersistenceException("Tariff cannot be null.");
            }
            if(tariffOptionsIds == null || tariffOptionsIds.length == 0){
                throw new PersistenceException("Options ids cannot be empty.");
            }
            tariff.setOptions(createOptionsListFromIds(tariffOptionsIds));
            tariffRepository.update(tariff);
        } catch (PersistenceException ex) {
            throw new TariffException("Tariff wasn't updated: ", ex);
        }
    }

    /**
     * Creates list of options from ids
     * @param ids options ids
     * @return list of options
     */
    private List<Option> createOptionsListFromIds(String[] ids) {
        List<Option> options = new ArrayList<>();
        for (String optionId : ids) {
            options.add(optionRepository.getById(Integer.parseInt(optionId)));
        }
        return options;
    }

    /**
     * Deletes tariff
     * @param id id of tariff to delete
     */
    @Override
    @Transactional
    public void deleteTariff(int id) {
        try {
            Tariff tariff = getTariffById(id);
            if(tariff == null) {
                throw new PersistenceException("Tariff wasn't found");
            }
            List<Contract> contracts = contractService.getAllContractsByTariff(tariff);
            if(!contracts.isEmpty()) {
                //try to add another tariff to contracts
                List<Tariff> tariffs = getAllTariffs();
                Tariff newTariff = null;
                for(Tariff possibleTariff : tariffs) {
                    if(possibleTariff.getId() != tariff.getId()) {
                        newTariff = possibleTariff;
                        break;
                    }
                }
                if(newTariff != null) {
                    List<Option> options = optionRepository.getAllOptionsForTariff(newTariff.getId());
                    int[] optionsIds = new int[options.size()];
                    for(int i = 0; i < options.size(); i++) {
                        optionsIds[i] = options.get(i).getId();
                    }
                    for (Contract contract : contracts) {
                        contractService.updateContract(contract.getNumbersPool().getNumber(), newTariff.getId(), optionsIds);
                        contract.setTariff(newTariff);
                    }
                }
            }
            tariffRepository.remove(tariff);
        } catch (PersistenceException ex) {
            throw new TariffException("Tariff wasn't deleted: ", ex);
        }
    }
}
