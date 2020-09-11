package com.imooc.service;

import com.imooc.pojo.Users;
import org.springframework.stereotype.Service;


public interface UserService {

    /**
    *@Description 判断用户名是否存在
    *@Param
    *@Return boolean
    *@Author Tdxing
    *@Date 2020/9/11
    *@Time 10:23
    */
    public boolean queryUserNameIsExist(String username);

    /**
    *@Description 保存用户
    *@Param
    *@Return null
    *@Author Tdxing
    *@Date 2020/9/11
    *@Time 10:25
    */
    public void saveUser(Users user);
}
