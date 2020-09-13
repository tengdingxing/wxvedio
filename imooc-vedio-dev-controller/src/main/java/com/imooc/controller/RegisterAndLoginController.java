package com.imooc.controller;

import com.imooc.pojo.Audience;
import com.imooc.service.UserService;
import com.imooc.pojo.Users;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.JwtHelper;
import com.imooc.utils.MD5Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterAndLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Audience audience;


    /**
    *@Description 用户注册
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/11
    *@Time 21:58
    */
    @PostMapping("register")
    public IMoocJSONResult register(@RequestBody Users user) throws Exception {

        //判断用户名，密码是否为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }

        //判断用户用户名是否存在
        boolean flag = this.userService.queryUserNameIsExist(user.getUsername());

        if (!flag){
            //保存用户
            user.setNickname(user.getUsername());
            user.setFansCounts(0);
            user.setFollowCounts(0);
            user.setReceiveLikeCounts(0);
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            this.userService.saveUser(user);
        }else {
            return IMoocJSONResult.errorMsg("用户已经存在");
        }

        return IMoocJSONResult.ok();
    }

    /**
    *@Description 用户登入
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/11
    *@Time 21:59
    */

    @PostMapping("login")
    public IMoocJSONResult login(@RequestBody Users user) throws Exception {

        //判断用户名用户是否存在
        boolean flag = this.userService.queryUserNameIsExist(user.getUsername());
        if (!flag){
            return IMoocJSONResult.errorMsg("请先注册");
        }
        //判断密码是否正确
        String username = user.getUsername();
        String password = user.getPassword();

        Boolean b = this.userService.queryUserIsExistByNameAndPassword(username, MD5Utils.getMD5Str(password));
        if (!b){
            return IMoocJSONResult.errorMsg("密码错误");
        }

        //生成token
        String jwtToken = JwtHelper.createJWT(user.getUsername(),
                user.getId(),
                user.getPassword(),
                audience.getClientId(),
                audience.getName(),
                audience.getExpiresSecond()*1000,
                audience.getBase64Secret());

        String jwt_token = "bearer;" + jwtToken;
        Map<String,String> map = new HashMap<>();
        map.put("jwtToken",jwt_token);
        return IMoocJSONResult.ok(map);
    }

}
