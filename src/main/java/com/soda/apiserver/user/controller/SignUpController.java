package com.soda.apiserver.user.controller;

import com.soda.apiserver.user.model.dto.SignUpDTO;
import com.soda.apiserver.user.model.entity.Role;
import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.model.entity.UserRole;
import com.soda.apiserver.user.repository.RoleRepository;
import com.soda.apiserver.user.repository.UserRepository;
import com.soda.apiserver.user.repository.UserRoleRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserRepository repository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUp){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        User user = null;

        try {
            user = repository.save(new User(0,
                    signUp.getUserName(),
                    passwordEncoder.encode(signUp.getPassword()),
                    signUp.getEmail(),
                    new java.sql.Date(new Date().getTime()),
                    "N",
                    null,
                    null
            ));
            System.out.println("user : "+user);
            Role role = roleRepository.findByRoleName("MEMBER");

            userRoleRepository.save(new UserRole(0,user,role));

        } catch (Exception e){
            System.out.println(e);
            responseMap.put("inputUserName",signUp.getUserName());
            responseMap.put("inputEmail",signUp.getEmail());
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(new ResponseMessage(400, "user not created",responseMap));
        }

        responseMap.put("userName",user.getUserName());
        responseMap.put("email",user.getEmail());

        return ResponseEntity
                .created(URI.create("/users/"+signUp.getUserName()))
                .headers(headers)
                .body(new ResponseMessage(201, "user created",responseMap));
    }
}
