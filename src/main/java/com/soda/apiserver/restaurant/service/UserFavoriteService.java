package com.soda.apiserver.restaurant.service;

import com.soda.apiserver.restaurant.dao.UserFavoriteMapper;
import com.soda.apiserver.restaurant.dto.UserFavoriteDto;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFavoriteService {
    UserFavoriteMapper userFavoriteMapper;

    public UserFavoriteService(UserFavoriteMapper userFavoriteMapper){
        this.userFavoriteMapper = userFavoriteMapper;
    }

    public void insertUserFavoriteData(UserFavoriteDto userFavoriteDto){
        userFavoriteMapper.insertUserFavoriteData(userFavoriteDto);
    }

    public JSONObject selectUserFavoriteById(String userId){
        List<JSONObject> favoriteList = new ArrayList<JSONObject>(0);
        List<UserFavoriteDto> favoriteDtoList = userFavoriteMapper.selectUserFavoriteById(userId);
        for(int i=0; i<favoriteDtoList.size(); i++){
            JSONObject j = new JSONObject();
            j.put("categoryId" , favoriteDtoList.get(i).getCategoryId());
            favoriteList.add(j);
        }
        JSONObject data = new JSONObject();
        data.put("userId",userId);
        data.put("favoriteList",favoriteList);
        return data;
    }

}
