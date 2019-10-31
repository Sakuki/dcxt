package com.chii.demo.mapper;

import com.chii.demo.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String oId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String oId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}