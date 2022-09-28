package com.soda.apiserver.restaurant.dao;

import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    void insertRestaurantList(RestaurantDto restaurantDto);
    List<RestaurantDto> selectRestaurantListWithPaging(SelectCriteria selectCriteria);
    RestaurantDto selectRestaurantWithId(String restaurantId);
    void updateRestaurantView(String restaurantId);
    int selectRestaurantTotal();
}
