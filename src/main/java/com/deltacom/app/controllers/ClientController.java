package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientCart;
import com.deltacom.app.entities.Contract;
import com.deltacom.app.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Controller for processing requests from/to user pages
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
    @Autowired
    ContractService contractService;

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
     * @return client
     */
    @ResponseBody
    @RequestMapping(value = "/getCurrentClient", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getCurrentClient(Principal principal) {
        return clientService.getClientByEmail(principal.getName());
    }

    /**
     * Gets contract by its number
     * @param number number of contract
     * @return found contract
     */
    @ResponseBody
    @RequestMapping(value = "/getContractByNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contract getContractByNumber(@RequestParam("number") String number) {
        return contractService.getContractByNumber(number);
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

    /**
     * Saves cart to session
     * @param number contract number
     * @param tariffId selected tariff id
     * @param optionsIds selected options ids
     * @param session current session
     */
    @ResponseBody
    @RequestMapping(value = "/saveCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientCart saveCart(@RequestParam("numberModal") String number,
                                     @RequestParam("selectTariff")  String tariffId,
                                     @RequestParam(value = "selectOptions[]", required = false) String[] optionsIds,
                                     HttpSession session) {
        ClientCart clientCart = new ClientCart(number, tariffId, optionsIds == null ? new String[0] : optionsIds);
        session.setAttribute("cart", clientCart);
        return clientCart;
    }

    /**
     * Processing ajax request from client contracts page to get cart from session.
     * @param session current session
     * @return clients cart
     */
    @ResponseBody
    @RequestMapping(value = "/getCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientCart getCart(HttpSession session) {
        Object cart = session.getAttribute("cart");
        return (ClientCart) (cart == null ? new ClientCart() : cart);
    }

    /**
     * Processing ajax request from client contracts page to remove cart from session.
     * @param session current session
     */
    @ResponseBody
    @RequestMapping(value = "/removeCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeCart(HttpSession session) {
        session.removeAttribute("cart");
    }
}
