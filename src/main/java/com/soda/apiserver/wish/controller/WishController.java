package com.soda.apiserver.wish.controller;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.wish.model.dto.RestaurantIdDTO;
import com.soda.apiserver.wish.model.entity.Restaurant;
import com.soda.apiserver.wish.model.entity.Wish;
import com.soda.apiserver.wish.model.entity.embed.WishId;
import com.soda.apiserver.wish.repository.RestaurantRepository;
import com.soda.apiserver.wish.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wish")
public class WishController {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public WishController(WishRepository wishRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMyWishList(){
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
        List<Wish> wishList = wishRepository.findWishByIdUser(user);
        List<Restaurant> restaurantList = new ArrayList<>();

        for(Wish wish : wishList){
            restaurantList.add(wish.getId().getRestaurant());
        }

        responseMap.put("count",restaurantList.size());
        responseMap.put("list",restaurantList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @PostMapping
    public ResponseEntity<?> addMyWishList(@RequestBody RestaurantIdDTO restaurantId){

        System.out.println(restaurantId);

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
        Restaurant restaurant = restaurantRepository.findById(restaurantId.getRestaurantId());

        System.out.println(restaurant);

        if(restaurant == null){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400,"no restaurant with id", null));
        }

        Wish addWish = new Wish(new WishId(user,restaurant),new java.sql.Date((new java.util.Date()).getTime()));
        wishRepository.save(addWish);

        responseMap.put("restaurant",restaurant);
        responseMap.put("date", addWish.getWishDate());

        return ResponseEntity
                .created(URI.create("/wish"))
                .headers(headers)
                .body(new ResponseMessage(201, "success",responseMap));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMyWishList(@RequestBody RestaurantIdDTO restaurantId){
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
        Restaurant restaurant = restaurantRepository.findById(restaurantId.getRestaurantId());

        if(restaurant == null){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400,"no restaurant with id", null));
        }

        Wish addWish = new Wish(new WishId(user,restaurant),new java.sql.Date((new java.util.Date()).getTime()));
        wishRepository.delete(addWish);

        return ResponseEntity
                .noContent()
                .headers(headers)
                .build();
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getWishListByUserName(@PathVariable("userName") String userName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        User user = userRepository.findByUserName(userName);
        List<Wish> wishList = wishRepository.findWishByIdUser(user);
        List<Restaurant> restaurantList = new ArrayList<>();

        for(Wish wish : wishList){
            restaurantList.add(wish.getId().getRestaurant());
        }

        responseMap.put("count",restaurantList.size());
        responseMap.put("list",restaurantList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }



}
