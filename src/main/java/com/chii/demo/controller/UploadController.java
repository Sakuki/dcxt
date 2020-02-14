package com.chii.demo.controller;
//import com.google.gson.Gson;
import com.chii.demo.pojo.Menu;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


@Controller
public class UploadController {
    private static String PATH = "D:/IDEA/IdeaProjects/dcxt/src/main/resources/static/pic/";

    @RequestMapping(value = "/TTTT", method = RequestMethod.GET)
    String TTT(HttpServletRequest request, Model model){
        return "TTTT";
    }

//    @ApiOperation(value = "设置引导页-上传文件-旧|弃")
    @RequestMapping(value = "updateImage", method = RequestMethod.POST, consumes = "multipart/form" +
            "-data")
    public String updateImage(@ApiParam(name = "files", value = "上传图片列表")
                                  @RequestParam("files") MultipartFile[] files, Model model) throws IOException {
//        ResponeInfo responeInfo = new ResponeInfo();
        System.out.println("here is updateImage");
//        String c = files[0].getOriginalFilename();
        if (files[0].getOriginalFilename().equals("")) {
//            responeInfo.setMsg("没有文件上传");
//            return responeInfo;
            model.addAttribute("msg","没有文件上传");
            return "TTTT";
        }
        File f = new File(PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        //遍历保存图片到本地
        for (int i = 0; i < files.length; i++) {
//            //获取文件原始名称
//            String name = files[i].getOriginalFilename();
//
//            if (name == null) {
//                name = (i + 1) + ".jpg";
//            }
//            name = PATH + name;
            String filename = PATH + UUID.randomUUID() + ".jpg";
            //根据获取到的原始文件名创建目标文件
            File image = new File(filename);
            //将收到的文件传输到目标文件中
            files[i].transferTo(image);
        }
        model.addAttribute("msg","上传成功");
        return "TTTT";
    }

    @RequestMapping("/pic")
    @ResponseBody
    public String Pic(HttpServletRequest request) throws IOException {
        String data = request.getParameter("dataURL");
        if (data == null){ // 图像数据为空
            // model.addAttribute("msg","没有文件上传");
            return "没有文件上传";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码,对字节数组字符串进行Base64解码并生成图片
        byte[] b = decoder.decodeBuffer(data.replace("data:image/jpeg;base64,", ""));
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        String imgName = "01003.jpg";
        String dbUrl = "";
        String imgFilePath;
        // 生成jpeg图片D:\test\attendance\src\main\webapp\assets\images\leave
        imgFilePath = PATH+imgName;//新生成的图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        return "上传成功";
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
