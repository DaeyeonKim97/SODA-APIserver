package com.soda.apiserver.user.controller;

import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.follow.repository.FollowRepository;
import com.soda.apiserver.review.model.dto.ReviewResponseDTO;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.review.repository.ReviewRepository;
import com.soda.apiserver.user.model.dto.OtherUserDTO;
import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.UserRepository;
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
@RequestMapping("user")
public class UserDetailController {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FollowRepository followRepository;

    @Autowired
    public UserDetailController(UserRepository userRepository, ReviewRepository reviewRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.followRepository = followRepository;
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getUserDetail(@PathVariable String userName, Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        User user = userRepository.findByUserName(userName);
        OtherUserDTO responseUser = new OtherUserDTO(user);
        List<Review> reviewList = reviewRepository.findReviewByUserOrderByCreateDateDesc(user,pageable);
        List<ReviewResponseDTO> responseReviewList = new ArrayList<>();
        for(Review review : reviewList){
            responseReviewList.add(new ReviewResponseDTO(review));
        }
        int followerNum = followRepository.countFollowByIdUser(user);
        int followingNum = followRepository.countFollowByIdFollower(user);

        responseMap.put("user",responseUser);
        responseMap.put("follower",followerNum);
        responseMap.put("following",followingNum);
        responseMap.put("reviewList", responseReviewList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }
}
