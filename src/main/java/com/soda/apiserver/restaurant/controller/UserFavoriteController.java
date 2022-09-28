package com.soda.apiserver.restaurant.controller;

import com.soda.apiserver.common.paging.Pagenation;
import com.soda.apiserver.common.paging.ResponseDtoWithPaging;
import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.common.response.ResponseDto;
import com.soda.apiserver.restaurant.dto.UserFavoriteDto;
import com.soda.apiserver.restaurant.service.UserFavoriteService;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class UserFavoriteController {
    private UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserFavoriteService userFavoriteService){
        this.userFavoriteService = userFavoriteService;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> insertUserFavoriteCategoryData(@RequestBody UserFavoriteDto favorite){

        userFavoriteService.insertUserFavoriteData(favorite);
        System.out.println(favorite);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", favorite));
    }


    @GetMapping()
    public ResponseEntity<ResponseDto> selectUserFavoriteById(@RequestParam String userId) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", userFavoriteService.selectUserFavoriteById(userId)));
    }

}
