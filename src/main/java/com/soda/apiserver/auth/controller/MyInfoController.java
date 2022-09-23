package com.soda.apiserver.auth.controller;

import com.soda.apiserver.auth.model.dto.ChangePasswordDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.service.MyInfoService;
import com.soda.apiserver.common.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class MyInfoController {
    private final MyInfoService myInfoService;

    @Autowired
    public MyInfoController(MyInfoService myInfoService) {
        this.myInfoService = myInfoService;
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<?> getMyInfo(HttpServletResponse response){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String username = null;

        try{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User myInfo = myInfoService.getMyInfo(username);

        if(myInfo == null){
            response.setStatus(401);
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        responseMap.put("user", myInfo);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "information quired",responseMap));
    }

    @PutMapping("password")
    public ResponseEntity<?> updateMyPassword(@RequestBody ChangePasswordDTO passwordRequest, HttpServletResponse response){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        String username = null;

        try{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        boolean result = myInfoService.updateMyPassword(username,
                passwordRequest.getOldPassword(), passwordRequest.getNewPassword());

        if(result){
            return ResponseEntity
                    .created(URI.create(""))
                    .headers(headers)
                    .body(new ResponseMessage(201, "password changed",null));
        }
        else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400, "failed to change password",null));
        }
    }
}
