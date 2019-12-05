package tech.wetech.mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wetech.mybatis.entity.User;
import tech.wetech.mybatis.example.Example;
import tech.wetech.mybatis.example.Sort;
import tech.wetech.mybatis.mapper.UserMapper;
import tech.wetech.mybatis.session.ExtConfiguration;
import tech.wetech.mybatis.session.ExtSqlSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 测试用例类
 */
public class MybatisExtTests {

    private static SqlSession sqlSession;

    private final Logger log = LoggerFactory.getLogger(MybatisExtTests.class);

    @BeforeClass
    public static void beforeClass() {
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
        SqlSessionFactory sqlSessionFactory = new ExtSqlSessionFactoryBuilder().build(configuration);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void testSelectByPrimaryKey() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKey(1);
        log.info("selectByPrimaryKey result: {}", user);
    }

    @Test
    public void testSelectById() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(1);
        log.info("selectById result: {}", user);
    }

    @Test
    public void testSelectByPrimaryKeyWithOptional() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKeyWithOptional(1).orElseThrow(() -> new RuntimeException("未查到数据"));
        log.info("selectByPrimaryKey result: {}", user);
    }

    @Test
    public void testInsert() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
//        user.setId(111);
        user.setUsername("222张三");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        user.setMobile("180xxxxxxxx");
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");
        user.setRegisterIp("127.0.0.1");
        user.setAvatar("aaaa");
        user.setWechatOpenId("222");
        int rows = mapper.insert(user);
//        sqlSession.commit();
        log.info("insertSelective result: {}", rows);
    }

    @Test
    public void testInsertSelective() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
//        user.setId(111);
        user.setUsername("张三");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        user.setMobile("180xxxxxxxx");
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");
        user.setRegisterIp("127.0.0.1");
        user.setAvatar("aaaa");
        user.setWechatOpenId("222");
        int rows = mapper.insertSelective(user);
//        sqlSession.commit();
        log.info("insertSelective result: {}", rows);
    }

    @Test
    public void testUpdateByPrimaryKey() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(111);
        user.setUsername("张三2223333");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        user.setMobile("180xxxxxxxx");
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");
        user.setRegisterIp("127.0.0.1");
        user.setAvatar("aaaa");
        user.setWechatOpenId("222");
        int rows = mapper.updateByPrimaryKey(user);
        log.info("updateByPrimaryKey result: {}", rows);
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(111);
        user.setUsername("张三");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        int rows = mapper.updateByPrimaryKeySelective(user);
        log.info("updateByPrimaryKey result: {}", rows);
    }

    @Test
    public void testSelectAll() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        log.info("selectAll result: {}", users);
    }

    @Test
    public void testSelectList() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(111);
//        user.setUsername("张三");
//        user.setPassword("aagewrwer");
//        user.setNickname("zhangsan");
        List<User> users = mapper.selectList(user);
        log.info("selectList result: {}", users);
    }

    @Test
    public void testSelectOne() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(111);
//        user.setUsername("张三");
//        user.setPassword("aagewrwer");
//        user.setNickname("zhangsan");
        User user1 = mapper.selectOne(user);
        log.info("selectList result: {}", user1);
    }

    @Test
    public void testSelectOneWithOptional() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        User user1 = mapper.selectOneWithOptional(user).orElseGet(() -> new User());
        log.info("selectList result: {}", user1);
    }

    @Test
    public void testExistsByPrimaryKey() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Boolean exists = mapper.existsByPrimaryKey(111);
        log.info("existsByPrimaryKey result: {}", exists);
    }

    @Test
    public void testInsertAll() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("张三111333");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        user.setMobile("180xxxxxxxx");
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");
        user.setRegisterIp("127.0.0.1");
        user.setAvatar("aaaa");
        user.setWechatOpenId("222");

        User user2 = new User();
        user2.setUsername("张三222333");
        user2.setPassword("aagewrwer");
        user2.setNickname("zhangsan");
        user2.setUserLevelId((byte) 22);
        user2.setMobile("180xxxxxxxx");
        user2.setRegisterTime(new Date());
        user2.setLastLoginTime(new Date());
        user2.setLastLoginIp("127.0.0.1");
        user2.setRegisterIp("127.0.0.1");
        user2.setAvatar("aaaa");
        user2.setWechatOpenId("222");

        int rows = mapper.insertAll(Arrays.asList(user, user2));
        log.info("insertAll result: {}", rows);
    }

    @Test
    public void testCount() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        int count = mapper.count(user);
        log.info("count result: {}", count);
    }

    @Test
    public void testCreateCriteria() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.createCriteria()
                .andEqualTo(User::getId, 122)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "select * from t_user")
                .selectList();
        log.info("createCriteria result: {}", users);
    }

    @Test
    public void testDeleteByPrimaryKey() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int rows = mapper.deleteByPrimaryKey(1);
//        sqlSession.commit();
        log.info("selectByPrimaryKey result: {}", rows);
    }

    @Test
    public void testSelectByExample() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Example<User> example = Example.of(User.class);

        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "select * from t_user");

        log.info("example:{}", example.getCriteria());
        example.setDistinct(true);
//        example.setLimit(1);
//        example.setOffset(2);
        example.setPage(2, 2);
        example.setSort(new Sort(Sort.Direction.DESC, "mobile", "username"));
        example.setForUpdate(true);
        List<User> users = mapper.selectByExample(example);
        log.info("selectByExample result: {}", users);
    }


    @Test
    public void testCountByExample() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Example<User> example = Example.of(User.class);

        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "select * from t_user");

        log.info("example:{}", example.getCriteria());
        Integer rows = mapper.countByExample(example);
        log.info("countByExample result: {}", rows);
    }

    @Test
    public void testDeleteByExample() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Example<User> example = Example.of(User.class);

        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "select * from t_user");
//        example.clear();
        int rows = mapper.deleteByExample(example);
        log.info("countByExample result: {}", rows);
    }

    @Test
    public void testUpdateByExample() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(999);
        user.setUsername("张三223223333");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        user.setUserLevelId((byte) 22);
        user.setMobile("180xxxxxxxx");
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp("127.0.0.1");
        user.setRegisterIp("127.0.0.1");
        user.setAvatar("aaaa");
        user.setWechatOpenId("222");

        Example<User> example = Example.of(User.class);
        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .andEqualTo(User::getUsername, "张三");
        int rows = mapper.updateByExample(user, example);
        log.info("updateByPrimaryKey result: {}", rows);
    }

    @Test
    public void testUpdateByExampleSelective() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("张三223223333");
        user.setPassword("aagewrwer");
        user.setNickname("zhangsan");
        Example<User> example = Example.of(User.class);
        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .andEqualTo(User::getUsername, "张三");
        int rows = mapper.updateByExampleSelective(user, example);
        log.info("updateByPrimaryKey result: {}", rows);
    }

    @Test
    public void testCreateExample() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.createExample()
                .setDistinct(true)
                .setColumns(User::getId, User::getBirthday, User::getRegisterTime)
                .setOrderByClause("id asc,register_time desc")
                .createCriteria()
                .andEqualTo(User::getId, 1)
                .selectOneWithOptional()
                .orElseThrow(() -> new RuntimeException("数据不存在"));
        log.info("createExample result: {}", user);
    }

//    @Test
//    public void testCustomMapper() {
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = mapper.selectByUsername("张三");
//        log.info("customMapper result: {}", users);
//    }

}
