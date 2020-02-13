package tech.wetech.mybatis.mapper;

import org.apache.ibatis.annotations.Select;
import tech.wetech.mybatis.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends BaseMapper<Goods> {
    List<Map<String, Object>> selectAllGoods();
    @Select("select * from t_goods")
    List<Goods> selectAllGoodsWithAnnotation();
}
