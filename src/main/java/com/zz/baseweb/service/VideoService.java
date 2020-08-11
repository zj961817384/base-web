package com.zz.baseweb.service;

import com.zz.baseweb.dal.em.RecordStatusEnum;
import com.zz.baseweb.dal.mapper.DownloadRecordDOMapper;
import com.zz.baseweb.dal.model.DownloadRecordDO;
import com.zz.baseweb.dal.model.DownloadRecordDOExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.List;

@Service
public class VideoService {

    @Resource
    DownloadRecordDOMapper downloadRecordDOMapper;

    public void commitDownloadTask(String videoId, String title, String urlStr, Integer width, Integer height, Integer type) {
        DownloadRecordDO downloadRecordDO = new DownloadRecordDO();
        downloadRecordDO.setMovieId(videoId);
        downloadRecordDO.setTitle(title);
        downloadRecordDO.setUrl(urlStr);
        downloadRecordDO.setType(type);
        downloadRecordDO.setWidth(width);
        downloadRecordDO.setHeight(height);
        downloadRecordDO.setStatus(RecordStatusEnum.INIT.name());
        downloadRecordDO.setLocalPath(createVideoLocalPath(videoId, type));

        DownloadRecordDO record = saveDownloadRecord(downloadRecordDO);

    }

    /**
     * 创建视频本地存储路径
     * @param id
     * @return
     */
    private String createVideoLocalPath(String id, Integer type) {
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        StringBuilder path = new StringBuilder(homeDirectory.getAbsolutePath());
        path.append(File.separator)
                .append("base-web-download")
                .append(File.separator)
                .append("video")
                .append(File.separator)
                .append(type)
                .append(File.separator);
        path.append(id).append(".flv");
        return path.toString();
    }

    /**
     * 保存下载记录到数据库
     * @param downloadRecordDO
     * @return
     */
    private DownloadRecordDO saveDownloadRecord(DownloadRecordDO downloadRecordDO) {
        DownloadRecordDOExample example = new DownloadRecordDOExample();
        example.createCriteria()
                .andMovieIdEqualTo(downloadRecordDO.getMovieId())
                .andUrlEqualTo(downloadRecordDO.getUrl());
        List<DownloadRecordDO> list = downloadRecordDOMapper.selectByExample(example);
        if (list.size() == 0) {
            downloadRecordDOMapper.insertSelective(downloadRecordDO);
            return downloadRecordDO;
        }
        return list.get(0);
    }
}
