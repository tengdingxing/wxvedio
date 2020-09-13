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

    /**
    *@Description 通过用户名和密码查询该用户是否存在
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/11
    *@Time 22:05
    */
    Boolean queryUserIsExistByNameAndPassword(String username,String password);

    /**
    *@Description 修改用户信息
    *@Param
    *@Return
    *@Author Tdxing
    *@Date 2020/9/13
    *@Time 10:59
    */
    public void updateUserInfo(Users user);
    
    /**
    *@Description 查询用户信息
    *@Param
    *@Return 
    *@Author Tdxing
    *@Date 2020/9/13
    *@Time 15:53
    */
    public Users queryUserInfo(String userId);
}
