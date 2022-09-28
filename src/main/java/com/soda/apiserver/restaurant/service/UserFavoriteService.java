package com.soda.apiserver.restaurant.service;

import com.soda.apiserver.restaurant.dao.UserFavoriteMapper;
import com.soda.apiserver.restaurant.dto.UserFavoriteDto;
import com.soda.apiserver.restaurant.dto.UserFavoriteListDto;
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

    public JSONObject selectUserFavoriteByIdForAI(String userId){
        List<JSONObject> store = new ArrayList<JSONObject>();
        List<String> favoriteDataList = userFavoriteMapper.selectUserFavoriteById2(userId);
        for(int i=0; i<favoriteDataList.size(); i++){
            JSONObject j = new JSONObject();
            j.put("food_category" , favoriteDataList.get(i));
            j.put("score", 5);
            store.add(j);
        }
        JSONObject data = new JSONObject();
        data.put("id", userId);
        data.put("store",store);
        return data;
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


    public void insertUserFavoriteList(UserFavoriteListDto favoriteListDto){
        for(int i=0; i<favoriteListDto.getFavoriteCategoryList().size(); i++){
            UserFavoriteDto userFavoriteDto = new UserFavoriteDto();
            userFavoriteDto.setUserId(favoriteListDto.getUserId());
            userFavoriteDto.setCategoryId(Integer.parseInt((String)favoriteListDto.getFavoriteCategoryList().get(i).get("categoryId")));
            userFavoriteMapper.insertUserFavoriteData(userFavoriteDto);
        }
    }

}
