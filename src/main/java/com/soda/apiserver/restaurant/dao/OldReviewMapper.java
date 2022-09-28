package com.soda.apiserver.restaurant.dao;

import com.soda.apiserver.restaurant.dto.OldReviewDto;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OldReviewMapper {

    void insertOldReview(OldReviewDto oldReviewDto);

    List<OldReviewDto> selectOldReviewsWithId(String reviewId);

}
