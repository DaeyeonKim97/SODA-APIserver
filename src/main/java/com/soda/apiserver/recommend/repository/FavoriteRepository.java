package com.soda.apiserver.recommend.repository;

import com.soda.apiserver.recommend.model.entity.Favorite;
import com.soda.apiserver.recommend.model.entity.embed.FavoriteId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findFavoriteByIdUserUserName(String username);
    List<Favorite> findAllBy(Pageable pageable);
}
