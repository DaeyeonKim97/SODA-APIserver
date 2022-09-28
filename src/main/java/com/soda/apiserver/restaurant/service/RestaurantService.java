package com.soda.apiserver.restaurant.service;

import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.restaurant.dao.RestaurantMapper;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private static RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public Object selectRestaurantListWithPaging(SelectCriteria selectCriteria) {

//        log.info("[ProductService] selectProductListWithPaging Start ===================================");
        List<RestaurantDto> productList = restaurantMapper.selectRestaurantListWithPaging(selectCriteria);

//        for(int i = 0 ; i < productList.size() ; i++) {
//            productList.get(i).setProductImageUrl(IMAGE_URL + productList.get(i).getProductImageUrl());
//        }
//        log.info("[ProductService] selectProductListWithPaging End ===================================");
        return productList;
    }

    public RestaurantDto selectRestaurantWithId(String restaurantId) {
        RestaurantDto restaurantDto = restaurantMapper.selectRestaurantWithId(restaurantId);
        return restaurantDto;
    }

    public void updateRestaurantView(String restaurantId){
        restaurantMapper.updateRestaurantView(restaurantId);
    }

    public int selectRestaurantTotal(){
        return restaurantMapper.selectRestaurantTotal();
    }


}
