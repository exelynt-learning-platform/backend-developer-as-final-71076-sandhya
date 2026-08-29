package com.example.resourcebooking.config;


import com.example.resourcebooking.entity.Role;
import com.example.resourcebooking.entity.User;
import com.example.resourcebooking.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("ADMIN user created");
            }

            if (userRepository.findByUsername("user").isEmpty()) {

                User user = new User();

                user.setUsername("user");
                user.setPassword(
                        passwordEncoder.encode("user123")
                );
                user.setRole(Role.USER);

                userRepository.save(user);

                System.out.println("USER user created");
            }
        };
    }
}