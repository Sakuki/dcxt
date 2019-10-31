package com.chii.demo.mapper;

import com.chii.demo.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uId);

    List<User> selectAll();

    List<User> selectSome(String uId,String uName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}