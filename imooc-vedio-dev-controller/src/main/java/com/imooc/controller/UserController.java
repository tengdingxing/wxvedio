package com.imooc.controller;

import com.imooc.service.UserService;
import com.imooc.pojo.Users;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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

}
