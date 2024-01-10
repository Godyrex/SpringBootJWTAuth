package com.example.springbootjwtauth.controller;

import com.example.springbootjwtauth.entity.Role;
import com.example.springbootjwtauth.entity.User;
import com.example.springbootjwtauth.iservice.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders ="*" )
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IAdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
    }
    @GetMapping("/add/{idUser}/{role}")
    public ResponseEntity<String> addRole(@PathVariable String idUser,@PathVariable Role role) {

        adminService.addRole(idUser,role);
        return ResponseEntity.ok("Role Added!");
    }
    @GetMapping("/remove/{idUser}/{role}")
    public ResponseEntity<String> removeRole(@PathVariable String idUser,@PathVariable Role role) {

        adminService.removeRole(idUser,role);
        return ResponseEntity.ok("Role Removed!");
    }
    @GetMapping("/ban/{idUser}")
    public ResponseEntity<String> banUser(@PathVariable String idUser) {

        adminService.banUser(idUser);
        return ResponseEntity.ok("User Banned!");
    }
    @GetMapping("/unban/{idUser}")
    public ResponseEntity<String> unbanUser(@PathVariable String idUser) {

        adminService.unbanUser(idUser);
        return ResponseEntity.ok("User Unbanned!");
    }
}
