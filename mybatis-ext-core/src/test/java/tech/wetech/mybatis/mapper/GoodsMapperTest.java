package tech.wetech.mybatis.mapper;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import tech.wetech.mybatis.BaseDataTest;
import tech.wetech.mybatis.ExtConfiguration;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.entity.Goods;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GoodsMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setup() throws Exception {
        DataSource dataSource = BaseDataTest.createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        Configuration configuration = new ExtConfiguration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setUseActualParamName(false); // to test legacy style reference (#{0} #{1})
        configuration.getTypeAliasRegistry().registerAlias(Goods.class);
        configuration.addMapper(GoodsMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Test
    public void testSelectAllGoods() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            List<Map<String, Object>> goods = goodsMapper.selectAllGoods();
            Assert.assertEquals(goods.size(), 100);
        }

    }

    @Test
    public void testSelectAllGoodsWithAnnotation() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            List<Goods> goods = goodsMapper.selectAllGoodsWithAnnotation();
            Assert.assertEquals(goods.size(), 100);
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            List<Goods> goods = Page.of(1, 10).list(goodsMapper::selectAll);
            Assert.assertEquals(goods.size(), 10);
        }
    }

    @Test
    public void testSelectByPrimaryKey() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods goods = goodsMapper.selectByPrimaryKey(10000L);
            Assert.assertEquals(goods.getId().longValue(), 10000L);
            Assert.assertEquals(goods.getName(), "轻奢纯棉刺绣水洗四件套");
            Assert.assertEquals(goods.getGoodsBrief(), "设计师原款，精致绣花");
        }
    }

    @Test
    public void testSelectByPrimaryKeyWithOptional() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods goods = goodsMapper.selectByPrimaryKeyWithOptional(10000L).get();
            Assert.assertEquals(goods.getId().longValue(), 10000L);
            Assert.assertEquals(goods.getName(), "轻奢纯棉刺绣水洗四件套");
            Assert.assertEquals(goods.getGoodsBrief(), "设计师原款，精致绣花");
        }
    }

    @Test
    public void testSelectOne() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods queryModel = new Goods();
            queryModel.setId(10097L);
            queryModel.setGoodsUnit("件");
            Goods goods = goodsMapper.selectOne(queryModel);
            Assert.assertEquals(goods.getName(), "彩色波点缓冲宠物牵引绳");
            Assert.assertEquals(goods.getGoodsBrief(), "精致合金，萌趣波点");
        }
    }

    @Test
    public void testSelectOneWithOptional() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods queryModel = new Goods();
            queryModel.setId(10097L);
            queryModel.setGoodsUnit("件");
            Goods goods = goodsMapper.selectOneWithOptional(queryModel).get();
            Assert.assertEquals(goods.getName(), "彩色波点缓冲宠物牵引绳");
            Assert.assertEquals(goods.getGoodsBrief(), "精致合金，萌趣波点");
        }
    }

    @Test
    public void testSelectList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods queryModel = new Goods();
            queryModel.setCategoryId(1017000L);
            queryModel.setGoodsUnit("件");
            List<Goods> goods = goodsMapper.selectList(queryModel);
            Assert.assertEquals(goods.size(), 10);
        }
    }

    @Test
    public void testExistsByPrimaryKey() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            boolean exists = goodsMapper.existsByPrimaryKey(999999L);
            Assert.assertFalse(exists);
        }
    }

    @Test
    public void testCount() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods queryModel = new Goods();
            queryModel.setCategoryId(1017000L);
            queryModel.setGoodsUnit("件");
            int count = goodsMapper.count(queryModel);
            Assert.assertEquals(count, 10);
        }
    }

    @Test
    public void testInsertSelective() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Goods goods = new Goods();
            goods.setCategoryId(1017000L);
            goods.setGoodsSn("xxxx");
            goods.setName("测试名称");
            goods.setBrandId(1001020L);
            goods.setGoodsUnit("件");
            goods.setGoodsBrief("");
            goods.setGoodsDesc("");
            goods.setIsOnSale(0);
            goods.setCreateTime(new Date());
            goods.setSortOrder(0L);
            goods.setAttributeCategory(0L);
            goods.setCounterPrice(new BigDecimal("0"));
            goods.setExtraPrice(new BigDecimal("0"));
            goods.setIsNew(0);
            goods.setKeywords("");
            goods.setGoodsNumber(0L);
            goods.setListPicUrl("");
            goods.setRetailPrice(new BigDecimal("0"));
            goods.setSellVolume(0L);
            goods.setPrimaryProductId(0L);
            goods.setUnitPrice(0.0D);
            goods.setPromotionDesc("");
            goods.setPromotionTag("");
            goods.setAppExclusivePrice(new BigDecimal("0"));
            goods.setIsAppExclusive(0);
            goods.setIsLimited(0);
            goods.setIsHot(0);
            goods.setPrimaryPicUrl("");
            goods.setIsDelete(0);
            goods.setVersion(0);
            int rows = goodsMapper.insertSelective(goods);
            Assert.assertEquals(rows, 1);
        }
    }


}
