package com.chii.demo.mapper;

import com.chii.demo.pojo.Item;

public interface ItemMapper {
    int deleteByPrimaryKey(String iId);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(String iId);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}