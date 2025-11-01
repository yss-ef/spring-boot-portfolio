package com.youssef.manytomany;

import com.youssef.manytomany.entities.Role;
import com.youssef.manytomany.entities.User;
import com.youssef.manytomany.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ManyToManyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManyToManyApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserService userService) {
        return args -> {
            List.of("user1", "user2", "user3", "user4", "user5", "user6").forEach(u -> {
                User user = new User();
                user.setUserName(u);
                user.setPassword(u+"1201");
                userService.addNewUser(user);
            });

            List.of("role1", "role2", "role3", "role4", "role5", "role6").forEach(r -> {
                Role role = new Role();
                role.setRoleName(r);
                role.setRoleDescription("role description : " + r);
                userService.addNewRole(role);
            });

            List<User> users = userService.findAllUsers();
            List<Role> roles = userService.findAllRoles();

            users.forEach(user -> {
                roles.forEach(role -> {
                    userService.addRoleToUser(role, user);
                });
            });
        };
    }
}
