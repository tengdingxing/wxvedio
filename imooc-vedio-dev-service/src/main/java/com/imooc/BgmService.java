package com.imooc;

import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Bgm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BgmService {
    @Autowired
    private BgmMapper bgmMapper;


    public Map<String, Object> findAllUsers() {

       Map<String,Object>map = new HashMap<>();

        Bgm record =new Bgm();
        List<Bgm> select = this.bgmMapper.select(record);

        map.put("errcode","0");
        map.put("msg","");
        map.put("allUsers",select);
        return map;
    }
}
