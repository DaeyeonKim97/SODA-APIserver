package com.soda.apiserver.restaurant.dao;

import com.soda.apiserver.restaurant.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {
    //메뉴 데이터 입력
    void insertMenu(MenuDto menuDto);
}
