package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.exceptions.RepositoryException;
import com.deltacom.app.repository.api.ContractRepository;
import com.deltacom.app.repository.api.TariffRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ContractRepositoryTest {
    @Autowired
    ContractRepositoryImpl contractRepository;
    @Autowired
    TariffRepositoryImpl tariffRepository;

    @Test
    public void getAllClientContractsById() throws Exception {
        List<Contract> realContractList = contractRepository.getAllClientContractsById(5);
        List<Contract> noneContractList = contractRepository.getAllClientContractsById(999);

        assertNotNull(realContractList);
        assertEquals(realContractList.size(), 2);
        assertTrue(noneContractList.isEmpty());
    }

    @Test
    public void getContractByNumber() throws Exception {
        Contract realContract = contractRepository.getContractByNumber("89219999999");
        Contract noneContract = contractRepository.getContractByNumber("8945465164");

        assertNotNull(realContract);
        assertEquals(realContract.getClient().getFirstName(), "Даниил");
        assertNull(noneContract);
    }

    @Test
    public void getAllContractsByTariff() throws Exception {
        Tariff realTariff = tariffRepository.getById(2);
        assertNotNull(realTariff);

        List<Contract> contracts = contractRepository.getAllContractsByTariff(realTariff);
        assertNotNull(contracts);
        assertEquals(contracts.size(), 3);

        Tariff tariffWithoutContract = tariffRepository.getById(4);
        assertNotNull(tariffWithoutContract);

        List<Contract> emptyContractList = contractRepository.getAllContractsByTariff(tariffWithoutContract);
        assertTrue(emptyContractList.isEmpty());
    }

    @Test
    @Rollback
    public void addTest() {
        Client client = new Client();
        client.setId(5);
        Tariff tariff = new Tariff();
        tariff.setId(1);
        contractRepository.add(new Contract(client, new NumbersPool("89314523412", true), tariff, null));
        assertEquals(contractRepository.getAll().size(), 5);
    }

    @Test(expected = RepositoryException.class)
    @Rollback
    public void addExceptionTest() {
        contractRepository.add(new Contract());
    }

    @Test
    @Rollback
    public void updateTest() {
        Client client = new Client();
        client.setId(5);
        Tariff tariff = new Tariff();
        tariff.setId(1);
        Contract contract = new Contract(client, new NumbersPool("89314523412", true), tariff, null);
        contract.setId(21);
        contractRepository.update(contract);
    }

    @Test
    @Rollback
    public void removeTest() {
        Contract contractToRemove = new Contract();
        contractToRemove.setId(16);
        contractRepository.remove(contractToRemove);

        assertEquals(contractRepository.getById(16), null);
    }

    @Test
    public void getByIdTest() {
        assertEquals(contractRepository.getById(16).getNumbersPool().getNumber(), "89219999999");
    }

    @Test
    public void getAllTest() {
        assertEquals(contractRepository.getAll().size(), 4);
    }
}
