package com.zz.baseweb.tools;

import com.zz.baseweb.dal.em.RecordStatusEnum;
import com.zz.baseweb.dal.mapper.DownloadRecordDOMapper;
import com.zz.baseweb.dal.model.DownloadRecordDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DownloadTaskManager {

    /**
     * 录制最大线程数
     */
    private static final Integer MAX_THREAD_COUNT = 3;
    private ConcurrentHashMap<Long, RecordVideoThread> taskThreadList;
    private ConcurrentHashMap<Long, DownloadRecordDO> taskRecordList;
    private static DownloadTaskManager downloadTaskManager;

    @Resource
    private DownloadRecordDOMapper downloadRecordDOMapper;

    public DownloadTaskManager() {
        this.taskThreadList = new ConcurrentHashMap(MAX_THREAD_COUNT);
        this.taskRecordList = new ConcurrentHashMap(MAX_THREAD_COUNT);
    }

    public static DownloadTaskManager getInstance() {
        if (downloadTaskManager == null) {
            downloadTaskManager = new DownloadTaskManager();
        }
        return downloadTaskManager;
    }

    /**
     * 提交下载任务
     * @param record
     * @return 队列没满返回true，可以继续提交，队列满了则返回false，稍后提交
     */
    public boolean commitTask(DownloadRecordDO record) {
        if (taskThreadList.size() >= MAX_THREAD_COUNT) {
            return false;
        }

        RecordVideoThread d = startDownload(record);
        taskThreadList.put(record.getId(), d);
        taskRecordList.put(record.getId(), record);

        record.setStatus(RecordStatusEnum.DOWNLOADING.name());
        downloadRecordDOMapper.updateByPrimaryKey(record);

        return true;
    }

    private RecordVideoThread startDownload(DownloadRecordDO record) {
        RecordVideoThread thread = new RecordVideoThread(this);
        thread.setFilePath(record.getLocalPath());
        thread.setRecordId(record.getId());
        thread.setStreamURL(record.getUrl());
        thread.setWidth(record.getWidth());
        thread.setHeight(record.getHeight());

        thread.start();
        return thread;
    }

    /**
     * 停止任务
     * @param key
     */
    public void stopTask(Long key) {
        Thread recordVideoThread = taskThreadList.get(key);
        if (recordVideoThread != null) {
            recordVideoThread.interrupt();
        }
        taskThreadList.remove(key);
        taskRecordList.remove(key);
    }

    /**
     * 完成任务
     * @param key
     */
    public void completeTask(Long key) {
        taskThreadList.remove(key);
        DownloadRecordDO downloadRecordDO = taskRecordList.get(key);
        if (downloadRecordDO != null) {
            downloadRecordDO.setStatus(RecordStatusEnum.COMPLETE.name());
            downloadRecordDOMapper.updateByPrimaryKey(downloadRecordDO);
        }
        taskRecordList.remove(key);
    }
}
