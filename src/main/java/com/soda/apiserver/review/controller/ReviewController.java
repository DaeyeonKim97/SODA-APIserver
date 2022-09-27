package com.soda.apiserver.review.controller;

import com.soda.apiserver.auth.model.dto.OtherUserDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.file.model.entity.Attach;
import com.soda.apiserver.file.repository.AttachRepository;
import com.soda.apiserver.file.util.OciUtil;
import com.soda.apiserver.review.model.dto.ReviewResponseDTO;
import com.soda.apiserver.review.model.entity.Category;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.review.repository.CategoryRepository;
import com.soda.apiserver.review.repository.ReviewRepository;
import com.soda.apiserver.wish.model.entity.Restaurant;
import com.soda.apiserver.wish.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final AttachRepository attachRepository;
    private final OciUtil ociUtil = new OciUtil();
    @Value("${spring.servlet.multipart.location}")
    private String configPath;

    @Autowired
    public ReviewController(UserRepository userRepository, ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, AttachRepository attachRepository) throws IOException {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.attachRepository = attachRepository;
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getUserReviewList(@PathVariable("userName") String userName, Pageable pageable){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();


        User user = userRepository.findByUserName(userName);

        List<Review> reviewList = reviewRepository.findReviewByUserOrderByCreateDateDesc(user, pageable);
        List<ReviewResponseDTO> reviewResponseDTOList = new ArrayList<>();

        for(Review review : reviewList){
            reviewResponseDTOList.add(
                    new ReviewResponseDTO(
                            review.getId(), new OtherUserDTO(review.getUser()),
                            review.getCategory().getName(), review.getRestaurant(), review.getAttach().getSavedPath(),
                            review.getGrade(), review.getContent(), review.getCreateDate()
                    )
            );
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("page",pageable.getPageNumber());
        pageMap.put("size",pageable.getPageSize());

        responseMap.put("userName", userName);
        System.out.println("user?? "+user);
        responseMap.put("count",reviewRepository.countReviewByUser(user));
        responseMap.put("list", reviewResponseDTOList);
        responseMap.put("page",pageMap);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @PostMapping
    public ResponseEntity<?> insertReview(@RequestParam MultipartFile[] uploadFile, @RequestParam int restaurantId , @RequestParam String categoryName, @RequestParam String content, @RequestParam int grade) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String username = null;
        try{
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        User user = userRepository.findByUserName(username);
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        Category category = categoryRepository.findByName(categoryName);

        if(reviewRepository.findReviewByRestaurantAndUser(restaurant,user) != null){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400,"이미 리뷰를 작성한 식당입니다.",null));
        }

        Review insertReview = null;

        for (MultipartFile file : uploadFile){
            if(!file.isEmpty()){
                String originalFileName = file.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
                System.out.println(username);
                System.out.println(originalFileName);
                System.out.println(ext);
                System.out.println(file.getContentType());

                File tempFile = new File(UUID.randomUUID()+"_"+originalFileName);
                File tempFileConfig = new File(this.configPath+"/"+tempFile.getPath());
                file.transferTo(tempFile);

                String saveName = UUID.randomUUID()+"_"+username+"."+ext;

                ociUtil.upload("soda-review",saveName,file.getContentType(), this.configPath+"/"+tempFile.getPath());

                String savedPath = "https://objectstorage.ap-seoul-1.oraclecloud.com/n/cnzrptgs7qfv/b/soda-review/o/"+saveName;


                Attach attach = new Attach(0,originalFileName,saveName,savedPath,ext,(new Date()).toString(),0,"Y");
                attachRepository.save(attach);

                insertReview = new Review(
                        0, user,restaurant,category,attach, grade, content,
                        new java.sql.Date((new Date()).getTime())
                );

                tempFile.delete();
                tempFileConfig.delete();
            }
        }

        reviewRepository.save(insertReview);
        ReviewResponseDTO responseReview = new ReviewResponseDTO(
                insertReview.getId(), new OtherUserDTO(insertReview.getUser()), insertReview.getCategory().getName(),
                insertReview.getRestaurant(), insertReview.getRestaurant().getImagePath(), insertReview.getGrade(),
                insertReview.getContent(), insertReview.getCreateDate()
        );
        responseMap.put("review",responseReview);

        return ResponseEntity
                .created(URI.create("/review"))
                .headers(headers)
                .body(new ResponseMessage(201, "review created",responseMap));
    }
}
