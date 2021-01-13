package com.qf.dao;


import com.qf.pojo.UserForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserForm, String> {
    UserForm findByOpenid(String openid);
}
