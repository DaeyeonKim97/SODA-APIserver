package com.soda.apiserver.recommend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.recommend.model.dto.AiFavoriteDTO;
import com.soda.apiserver.recommend.model.dto.AiReviewListDTO;
import com.soda.apiserver.recommend.model.entity.Favorite;
import com.soda.apiserver.recommend.repository.FavoriteRepository;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommand")
public class RecommandController {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final ObjectMapper mapper;

    @Autowired
    public RecommandController(UserRepository userRepository, FavoriteRepository favoriteRepository, ReviewRepository reviewRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> recommandList() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String userName = null;

        try{
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User user = userRepository.findByUserName(userName);
        List<Review> reviewList = reviewRepository.findByUserUserName(userName);
        List<Favorite> favoriteList = favoriteRepository.findFavoriteByIdUserUserName(userName);

        AiReviewListDTO aiReviewList = new AiReviewListDTO(user.getId(),reviewList);
        AiFavoriteDTO aiFavoriteList = new AiFavoriteDTO(user.getId(),favoriteList);

        Map<String,Object> requestMap = new HashMap<>();

        requestMap.put("review_list",aiReviewList);
        requestMap.put("fav_list", aiFavoriteList);

        String requestJSON = mapper.writeValueAsString(requestMap);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.0.173:5050/returnRecommend";
        HttpEntity<?> requestMessage = new HttpEntity<>(requestJSON,headers);
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage,String.class);

        System.out.println(response);
        responseMap.put("response",response);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }
}
