package com.zz.baseweb.dto;

import lombok.Data;

@Data
public class CommitRecordTaskRequest extends BaseReqeust {

    private Integer width;

    private Integer height;

    private String urlStr;

    private String title;

    private String videoId;
    private Integer type;
}
