package com.chii.demo.controller;
import com.chii.demo.mapper.UserMapper;
import com.chii.demo.pojo.User;
import com.chii.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
//@RestController(path="UserController")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/Login")
    public String Login( Model model){
        model.addAttribute("message","0");
        return "Login";
    }

    @GetMapping("/LogOut")
    public String LogOut( Model model, HttpServletRequest request){
        model.addAttribute("message","0");
        request.getSession().removeAttribute("UserName");
        request.getSession().removeAttribute("UserId");
        request.getSession().invalidate();
        return "Login";
    }

    @PostMapping("/UserLogin")
    public String user(User user, Model model, HttpServletRequest request){
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid is "+uuid);

        String id,passwd;
        User user1;
        HttpSession session = request.getSession();
        id = user.getuId();
        passwd = user.getuPassword();
		if(id.equals("")||passwd.equals("")){
			model.addAttribute("message","2");
			return "Login";
		}
        List<User> userList;
        userList = userMapper.selectAll();
        for(int i =0; i < userList.size(); i++)
        {
            if(userList.get(i).getuId().equals(id)){
                if(userList.get(i).getuPassword().equals(passwd)){
                    user1 = userMapper.selectByPrimaryKey(id);
                    session.setAttribute("UserId",user1.getuId());
                    session.setAttribute("UserName",user1.getuName());
                    model.addAttribute("UserId",user1.getuId());
                    model.addAttribute("UserName",user1.getuName());
                    return "redirect:/Sys_Home";
                }
            }
        }
        System.out.println("账号或密码错误");
        model.addAttribute("message","1");
        return "Login";
    }

    @RequestMapping("/Sys_Home")
    public String Sys_Home(User user, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
//            model.addAttribute("UserId",);
            return "redirect:/Login";
        }
        String uId,uName;
        uId = (String)session.getAttribute("UserId");
        uName = (String)session.getAttribute("UserName");
        System.out.println(uId+uName);
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        return "Sys_Home";
    }

    @RequestMapping("/User_Home")
    public String User_Home(User user, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
//            model.addAttribute("UserId",);
            return "redirect:/Login";
        }
        String uId,uName;
        uId = (String)session.getAttribute("UserId");
        uName = (String)session.getAttribute("UserName");
        System.out.println(uId+uName);
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        return "User_Home";
    }

    @RequestMapping("/ChangePwd")
    public String ChangePwd(User user, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        String uId,uName;
        uId = (String)session.getAttribute("UserId");
        uName = (String)session.getAttribute("UserName");
        user = userMapper.selectByPrimaryKey(uId);
        model.addAttribute("user",user);
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        return "ChangePwd";
    }

    @RequestMapping("/ChangeMsg")
    public String ChangeMsg(User user, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        String uId = (String)session.getAttribute("UserId");
        String uName = (String)session.getAttribute("UserName");
		if( user.getuName().equals("") || user.getuAge().equals("") || 
		user.getuPassword().equals("") || user.getuSex().equals(""))
        {
			model.addAttribute("UserName",uName);
            model.addAttribute("UserId",uId);
            model.addAttribute("getInfo","0");
			return "ChangePwd";
		}else{
			user.setuId(uId);
			int getInfo = userMapper.updateByPrimaryKeySelective(user);
			model.addAttribute("getInfo",getInfo);
			model.addAttribute("UserName",uName);
			model.addAttribute("UserId",uId);
			return "ChangePwd";
		}
        
    }

    @RequestMapping("/AddEmpInfo")
    public String AddEmpInfo(User user,Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        String uId = user.getuId();
        if(user.getuId().equals("") || user.getuName().equals("") ||
                user.getuAge().equals("") || user.getuPassword().equals("") ||
                user.getuSex().equals(""))
        {
            String UserId = (String)session.getAttribute("UserId");
			String uName = (String)session.getAttribute("UserName");
			model.addAttribute("UserName",uName);
            model.addAttribute("UserId",UserId);
            model.addAttribute("flag","0");
            model.addAttribute("getInfo","1");
            return "EmpInfo";
        }else
        {
            List<User> userList = userMapper.selectAll();
            for (int i=0; i<userList.size(); i++){
                if (userList.get(i).getuId().equals(uId)){
                    String UserId = (String)session.getAttribute("UserId");
					String uName = (String)session.getAttribute("UserName");
					model.addAttribute("UserName",uName);
                    model.addAttribute("UserId",UserId);
                    model.addAttribute("flag","2");
                    model.addAttribute("getInfo","1");
                    return "EmpInfo";
                }
            }
            userMapper.insert(user);
            String UserId = (String)session.getAttribute("UserId");
			String uName = (String)session.getAttribute("UserName");
			model.addAttribute("UserName",uName);
            model.addAttribute("UserId",UserId);
            model.addAttribute("flag","1");
            model.addAttribute("getInfo","1");
            return "EmpInfo";
        }
    }

    @RequestMapping("/SearchEmpInfo")
    public String SearchEmpInfo(User user, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        String id,name;
        id = user.getuId();
        name = user.getuName();
        List<User> userList;
        userList = userMapper.selectSome(id,name);
        String uId = (String)session.getAttribute("UserId");
		String uName = (String)session.getAttribute("UserName");
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        model.addAttribute("userList",userList);
        model.addAttribute("getInfo","2");
        return "EmpInfo";
    }

    @GetMapping("/EmpInfo")
    public String findEmpInfo(User user, Model model,@RequestParam String getInfo, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            System.out.println("LogOut!!!");
            return "redirect:/Login";
        }
        List<User> userList;
        userList = userMapper.selectAll();
        String uId = (String)session.getAttribute("UserId");
		String uName = (String)session.getAttribute("UserName");
        model.addAttribute("UserName",uName);
        model.addAttribute("UserId",uId);
        model.addAttribute("userList",userList);
        model.addAttribute("getInfo",getInfo);
        return "EmpInfo";
    }
    @PostMapping("/EditEmpInfo")
    public String EditEmpInfo(User user, Model model, HttpServletRequest request){
        String id,password,name,age,sex;
        id = request.getParameter("id");
        name = request.getParameter("name");
        if (name!="")   //是否有拿到数据
        {
            password = request.getParameter("password");
            age = request.getParameter("age");
            sex = request.getParameter("sex");
            user.setuId(id);
            user.setuName(name);
            user.setuPassword(password);
            user.setuAge(age);
            user.setuSex(sex);
            userMapper.updateByPrimaryKey(user);//修改数据库数据

            System.out.println("userId is "+id);
            System.out.println("userName is "+name);
            System.out.println("userPassword is "+password);
            System.out.println("userSge is "+age);
            System.out.println("userSex is "+sex);
        }
        return "EmpInfo";
    }

    @PostMapping("/DelEmpInfo")
    public String DelEmpInfo(User user, HttpServletRequest request){
        String id = request.getParameter("id");
        userMapper.deleteByPrimaryKey(id);
        return "EmpInfo";
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "hello moto";
    }

    @GetMapping("/user2")
    public User test() {
        User user = userService.getUserInfoByUsername("101");
        System.out.println(user.getuPassword());
        return user;
    }
}
