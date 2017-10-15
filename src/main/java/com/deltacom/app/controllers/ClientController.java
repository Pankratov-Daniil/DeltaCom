package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ContractService;
import com.deltacom.app.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 * Controller for processing requests from/to user pages
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
    @Autowired
    ContractService contractService;
    @Autowired
    TariffService tariffService;

    /**
     * Processing request to client index page
     * @return client index page
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("user/index");
    }

    /**
     * Processing request to client contract page.
     * Transferring client contracts to the model.
     * @return client contracts page
     */
    @RequestMapping(value = "/contracts")
    public ModelAndView contracts() {
        return new ModelAndView("user/contracts");
    }

    /**
     * Processing ajax request from client contracts page to get client.
     * @param principal security principal to get email
     * @return list of clients
     */
    @ResponseBody
    @RequestMapping(value = "/getCurrentClient", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getCurrentClient(Principal principal) {
        return clientService.getClientByEmail(principal.getName());
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

        return new ModelAndView("redirect:/user/contracts");
    }

    /**
     * Processing ajax request from client contracts page to block or unblock contract.
     * @param contractId contract id
     * @param blockContract true if need to block, false otherwise
     */
    @ResponseBody
    @RequestMapping(value = "/blockContract", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean blockContract(@RequestParam("contractId") int contractId,
                                 @RequestParam("block") boolean blockContract,
                                 @RequestParam("blockedByOperator") boolean blockedByOperator) {
        if(blockedByOperator) {
            return false;
        }
        contractService.blockContract(contractId, blockContract, blockedByOperator);
        return true;
    }
}
