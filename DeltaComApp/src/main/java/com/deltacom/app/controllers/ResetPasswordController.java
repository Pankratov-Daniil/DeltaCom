package com.deltacom.app.controllers;

import com.deltacom.app.entities.Client;
import com.deltacom.app.services.api.ClientService;
import com.deltacom.app.services.api.MessageSenderService;
import com.deltacom.app.utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for resetting password
 */
@Controller
public class ResetPasswordController {
    @Autowired
    ClientService clientService;
    @Autowired
    MessageSenderService messageSenderService;

    /**
     * Processing request to forgot password page
     * @param token unique token
     * @return redirect to login or to forgot password page
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword(@RequestParam String token) {
        if(token == null || token.isEmpty() || clientService.getClientByForgottenPassToken(token) == null) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView("setNewPassword");
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    /**
     * Processing request to reset password
     * @param password new password
     * @param token unique token
     * @return redirect to login page
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestParam String password, @RequestParam String token) {
        if(password == null || password.length() < 6) {
            return ResponseEntity.unprocessableEntity().body("Password must contain more than 6 symbols.");
        } else if (token == null || token.isEmpty()) {
            return ResponseEntity.unprocessableEntity().body("Token error.");
        }
        Client client = clientService.getClientByForgottenPassToken(token);
        if(client == null) {
            return ResponseEntity.unprocessableEntity().body("Your token wasn't found.");
        }
        client.setActivated(true);
        client.setPassword(PasswordEncrypter.encryptPassword(password));
        client.setForgottenPassToken(null);
        clientService.updateClient(client);
        return ResponseEntity.ok().header("Location", "/").body("OK");
    }

    /**
     * Processing request to send reset password link
     * @param email entered email
     * @return redirect to login
     */
    @ResponseBody
    @RequestMapping(value = "/sendResetPasswordLink", method = RequestMethod.POST)
    public ResponseEntity<String> sendResetPasswordLink(@RequestParam String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Matcher mat = pattern.matcher(email);
        if(mat.matches()) {
            messageSenderService.sendResetPasswordEmail(email);
            return ResponseEntity.ok().body("OK");
        } else {
            return ResponseEntity.unprocessableEntity().body("Wrong email!");
        }
    }
}
