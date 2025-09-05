package com.justinmarotta.postgresql.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.justinmarotta.postgresql.model.Role;
import com.justinmarotta.postgresql.model.User;
import com.justinmarotta.postgresql.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController  {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.findByUsername(user.getUsername());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 
        catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().equals("User not found")) {
                userService.saveUser(user);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            } 
            else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginAndLogout(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("change/{role}")
    public ResponseEntity<?> changeRole(Principal principal, @PathVariable("role") Role role) {
        User user = userService.changeRole(role, principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
