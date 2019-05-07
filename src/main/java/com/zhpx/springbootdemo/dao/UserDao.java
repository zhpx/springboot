package com.zhpx.springbootdemo.dao;


import com.zhpx.springbootdemo.dao.pojo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Integer> {

}
