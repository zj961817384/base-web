package com.zz.baseweb.dal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackages = {"com.zz.baseweb.dal.mapper"},
        sqlSessionFactoryRef = "masterSqlSessionFactory"
)
public class DatabaseConfiguration {

}
