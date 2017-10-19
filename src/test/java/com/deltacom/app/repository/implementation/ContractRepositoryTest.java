package com.deltacom.app.repository.implementation;

import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Tariff;
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
    ContractRepository contractRepository;
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
}
