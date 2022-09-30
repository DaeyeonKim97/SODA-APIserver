package com.soda.apiserver.review.controller;

import com.soda.apiserver.user.model.dto.OtherUserDTO;
import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.file.model.entity.Attach;
import com.soda.apiserver.file.repository.AttachRepository;
import com.soda.apiserver.file.util.OciUtil;
import com.soda.apiserver.follow.model.entity.Follow;
import com.soda.apiserver.follow.repository.FollowRepository;
import com.soda.apiserver.review.model.dto.CommentResponseDTO;
import com.soda.apiserver.review.model.dto.LikeResponseDTO;
import com.soda.apiserver.review.model.dto.ReviewCommentDTO;
import com.soda.apiserver.review.model.dto.ReviewResponseDTO;
import com.soda.apiserver.review.model.entity.Category;
import com.soda.apiserver.review.model.entity.Comment;
import com.soda.apiserver.review.model.entity.Like;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.review.model.entity.embed.LikeId;
import com.soda.apiserver.review.repository.CategoryRepository;
import com.soda.apiserver.review.repository.CommentRepository;
import com.soda.apiserver.review.repository.LikeRepository;
import com.soda.apiserver.review.repository.ReviewRepository;
import com.soda.apiserver.wish.model.entity.Restaurant;
import com.soda.apiserver.wish.model.entity.Wish;
import com.soda.apiserver.wish.repository.RestaurantRepository;
import com.soda.apiserver.wish.repository.WishRepository;
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
    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final WishRepository wishRepository;
    private final OciUtil ociUtil = new OciUtil();
    @Value("${spring.servlet.multipart.location}")
    private String configPath;

    @Autowired
    public ReviewController(UserRepository userRepository, ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, AttachRepository attachRepository, FollowRepository followRepository, LikeRepository likeRepository, CommentRepository commentRepository, WishRepository wishRepository) throws IOException {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.attachRepository = attachRepository;
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.wishRepository = wishRepository;
    }

    @GetMapping ("recent")
    public ResponseEntity<?> recentReview(Pageable pageable){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        List<Review> reviewList = reviewRepository.findAllByOrderByCreateDateDesc(pageable);
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

        responseMap.put("list", reviewResponseDTOList);
        responseMap.put("page",pageMap);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
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

    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable int reviewId){

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
        Review deleteReview = reviewRepository.findById(reviewId);
        if (deleteReview == null){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400, "no review with id",null));
        }

        System.out.println(username);
        System.out.println(deleteReview.getUser().getUserName());

        if(deleteReview.getUser().getUserName().equals(username)){
            Attach attach = deleteReview.getAttach();
            reviewRepository.delete(deleteReview);
            attachRepository.delete(attach);
        }
        else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400, "cannot delete other's review",null));
        }

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("following")
    public ResponseEntity<?> getUserReviewList(Pageable pageable){

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
        List<Follow> followingList = followRepository.findFollowByIdFollower(user);
        List<User> userList = new ArrayList<>();
        for (Follow following : followingList){
            userList.add(following.getId().getUser());
        }

        List<Review> reviewList = reviewRepository.findReviewByUserInOrderByCreateDateDesc(userList, pageable);
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
        responseMap.put("count",reviewRepository.countReviewByUser(user));
        responseMap.put("list", reviewResponseDTOList);
        responseMap.put("page",pageMap);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @PostMapping("like/{reviewId}")
    public ResponseEntity<?> likeReview(@PathVariable int reviewId){
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
        Review review = reviewRepository.findById(reviewId);

        Like like = new Like(new LikeId(review,user),new java.sql.Date((new Date()).getTime()));
        likeRepository.save(like);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @GetMapping("like/{reviewId}")
    public ResponseEntity<?> reviewLikeList(@PathVariable int reviewId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        List<Like> likeList = likeRepository.findLikeByIdReviewId(reviewId);
        int likeCount = likeRepository.countByIdReviewId(reviewId);
        List<LikeResponseDTO> responseLikeList = new ArrayList<>();

        for(Like like : likeList){
            responseLikeList.add(new LikeResponseDTO(like.getId().getUser(), like.getLikeDate()));
        }

        responseMap.put("count",likeCount);
        responseMap.put("list", responseLikeList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @DeleteMapping("like/{reviewId}")
    public ResponseEntity<?> unlikeReview(@PathVariable int reviewId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        String userName = null;

        try{
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        User user = userRepository.findByUserName(userName);
        Review review = reviewRepository.findById(reviewId);

        Like like = likeRepository.findLikeById(new LikeId(review, user));
        likeRepository.delete(like);

        return ResponseEntity
                .noContent()
                .headers(headers)
                .build();
    }

    @PostMapping("comment/{reviewId}")
    public ResponseEntity<?> commentReview(@PathVariable int reviewId, @RequestBody ReviewCommentDTO paramComment){
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
        Review review = reviewRepository.findById(reviewId);

        Comment comment = new Comment(0,user,review,paramComment.getComment(),new java.sql.Date((new Date()).getTime()));
        commentRepository.save(comment);
        responseMap.put("reviewId",reviewId);
        responseMap.put("comment",comment.getContent());

        return ResponseEntity
                .created(URI.create("/comment"))
                .headers(headers)
                .body(new ResponseMessage(201, "success",responseMap));
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        String userName = null;

        try{
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Comment comment = commentRepository.findById(commentId);

        if(userName != comment.getUser().getUserName()){
            return ResponseEntity
                    .badRequest()
                    .body("Not your comment");
        }

        commentRepository.delete(comment);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("detail/{reviewId}")
    public ResponseEntity<?> getDetailReview(@PathVariable int reviewId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        Review review = reviewRepository.findById(reviewId);
        ReviewResponseDTO responseReview = new ReviewResponseDTO(review);
        responseMap.put("review",responseReview);

        int likeCount = likeRepository.countByIdReviewId(reviewId);
        responseMap.put("likeCount",likeCount);

        int commentCount = commentRepository.countByReviewId(reviewId);
        responseMap.put("commentCount",commentCount);
        List<Comment> commentList = commentRepository.findByReviewId(reviewId);
        List<CommentResponseDTO> responseCommentList = new ArrayList<>();
        for(Comment comment : commentList){
            responseCommentList.add(new CommentResponseDTO(comment));
        }
        responseMap.put("commentList",responseCommentList);

        String userName = null;
        try{
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        boolean isLike = false;
        Like userLike = likeRepository.findLikeByIdReviewIdAndIdUserUserName(reviewId,userName);
        isLike = (userLike != null);
        responseMap.put("isLike", isLike);

        boolean isWish = false;
        Wish userWish = wishRepository.findWishByIdRestaurantAndIdUserUserName(review.getRestaurant(),userName);
        isWish = (userWish!=null);
        responseMap.put("isWish", isWish);


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }
}
