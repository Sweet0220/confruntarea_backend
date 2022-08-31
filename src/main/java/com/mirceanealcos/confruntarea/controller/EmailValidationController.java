package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.service.MailService;
import com.mirceanealcos.confruntarea.service.ResetPasswordTokenService;
import com.mirceanealcos.confruntarea.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/email-validation")
@Slf4j
public class EmailValidationController {

    private final UserService userService;
    private final ResetPasswordTokenService tokenService;
    private final MailService mailService;

    @Autowired
    public EmailValidationController(UserService userService, ResetPasswordTokenService tokenService, MailService mailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @GetMapping
    public ResponseEntity<String> validateAccount(@RequestParam("token") String token, @RequestParam("username") String username) {
        try {
            tokenService.validateAccount(token, username);
            return new ResponseEntity<>("Account validated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
