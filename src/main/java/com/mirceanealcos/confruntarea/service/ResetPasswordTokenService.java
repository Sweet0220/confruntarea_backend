package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.UserLoginDTO;
import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.repository.ResetPasswordTokenRepository;
import com.mirceanealcos.confruntarea.repository.UserRepository;
import com.mirceanealcos.confruntarea.security.model.ResetPasswordToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordTokenService(ResetPasswordTokenRepository tokenRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void generateToken(User user, long duration) {
        if(tokenRepository.getByUserUsername(user.getUsername()) != null) {
            tokenRepository.deleteByUserUsername(user.getUsername());
        }
        String token = UUID.randomUUID().toString();
        ResetPasswordToken newToken = new ResetPasswordToken();
        newToken.setUser(user);
        newToken.setToken(token);
        Date currentTime = new Date();
        Date expiryDate = new Date(currentTime.getTime() + duration);
        newToken.setExpiryDate(expiryDate);
        tokenRepository.save(newToken);
    }

    public boolean validateToken(String UUID, String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new Exception("Username invalid!");
        }

        ResetPasswordToken token = tokenRepository.getByUserUsername(username);

        if(token == null) {
            throw new Exception("Token does not exist!");
        }

        if(!token.getToken().equals(UUID)) {
            throw new Exception("Token invalid!");
        }

        Calendar cal = Calendar.getInstance();
        if(token.getExpiryDate().before(cal.getTime())) {
            throw new Exception("Token expired!");
        }
        return true;
    }

    public void changePassword(UserLoginDTO credentials, String token) throws Exception {
        validateToken(token, credentials.getUsername());
        User user = userRepository.findByUsername(credentials.getUsername());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        userRepository.save(user);
        ResetPasswordToken resetToken = tokenRepository.getByUserUsername(credentials.getUsername());
        tokenRepository.delete(resetToken);
    }

    public void validateAccount(String token, String username) throws Exception{
        try {
            validateToken(token, username);
            User user = userRepository.findByUsername(username);
            user.setEnabled(true);
            userRepository.save(user);
            ResetPasswordToken tok = tokenRepository.getByUserUsername(username);
            tokenRepository.delete(tok);
        } catch (Exception e) {
            if(e.getMessage().equals("Token expired!")) {
                ResetPasswordToken tok = tokenRepository.getByUserUsername(username);
                tokenRepository.delete(tok);
                User user = userRepository.findByUsername(username);
                userRepository.delete(user);
            }
            throw e;
        }
    }

}
