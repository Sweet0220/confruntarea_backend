package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.dto.UserLoginDTO;
import com.mirceanealcos.confruntarea.service.MailService;
import com.mirceanealcos.confruntarea.service.ResetPasswordTokenService;
import com.mirceanealcos.confruntarea.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(path = "/reset-password")
@Transactional
@Slf4j
public class ResetPasswordController {

    private final UserService userService;
    private final ResetPasswordTokenService tokenService;
    private final MailService mailService;

    @Autowired
    public ResetPasswordController(UserService userService, ResetPasswordTokenService tokenService, MailService mailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @GetMapping(path = "/for/{username}")
    public ResponseEntity<String> checkUser(@PathVariable("username") String username) {
        try {
            if(userService.userExists(username)) {
                tokenService.generateToken(userService.getUserEntityByUsername(username));
                mailService.sendResetPasswordLink(username);
                return new ResponseEntity<>("Mail sent successfully!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User does not exist!", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token, @RequestParam("username") String username) {
        try{
            tokenService.validateToken(token, username);
            return new ResponseEntity<>("Token is valid!", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/change")
    public ResponseEntity<String> changePassword(@RequestBody UserLoginDTO credentials, @RequestParam("token") String token) {
        try {
            tokenService.changePassword(credentials, token);
            return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
