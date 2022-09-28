package com.soda.apiserver.restaurant.dao;

import com.soda.apiserver.restaurant.dto.UserFavoriteDto;
import com.soda.apiserver.restaurant.dto.UserFavoriteListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFavoriteMapper{
    void insertUserFavoriteData(UserFavoriteDto userFavoriteDto);
    List<UserFavoriteDto> selectUserFavoriteById(String userId);

    List<String> selectUserFavoriteById2(String userId);
}
