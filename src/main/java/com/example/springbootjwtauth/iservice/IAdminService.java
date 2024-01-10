package com.example.springbootjwtauth.iservice;

import com.example.springbootjwtauth.entity.Role;
import com.example.springbootjwtauth.entity.User;

import java.util.List;

public interface IAdminService {
    List<User> getAllUsers();
    void addRole(String idUser, Role role);
    void removeRole(String idUser, Role role);
    void banUser(String idUser);
    void unbanUser(String idUser);

}
