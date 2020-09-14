package com.imooc.controller;

import com.imooc.pojo.Bgm;
import com.imooc.service.BgmService;
import com.imooc.service.imp.BgmServiceImp;
import com.imooc.utils.IMoocJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @PostMapping("/queryBgmList")
    @ResponseBody
    public IMoocJSONResult list(){

        List<Bgm> list = this.bgmService.list();

       if (CollectionUtils.isEmpty(list)){
            return IMoocJSONResult.errorMsg("歌曲列表为空，请先添加歌曲");
        }

        Map<String,Object> map =  new HashMap<>();
        map.put("songs",list);
        return IMoocJSONResult.ok(map);
    }
}
