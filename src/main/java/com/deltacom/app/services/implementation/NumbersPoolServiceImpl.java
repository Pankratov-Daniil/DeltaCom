package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.repository.implementation.NumbersPoolRepositoryImpl;
import com.deltacom.app.services.api.NumbersPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("NumbersPoolService")
public class NumbersPoolServiceImpl implements NumbersPoolService {
    @Autowired
    private NumbersPoolRepositoryImpl numbersPoolRepository;

    /**
     * Creates new NumbersPool entity in database.
     * @param entity NumbersPool entity to be created
     */
    @Override
    @Transactional
    public void create(NumbersPool entity) {
        numbersPoolRepository.add(entity);
    }

    /**
     * Updates NumbersPool entity in database.
     * @param entity NumbersPool entity to be updated
     */
    @Override
    @Transactional
    public void update(NumbersPool entity) {
        numbersPoolRepository.update(entity);
    }

    /**
     * Deletes NumbersPool entity in database.
     * @param entity NumbersPool entity to be deleted
     */
    @Override
    @Transactional
    public void delete(NumbersPool entity) {
        numbersPoolRepository.remove(entity);
    }

    /**
     * Gets NumbersPool entity by its id from database.
     * @param id id of NumbersPool entity to be found
     * @return founded NumbersPool entity
     */
    @Override
    @Transactional
    public NumbersPool getById(String id) {
        return (NumbersPool) numbersPoolRepository.getById(id);
    }

    /**
     * Gets all NumbersPool entities from database.
     * @return List of NumbersPool entities from database
     */
    @Override
    @Transactional
    public List<NumbersPool> getAll() {
        return numbersPoolRepository.getAll();
    }

    @Override
    @Transactional
    public List<String> getAllUnusedNumbers() {
        return numbersPoolRepository.getAllUnusedNumbers();
    }
}
