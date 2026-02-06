package com.ayduh.warehouse.service;

import com.ayduh.warehouse.model.Role;
import com.ayduh.warehouse.model.User;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private final List<User> users = new ArrayList<>();
    private User currentUser;

    public AuthService() {
        users.add(new User("admin", "admin", Role.ADMIN));
        users.add(new User("manager", "manager", Role.MANAGER));
        users.add(new User("user", "user", Role.USER));
    }

    public User login(String username, String password) {
        validateText(username, "Username");
        validateText(password, "Password");

        User found = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        if (!found.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }

        currentUser = found;
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void validateText(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty");
        }
    }
}
