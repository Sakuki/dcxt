package com.chii.demo.mapper;

import com.chii.demo.pojo.Menu;
import com.chii.demo.pojo.MenuAndKind;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(String mId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String mId);

    List<Menu> selectAll();

    List<Menu> selectSome(String mId, String mName, String mWay, String mFlavor, String mIng, String kId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuAndKind> selectKindName();
}