package com.soda.apiserver.restaurant.controller;

import com.soda.apiserver.common.paging.Pagenation;
import com.soda.apiserver.common.paging.ResponseDtoWithPaging;
import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.common.response.ResponseDto;
import com.soda.apiserver.restaurant.dao.OldReviewMapper;
import com.soda.apiserver.restaurant.service.OldReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oldReview")
public class OldReviewController {
    private OldReviewService oldReviewService;

    public OldReviewController(OldReviewService oldReviewService){
        this.oldReviewService = oldReviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<ResponseDto> selectRestarantWithId(@RequestParam String reviewId) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", oldReviewService.selectOldReviewsWithId(reviewId)));
    }

}
