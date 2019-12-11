package com.chii.demo.mapper;

import com.chii.demo.pojo.Order;
import java.util.Date;

public interface OrderMapper {
    int deleteByPrimaryKey(Date oDate);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Date oDate);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}