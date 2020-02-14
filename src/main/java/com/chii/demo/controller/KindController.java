package com.chii.demo.controller;
import com.chii.demo.mapper.KindMapper;
import com.chii.demo.pojo.Kind;
import com.chii.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

public class KindController {
    @Autowired
    KindMapper kindMapper;

    @RequestMapping("/AddKindInfo")
    public String AddKindInfo(Kind kind,Model model){
        String kId = kind.getkId();
        if(kind.getkId().equals("") || kind.getkName().equals("") || kind.getkMain().equals(""))
        {
            model.addAttribute("flag","0");
            model.addAttribute("getInfo","1");
            return "KindInfo";
        }else
        {
            List<Kind> kindList = kindMapper.selectAll();
            for (int i=0; i<kindList.size(); i++){
                if (kindList.get(i).getkId().equals(kId)){
                    model.addAttribute("flag","2");
                    model.addAttribute("getInfo","1");
                    return "KindInfo";
                }
            }
            kindMapper.insert(kind);
            model.addAttribute("flag","1");
            model.addAttribute("getInfo","1");
            return "KindInfo";
        }
    }

    @RequestMapping("/SearchKindInfo")
    public String SearchKindInfo(Kind kind, Model model){
        String id,name,main;
        id = kind.getkId();
        name = kind.getkName();
        main = kind.getkMain();
        List<Kind> kindList;
        kindList = kindMapper.selectSome(id,name,main);
        model.addAttribute("kindList",kindList);
        model.addAttribute("getInfo","2");
        return "KindInfo";
    }

    @GetMapping("/KindInfo")
    public String findKindInfo(Kind kind, Model model, @RequestParam String getInfo){
        List<Kind> kindList;
        kindList = kindMapper.selectAll();
        model.addAttribute("kindList",kindList);
        model.addAttribute("getInfo",getInfo);
        return "KindInfo";
    }

    @PostMapping("/EditKindInfo")
    public String EditKindInfo(Kind kind, Model model, HttpServletRequest request){
        String id,name,main;
        id = request.getParameter("id");
        name = request.getParameter("name");
        if (name!="")   //是否有拿到数据
        {
            main = request.getParameter("main");
            kind.setkId(id);
            kind.setkName(name);
            kind.setkMain(main);
            kindMapper.updateByPrimaryKey(kind);//修改数据库数据

            System.out.println("Id is "+id);
            System.out.println("Name is "+name);
            System.out.println("main is "+main);
        }
        return "KindInfo";
    }

    @PostMapping("/DelKindInfo")
    public String DelKindInfo(Kind kind, HttpServletRequest request){
        String id = request.getParameter("id");
        kindMapper.deleteByPrimaryKey(id);
        return "KindInfo";
    }
}
