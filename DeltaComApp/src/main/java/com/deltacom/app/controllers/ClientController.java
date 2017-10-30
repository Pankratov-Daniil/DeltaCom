package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientCart;
import com.deltacom.app.exceptions.ClientException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Controller for processing requests from/to user pages
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
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
    @RequestMapping(value = "/getCurrentClient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getCurrentClient(Principal principal) {
        return clientService.getClientByEmail(principal.getName());
    }

    /**
     * Processing ajax request from client contracts page to block or unblock contract.
     * @param contractId contract id
     * @param blockContract true if need to block, false otherwise
     */
    @ResponseBody
    @RequestMapping(value = "/blockContract", method = RequestMethod.POST)
    public void blockContract(@RequestParam("contractId") int contractId,
                                 @RequestParam("block") boolean blockContract,
                                 @RequestParam("blockedByOperator") boolean blockedByOperator) {
        if(blockedByOperator) {
            throw new ClientException("Client can't block contract by operator", new Exception());
        }
        contractService.blockContract(contractId, blockContract, blockedByOperator);
    }

    /**
     * Saves cart to session
     * @param session current session
     */
    @ResponseBody
    @RequestMapping(value = "/saveCart", method = RequestMethod.POST)
    public void saveCart(@RequestBody ClientCart clientCart, HttpSession session) {
        session.setAttribute("cart", clientCart);
    }

    /**
     * Processing ajax request from client contracts page to get cart from session.
     * @param session current session
     * @return clients cart
     */
    @ResponseBody
    @RequestMapping(value = "/getCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientCart getCart(HttpSession session) {
        Object cart = session.getAttribute("cart");
        return (ClientCart) (cart == null ? new ClientCart() : cart);
    }

    /**
     * Processing ajax request from client contracts page to remove cart from session.
     * @param session current session
     */
    @ResponseBody
    @RequestMapping(value = "/removeCart", method = RequestMethod.POST)
    public void removeCart(HttpSession session) {
        session.removeAttribute("cart");
    }
}