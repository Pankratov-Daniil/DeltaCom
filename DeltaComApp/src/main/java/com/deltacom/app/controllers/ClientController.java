package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.entities.ClientCart;
import com.deltacom.app.entities.ClientLocation;
import com.deltacom.app.exceptions.ClientException;
import com.deltacom.app.services.api.ClientLocationService;
import com.deltacom.app.services.api.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * Controller for processing requests from/to user pages
 */
@Controller
@RequestMapping(value = "/user")
public class ClientController extends CommonController {
    @Autowired
    private ClientLocationService clientLocationService;
    @Autowired
    MessageSenderService messageSenderService;

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
     * Processing request to client settings page
     * @return client settings page
     */
    @RequestMapping(value = "/settings")
    public ModelAndView settings() {
        return new ModelAndView("user/settings");
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

    /**
     * Processing ajax request from client settings page to get current client locations
     * @return list of client locations
     */
    @ResponseBody
    @RequestMapping(value = "/getLocations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientLocation> getLocations(Principal principal) {
        Client client = clientService.getClientByEmail(principal.getName());
        return clientLocationService.getClientLocationsByClientId(client.getId());
    }

    /**
     * Processing ajax request from client settings page to change password
     * @param oldPassword old password
     * @param newPassword new password
     * @param confirmPassword confirmation for new password
     */
    @ResponseBody
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                 @RequestParam String confirmPassword, Principal principal) {
        if(!newPassword.equals(confirmPassword)) {
            return "Passwords not equal.";
        }
        if(oldPassword.equals(newPassword)) {
            return "";
        }

        Client client = clientService.getClientByEmail(principal.getName());
        try {
            clientService.changePassword(client, oldPassword, newPassword);
        } catch (ClientException ex) {
            return ex.getMessage();
        }
        return "";
    }

    /**
     * Processing ajax request from client settings page to confirm number for two factor authorization
     * @param number client number
     * @return "" if ok, "time" if sms was send less than 5 minutes ago, "Error" if there was error with adding sms code to DB
     */
    @ResponseBody
    @RequestMapping(value = "/confirmNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String confirmNumber(@RequestParam String number,
                                Principal principal) {
        return clientService.confirmNumberFor2FA(principal.getName(), number);
    }

    /**
     * Processing ajax request from client settings page to change status of two factor authorization
     * @param number client number for two factor auth
     */
    @ResponseBody
    @RequestMapping(value = "/changeTwoFactorAuth", method = RequestMethod.POST)
    public void changeTwoFactorAuth(@RequestParam String number,
                                    @RequestParam String smsCode,
                                    Principal principal) {
        clientService.updateTwoFactorAuth(principal.getName(), number, smsCode);
    }

    /**
     * Processing ajax request from client settings page to get client contract numbers
     * @return list of client contracts numbers or null
     */
    @ResponseBody
    @RequestMapping(value = "/getClientNumbers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getClientNumbers(Principal principal) {
        return clientService.getClientNumbers(principal.getName());
    }

    /**
     * Processing ajax request from client settings page to get client 2FA status
     * @return number witch used for 2FA or empty string
     */
    @ResponseBody
    @RequestMapping(value = "/getTwoFactorAuthStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTwoFactorAuthStatus(Principal principal) {
        return clientService.getTwoFactorAuthStatus(principal.getName());
    }
}
