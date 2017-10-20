package com.deltacom.app.services.implementation;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.NumbersPool;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.exceptions.ContractException;
import com.deltacom.app.services.api.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-test.xml")
public class ContractServiceTest {
    @Autowired
    ContractService contractService;

    @Test
    public void getAllClientContractsByEmail() throws Exception {
        assertEquals(contractService.getAllClientContractsByEmail("mobigod0@gmail.com").size(), 2);
    }

    @Test(expected = ContractException.class)
    public void getAllClientContractsByEmailExceptionTest() {
        contractService.getAllClientContractsByEmail("abra@cadabra.com");
    }

    @Test
    public void getAllContractsByTariff() throws Exception {
        Tariff tariff = new Tariff();
        tariff.setId(2);
        assertEquals(contractService.getAllContractsByTariff(tariff).size(), 3);

        tariff.setId(999);
        assertEquals(contractService.getAllContractsByTariff(tariff).size(), 0);
    }

    @Test
    public void getContractByNumber() throws Exception {
        assertEquals(contractService.getContractByNumber("89222222222").getId(), 21);
        assertEquals(contractService.getContractByNumber("48239047"), null);
    }

    @Test
    public void getContractById() throws Exception {
        assertEquals(contractService.getContractById(21).getNumbersPool().getNumber(), "89222222222");
        assertEquals(contractService.getContractById(9999), null);
    }

    @Test
    @Rollback
    public void addNewContract() throws Exception {
        String[] optionsIds = new String[] {"1"};
        contractService.addNewContract(5, "89314523412", 1, optionsIds);
        assertEquals(contractService.getContractByNumber("89314523412").getNumbersPool().isUsed(), true);
    }

    @Test
    @Rollback
    public void addNewContractFailsOptionsCheck() throws Exception {
        String[] optionsIds = new String[] {"1", "2", "3"};
        assertEquals(contractService.addNewContract(5, "89314523412", 1, optionsIds), false);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void addNewContractTariffNullException() throws Exception {
        contractService.addNewContract(5, "89314523412", 879, new String[0]);
        assertEquals(contractService.getContractByNumber("89314523412").getNumbersPool().isUsed(), true);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void addNewContractClientNullException() throws Exception {
        contractService.addNewContract(999, "89314523412", 1, new String[0]);
        assertEquals(contractService.getContractByNumber("89314523412").getNumbersPool().isUsed(), true);
    }

    @Test
    @Rollback
    public void blockContract() throws Exception {
        contractService.blockContract(21, true, false);
        assertEquals(contractService.getContractById(21).isBlocked(), true);
        assertEquals(contractService.getContractById(21).isBlockedByOperator(), false);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void blockContractExceptionTest() throws Exception {
        contractService.blockContract(999, false, false);
    }

    @Test
    @Rollback
    public void updateContract() throws Exception {
        contractService.updateContract("89222222222", "2", new String[0]);

        assertEquals(contractService.getContractByNumber("89222222222").getTariff().getId(), 2);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void updateContractContractNullException() throws Exception {
        contractService.updateContract("78498515651", "2", new String[0]);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void updateContractTariffNullException() throws Exception {
        contractService.updateContract("89222222222", "9584", new String[0]);
    }

    @Test
    @Rollback
    public void deleteContract() throws Exception {
        Contract contract = new Contract();
        contract.setId(21);
        contractService.deleteContract(21);
        assertEquals(contractService.getContractById(21), null);
    }

    @Test(expected = ContractException.class)
    @Rollback
    public void deleteContractExceptionTest() {
        contractService.deleteContract(21467);
    }
}