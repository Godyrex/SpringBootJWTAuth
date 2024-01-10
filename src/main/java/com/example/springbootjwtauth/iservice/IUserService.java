package com.example.springbootjwtauth.iservice;

import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.payload.request.UserRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IUserService {
    UserRequest updateUser (UserRequest userRequest);
    void deleteUser (String idUser);
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
    boolean verify(String verificationCode);
}
