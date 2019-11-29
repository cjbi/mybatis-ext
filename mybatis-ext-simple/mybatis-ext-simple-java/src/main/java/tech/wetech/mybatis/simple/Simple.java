package tech.wetech.mybatis.simple;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wetech.mybatis.session.ExtConfiguration;
import tech.wetech.mybatis.session.ExtSqlSessionFactoryBuilder;
import tech.wetech.mybatis.simple.mapper.UserMapper;

import javax.sql.DataSource;
import java.util.Properties;

public class Simple {

    public static final SqlSessionFactory sqlSessionFactory;

    public static final Logger log = LoggerFactory.getLogger(Simple.class);

    static {
        //新建一个连接池方式的数据源工厂
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        //设置数据源
        Properties systemProperties = System.getProperties();
        //-Ddriver=<驱动> -Durl=<数据库连接> -Dusername=<用户名> -Dpassword=<密码>
        String driver = systemProperties.getProperty("driver");
        String url = systemProperties.getProperty("url");
        String username = systemProperties.getProperty("username");
        String password = systemProperties.getProperty("password");
        assert driver != null : "driver cannot be null";
        assert url != null : "url cannot be null";
        assert username != null : "username cannot be null";
        assert password != null : "password cannot be null";
        Properties properties = new Properties();
        properties.setProperty("driver", driver);
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        pooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
        //新建会话工厂
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        ExtConfiguration configuration = new ExtConfiguration(environment);
        configuration.setLogImpl(Log4jImpl.class);
        //添加Mapper映射
        configuration.addMapper(UserMapper.class);
        sqlSessionFactory = new ExtSqlSessionFactoryBuilder().build(configuration);
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static void main(String[] args) {
        SqlSession sqlSession = Simple.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        log.info("mybatis-ext查询出的所有用户：{}",mapper.selectAll());
        log.info("注解方式查询出的所有用户：{}",mapper.selectAllUserWithAnnotation());
        log.info("xml方式查询出的所有用户：{}",mapper.selectAllUser());
    }

}
