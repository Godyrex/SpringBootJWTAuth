package com.example.springbootjwtauth.controller;

import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.iservice.IUserService;
import com.example.springbootjwtauth.payload.JwtResponse;
import com.example.springbootjwtauth.payload.SignupResposne;
import com.example.springbootjwtauth.payload.request.LoginRequest;
import com.example.springbootjwtauth.payload.request.SignupRequest;
import com.example.springbootjwtauth.repository.UserRepository;
import com.example.springbootjwtauth.security.jwt.JWTUtils;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.example.springbootjwtauth.entity.Role.USER;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders ="*" )
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JWTUtils jwtUtils;
    private final IUserService userService;
    @PostMapping("/signing")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getName(),
                userDetails.getLastname(),
                userDetails.isVerified(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResposne> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResposne("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResposne("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(true);
        user.setVerified(false);
        user.getRole().add(USER);
        userService.sendVerificationEmail(user,"localhost:8080");
        userRepository.save(user);

        return ResponseEntity.ok(new SignupResposne("User Created"));
    }
}
