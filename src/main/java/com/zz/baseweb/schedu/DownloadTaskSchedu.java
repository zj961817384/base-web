package com.zz.baseweb.schedu;

import com.zz.baseweb.dal.em.RecordStatusEnum;
import com.zz.baseweb.dal.mapper.DownloadRecordDOMapper;
import com.zz.baseweb.dal.model.DownloadRecordDO;
import com.zz.baseweb.dal.model.DownloadRecordDOExample;
import com.zz.baseweb.tools.DownloadTaskManager;
import org.apache.ibatis.session.RowBounds;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class DownloadTaskSchedu {

    @Resource
    private DownloadRecordDOMapper downloadRecordDOMapper;

    @Resource
    private DownloadTaskManager downloadTaskManager;

    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
//    @Scheduled(cron = "0/10 * * * * ?") //10s执行一次
    public void refreshDownloadTask() {
        DownloadRecordDOExample example = new DownloadRecordDOExample();
        example.createCriteria()
                .andStatusEqualTo(RecordStatusEnum.INIT.name());
        List<DownloadRecordDO> records = downloadRecordDOMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 5));
        System.out.println("待下载记录: " + records.stream().map(DownloadRecordDO::getId).collect(Collectors.toList()));
        for (DownloadRecordDO record : records) {
            if (record.getStatus().equals(RecordStatusEnum.INIT.name())){
                if (!downloadTaskManager.commitTask(record)) {
                    break;
                }
            }
        }
    }
}
