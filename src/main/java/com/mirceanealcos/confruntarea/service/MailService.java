package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.mail.EmailSenderService;
import com.mirceanealcos.confruntarea.repository.ResetPasswordTokenRepository;
import com.mirceanealcos.confruntarea.repository.UserRepository;
import com.mirceanealcos.confruntarea.security.model.ResetPasswordToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MailService {

    private final EmailSenderService senderService;
    private final UserRepository userRepository;
    private final ResetPasswordTokenRepository tokenRepository;

    @Autowired
    public MailService(EmailSenderService senderService, UserRepository userRepository, ResetPasswordTokenRepository tokenRepository) {
        this.senderService = senderService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public void sendUsernames(String email) throws Exception {
        List<User> users = this.userRepository.findByEmail(email);
        if(users.isEmpty()) {
            throw new Exception("No users with email " + email + " found..");
        }

        String subject = "Here are your usernames!";
        final String[] body = {"Hi there, I am Marcel from project_confruntarea and my mission is to deliver you your usernames that you forgot. =P" + '\n'};
        body[0] += "Here are all the usernames linked to your email address: " + '\n' + '\n';
        users.forEach(user -> {
            body[0] += user.getUsername() + '\n';
        });
        body[0] += "\n Don't forget that you can always use the forgot_username button to get this mail again.\n Have fun!";

        senderService.sendEmail(email, subject, body[0]);

        log.info("Email sent successfully to " + email);
    }

    public void sendResetPasswordLink(String username) {
        User user = userRepository.findByUsername(username);
        ResetPasswordToken token = tokenRepository.getByUserUsername(username);
        String subject = "Reset your password";
        String email = user.getEmail();
        String body = "Hi there, I am Marcel from project_confruntarea and I heard you wanna reset your password 'cause you forgot it lmao. \n";
        body += "The account username this request is dedicated to is: " + user.getUsername() + ".\n";
        body += "To reset your password, use the following link: http://localhost:4200/forgot-password/reset-form?token=" + token.getToken() + "&username=" + user.getUsername() + '\n';
        body += "The link is valid only for the next 5 minutes, so move quickly. =P\n";
        body += "If the request hasn't been made by you, please ignore this mail and contact our support team.\n";
        body += "Have fun!";

        senderService.sendEmail(email,subject,body);
        log.info("Reset password email successfully sent to " + email);
    }



}
