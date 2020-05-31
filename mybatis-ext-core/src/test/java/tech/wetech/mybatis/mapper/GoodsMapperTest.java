package tech.wetech.mybatis.mapper;

import org.apache.ibatis.logging.stdout.StdOutImpl;
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
import tech.wetech.mybatis.domain.Sort;
import tech.wetech.mybatis.entity.Goods;
import tech.wetech.mybatis.example.Criteria;
import tech.wetech.mybatis.example.Example;

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
        configuration.setLogImpl(StdOutImpl.class);
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

    @Test
    public void testSelectByExampleWithSub() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Example<Goods> example = Example.of(Goods.class);
            example.or()
                    .orEqualTo(Goods::getName, "轻奢纯棉刺绣水洗四件套")
                    .andEqualTo(Goods::getId, 10000);
            example.or()
                    .andEqualTo(Goods::getGoodsBrief, "厚实舒适");
            example.and()
                    .andLessThanOrEqualTo(Goods::getId, 10099)
                    .andGreaterThanOrEqualTo(Goods::getId, 10000);
            Criteria<Goods> criteria = new Criteria<>();
            criteria.andIsNull("brandId").andLessThan(Goods::getGoodsNumber, 1L);
            example.or(criteria);
            List<Goods> users = goodsMapper.selectByExample(example);
            Assert.assertEquals(2, users.size());
            Assert.assertEquals("轻奢纯棉刺绣水洗四件套", users.get(0).getName());
            Assert.assertEquals("埃及进口长绒棉毛巾", users.get(1).getName());
        }
    }

    @Test
    public void testSelectSort() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Example<Goods> example = Example.of(Goods.class);
            example.setSort(Sort.by("id").desc());
            List<Goods> goods = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods.get(0).getId(), Long.valueOf(10099));
            example.setSort(Sort.by("name", Sort.Direction.DESC).and("id"));
            List<Goods> goods2 = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods2.get(0).getId(), Long.valueOf(10041));
            example.setSort(Sort.by("name", Sort.Direction.DESC).and("id", Sort.Direction.DESC));
            List<Goods> goods3 = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods3.get(0).getId(), Long.valueOf(10041));
        }
    }

    @Test
    public void testSelectSortWithLambda() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Example<Goods> example = Example.of(Goods.class);
            example.setSort(Sort.by(Goods::getId).desc());
            List<Goods> goods = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods.get(0).getId(), Long.valueOf(10099));
            example.setSort(Sort.by(Goods::getName, Sort.Direction.DESC).and(Goods::getId));
            List<Goods> goods2 = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods2.get(0).getId(), Long.valueOf(10041));
            example.setSort(Sort.by(Goods::getName, Sort.Direction.DESC).and(Goods::getId, Sort.Direction.DESC));
            List<Goods> goods3 = goodsMapper.selectByExample(example);
            Assert.assertEquals(goods3.get(0).getId(), Long.valueOf(10041));
        }
    }

    @Test
    public void testSelectWithPage() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GoodsMapper goodsMapper = session.getMapper(GoodsMapper.class);
            Page list = Page.of(1, 3, true).list(() -> goodsMapper.selectAllGoods());
            Assert.assertEquals(list.getTotal(), 100L);
            Assert.assertEquals(list.size(), 3);
            List<Map<String, Object>> list2 = goodsMapper.selectAllGoodsWithPage(Page.of(1, 5));
            Assert.assertEquals(list2.size(), 5);
        }
    }


}
