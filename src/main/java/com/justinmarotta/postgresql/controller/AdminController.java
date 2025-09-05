package com.justinmarotta.postgresql.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinmarotta.postgresql.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private IUserService userService;

    @GetMapping("all")
    public ResponseEntity<?> getAllUsers(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(userService.findAllUsers());
    }
    

}
