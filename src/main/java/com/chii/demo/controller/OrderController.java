package com.chii.demo.controller;
import com.chii.demo.mapper.OrderMapper;
import com.chii.demo.pojo.Order;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;

@Controller

public class OrderController {
    @Autowired
    OrderMapper orderMapper;


    @GetMapping("/Order")
    public String SetOrder(Model model, Order order){
        List<Order> orderList = orderMapper.selectUser("123456");
        model.addAttribute("orderList",orderList);
        System.out.println(orderList.get(0).getoDate().toString());
//        Date time =new Date(orderList.get(0).getoDate().toString());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String timeFormat = sdf.format(time);
        System.out.println(orderList.get(0).getoDate().toString());
        return "redirect:/Order";
    }
}
