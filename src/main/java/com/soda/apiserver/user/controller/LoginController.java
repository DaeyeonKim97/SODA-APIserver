package com.soda.apiserver.user.controller;

import com.soda.apiserver.user.model.dto.LoginDTO;
import com.soda.apiserver.user.util.JwtUtil;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.recommend.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public LoginController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, FavoriteRepository favoriteRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.favoriteRepository = favoriteRepository;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginInfo){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        String token = null;
        Boolean selectCategory = false;

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginInfo.getUserName(),
                            loginInfo.getPassword()
                    )
            );
        } catch (Exception e){
            System.out.println(e);
            responseMap.put("inputId", loginInfo.getUserName());
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(new ResponseMessage(400, "login failed", responseMap));
        }

        if(favoriteRepository.findFavoriteByIdUserUserName(loginInfo.getUserName()).size() == 0){
            selectCategory = true;
        };

        token = jwtUtil.generateToken(loginInfo.getUserName());
        responseMap.put("id",loginInfo.getUserName());
        responseMap.put("token",token);
        responseMap.put("needSelect",selectCategory);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "token generated", responseMap));
    }

}
