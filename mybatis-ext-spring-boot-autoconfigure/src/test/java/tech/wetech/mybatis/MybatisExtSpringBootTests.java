package tech.wetech.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.entity.User;
import tech.wetech.mybatis.example.Example;
import tech.wetech.mybatis.mapper.UserMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisExtSpringBootTests {

    @Autowired
    private UserMapper mapper;

    private final Logger log = LoggerFactory.getLogger(MybatisExtSpringBootTests.class);

    @Test
    public void testSelectAll() {
        log.info("log: {}", mapper.selectAll());
    }

    @Test
    public void testSelectByPrimaryKey() {
        User user = mapper.selectByPrimaryKey(1);
        log.info("selectByPrimaryKey result: {}", user);
    }

    @Test
    public void testSelectById() {
        User user = mapper.selectById(1);
        log.info("selectById result: {}", user);
    }

    @Test
    public void testSelectByPrimaryKeyWrap() {
        User user = mapper.selectByPrimaryKeyWithOptional(1).orElseThrow(() -> new RuntimeException("未查到数据"));
        log.info("selectByPrimaryKey result: {}", user);
    }

    @Test
    public void testSelectByExample() {
        Example<User> example = Example.of(User.class);

        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "selectOne * from t_user");

        log.info("example:{}", example.getCriteria());
        example.setDistinct(true);
        example.setLimit(1);
        example.setOffset(2);
        List<User> users = mapper.selectByExample(example);
        log.info("selectByExample result: {}", users);
    }


    @Test
    public void testCountByExample() {
        Example<User> example = Example.of(User.class);

        example.createCriteria()
                .andEqualTo(User::getId, 1)
                .orEqualTo(User::getUsername, "张三")
                .orNotLike(User::getAvatar, "aaa")
                .orIsNull(User::getBirthday)
                .orBetween(User::getRegisterTime, new Date(), new Date())
                .orIn(User::getMobile, Arrays.asList(111, "aaa", 222))
                .andLike(User::getAvatar, "selectOne * from t_user");

        log.info("example:{}", example.getCriteria());
        Integer rows = mapper.countByExample(example);
        log.info("countByExample result: {}", rows);
    }

    @Test
    public void testCreateExample() {
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

    @Test
    public void testSelectAllWithThreadContext() {
        ThreadContext.setPage(1, 3, true);
        Page<User> users = (Page<User>) mapper.selectAll();
        log.info("testSelectAllWithThreadContext result: {}", users);
    }

    @Test
    public void testSelectUserWithPage() {
        Page page = new Page(1, 2, true);
        List<User> users = mapper.selectUserWithPage(page);
        log.info("testSelectUserWithPage result: {}", users);
    }

    @Test
    public void testSelectAllUserWithFunctionInterface() {
        Page page = new Page(1, 3, true);
        page.select(() -> mapper.selectAllUser());
        log.info("testSelectAllUserWithFunctionInterface result: {}", page);
    }

}
