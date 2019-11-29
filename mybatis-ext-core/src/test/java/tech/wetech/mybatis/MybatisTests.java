package tech.wetech.mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wetech.mybatis.mapper.UserMapper;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis 简单示例
 */
public class MybatisTests {

    private final Logger log = LoggerFactory.getLogger(MybatisTests.class);

    @Test
    public void testSimple() {
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
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        log.info("selectAll result:{}", mapper.selectAllUser());
        log.info("selectOne result:{}", mapper.existById(1));
    }

}
