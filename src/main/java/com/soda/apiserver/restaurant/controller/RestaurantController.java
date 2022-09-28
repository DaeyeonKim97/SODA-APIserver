package com.soda.apiserver.restaurant.controller;

import com.soda.apiserver.common.paging.Pagenation;
import com.soda.apiserver.common.paging.ResponseDtoWithPaging;
import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.common.response.ResponseDto;
import com.soda.apiserver.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService){this.restaurantService = restaurantService; }


    @GetMapping("/restaurants")
    public ResponseEntity<ResponseDto> selectRestarantListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset,@RequestParam(name="limit", defaultValue="10") String limit) {
        System.out.println("call selectRestarantListWithPaging");
//            log.info("[ProductController] selectProductListWithPaging : " + offset);

//            int totalCount = restaurantService.selectRestaurantTotal();
        int totalCount = restaurantService.selectRestaurantTotal();
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, Integer.parseInt(limit), buttonAmount);

    //          log.info("[ProductController] selectCriteria : " + selectCriteria);
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(restaurantService.selectRestaurantListWithPaging(selectCriteria));
        System.out.println("call select API");
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    @GetMapping("/restaurant")
    public ResponseEntity<ResponseDto> selectRestarantWithId(@RequestParam String restaurantId) {
        restaurantService.updateRestaurantView(restaurantId);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", restaurantService.selectRestaurantWithId(restaurantId)));
    }
}

