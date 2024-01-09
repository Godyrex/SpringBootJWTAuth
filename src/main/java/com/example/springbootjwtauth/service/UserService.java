package com.example.springbootjwtauth.service;

import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.exceptions.UserNotFoundException;
import com.example.springbootjwtauth.iservice.IUserService;
import com.example.springbootjwtauth.payload.request.UserRequest;
import com.example.springbootjwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService  implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Patcher patcher;
    @Override
    public UserRequest updateUser (UserRequest userRequest){
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
            incompleteUser.setPassword("******");
            return incompleteUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return incompleteUser;
    }

    @Override
    public void deleteUser(String idUser) {
        if(userRepository.existsById(idUser)){
            userRepository.deleteById(idUser);
        }else{
            throw new UserNotFoundException("User doesn't exist!");
        }
    }
}
