package com.facelink.service;

import com.facelink.dto.response.Accuracy;
import com.facelink.entity.*;
import com.facelink.repository.AccountDetailRepository;
import com.facelink.repository.AccountInfoRepository;
import com.facelink.repository.AccountRepository;
import com.facelink.repository.UserRoleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthenticationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDetailRepository accountDetailRepository;
    @Autowired
    private AccountInfoRepository accountInfoRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public Account findAccountById(Long id) {
        return this.accountRepository.findById(id).orElse(null);
    }

    @Transactional
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = this.accountRepository.save(account);
        AccountInfo accountInfo = AccountInfo.builder()
                .account(savedAccount)
                .firstName(account.getAccountInfo().getFirstName().trim())
                .lastName(account.getAccountInfo().getLastName().trim())
                .fullName(account.getAccountInfo().getLastName().trim() + " " + account.getAccountInfo().getFirstName().trim())
                .dateOfBirth(account.getAccountInfo().getDateOfBirth())
                .gender(account.getAccountInfo().getGender())
                .build();
        AccountDetail accountDetail = AccountDetail.builder()
                .account(savedAccount)
                .followers(0)
                .following(0)
                .build();
        this.accountDetailRepository.save(accountDetail);
        this.accountInfoRepository.save(accountInfo);
        savedAccount.setAccountInfo(accountInfo);
        savedAccount.setAccountDetails(accountDetail);
        this.userRoleRepository.save(UserRole.builder().role(Role.builder().id(2).build()).account(savedAccount).build());
        savedAccount = this.accountRepository.save(savedAccount);
        return savedAccount;
    }

    public Boolean accountLocked(Long id) {
        return this.accountRepository.isAccountLocked(id);
    }


    public Accuracy checkEmail(String email) {
        var account = this.accountRepository.findByEmail(email);
        if (account.isPresent()) {
            var a = account.get();
            return Accuracy.builder()
                    .email(a.getEmail())
                    .isEnabled(a.getIsEnabled())
                    .isLocked(a.getIsLocked())
                    .build();
        } else {
            return null;
        }
    }

    public void updatePassword(String email, String password) {
        this.accountRepository.updatePassword(passwordEncoder.encode(password), email);
    }

    public void sendMail(String to, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Facelink User Support Center");
        String htmlContent = "<html>" +
                "<body style='font-family:Arial,sans-serif;color:#333;'>" +
                "<p>Hey Dev! 👋</p>" +
                "<p><strong style='color:#6a1b9a;'>Admin Of Facelink. </strong> We have received your request to forget your password, We have checked and verified that you are the account owner and have updated your new password.</p>" +
                "<p>Your new password is: " + password + "</p>" +
                "<hr style='border:none;border-top:1px solid #eee;'/>" +
                "<div style='text-align:center;margin-top:20px;'>" +
                "<img src=\"https://res.cloudinary.com/dswqplrdx/image/upload/v1723106379/llz0qdvbkccul0liqzy3.png\" alt=\"Image\" style='width:100%;max-width:600px;border-radius:10px;'/>" +
                "</div>" +
                "<h3 style='color:#f97316;'>🎉 Facelink User Support Center!</h3>" +
                "<p>Please verify here(Expires in 1 minute)!</p>" +
                "<p style='margin-top:30px;'><a href=\"\" style='background-color:#6a1b9a;color:#fff;padding:10px 20px;text-decoration:none;border-radius:5px;'>Verify</a></p>" +
                "</body>" +
                "</html>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public String generatorPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
