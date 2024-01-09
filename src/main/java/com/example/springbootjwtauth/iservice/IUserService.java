package com.example.springbootjwtauth.iservice;

import com.example.springbootjwtauth.payload.request.UserRequest;

public interface IUserService {
    UserRequest updateUser (UserRequest userRequest);
    void deleteUser (String idUser);
}
