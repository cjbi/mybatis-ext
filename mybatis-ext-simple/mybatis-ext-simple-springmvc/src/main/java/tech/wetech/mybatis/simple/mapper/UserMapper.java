package tech.wetech.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.wetech.mybatis.mapper.BaseMapper;
import tech.wetech.mybatis.simple.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User, Integer> {

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

}
