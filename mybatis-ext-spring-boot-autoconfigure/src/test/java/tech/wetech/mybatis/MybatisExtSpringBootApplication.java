package tech.wetech.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "tech.wetech.mybatis.mapper")
public class MybatisExtSpringBootApplication {

}
