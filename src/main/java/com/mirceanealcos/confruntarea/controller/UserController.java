package com.mirceanealcos.confruntarea.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirceanealcos.confruntarea.dto.UserDTO;
import com.mirceanealcos.confruntarea.dto.UserLoginDTO;
import com.mirceanealcos.confruntarea.dto.UserRegisterDTO;
import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


/**
 * User controller with suggestive endpoints
 */
@RestController
@RequestMapping(path = "/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            if(users.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            if(user == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<List<UserDTO>> getUsersByEmail(@PathVariable("email") String email) {
        try {
            List<UserDTO> userDTOS = userService.getUsersByEmail(email);
            if(userDTOS.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/user-register")
    public ResponseEntity<String> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userService.registerUser(userRegisterDTO);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            if(e.getMessage().startsWith("could not execute statement; SQL [n/a]; constraint [user.username];")) {
                return new ResponseEntity<>("Username is already taken!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(e.getMessage().startsWith("Failed messages: javax.mail.SendFailedException: Invalid Addresses;")) {
                return new ResponseEntity<>("Invalid email!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method takes in a refresh token that is verified for validity from the request.
     * If the token is valid, a new access token is generated and returned via response to the user.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException because of the requirement to write a new token to the response via an object mapper, this exception can occur
     */
    @GetMapping(path="/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //to-do: make a jwt util class and move this functionality there
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                String secret = "v3RyS3cUr3K3yLm40";
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUserEntityByUsername(username);
                List<String> roles = new ArrayList<>();
                roles.add(user.getRole());
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000 ))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", roles)
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error(e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing!");
        }
    }

    @PutMapping(path = "/purchase/{username}/{amount}")
    public ResponseEntity<String> performPurchase(@PathVariable("username") String username, @PathVariable("amount") Integer amount) {
        try {
            userService.performPurchase(username, amount);
            return new ResponseEntity<>("Purchase successfully made!", OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(username, userDTO);
            return new ResponseEntity<>("User with username " + username + " updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{username}")
    public ResponseEntity<String> deleteUserById(@PathVariable("username") String username) {
        try {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>("User with username " + username + " deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/page/{page}")
    public ResponseEntity<List<UserDTO>> getUsersByPage(@PathVariable("page") Integer page) {
        try {
            List<UserDTO> dtos = userService.getUserPage(page);
            if(dtos.isEmpty()) {
                return new ResponseEntity<>(null, NO_CONTENT);
            }
            return new ResponseEntity<>(dtos, OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
        }
    }

}
