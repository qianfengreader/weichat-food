package com.qf.dao;


import com.qf.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 微信：2501902696
 * desc:
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByOpenid(String openid);
}
