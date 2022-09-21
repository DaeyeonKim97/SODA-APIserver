package com.soda.apiserver.auth.controller;

import com.soda.apiserver.auth.model.dto.ChangePasswordDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public Object getMyInfo(HttpServletResponse response){
        String username = null;
        try{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            response.setStatus(401);
            return "invalid token";
        }

        User myInfo = myInfoService.getMyInfo(username);

        if(myInfo == null){
            response.setStatus(401);
            return "invalid token";
        }

        return myInfo;
    }

    @PutMapping("password")
    public String updateMyPassword(@RequestBody ChangePasswordDTO passwordRequest, HttpServletResponse response){
        String username = null;
        try{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            response.setStatus(401);
            return "invalid token";
        }
        boolean result = myInfoService.updateMyPassword(username,
                passwordRequest.getOldPassword(), passwordRequest.getNewPassword());

        if(result){
            return "password successfully updated";
        }
        else {
            response.setStatus(400);
            return "password update failed";
        }
    }
}
