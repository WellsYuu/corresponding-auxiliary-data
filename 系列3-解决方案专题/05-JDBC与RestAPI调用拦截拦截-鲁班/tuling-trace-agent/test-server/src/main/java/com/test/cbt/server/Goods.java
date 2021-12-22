package com.test.cbt.server;/**
 * Created by Administrator on 2018/6/8.
 */

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/8
 **/
public class Goods implements java.io.Serializable {
    private String goodsName;
    private Long goodsId;
    private String desc;
    private String shopInfo;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }
}
