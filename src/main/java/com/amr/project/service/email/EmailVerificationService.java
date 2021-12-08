package com.amr.project.service.email;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.User;
import com.amr.project.util.EmailVerificationTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmailVerificationService {

    private UserDao userDao;
    private EmailSenderService senderService;

    @Transactional
    public String sendVerificationEmail(User createdUser) {
        createdUser.setActivationCode(EmailVerificationTokenGenerator.generate());
        Mail verificationMessage = new Mail();
        verificationMessage.setTo(createdUser.getEmail());
        verificationMessage.setMessage("<a href=http://localhost:8888/registrationConfirm?email=" +
                createdUser.getEmail() + "&token=" + createdUser.getActivationCode() +
                ">Verify email and activate account</a>");
        verificationMessage.setSubject("Verify new account with e-mail: " + createdUser.getEmail());
        userDao.update(createdUser);
        return senderService.sendEmailWithHTML(verificationMessage);
    }

    @Transactional
    public String activateUser(String email, String activationCode) {
        User checkedUser = userDao.findByEmail(email);
        String checkingResult = checkUser(checkedUser, activationCode);
        if (checkingResult.equals("Ok")) {
            checkedUser.setActivate(true);
            userDao.update(checkedUser);
            return "Email " + email + " successfully confirm!!! You can log in your account";
        }
        return checkingResult;
    }

    private String checkUser(User checkedUser, String activationCode) {
        if (checkedUser == null) {
            return "User not found!!!";
        }
        if (checkedUser.isActivate()) {
            return "Email already verified and account already activated!!!";
        }
        if (checkedUser.getActivationCode().equals(activationCode)) {
            return "Ok";
        } else {
            return "Activation code incorrect!!!";
        }
    }
}

