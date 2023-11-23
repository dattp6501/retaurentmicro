package com.dattp.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.productservice.entity.CommentDish;

public interface CommentDishRepository extends JpaRepository<CommentDish,Long>{
    @Query(
        value="SELECT * FROM COMMENT_DISH cd "
        +"WHERE cd.dish_id=:dish_id AND cd.user_id=:user_id "
        , nativeQuery=true
    )
    public CommentDish findByDishIdAndUserId(@Param("dish_id") Long dishId, @Param("user_id") Long userId);

    @Modifying
    @Query(
        value = "INSERT INTO COMMENT_DISH(star,comment,dish_id,user_id,username) "
        +"VALUES(:star,:comment,:dish_id,:user_id,:username) ", 
        nativeQuery=true
    )
    public int save(@Param("star") int star, @Param("comment") String comment, @Param("dish_id") Long dishId, @Param("user_id") Long userId, @Param("username") String username);

    @Modifying
    @Query(
        value="UPDATE COMMENT_DISH cd "
        +"SET cd.star=:star, cd.comment=:comment "
        +"WHERE cd.dish_id=:dish_id AND cd.user_id=:user_id "
        , nativeQuery = true
    )
    public int update(@Param("star") int star, @Param("comment") String comment, @Param("dish_id") Long dishId, @Param("user_id") Long userId);

}