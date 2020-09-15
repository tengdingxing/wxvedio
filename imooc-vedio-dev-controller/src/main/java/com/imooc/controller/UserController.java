package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.Videos;
import com.imooc.service.UserService;
import com.imooc.service.VideoService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.config.VerifyToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private VerifyToken verifyToken;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;


    /**
    *@Description 头像上传接口
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/13
    *@Time 15:39
    */
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {

        //文件保存路径
        String fileSpace = "D:/imooc_dev_vedios";

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

        return IMoocJSONResult.ok(uploadPathDB);
    }

    /**
    *@Description 视频上传接口
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/14
    *@Time 9:37
    */

    @PostMapping("/uploadVedios")
    public IMoocJSONResult uploadFile(String userId,@RequestParam("file") MultipartFile[] file) throws IOException {

        //文件保存路径
        String fileSpace = "D:/imooc_dev_vedios";

        //用户保存的路径（相对路径）
        String uploadPathDB = "/"+userId+"/vedios";

        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filename;
        try {
            if (file != null || file.length >0){

                //获取file的名字
                filename = file[0].getOriginalFilename();


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
                    inputStream =  file[0].getInputStream();

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

        Videos videos = new Videos();
        videos.setUserId(userId);
        videos.setCreateTime(new Date());
        videos.setAudioId(null);
        videos.setCoverPath(null);
        videos.setVideoDesc("");
        videos.setVideoPath(uploadPathDB);
        videos.setLikeCounts(0l);
        videos.setVideoHeight(10);
        videos.setVideoWidth(10);
        videos.setStatus(1);
        videos.setVideoSeconds(10.1f);

        this.videoService.save(videos);

        return IMoocJSONResult.ok(uploadPathDB);
    }

    /**
    *@Description 查询用户信息
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/13
    *@Time 15:39
    */
    @PostMapping("/queryUserInfo")
    public IMoocJSONResult queryUserInfo(@RequestBody Map<String,String>map){

        //进行token校验
        Boolean flag = this.verifyToken.verifyToken(map);
        if (!flag){
            return IMoocJSONResult.errorTokenMsg("token失效");
        }

        //获取用户id
        String userId = map.get("userId");
        //判断用户的id是否为空
        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("不存在这个id");
        }

        //判断用户是否存在
        Users user = this.userService.queryUserInfo(userId);

        if (user == null){
            return IMoocJSONResult.errorMsg("用户不存在");
        }

        Map<String,Object> return_map = new HashMap<>();
        return_map.put("user",user);

        return IMoocJSONResult.ok(return_map);
    }

}
