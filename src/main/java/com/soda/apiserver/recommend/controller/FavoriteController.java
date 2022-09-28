package com.soda.apiserver.recommend.controller;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.recommend.model.entity.Favorite;
import com.soda.apiserver.recommend.model.entity.embed.FavoriteId;
import com.soda.apiserver.recommend.repository.FavoriteRepository;
import com.soda.apiserver.review.model.entity.Category;
import com.soda.apiserver.review.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("favorite")
public class FavoriteController {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;

    public FavoriteController(UserRepository userRepository, CategoryRepository categoryRepository, FavoriteRepository favoriteRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @PostMapping
    public ResponseEntity<?> registFavorite(@RequestBody List<Integer> categoryIdList){
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

        for(int categoryId : categoryIdList){
            Category category = categoryRepository.findById(categoryId);
            if(category != null){
                FavoriteId favoriteId = new FavoriteId(category, user);
                favoriteRepository.save(new Favorite(favoriteId));
            }
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @GetMapping
    public ResponseEntity<?> categoryList(Pageable pageable) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Map<String, Object> responseMap = new HashMap<>();

        List<Category> categoryList = categoryRepository.findAllBy(pageable);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("page", pageable.getPageNumber());
        pageMap.put("size", pageable.getPageSize());

        responseMap.put("page", pageMap);
        responseMap.put("count", categoryRepository.countBy());
        responseMap.put("list", categoryList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success", responseMap));
    }
}
