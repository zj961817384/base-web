package com.zz.baseweb.dal.model;

import java.util.Date;
import lombok.Data;

@Data
public class DownloadRecordDO {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String movieId;

    private String localPath;

    private String url;

    private Integer type;

    private String status;

    private String title;

    private Integer width;

    private Integer height;

    public DownloadRecordDO(Long id, Date gmtCreate, Date gmtModified, String movieId, String localPath, String url, Integer type, String status, String title, Integer width, Integer height) {
        this.id = id;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.movieId = movieId;
        this.localPath = localPath;
        this.url = url;
        this.type = type;
        this.status = status;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public DownloadRecordDO() {
        super();
    }
}