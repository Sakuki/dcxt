package com.chii.demo.controller;
import com.chii.demo.mapper.KindMapper;
import com.chii.demo.mapper.MenuMapper;
import com.chii.demo.pojo.Menu;
import com.chii.demo.pojo.Kind;
import com.chii.demo.pojo.MenuAndKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Controller
public class OrderController {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    KindMapper kindMapper;

    @GetMapping("/Order")
    public String SetOrder(Model model, Kind kind, Menu menu){
        List<Menu> menuList= menuMapper.selectAll();
        List<Kind> kindList= kindMapper.selectAll();
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        model.addAttribute("menuList",menuList);
        model.addAttribute("kindList",kindList);
        return "Order";
    }

//    @RequestMapping("/findIndexKind")
//    @ResponseBody
//    public List findIndexKind(Model model,Kind kind,Menu menu){
//        List<Kind> kindList= kindMapper.selectAll();
//        List<String> kNameList = new ArrayList<>();
//        for (int i=0;i <kindList.size();i++)
//        {
//            kNameList.add(kindList.get(i).getkName());
//        }
//        return kNameList;
//    }

}
