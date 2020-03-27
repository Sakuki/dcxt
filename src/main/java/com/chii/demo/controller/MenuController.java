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
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Controller

public class MenuController {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    KindMapper kindMapper;
     private static String PATH = "D:/IDEA/IdeaProjects/dcxt/src/main/resources/static/pic";
	//private static String PATH = "/www/server/tomcat/webapps/resources/image/Sam/pic/";

    @RequestMapping("/AddMenuInfo")
    @ResponseBody
    public String AddMenuInfo(Menu menu,HttpServletRequest request) throws IOException {
        String id,name,kName;
        id = request.getParameter("id");
        name = request.getParameter("name");
        kName = request.getParameter("kName");
        if(id.equals("")||name.equals("")||kName.equals("请选择"))
        {
            return "0";
        }else
        {
            List<Menu> menuList = menuMapper.selectAll();
            for (int i=0; i<menuList.size(); i++){
                if (menuList.get(i).getmId().equals(id)||menuList.get(i).getmName().equals(name)){
                    return "2";
                }
            }

            menu = ThisMenu(request,menu);
            menuMapper.insertSelective(menu);
            return "1";
        }
    }

    @RequestMapping("/EditPic")
    @ResponseBody
    public String EditPic(Menu menu ,HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        String dataUrl = request.getParameter("dataUrl");
        if (dataUrl==""){
            return "0";
        }
        menu = menuMapper.selectByPrimaryKey(id);
        menu.setmPic("pic/"+id+".jpg");
        menuMapper.updateByPrimaryKeySelective(menu);
        upload(dataUrl,id);
        return "1";
    }

    @RequestMapping("/SearchMenuInfo")
    public String SearchMenuInfo(MenuAndKind menuAndKind, Model model, HttpServletRequest request){
        String kname,id,name,kid="";
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
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
        String uId = (String)session.getAttribute("UserId");
		String uName = (String)session.getAttribute("UserName");
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
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
    public String findMenuInfo(Menu menu, Model model, @RequestParam String getInfo, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        List<Menu> menuList;
        List<Kind> kindList;
        List<MenuAndKind> menuAndKindList = new ArrayList<>();
        menuList = menuMapper.selectAll();
        kindList = kindMapper.selectAll();
        menuAndKindList = findMenuAndKind(menuList,kindList);

        String uId = (String)session.getAttribute("UserId");
		String uName = (String)session.getAttribute("UserName");
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
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
    public String EditMenuInfo(Menu menu, Model model, HttpServletRequest request) throws IOException {
        String id,name,way,flavor,ing,price1,pic,kid,kName;
        Long price = null;
        id = request.getParameter("id");
        name = request.getParameter("name");
        if (name!="")   //是否有拿到数据
        {
            menu = ThisMenu(request, menu);
            menuMapper.updateByPrimaryKeySelective(menu);//修改数据库数据
        }
        return "MenuInfo";
    }

    @PostMapping("/DelMenuInfo")
    public String DelMenuInfo(Menu menu, HttpServletRequest request){
        String id = request.getParameter("id");
        menu = menuMapper.selectByPrimaryKey(id);
        if (menu.getmPic().equals("")){
			menuMapper.deleteByPrimaryKey(id);
        }else{
			File f1 = new File(PATH+id+".jpg");
			f1.delete();
			menuMapper.deleteByPrimaryKey(id);
		}
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

    public Menu ThisMenu(HttpServletRequest request, Menu menu) throws IOException {
        String data,id,name,kId,way,flavor,ing,price1,kName,pic;
        Long price=null;
        id = request.getParameter("id");
        name = request.getParameter("name");
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
        data = request.getParameter("dataURL");
        if (data!=""){
            pic = "pic/"+id+".jpg";
            menu.setmPic(pic);
            upload(data,id);
        }
        kName = request.getParameter("kName");
        kId = FindkId(kName);//使用名字查找ID
        menu.setkId(kId);
        menu.setmId(id);    //将修改数据添加到menu上
        menu.setmName(name);
        menu.setmWay(way);
        menu.setmFlavor(flavor);
        menu.setmIng(ing);
        menu.setmPrice(price);
        return menu;
    }

    public void upload(String data,String id) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码,对字节数组字符串进行Base64解码并生成图片
        byte[] b = decoder.decodeBuffer(data.replace("data:image/jpeg;base64,", ""));
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        String imgName = id+".jpg";
        String dbUrl = "";
        String imgFilePath;
        // 生成jpeg图片D:\test\attendance\src\main\webapp\assets\images\leave
        imgFilePath = PATH+imgName;//新生成的图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
    }
}
