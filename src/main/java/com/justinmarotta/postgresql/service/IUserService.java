package com.justinmarotta.postgresql.service;

import java.util.List;

import com.justinmarotta.postgresql.model.Role;
import com.justinmarotta.postgresql.model.User;

public interface IUserService {


    public User findByUsername(String username);

    public User changeRole(Role newRole, String username);

    public User saveUser(User user);

    public List<User> findAllUsers();
}