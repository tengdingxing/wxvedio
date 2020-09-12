package com.imooc.service.imp;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {

        Users user = new Users();
        user.setUsername(username);
        Users result = this.usersMapper.selectOne(user);

        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {

        String userId = this.sid.nextShort();
        //设置用户的id
        user.setId(userId);
        //user.setNickname(user.getUsername());
        this.usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean queryUserIsExistByNameAndPassword(String username, String password) {

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);

        Users result = this.usersMapper.selectOne(user);

        return result == null ? false : true;
    }
}
