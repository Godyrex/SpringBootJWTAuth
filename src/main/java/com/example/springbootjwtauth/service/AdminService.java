package com.example.springbootjwtauth.service;

import com.example.springbootjwtauth.entity.Role;
import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.exceptions.RoleAlreadyAssignedException;
import com.example.springbootjwtauth.exceptions.UserBanException;
import com.example.springbootjwtauth.exceptions.UserNotFoundException;
import com.example.springbootjwtauth.exceptions.UserRoleNotFoundException;
import com.example.springbootjwtauth.iservice.IAdminService;
import com.example.springbootjwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addRole(String idUser, Role role) {
        User user = userRepository.findById(idUser)
                .orElseThrow(
                        ()-> new UserNotFoundException("User not found with id :"+ idUser));
        if(user.getRole().contains(role)){
            throw new RoleAlreadyAssignedException("Role "+role.name()+" already assigned to user "+idUser);
        }else{
            user.getRole().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRole(String idUser, Role role) {
        User user = userRepository.findById(idUser).orElseThrow(
                ()-> new UserNotFoundException("User not found with id :"+ idUser));
        if(user.getRole().contains(role)){
            user.getRole().remove(role);
            userRepository.save(user);
        }else{
            throw new UserRoleNotFoundException("Role "+role.name()+" not assigned to user "+idUser);

        }

    }

    @Override
    public void banUser(String idUser) {
        User user = userRepository.findById(idUser).orElseThrow(
                ()-> new UserNotFoundException("User not found with id :"+ idUser));
        if(user.isEnabled()){
            user.setEnabled(false);
            userRepository.save(user);
        }else{
            throw new UserBanException("User "+idUser +" already banned!");
        }
    }

    @Override
    public void unbanUser(String idUser) {
        User user = userRepository.findById(idUser).orElseThrow(
                ()-> new UserNotFoundException("User not found with id :"+ idUser));
        if(!user.isEnabled()){
            user.setEnabled(true);
            userRepository.save(user);
        }else{
            throw new UserBanException("User "+idUser +" already unbanned!");
        }

    }
}
