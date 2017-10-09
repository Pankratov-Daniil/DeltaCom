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
     * Updates NumbersPool entity in database.
     * @param entity NumbersPool entity to be updated
     */
    @Transactional
    public void updateNumbersPool(NumbersPool entity) {
        numbersPoolRepository.update(entity);
    }

    /**
     * Gets all unused numbers from database.
     * @return list of unused number
     */
    @Override
    @Transactional
    public List<String> getAllUnusedNumbers() {
        return numbersPoolRepository.getAllUnusedNumbers();
    }
}
