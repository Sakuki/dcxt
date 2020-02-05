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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public String SetXiaDan(Model model, Kind kind, Menu menu, HttpServletRequest request){
        String userId = request.getParameter("userId");
        List<Menu> menuList= menuMapper.selectAll();
        List<Kind> kindList= kindMapper.selectAll();
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        model.addAttribute("menuList",menuList);
        model.addAttribute("kindList",kindList);
        model.addAttribute("userId",userId);
        return "XiaDan";
    }
    @RequestMapping("/AddOrder")
    @ResponseBody
    public String AddOrder(ModelMap model, Order order, HttpServletRequest request){
        String ID,user,desk,data,number,date;
        String totalprice;
//        ID = "00000005";
        ID = findID();
        user = request.getParameter("userId");
        desk = request.getParameter("desk");
        date = request.getParameter("date");
        data = request.getParameter("data");
        number = request.getParameter("number");
        totalprice = request.getParameter("totalprice");
        System.out.println("totalprice1 is "+totalprice);
        order.setoId(ID);
        order.setoUser(user);
        order.setoDesk(desk);
        order.setoDate(ChangerDate(date));
        order.setoData(data);
        order.setoNumber(Integer.parseInt(number));
        model.put("ID",ID);
        model.put("user",user);
        model.put("desk",desk);
        model.put("date",ChangerDate(date));
        model.put("data",data);
        model.put("number",number);
        model.put("totalprice",totalprice);
        System.out.println("12311111111321");
//        return "redirect:/alipay";
        int flag = orderMapper.insertSelective(order);
        String flag1 = String.valueOf(flag);
//        String flag1 = "1";
        return ID;
    }

    public String findID(){
        List<Order> orderList = orderMapper.selectAll();
        int num = orderList.get(orderList.size()-1).getoId().length(),IDNum;
        String TheNum = "";
        String preID = orderList.get(orderList.size()-1).getoId().toString();
        String str = preID.substring(0,8);
        System.out.println("str is "+str);
        String[] date = new  SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        String theDate = date[0]+date[1]+date[2];
        System.out.println("date is "+theDate);
        if (str.equals(theDate)){
            IDNum = Integer.parseInt(preID.substring(8,11))+1;
            if (IDNum/10==0){
                TheNum = "00"+String.valueOf(IDNum);
            }
            else if (IDNum/10!=0&&IDNum/100==0){
                TheNum = "0"+String.valueOf(IDNum);
            }else {
                TheNum = String.valueOf(IDNum);
            }
            System.out.println("IDNum is "+IDNum);
        }else {
            TheNum = "001";
        }
        return theDate+TheNum;
//        Calendar cal = Calendar.getInstance();
//        int y = cal.get(cal.YEAR);
//        int m = cal.get(cal.MONTH+1);
//        int d = cal.get(cal.DATE);
//        String theDate = "";
//        theDate = theDate.concat(String.valueOf(y));
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
