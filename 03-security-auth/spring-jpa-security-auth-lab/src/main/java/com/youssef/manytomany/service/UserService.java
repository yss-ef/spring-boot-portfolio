package com.youssef.manytomany.service;

import com.youssef.manytomany.entities.Role;
import com.youssef.manytomany.entities.User;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    User findUserByName(String name);
    List<User> findUserByRole(Role role);
    Role findRoleByName(String name);
    List<Role> findRoleByUsername(String username);
    List<User> findAllUsers();
    List<Role> findAllRoles();
    void addRoleToUser(Role role, User user);

}
