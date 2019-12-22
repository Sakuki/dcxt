package com.chii.demo.controller;
import com.chii.demo.mapper.KindMapper;
import com.chii.demo.mapper.MenuMapper;
import com.chii.demo.mapper.OrderMapper;
import com.chii.demo.pojo.Menu;
import com.chii.demo.pojo.Kind;
import com.chii.demo.pojo.MenuAndKind;
import com.chii.demo.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;

@Controller
public class XiaDanController {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    KindMapper kindMapper;
    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/XiaDan")
    public String SetXiaDan(Model model, Kind kind, Menu menu){
        List<Menu> menuList= menuMapper.selectAll();
        List<Kind> kindList= kindMapper.selectAll();
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        model.addAttribute("menuList",menuList);
        model.addAttribute("kindList",kindList);
        return "XiaDan";
    }
    @PostMapping("/AddOrder")
    public String AddOrder(Model model, Order order, HttpServletRequest request){
        String ID,user,desk,data,number;
        String date;
        ID = "00000005";
        user = "123456";
        desk = request.getParameter("desk");
        date = request.getParameter("date");
        data = request.getParameter("data");
        number = request.getParameter("number");
        order.setoId(ID);
        order.setoUser(user);
        order.setoDesk(desk);
        order.setoDate(ChangerDate(date));
        order.setoData(data);
        order.setoNumber(Integer.parseInt(number));
        orderMapper.insertSelective(order);
        return "redirect:/Order";
//        return "redirect: Order.action";
    }

    public Date ChangerDate(String TheDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(TheDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
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
