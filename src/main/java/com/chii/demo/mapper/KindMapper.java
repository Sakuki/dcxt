package com.chii.demo.mapper;

import com.chii.demo.pojo.Kind;

import java.util.List;

public interface KindMapper {
    int deleteByPrimaryKey(String kId);

    int insert(Kind record);

    int insertSelective(Kind record);

    Kind selectByPrimaryKey(String kId);

    List<Kind> selectAll();

    List<Kind> selectSome(String kId, String kName, String kMain);

    int updateByPrimaryKeySelective(Kind record);

    int updateByPrimaryKey(Kind record);
}