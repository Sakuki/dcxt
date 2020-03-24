package com.chii.demo.controller;
import com.chii.demo.mapper.MenuMapper;
import com.chii.demo.mapper.OrderMapper;
import com.chii.demo.pojo.Kind;
import com.chii.demo.pojo.Menu;
import com.chii.demo.pojo.Menu_Num;
import com.chii.demo.pojo.Order;
import com.chii.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ExportController {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MenuMapper menuMapper;

    @GetMapping("/Export_Turn")
    public String Export_turn(Kind kind, Model model, @RequestParam String getInfo, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        String uId = (String)session.getAttribute("UserId");
		String uName = (String)session.getAttribute("UserName");
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        model.addAttribute("getInfo",getInfo);
        return "Export_Turn";
    }

    @RequestMapping("/SelectDate")  //获取营业额
    @ResponseBody
    public String[] SelectDate(Order order, HttpServletRequest request) throws ParseException {
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        String[] data = new String[2];
        List<Order> orderList = orderMapper.selectAll();
        List<Order> ThisTimeOrder = ThisTimeOrder(orderList,date1,date2);   //所选择时间内的有效订单
        double totalPrice =0.00;
        for (int i=0; i<ThisTimeOrder.size(); i++){
            totalPrice = totalPrice+Double.parseDouble(ThisTimeOrder.get(i).getoTotalprice());
        }
        data[0] = String.valueOf(ThisTimeOrder.size());
        data[1] = String.valueOf(totalPrice);
        return data;
    }

    @RequestMapping("/SelectNum")   //获取销量
    @ResponseBody
    public List<Menu_Num> SelectNum(Menu_Num menu_num, HttpServletRequest request) throws ParseException {
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        List<Order> orderList = orderMapper.selectAll();
        List<Order> ThisTimeOrder = ThisTimeOrder(orderList,date1,date2);    //所选择时间内的有效订单
        List<Menu> menuList = menuMapper.selectAll();
        List<Menu_Num> menu_numList = TheMenu(menuList);
        for (int i=0; i<ThisTimeOrder.size(); i++){         //将订单销量加入到数据内
            String oData = ThisTimeOrder.get(i).getoData();
            String oData_split[] = oData.split("-");
            int length = oData_split.length;
            for (int j = 0; j < (oData_split.length - 2) / 3; j++){
                String name = oData_split[j * 3 + 1];
                int num = Integer.parseInt(oData_split[j * 3 + 2]);
//                Long price = Long.parseLong(oData_split[j * 3 + 3]);
                for (int k=0; k<menu_numList.size(); k++){
                    if (menu_numList.get(k).getmName().equals(name)){
                        int n = menu_numList.get(k).getNum()+num;
                        menu_numList.get(k).setNum(n);
                    }
                }
            }
        }
        System.out.println("---Sorting using Comparator by Age---");
        Collections.sort(menu_numList, new Comparator<Menu_Num>() {     //将menu_numList按num从大到小排序
            @Override
            public int compare(Menu_Num o1, Menu_Num o2) {
                return o1.getNum() == o2.getNum() ? 0 : (o1.getNum() < o2.getNum() ? 1 : -1);
            }
        });

//        slist = list.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
//        slist.forEach(e -> System.out.println("Id:"+ e.getId()+", Name: "+e.getName()+", Age:"+e.getAge()));

        System.out.println(menu_numList);
        return menu_numList;
    }

    public List<Menu_Num> TheMenu(List<Menu> menuList){
        Menu_Num menu_num;
        List<Menu_Num> menu_numList = new ArrayList<>();
        for (int i=0; i<menuList.size(); i++){
            menu_num = new Menu_Num(menuList.get(i).getmId(),menuList.get(i).getmName(),
                    menuList.get(i).getmPrice(),menuList.get(i).getkId(),0);
            menu_numList.add(menu_num);
        }
        return menu_numList;
    }

    public List<Order> ThisTimeOrder(List<Order> orderList, String date1, String date2 ) throws ParseException {
        List<Order> nowOrder = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFirst,dateLast;
        dateFirst = dateFormat.parse(date1);
        dateLast = dateFormat.parse(date2);
        for (int i = 0; i < orderList.size(); i++) {
            Date TheDate = dateFormat.parse(dateFormat.format(orderList.get(i).getoDate()));
            if ((dateFirst.before(TheDate)||dateFirst.equals(TheDate))
                    && (dateLast.after(TheDate)||dateLast.equals(TheDate))
                    && orderList.get(i).getoFlag().equals("1")) {
                nowOrder.add(orderList.get(i));
            }
        }
        return nowOrder;
    }
}
