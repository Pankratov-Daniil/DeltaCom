package com.deltacom.app.controllers;

import com.deltacom.app.entities.*;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller for processing requests from/to manager pages
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends CommonController {
    @Autowired
    NumbersPoolService numbersPoolService;

    /**
     * Processing request to manager 'index' page
     * @return index page
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("manager/index");
    }

    /**
     * Processing request to manager 'browse all clients' page
     * @return browse all clients page
     */
    @RequestMapping(value = "/browseAllClients")
    public ModelAndView browseAllClients() {
        return new ModelAndView("manager/browseAllClients");
    }

    /**
     * Processing request to manager 'tariffs actions' page
     * @return tariffs actions page
     */
    @RequestMapping(value = "/tariffsActions")
    public ModelAndView tariffsActions() {
        return new ModelAndView("manager/tariffsActions");
    }

    /**
     * Processing request to manager 'options actions' page
     * @return options actions page
     */
    @RequestMapping(value = "/optionsActions")
    public ModelAndView optionsActions() {
        return new ModelAndView("manager/optionsActions");
    }

    /**
     * Processing request to 'register new contract' and register new client
     */
    @ResponseBody
    @RequestMapping(value = "/regNewContract", method = RequestMethod.POST)
    public void regNewContract(@RequestBody ContractDTO contractDTO) {
        contractService.addNewContract(contractDTO.getClientId(), contractDTO.getNumber(),
                contractDTO.getTariffId(), contractDTO.getOptionsIds());
    }

    /**
     * Removes client
     * @param clientId id of client to be removed
     */
    @ResponseBody
    @RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
    public void deleteClient(@RequestParam int clientId) {
        clientService.removeClient(clientId);
    }

    /**
     * Gets all unused numbers
     * @return list of unused numbers
     */
    @ResponseBody
    @RequestMapping(value = "/getUnusedNumbers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getUnusedNumbers() {
        return numbersPoolService.getAllUnusedNumbers();
    }

    /**
     * Removes contract
     * @param contractId id of contract to be removed
     */
    @ResponseBody
    @RequestMapping(value = "/deleteContract", method = RequestMethod.POST)
    public void deleteContract(@RequestBody int contractId) {
        contractService.deleteContract(contractId);
    }

    /**
     * Changes option
     * @param optionDTO option from page
     */
    @RequestMapping(value = "/changeOption", method = RequestMethod.POST)
    public @ResponseBody void changeOption(@RequestBody OptionDTO optionDTO) {
        Option option = optionDTO.toOption();
        optionService.updateOption(option, optionDTO.getIncompatibleOptions(), optionDTO.getCompatibleOptions());
    }

    /**
     * Creates new option
     * @param optionDTO option from page
     */
    @RequestMapping(value = "/createOption", method = RequestMethod.POST)
    public @ResponseBody void createOption(@RequestBody OptionDTO optionDTO) {
        Option option = optionDTO.toOption();
        optionService.addOption(option, optionDTO.getIncompatibleOptions(), optionDTO.getCompatibleOptions());
    }

    /**
     * Deletes option
     * @param optionId id of option to delete
     */
    @RequestMapping(value = "/deleteOption", method = RequestMethod.POST)
    public @ResponseBody void deleteOption(@RequestBody int optionId) {
        optionService.deleteOption(optionId);
    }

    /**
     * Creates tariff
     * @param tariffDTO tariff without options
     */
    @ResponseBody
    @RequestMapping(value = "/createTariff", method = RequestMethod.POST)
    public void createTariff(@RequestBody TariffDTO tariffDTO) {
        Tariff tariff = tariffDTO.toTariff();
        tariffService.addTariff(tariff, tariffDTO.getOptionsIds());
    }

    /**
     * Changes tariff
     * @param tariffDTO tariff from page
     */
    @ResponseBody
    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public void changeTariff(@RequestBody TariffDTO tariffDTO) {
        Tariff tariff = tariffDTO.toTariff();
        tariffService.updateTariff(tariff, tariffDTO.getOptionsIds());
    }

    /**
     * Deletes tariff
     * @param tariffId id of tariff to delete
     */
    @ResponseBody
    @RequestMapping(value = "/deleteTariff", method = RequestMethod.POST)
    public void deleteTariff(@RequestBody int tariffId) {
        tariffService.deleteTariff(tariffId);
    }

    /**
     * Processing ajax request from 'browse all clients' page to get client by his number.
     * @param number number of client
     * @return list of clients
     */
    @ResponseBody
    @RequestMapping(value = "/searchClientByNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Client searchClientByNumber(@RequestParam String number) {
        return clientService.getClientByNumber(number);
    }

    @ResponseBody
    @RequestMapping(value = "/getClientIdByEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int getClientIdByEmail(@RequestParam String email) {
        return clientService.getClientByEmail(email).getId();
    }

    /**
     * Processing ajax request from 'browse all clients' page to get clients for table.
     * @param startIndex index from which the countdown begins
     * @param countEntries how many clients need to be returned
     * @return list of clients
     */
    @ResponseBody
    @RequestMapping(value = "/getClientsForSummaryTable", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Client> getClientsForSummaryTable(@RequestParam int startIndex,
                                                  @RequestParam int countEntries) {
        return clientService.getClientsFromIndex(startIndex, countEntries);
    }

    @ResponseBody
    @RequestMapping(value = "/getClientsCount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getClientsCount() {
        return clientService.getClientsCount();
    }

    /**
     * Processing ajax request from 'browse all clients' page to block or unblock contract.
     * @param contractId contract id
     * @param blockContract true if need to block, false otherwise
     */
    @ResponseBody
    @RequestMapping(value = "/blockContract", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean blockContract(@RequestParam("contractId") int contractId,
                              @RequestParam("block") boolean blockContract) {
        contractService.blockContract(contractId, blockContract, true);
        return true;
    }
}
