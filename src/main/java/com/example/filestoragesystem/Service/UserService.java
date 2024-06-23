package com.example.filestoragesystem.Service;

import com.example.filestoragesystem.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
