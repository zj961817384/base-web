package com.zz.baseweb.dal.em;

import lombok.Data;

/**
 * 下载记录状态枚举
 */
public enum  RecordStatusEnum {
    INIT("等待下载"),
    DOWNLOADING("下载中"),
    COMPLETE("下载成功"),
    FAIL("下载失败");

    private final String desc;

    RecordStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
