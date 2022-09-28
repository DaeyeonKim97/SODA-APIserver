package com.soda.apiserver.restaurant.service;

import com.soda.apiserver.restaurant.dao.OldReviewMapper;
import com.soda.apiserver.restaurant.dto.OldReviewDto;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OldReviewService {
    private OldReviewMapper oldReviewMapper;

    public Object selectOldReviewsWithId(String reviewId) {
        List<OldReviewDto> oldReviewDtoList = oldReviewMapper.selectOldReviewsWithId(reviewId);
        return oldReviewDtoList;
    }
}
