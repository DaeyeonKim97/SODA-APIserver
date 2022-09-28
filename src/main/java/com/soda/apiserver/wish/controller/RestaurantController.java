package com.soda.apiserver.wish.controller;

import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.review.model.dto.ReviewResponseDTO;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.review.repository.ReviewRepository;
import com.soda.apiserver.wish.model.entity.Restaurant;
import com.soda.apiserver.wish.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantController(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("{restaurantId}")
    public ResponseEntity<?> restaurantDetail(@PathVariable int restaurantId, Pageable pageable){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        int sum = 0;
        List<ReviewResponseDTO> reviewList = new ArrayList<>();
        for(Review review : reviewRepository.findReviewByRestaurantId(restaurantId, pageable)){
            reviewList.add(new ReviewResponseDTO(review));
            sum += review.getGrade();
        };

        responseMap.put("restaurant",restaurant);
        responseMap.put("reviewList",reviewList);
        responseMap.put("grade",((float)sum)/((float) reviewList.size()));


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }
}
