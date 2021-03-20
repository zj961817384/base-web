package com.zz.baseweb.dal.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DatabaseBeanConfig {

    private String url = "jdbc:mysql://localhost:3306/video?characterEncoding=utf-8&useUnicode=true&useSSL=true";
    private String username = "root";
    private String password = "Abcd1234";

    @Bean
    public DataSource videoDataSource() {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setUrl(url);
        pooledDataSource.setUsername(username);
        pooledDataSource.setPassword(password);
        pooledDataSource.setDriver("com.mysql.jdbc.Driver");
        pooledDataSource.setPoolMaximumIdleConnections(0);
        pooledDataSource.setPoolPingEnabled(true);
        pooledDataSource.setPoolPingQuery("select 1");
        return pooledDataSource;
    }

    @Bean
    public SqlSessionFactory masterSqlSessionFactory(@Autowired @Qualifier("videoDataSource") DataSource videoDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(videoDataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zz.baseweb.dal.model");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager masterTransactionManager(@Autowired @Qualifier("videoDataSource") DataSource videoDataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(videoDataSource);
        return manager;
    }
}
