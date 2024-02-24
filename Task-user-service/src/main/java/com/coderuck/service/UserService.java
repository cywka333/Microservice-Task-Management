package com.coderuck.service;

import com.coderuck.model.User;

import java.util.List;

public interface UserService {

    public User getUserProfile(String jwt);

    public List<User> getAllUsers();
}
;