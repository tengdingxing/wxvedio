package com.imooc.controller;

import com.imooc.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @GetMapping("allUsers")
    @ResponseBody
    public ResponseEntity<Map<String,Object>>findAllUsers(){

        Map<String,Object>map = new HashMap<>();

        map = this.bgmService.findAllUsers();

        return ResponseEntity.ok(map);
    }
}
