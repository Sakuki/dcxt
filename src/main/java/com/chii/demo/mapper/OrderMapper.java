package com.chii.demo.mapper;

import com.chii.demo.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String oId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String oId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectAll();

    List<Order> selectUser(String oUser);
}