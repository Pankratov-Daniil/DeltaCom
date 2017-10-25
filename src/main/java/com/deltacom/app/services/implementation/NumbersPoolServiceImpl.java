package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.exceptions.NumbersPoolException;
import com.deltacom.app.repository.implementation.NumbersPoolRepositoryImpl;
import com.deltacom.app.services.api.NumbersPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Operations with repository for NumbersPool entities.
 */
@Service("NumbersPoolService")
public class NumbersPoolServiceImpl implements NumbersPoolService {
    @Autowired
    private NumbersPoolRepositoryImpl numbersPoolRepository;

    /**
     * Updates NumbersPool entity in database.
     * @param numbersPool NumbersPool entity to be updated
     */
    @Transactional
    public void updateNumbersPool(NumbersPool numbersPool) {
        try {
            if(numbersPool == null) {
                throw new PersistenceException("Numbers pool cannot be null.");
            }
            numbersPoolRepository.update(numbersPool);
        } catch (PersistenceException ex) {
            throw new NumbersPoolException("NumbersPool wasn't updated: ", ex);
        }
    }

    /**
     * Gets all unused numbers from database.
     * @return list of unused number
     */
    @Override
    @Transactional
    public List<String> getAllUnusedNumbers() {
        try {
            return numbersPoolRepository.getAllUnusedNumbers();
        } catch (PersistenceException ex) {
            throw new NumbersPoolException("NumbersPool wasn't gotten: ", ex);
        }
    }
}
