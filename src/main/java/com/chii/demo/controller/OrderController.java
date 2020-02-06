package com.chii.demo.controller;
import com.chii.demo.mapper.OrderMapper;
import com.chii.demo.pojo.Order;
import com.sun.org.apache.xpath.internal.operations .Mod;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;

@Controller

public class OrderController {
    @Autowired
    OrderMapper orderMapper;


    @RequestMapping("/Order")
    public String SetOrder(Model model, Order order, HttpServletRequest request){
        String userId = request.getParameter("userId");
        String desk_id = request.getParameter("desk_id");
        List<Order> orderList = orderMapper.selectUser(userId);
        model.addAttribute("orderList",orderList);
        model.addAttribute("userId",userId);
        model.addAttribute("desk_id",desk_id);
        System.out.println("11111111111111");
//        System.out.println(orderList.get(0).getoDate().toString());
//        Date time =new Date(orderList.get(0).getoDate().toString());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String timeFormat = sdf.format(time);
        return "Order";
    }

    @GetMapping("/Order_Use")
    public String AllOrders(Model model, Order order, @RequestParam String getInfo){
        List<Order> orderList = orderMapper.selectAll();
        List<Order> nowOrder = NowOrders(orderList);
        model.addAttribute("orderList",orderList);
        model.addAttribute("getInfo",getInfo);
        model.addAttribute("nowOrder",nowOrder);
        return "Order_Use";
    }

    public List<Order> NowOrders(List<Order> orderList){
        int l;
        List<Order> nowOrder = new ArrayList<>();
        Order order = null;
        for(int i=0; i<orderList.size();i++)
        {
            String Data = orderList.get(i).getoData();
            String Data_split[] = Data.split("-");
            String flag = orderList.get(i).getoFlag();
            l = Data_split.length;
            if (Data_split[l-1].equals("0")&&flag.equals("1")){
                nowOrder.add(orderList.get(i));
            }
        }
        return nowOrder;
    }

    @RequestMapping("/ChangeData")
    @ResponseBody
    public String ChangeData(Model model,Order order, HttpServletRequest request){
        String oID = request.getParameter("oID");
        Order order1 = orderMapper.selectByPrimaryKey(oID);
        String oData = order1.getoData();
        String oData_split[] = oData.split("-");
        int length = oData_split.length;
        String nowData="";
        for (int i=0; i<oData_split.length-1; i++){
            nowData = nowData.concat(oData_split[i]);
            nowData = nowData.concat("-");
        }
        nowData = nowData.concat("1");
//        System.out.println(nowData);
        order1.setoData(nowData);
        int flag = orderMapper.updateByPrimaryKey(order1);
        String flag1 = String.valueOf(flag);
        return flag1;
    }
}
