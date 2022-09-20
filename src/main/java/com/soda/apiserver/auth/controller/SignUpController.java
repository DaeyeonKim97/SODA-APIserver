package com.soda.apiserver.auth.controller;

import com.soda.apiserver.auth.model.dto.SignUpDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.model.entity.UserRole;
import com.soda.apiserver.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SignUpController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDTO signUp, HttpServletResponse response){
        try {
            List<UserRole> userRole = new ArrayList<>();
            userRole.add(new UserRole());
            repository.save(new User(0,
                    signUp.getUserName(),
                    signUp.getPassword(),
                    signUp.getEmail(),
                    new java.sql.Date(new Date().getTime()),
                    "N",
                    null,
                    null,
                    userRole
            ));
        } catch (Exception e){
            System.out.println(e);
            response.setStatus(400);
            return "Not created";
        }
        response.setStatus(201);
        return "created";
    }
}
