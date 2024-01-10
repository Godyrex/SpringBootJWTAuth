package com.example.springbootjwtauth.service;

import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.exceptions.UserNotFoundException;
import com.example.springbootjwtauth.iservice.IUserService;
import com.example.springbootjwtauth.payload.request.UserRequest;
import com.example.springbootjwtauth.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class UserService  implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Patcher patcher;
    private final JavaMailSender mailSender;
    @Override
    public User updateUser (UserRequest userRequest){
        User existingUser = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        UserRequest incompleteUser = UserRequest.build(existingUser);
        try {
            patcher.userPatcher(incompleteUser,userRequest);
            existingUser.setEmail(incompleteUser.getEmail());
            existingUser.setPassword(encoder.encode(incompleteUser.getPassword()));
            existingUser.setName(incompleteUser.getName());
            existingUser.setLastname(incompleteUser.getLastname());

            userRepository.save(existingUser);
            return existingUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return existingUser;
    }

    @Override
    public void deleteUser(String idUser) {
        if(userRepository.existsById(idUser)){
            userRepository.deleteById(idUser);
        }else{
            throw new UserNotFoundException("User doesn't exist!");
        }
    }
    @Override
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "noreply@oussemaouakad.com";
        String senderName = "noreply";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "SpringBootJWTAuth.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
            content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findUserByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerified(true);
            userRepository.save(user);

            return true;
        }

    }
}
