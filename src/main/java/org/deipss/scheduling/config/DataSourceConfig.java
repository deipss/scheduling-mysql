package org.deipss.scheduling.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "org.deipss.scheduling.dal.mapper", sqlSessionTemplateRef = "schedulingSqlSessionTemplate")
public class DataSourceConfig {

    @Bean("schedulingSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("schedulingDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        return factoryBean.getObject();
    }

    @Bean("schedulingTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("schedulingTransactionManager") PlatformTransactionManager tx) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(tx);
        return transactionTemplate;
    }


    @Bean("schedulingTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("schedulingDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean("schedulingSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("schedulingSqlSessionFactory") SqlSessionFactory factory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(factory);
        return sqlSessionTemplate;
    }

    @Bean("schedulingDataSource")
    public DataSource dataSource() {
        DataSource ds = new MysqlDataSource();
        return ds;
    }

}
