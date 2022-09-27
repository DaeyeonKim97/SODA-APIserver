package com.soda.apiserver.follow.controller;

import com.soda.apiserver.auth.model.dto.OtherUserDTO;
import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.common.response.ResponseMessage;
import com.soda.apiserver.follow.model.entity.Follow;
import com.soda.apiserver.follow.model.entity.embed.FollowId;
import com.soda.apiserver.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public FollowController(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    @GetMapping("following")
    public ResponseEntity<?> selectFollowingUser(){
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

        User follower = userRepository.findByUserName(userName);
        List<Follow> followingList = followRepository.findFollowByIdFollower(follower);
        List<OtherUserDTO> followingUserList = new ArrayList<>();
        for(Follow following : followingList){
            followingUserList.add(new OtherUserDTO(following.getId().getUser()));
        }

        responseMap.put("count",followingUserList.size());
        responseMap.put("following list",followingUserList);


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @GetMapping("follower")
    public ResponseEntity<?> selectFollowerUser(){
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
        List<Follow> followerList = followRepository.findFollowByIdUser(user);
        List<OtherUserDTO> followerUserList = new ArrayList<>();
        for(Follow follower : followerList){
            followerUserList.add(new OtherUserDTO(follower.getId().getFollower()));
        }

        responseMap.put("count",followerUserList.size());
        responseMap.put("follower list",followerUserList);


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> selectFollowerUser(@PathVariable("userName") String userName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();

        User user = userRepository.findByUserName(userName);

        List<Follow> followingList = followRepository.findFollowByIdFollower(user);
        List<OtherUserDTO> followingUserList = new ArrayList<>();
        for(Follow following : followingList){
            followingUserList.add(new OtherUserDTO(following.getId().getUser()));
        }
        Map<String,Object> followingMap = new HashMap<>();
        followingMap.put("count",followingUserList.size());
        followingMap.put("list",followingUserList);

        List<Follow> followerList = followRepository.findFollowByIdUser(user);
        List<OtherUserDTO> followerUserList = new ArrayList<>();
        for(Follow follow : followerList){
            followerUserList.add(new OtherUserDTO(follow.getId().getFollower()));
        }
        Map<String,Object> followerMap = new HashMap<>();
        followerMap.put("count",followerUserList.size());
        followerMap.put("list",followerUserList);


        responseMap.put("userName",userName);
        responseMap.put("following",followingMap);
        responseMap.put("follower",followerMap);


        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "success",responseMap));
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> followUser(@PathVariable("userName") String userName){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String followerName = null;

        try{
            followerName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User user = userRepository.findByUserName(userName);
        User follower = userRepository.findByUserName(followerName);

        System.out.println(user);
        System.out.println(follower);

        if(user == null || user.getUserName() == follower.getUserName()){
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage(400, "cannot follow yourself", null));
        }

        Follow insertFollow = new Follow(new FollowId(user,follower),
                                new Date((new java.util.Date()).getTime()));
        followRepository.save(insertFollow);

        responseMap.put("followed",user.getUserName());
        responseMap.put("following",follower.getUserName());

        return ResponseEntity
                .created(URI.create("/follow"))
                .headers(headers)
                .body(new ResponseMessage(201, "follow success",responseMap));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> unfollowUser(@PathVariable("userName") String userName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        Map<String,Object> responseMap = new HashMap<>();
        String followerName = null;

        try{
            followerName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User user = userRepository.findByUserName(userName);
        User follower = userRepository.findByUserName(followerName);

        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Follow deleteFollow = followRepository.findFollowById(new FollowId(user,follower));
        followRepository.delete(deleteFollow);

        return ResponseEntity
                .noContent()
                .headers(headers)
                .build();

    }
}
