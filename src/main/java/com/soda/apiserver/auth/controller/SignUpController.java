package com.soda.apiserver.auth.controller;

import com.soda.apiserver.auth.model.dto.SignUpDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.model.entity.UserRole;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@RestController
public class SignUpController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUp, HttpServletResponse response){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        try {
            List<UserRole> userRole = new ArrayList<>();
            userRole.add(new UserRole());
            repository.save(new User(0,
                    signUp.getUserName(),
                    passwordEncoder.encode(signUp.getPassword()),
                    signUp.getEmail(),
                    new java.sql.Date(new Date().getTime()),
                    "N",
                    null,
                    null,
                    userRole
            ));
        } catch (Exception e){
            System.out.println(e);
            responseMap.put("Input ID",signUp.getUserName());
            responseMap.put("Input Email",signUp.getEmail());
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(new ResponseMessage(400, "user not created",responseMap));
        }

        responseMap.put("ID",signUp.getUserName());
        responseMap.put("Email",signUp.getEmail());

        return ResponseEntity
                .created(URI.create("/users/"+signUp.getUserName()))
                .headers(headers)
                .body(new ResponseMessage(201, "user created",responseMap));
    }
}
