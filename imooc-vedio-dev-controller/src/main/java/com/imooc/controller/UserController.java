package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {

        //文件保存路径
        String fileSpace = "D:\\imooc_dev_vedios";

        //用户保存的路径（相对路径）
        String uploadPathDB = "/"+userId+"/face";

        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null || files.length >0){

                //获取file的名字
                String filename = files[0].getOriginalFilename();

                if (StringUtils.isNotBlank(filename)){
                    //文件最终上传的保存路径
                    String path = fileSpace+uploadPathDB+"/"+filename;
                    //设置数据库保存的路径
                   uploadPathDB += ("/"+filename);

                    File outFile = new File(path);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
                         //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    outputStream = new FileOutputStream(outFile);
                    inputStream =  files[0].getInputStream();

                    //工具类进行拷贝
                    IOUtils.copy(inputStream,outputStream);

                }

            }else {
                return IMoocJSONResult.errorMsg("上传文件不能为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                outputStream.flush();
                outputStream.close();
            }
        }

        //进行路径的保存
        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        this.userService.updateUserInfo(user);

        return IMoocJSONResult.ok();
    }
}
