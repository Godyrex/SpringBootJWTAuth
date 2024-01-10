package com.example.springbootjwtauth.controller;

import com.example.springbootjwtauth.iservice.IUserService;
import com.example.springbootjwtauth.payload.request.UserRequest;
import com.example.springbootjwtauth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders ="*" )
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PatchMapping("/update")
    public ResponseEntity<UserRequest> updateUser(@Valid @RequestBody UserRequest userRequest) throws IllegalAccessException {
    if(Objects.equals(userRequest.getId(), AuthService.getCurrentUserDetails().getId())){
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }else{
        throw new IllegalAccessException("User "+AuthService.getCurrentUserDetails().getId()+" tried to update user "+userRequest.getId()+" information" );
    }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return  ResponseEntity.ok("User Deleted!");
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return ResponseEntity.ok("Account verified");
        } else {
            return ResponseEntity.badRequest().body("Account not verified");
        }
    }

}
