package tech.wetech.mybatis.entity;

import tech.wetech.mybatis.annotation.LogicDelete;
import tech.wetech.mybatis.annotation.SelectEntityKey;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_goods")
public class Goods {
    @Id
    @SelectEntityKey(statement = "VALUES IDENTITY_VAL_LOCAL()", before = false)
    private Long id;
    private Long categoryId;
    private String goodsSn;
    private String name;
    private Long brandId;
    private String goodsUnit;
    private String goodsBrief;
    private String goodsDesc;
    private Integer isOnSale;
    private Date createTime;
    private Long sortOrder;
    private Long attributeCategory;
    private BigDecimal counterPrice;
    private BigDecimal extraPrice;
    private Integer isNew;
    private String keywords;
    private Long goodsNumber;
    private String listPicUrl;
    private BigDecimal retailPrice;
    private Long sellVolume;
    private Long primaryProductId;
    private Double unitPrice;
    private String promotionDesc;
    private String promotionTag;
    private BigDecimal appExclusivePrice;
    private Integer isAppExclusive;
    private Integer isLimited;
    private Integer isHot;
    private String primaryPicUrl;
    @LogicDelete
    private Integer isDelete;
    @Version
    private Integer version;
    @Transient
    private Integer pageSize;
    @Transient
    private Integer pageNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getAttributeCategory() {
        return attributeCategory;
    }

    public void setAttributeCategory(Long attributeCategory) {
        this.attributeCategory = attributeCategory;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Long goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getListPicUrl() {
        return listPicUrl;
    }

    public void setListPicUrl(String listPicUrl) {
        this.listPicUrl = listPicUrl;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Long getSellVolume() {
        return sellVolume;
    }

    public void setSellVolume(Long sellVolume) {
        this.sellVolume = sellVolume;
    }

    public Long getPrimaryProductId() {
        return primaryProductId;
    }

    public void setPrimaryProductId(Long primaryProductId) {
        this.primaryProductId = primaryProductId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc;
    }

    public String getPromotionTag() {
        return promotionTag;
    }

    public void setPromotionTag(String promotionTag) {
        this.promotionTag = promotionTag;
    }

    public BigDecimal getAppExclusivePrice() {
        return appExclusivePrice;
    }

    public void setAppExclusivePrice(BigDecimal appExclusivePrice) {
        this.appExclusivePrice = appExclusivePrice;
    }

    public Integer getIsAppExclusive() {
        return isAppExclusive;
    }

    public void setIsAppExclusive(Integer isAppExclusive) {
        this.isAppExclusive = isAppExclusive;
    }

    public Integer getIsLimited() {
        return isLimited;
    }

    public void setIsLimited(Integer isLimited) {
        this.isLimited = isLimited;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
