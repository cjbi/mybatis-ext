package tech.wetech.mybatis.spring.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.spring.custom.CustomMapper;
import tech.wetech.mybatis.spring.entity.User;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper extends CustomMapper<User> {

//    @Select("select * from weshop_user")
    List<Map<String, Object>> selectAllUser();

    @Select("select * from weshop_user")
    List<User> selectAllUserWithAnnotation();

    @Select("select * from weshop_user where id=#{id}")
    User selectById(Integer id);

    @Update("update weshop_user set username='dddd' where id=#{id}")
    int updateById(Integer id);

    @Select("select count(*) from weshop_user where id=#{id}")
    Boolean existById(Integer id);

    @Select("select * from weshop_user")
    List<User> selectUserWithPage(Page page);

}
