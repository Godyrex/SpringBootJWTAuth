package com.example.springbootjwtauth.payload.request;

import com.example.springbootjwtauth.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String id;
    @Size(max = 50)
    @Email
    private String email;
    @Size(max = 120)
    private String password;
    @Size(max = 20)
    private String name;
    @Size(max = 20)
    private String lastname;
    public  static UserRequest build(User user){
    return new UserRequest(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getLastname()
    );
    }
}
