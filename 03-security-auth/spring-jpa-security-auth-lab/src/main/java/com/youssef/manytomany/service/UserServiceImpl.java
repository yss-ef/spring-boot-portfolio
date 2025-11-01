package com.youssef.manytomany.service;

import com.youssef.manytomany.entities.Role;
import com.youssef.manytomany.entities.User;
import com.youssef.manytomany.repositories.RoleRepository;
import com.youssef.manytomany.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public List<User> findUserByRole(Role role) {
        return null ;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public List<Role> findRoleByUsername(String username) {
        return List.of();
    }

    @Override
    public void addRoleToUser(Role role, User user) {
        User u = userRepository.findByUserName(user.getUserName());
        Role r = roleRepository.findByRoleName(role.getRoleName());
        if (u.getRoles() != null){
            u.getRoles().add(r);
            r.getUsers().add(u);
        }
        //userRepository.save(u); --> pqs besoin car transactional
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
