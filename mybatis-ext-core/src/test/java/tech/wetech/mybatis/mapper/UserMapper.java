package tech.wetech.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    //    @Select("select * from weshop_user")
    List<Map<String, Object>> selectAllUser();

    @Select("select * from weshop_user")
    List<User> selectAllUserWithAnnotation();

    @Select("select * from weshop_user")
    List<User> selectUserWithPage(Page page);

    @Select("select * from weshop_user where id=#{id}")
    User selectById(@Param("id") Integer id, @Param("page") Page page);

    @Update("update weshop_user set username='dddd' where id=#{id}")
    int updateById(Integer id);

    @Select("select count(*) from weshop_user where id=#{id}")
    Boolean existById(Integer id);

}
