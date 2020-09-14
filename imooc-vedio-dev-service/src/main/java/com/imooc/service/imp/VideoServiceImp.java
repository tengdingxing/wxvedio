package com.imooc.service.imp;

import com.imooc.mapper.VideoMapper;
import com.imooc.pojo.Videos;
import com.imooc.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImp implements VideoService {

    @Autowired
    private Sid sid;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void save(Videos video) {

        video.setId(this.sid.nextShort());

        this.videoMapper.insert(video);
    }
}
