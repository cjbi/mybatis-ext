package tech.wetech.mybatis.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cjbi
 */
@SpringBootApplication
@MapperScan(basePackages = "tech.wetech.mybatis.simple.mapper")
public class MybatisExtSimpleSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisExtSimpleSpringbootApplication.class, args);
    }

}
