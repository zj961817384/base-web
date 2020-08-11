package com.zz.baseweb.controller;

import com.zz.baseweb.dto.CommitRecordTaskRequest;
import com.zz.baseweb.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/record")
@RestController
public class VideoRecordController {

    @Resource
    VideoService videoService;

    @RequestMapping("/commitRecordTask")
    public String commitRecordTask(CommitRecordTaskRequest request) {
        videoService.commitDownloadTask(request.getVideoId(), request.getTitle(), request.getUrlStr(), request.getWidth(), request.getHeight(), request.getType());
        return "SUCCESS";
    }
}
