package tech.wetech.mybatis.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.wetech.mybatis.spring.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class MybatisSpringTests {

    private final Logger log = LoggerFactory.getLogger(MybatisSpringTests.class);

    @Autowired
    private UserMapper mapper;

    @Test
    public void testSimple() {
        log.info("log: {}", mapper.selectAllUser());
    }

}
