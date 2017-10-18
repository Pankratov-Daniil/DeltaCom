package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.entities.Option;
import com.deltacom.app.entities.Tariff;
import com.deltacom.app.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller for processing requests from/to manager pages
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends CommonController {
    @Autowired
    NumbersPoolService numbersPoolService;
    @Autowired
    ContractService contractService;

    private static final String CLIENT_ID_NAME = "clientId";

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
     * Processing request to managers 'add new contract' page
     * @param session current session
     * @return 'add new contract' page or 'index' page if there is no client in session
     */
    @RequestMapping(value = "/addNewContract")
    public ModelAndView addNewContract(HttpSession session, RedirectAttributes ra) {
        if(session.getAttribute(CLIENT_ID_NAME) == null) {
            return new ModelAndView("redirect:/manager/index");
        }
        ModelAndView modelAndView = new ModelAndView("manager/addNewContract");

        // pass to model unused numbers and available tariffs
        modelAndView.addObject("unusedNumbers", numbersPoolService.getAllUnusedNumbers());
        modelAndView.addObject("availableTariffs", tariffService.getAllTariffs());

        return modelAndView;
    }

    /**
     * Processing request to 'register new contract' and register new client
     * @param session current session
     */
    @RequestMapping(value = "/regNewContract")
    public ModelAndView regNewContract(@RequestParam("selectNumber") String selectedNumber,
                                       @RequestParam("selectTariff") String selectedTariff,
                                       @RequestParam("selectOptions") String[] selectedOptions,
                                       HttpSession session,
                                       RedirectAttributes ra) {
        int clientId = (int)session.getAttribute(CLIENT_ID_NAME);
        if(contractService.addNewContract(clientId, selectedNumber, Integer.parseInt(selectedTariff), selectedOptions)) {
            session.setAttribute("successContractCreation", true);
        }
        return new ModelAndView("redirect:/manager/browseAllClients");
    }

    /**
     * Changes contract
     * @param selectedNumber selected number
     * @param selectedTariffId selected tariff id
     * @param selectedOptionsIds selected options ids
     * @return redirect to previous page
     */
    @RequestMapping(value = "/changeContract")
    public ModelAndView changeContract(@RequestParam("numberModal") String selectedNumber,
                                       @RequestParam("selectTariff") String selectedTariffId,
                                       @RequestParam("selectOptions") String[] selectedOptionsIds,
                                       RedirectAttributes ra) {
        contractService.updateContract(selectedNumber, selectedTariffId, selectedOptionsIds);

        return new ModelAndView("redirect:/manager/browseAllClients");
    }

    /**
     * Removes contract
     * @param contractId id of contract to be removed
     */
    @ResponseBody
    @RequestMapping(value = "/deleteContract")
    public void deleteContract(@RequestParam("contractId") int contractId) {
        contractService.deleteContract(contractId);
    }

    /**
     * Changes option
     * @param option option from page
     * @return redirect to previous page
     */
    @RequestMapping(value = "/changeOption")
    public ModelAndView changeOption(@ModelAttribute("newOption") Option option,
                                     @RequestParam(value = "incompatibleOptionsList", required = false) String[] incompatibleOptionsListIds,
                                     @RequestParam(value = "compatibleOptionsList", required = false) String[] compatibleOptionsIds,
                                     RedirectAttributes ra) {
        optionService.updateOption(option, incompatibleOptionsListIds, compatibleOptionsIds);
        return new ModelAndView("redirect:/manager/optionsActions");
    }

    /**
     * Creates new option
     * @param option option from page
     * @return redirect to previous page
     */
    @RequestMapping(value = "/createOption")
    public ModelAndView createOption(@ModelAttribute("newOption") Option option,
                                     @RequestParam(value = "incompatibleOptionsList", required = false) String[] incompatibleOptionsListIds,
                                     @RequestParam(value = "compatibleOptionsList", required = false) String[] compatibleOptionsIds,
                                     RedirectAttributes ra) {
        optionService.addOption(option, incompatibleOptionsListIds, compatibleOptionsIds);
        return new ModelAndView("redirect:/manager/optionsActions");
    }

    /**
     * Deletes option
     * @param optionId id of option to delete
     * @return redirect to previous page
     */
    @RequestMapping(value = "/deleteOption")
    public ModelAndView deleteOption(@RequestParam(value = "idDelOption") int optionId,
                                     RedirectAttributes ra) {
        optionService.deleteOption(optionId);
        return new ModelAndView("redirect:/manager/optionsActions");
    }

    /**
     * Creates tariff
     * @param tariff tariff without options
     * @param tariffOptionsId ids of tariff options
     * @return redirect to previous page
     */
    @RequestMapping(value = "/createTariff")
    public ModelAndView createTariff(@ModelAttribute("newTariff") Tariff tariff,
                                     @RequestParam(value = "tariffOptions") String[] tariffOptionsId,
                                     RedirectAttributes ra) {
        tariffService.addTariff(tariff, tariffOptionsId);
        return new ModelAndView("redirect:/manager/tariffsActions");
    }

    /**
     * Changes tariff
     * @param tariff tariff from page
     * @param tariffOptionsIds ids of selected options
     * @return redirect to previous page
     */
    @RequestMapping(value = "/changeTariff")
    public ModelAndView changeTariff(@ModelAttribute("newTariff") Tariff tariff,
                                     @RequestParam(value = "tariffOptions") String[] tariffOptionsIds,
                                     RedirectAttributes ra) {
        tariffService.updateTariff(tariff, tariffOptionsIds);
        return new ModelAndView("redirect:/manager/tariffsActions");
    }

    /**
     * Deletes tariff
     * @param tariffId id of tariff to delete
     * @return redirect to previous page
     */
    @RequestMapping(value = "/deleteTariff")
    public ModelAndView deleteTariff(@RequestParam(value = "idDelTariff") int tariffId,
                                     RedirectAttributes ra) {
        tariffService.deleteTariff(tariffId);
        return new ModelAndView("redirect:/manager/tariffsActions");
    }

    /**
     * Processing ajax request from 'browse all clients' page when open change tariff modal page.
     * @param number number of contract
     * @return list of options for contract
     */
    @ResponseBody
    @RequestMapping(value = "/getContractByNumber", produces=MediaType.APPLICATION_JSON_VALUE)
    public Contract getOptionsFromContractByNumber(@RequestParam("number") String number) {
        return contractService.getContractByNumber(number);
    }

    /**
     * Processing ajax request from 'browse all clients' page to get client by his number.
     * @param number number of client
     * @return list of clients
     */
    @ResponseBody
    @RequestMapping(value = "/searchClientByNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client searchClientByNumber(@RequestParam("number") String number) {
        return clientService.getClientByNumber(number);
    }

    /**
     * Processing ajax request from 'browse all clients' page to get clients for table.
     * @param startId id from which the countdown begins
     * @param countEntries how many clients need to be returned
     * @return list of clients
     */
    @ResponseBody
    @RequestMapping(value = "/getClientsForSummaryTable", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Client> getClientsForSummaryTable(@RequestParam("startId") int startId,
                                                  @RequestParam("countEntries") int countEntries) {
        return clientService.getClientsByIds(startId, countEntries);
    }

    /**
     * Processing ajax request from 'browse all clients' page to add new client id to session.
     * @param clientId client id
     * @param session current session
     */
    @RequestMapping(value = "/addNewClientIdToSession")
    public void addNewClientIdToSession(@RequestParam(CLIENT_ID_NAME) int clientId, HttpSession session) {
        session.setAttribute(CLIENT_ID_NAME, clientId);
    }

    /**
     * Processing ajax request from 'add new contract' page to remove client id from session.
     * @param session current session
     */
    @RequestMapping(value = "/removeClientIdFromSession")
    public void removeClientIdFromSession(HttpSession session) {
        session.removeAttribute(CLIENT_ID_NAME);
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
