package com.soda.apiserver.follow.repository;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.follow.model.entity.Follow;
import com.soda.apiserver.follow.model.entity.embed.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findFollowByIdUser(User user);
    List<Follow> findFollowByIdFollower(User follower);
    Follow findFollowById(FollowId followId);
    int countFollowByIdFollower(User follower);
    int countFollowByIdUser(User user);
    Follow findByIdUserUserNameAndIdFollowerUserName(String username, String followername);
}
