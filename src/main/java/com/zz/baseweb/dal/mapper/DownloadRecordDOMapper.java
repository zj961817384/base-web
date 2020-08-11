package com.zz.baseweb.dal.mapper;

import com.zz.baseweb.dal.model.DownloadRecordDO;
import com.zz.baseweb.dal.model.DownloadRecordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface DownloadRecordDOMapper {
    long countByExample(DownloadRecordDOExample example);

    int deleteByExample(DownloadRecordDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DownloadRecordDO record);

    int insertSelective(DownloadRecordDO record);

    List<DownloadRecordDO> selectByExampleWithRowbounds(DownloadRecordDOExample example, RowBounds rowBounds);

    List<DownloadRecordDO> selectByExample(DownloadRecordDOExample example);

    DownloadRecordDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DownloadRecordDO record, @Param("example") DownloadRecordDOExample example);

    int updateByExample(@Param("record") DownloadRecordDO record, @Param("example") DownloadRecordDOExample example);

    int updateByPrimaryKeySelective(DownloadRecordDO record);

    int updateByPrimaryKey(DownloadRecordDO record);
}