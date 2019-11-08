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

public class MenuController {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    KindMapper kindMapper;

    @RequestMapping("/AddMenuInfo")
    public String AddMenuInfo(Menu menu,Kind kind,Model model){
        String kId;
        if(menu.getmId().equals("")||menu.getmName().equals("")||kind.getkName().equals(""))
        {
            model.addAttribute("flag","0");
            model.addAttribute("getInfo","1");
            return "MenuInfo";
        }else
        {
            kId = FindkId(kind.getkName());
            menu.setkId(kId);
            menuMapper.insert(menu);
            model.addAttribute("flag","1");
            model.addAttribute("getInfo","1");
            return "MenuInfo";
        }
    }

    @RequestMapping("/SearchMenuInfo")
    public String SearchMenuInfo(MenuAndKind menuAndKind, Model model){
        String kname,id,name,kid="";
        List<Menu> menuList,menuList1;
        List<Kind> kindList= kindMapper.selectAll();
        List<MenuAndKind> menuAndKindList;
        id = menuAndKind.getmId();
        name = menuAndKind.getmName();
        kname = menuAndKind.getkName();
        System.out.println(id+name+kname);
//        menuList1 = menuMapper.selectSome("","","","","","02");
        kid = FindkId(kname);
        menuList = menuMapper.selectSome(id, name, "","","",kid);//获取模糊查询后的数据
        menuAndKindList = findMenuAndKind(menuList,kindList);
        model.addAttribute("menuAndKindList",menuAndKindList);
        model.addAttribute("getInfo","2");
        return "MenuInfo";
    }

    public List<MenuAndKind> findMenuAndKind(List<Menu> menuList,List<Kind> kindList){
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        MenuAndKind menuAndKind;
        for(int i=0; i<menuList.size(); i++)
        {
            menuAndKind = new MenuAndKind(menuList.get(i).getmId(),menuList.get(i).getmName(),menuList.get(i).getmWay(),
                    menuList.get(i).getmFlavor(),menuList.get(i).getmIng(),menuList.get(i).getmPrice(),
                    menuList.get(i).getmPic(),"","","");
            menuAndKindList.add(menuAndKind);//数据一条一条添加到List里
            for (int j=0; j<kindList.size(); j++)
            {
                if (menuList.get(i).getkId().equals(kindList.get(j).getkId()))//将kId相同的数据添加到List里
                {
                    menuAndKindList.get(i).setkId(kindList.get(j).getkId());
                    menuAndKindList.get(i).setkName(kindList.get(j).getkName());
                    menuAndKindList.get(i).setkMain(kindList.get(j).getkMain());
                }
            }
        }
        return menuAndKindList;
    }

    @GetMapping("/MenuInfo")
    public String findMenuInfo(Menu menu, Model model, @RequestParam String getInfo){
        List<Menu> menuList;
        List<Kind> kindList;
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        menuList = menuMapper.selectAll();
        kindList = kindMapper.selectAll();
        menuAndKindList = findMenuAndKind(menuList,kindList);


//        menuAndKindList = menuMapper.selectKindName();
//        System.out.println(menuAndKindList.get(1).getkName());

//        for (int i = 0;i<menuList.size();i++)
//        {
//            for (int j = 0; j<kindList.size(); j++)
//            if (menuList.get(i).getkId().equals(kindList.get(j).getkId()))
//            {
//                menuList.get(i).setkId(kindList.get(j).getkName());//将kId改成对应的kName
//            }
//        }
//        model.addAttribute("menuList",menuList);
        model.addAttribute("menuAndKindList",menuAndKindList);
//        model.addAttribute("kindList",kindList);
        model.addAttribute("getInfo",getInfo);
        return "MenuInfo";
    }

    @RequestMapping("/findSelect")
    @ResponseBody
    public List findSelect() {//查找选项
        List<Kind> kindList;
        List<String> kNameList = new ArrayList<>();
        kindList = kindMapper.selectAll();
        for (int i=0;i <kindList.size();i++)
        {
            kNameList.add(kindList.get(i).getkName());
        }
        return kNameList;
    }

    @PostMapping("/EditMenuInfo")
    public String EditMenuInfo(Menu menu, Model model, HttpServletRequest request){
        String id,name,way,flavor,ing,price1,pic,kid,kName;
        Long price = null;
        id = request.getParameter("id");
        name = request.getParameter("name");
        if (name!="")   //是否有拿到数据
        {
            way = request.getParameter("way");
            flavor = request.getParameter("flavor");
            ing = request.getParameter("ing");
            price1 = request.getParameter("price");
            price1 = price1.trim();//去除空格
            if (price1.equals(""))
            {
            }else {
                price = Long.parseLong(price1);
                System.out.println("price is "+price);
            }
            pic = request.getParameter("pic");
            kName = request.getParameter("kName");
            menu.setmId(id);    //将修改数据添加到menu上
            menu.setmName(name);
            menu.setmWay(way);
            menu.setmFlavor(flavor);
            menu.setmIng(ing);
            menu.setmPrice(price);
            menu.setmPic(pic);
            kid = FindkId(kName);
            menu.setkId(kid);
            menuMapper.updateByPrimaryKeySelective(menu);//修改数据库数据
        }
        return "MenuInfo";
    }

    @PostMapping("/DelMenuInfo")
    public String DelMenuInfo(Menu menu, HttpServletRequest request){
        String id = request.getParameter("id");
        menuMapper.deleteByPrimaryKey(id);
        return "MenuInfo";
    }

    public String FindkId(String kName){
        String kId="";
        List<Menu> menuList = menuMapper.selectAll();
        List<Kind> kindList = kindMapper.selectAll();
        for (int i = 0; i<kindList.size(); i++)
        {
            if (kindList.get(i).getkName().equals(kName))
            {
                kId = kindList.get(i).getkId();
            }
        }
        return kId;
    }
}
