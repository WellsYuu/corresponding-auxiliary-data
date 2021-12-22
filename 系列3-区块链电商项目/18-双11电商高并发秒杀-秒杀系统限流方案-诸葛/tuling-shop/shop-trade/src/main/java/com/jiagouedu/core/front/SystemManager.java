package com.jiagouedu.core.front;

import com.google.common.collect.Lists;
import com.jiagouedu.core.cache.CacheProvider;
import com.jiagouedu.core.cache.SimpleCacheProvider;

import com.jiagouedu.services.common.*;
import com.jiagouedu.services.front.area.bean.Area;
import com.jiagouedu.services.front.attribute.bean.Attribute;


import com.jiagouedu.services.front.navigation.bean.Navigation;

import com.jiagouedu.services.front.product.bean.Product;
import com.jiagouedu.services.front.product.bean.ProductStockInfo;
import com.jiagouedu.services.manage.accountrank.bean.AccountRank;
import com.jiagouedu.services.manage.activity.bean.Activity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;


/**
 * 系统管理类.
 * 1、负责管理system.properties的东东
 * 2、负责缓存管理
 * @author wukong 图灵学院 QQ:245553999
 */
public class SystemManager {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SystemManager.class);
	private static Properties p = new Properties();
    private static CacheProvider cacheProvider = new SimpleCacheProvider();
	private static SystemManager instance;

    @PostConstruct
    public void afterPropertiesSet(){
        instance = this;
    }
	/**
	 * 商品目录，树形结构
	 */
//	private List<Catalog> catalogs = new LinkedList<Catalog>();//产品目录列表
//    private List<Catalog> catalogsArticle = new LinkedList<Catalog>();//文章目录列表
//    private List<Attribute> attrs;//属性集合
//    private Map<String,Attribute> attrsMap = new HashMap<String, Attribute>();//属性集合map

//    private OrdersReport ordersReport = new OrdersReport();//后台首页,统计数据
//    private Map<String,AccountRank> accountRankMap = new TreeMap<String, AccountRank>();//会员等级表
//    private Map<String,NotifyTemplate> notifyTemplateMap;//邮件模板
//    private List<Product> hotSearchProductList;//热门搜索的商品列表
//    private String alipayConfig;//支付宝卖家账号
//    private String commentTypeCode;//启用的评论插件的代号
//    private List<Hotquery> hotqueryList;//热门查询列表
//	public static Map<Integer, Integer> catalogMap = new HashMap<Integer, Integer>();//目录表，key:目录ID，value:目录顶级PID
	
//	/**
//	 * 目录的MAP形式，具有层级关系。key：主类别ID，value：主类别对象，可以通过该对象的getChildren()方法获取该主类别的所有的子类别集合
//	 */
//	private Map<String,Catalog> catalogsMap = new HashMap<String,Catalog>();
//
//	/**
//	 * 目录的MAP形式，具有层级关系。key：主类别code，value：主类别对象，可以通过该对象的getChildren()方法获取该主类别的所有的子类别集合
//	 */
//	private Map<String,Catalog> catalogsCodeMap = new HashMap<String,Catalog>();
//
//	//商品库存应该使用JAVA类库中的读写锁，key:商品ID，value：商品对象
//	private ConcurrentMap<String, ProductStockInfo> productStockMap;// = new ConcurrentHashMap<String, ProductStockInfo>();//商品库存表
//	private  Object product_stock_lock = new Object();//商品库存锁，操作商品库存的时候需要进行加锁
//	/**
//	 * 促销的商品 top=10
//	 */
//	private List<List<Product>> goodsTopList;
////	public static List<IndexMenu> indexMenuList;
//	private List<Navigation> navigations;
//	private List<Product> hotProducts;//热门商品
//	private List<Product> historyProducts;//浏览过的商品历史列表，仅限于当前session中存储
//	private List<Product> newProducts;//新品商品
//	private List<Product> saleProducts;//特价商品
//	@Deprecated
//	private List<Product> suijiProducts;//随机推荐的商品
//	private List<IndexImg> indexImages;//门户滚动图片
//	@Deprecated
//	private List<News> news;//文章列表
//	@Deprecated
	private Map<String,News> newsMap = new HashMap<String, News>();//文章map；key:code
//
//	private List<Catalog> newsCatalogs;//文章目录。文章目前只有一级目录
//	private List<News> noticeList;//系统通知
//	private Map<String, Area> areaMap = new HashMap<String, Area>();//省市区集合
//	private Map<String,Express> expressMap;//前台订单支付页面--物流列表
//	private Map<String,Advert> advertMap;//广告列表
//
	private static Map<String,String> manageExpressMap = new HashMap<String, String>();//后台发货页面物流公司列表
//	private AliyunOSS aliyunOSS;//阿里云存储的配置信息
////	public static Task task;//系统定时任务
//	private List<Product> indexLeftProduct;//加载首页左侧的商品列表，此位置的商品从全局加载
//	private Map<String,Activity> activityMap = new HashMap<String, Activity>();//所有活动列表
//
//	/**
//	 * 促销活动的商品列表activity_discountType_c=c
//	 * key:【r:减免；d:折扣；s:双倍积分】
//	 * value:【商品列表ArrayList】
//	 */
//	private Map<String,List<Product>> activityProductMap = new HashMap<String, List<Product>>();
//	private List<Product> activityScoreProductList;//积分商城商品列表
//	private List<Product> activityTuanProductList;//团购活动商品列表
//
//
//	/////////////////后台缓存///////////////////
//	/**
//	 * 后台类目查询的JSON字符串缓存
//	 */
//	private String productCatalogJsonStr;//商品类目JSON字符串缓存
//	private String articleCatalogJsonStr;//缓存类目JSON字符串缓存

    public static SystemManager getInstance(){
        return instance;
    }

    static {
		init();
	}
	private static void init(){
	/*	try {
			p.load(SystemListener.class
					.getResourceAsStream("/system.properties"));
//			code.load(new BufferedReader(new InputStreamReader(SystemListener.class
//					.getResourceAsStream("/code.properties"), "utf-8")));
			logger.info(p.toString());
//			log.info(code.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

        manageExpressMap.put("shunfeng", "顺风快递");
        manageExpressMap.put("ems", "EMS");
        manageExpressMap.put("shentong", "申通E物流");
        manageExpressMap.put("yuantong", "圆通速递");
        manageExpressMap.put("zhongtong", "中通速递");
        manageExpressMap.put("zhaijisong", "宅急送");
        manageExpressMap.put("yunda", "韵达快运");
        manageExpressMap.put("tiantian", "天天快递");
        manageExpressMap.put("lianbangkuaidi", "联邦快递");
        manageExpressMap.put("huitongkuaidi", "汇通快运");
	}
	
	public String getProperty(String key){
		return p.getProperty(key);
	}
	
	private Random random = new Random();



    //应用缓存管理

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(CacheProvider cacheProvider) {
        SystemManager.cacheProvider = cacheProvider;
    }

    private static String buildKey(String key) {
        return "SystemManager." + StringUtils.trimToEmpty(key);
    }
    private static void putCacheObject(String key, Serializable cacheObject){
        String key1 = buildKey(key);
        if(cacheObject == null){
            cacheProvider.remove(key1);
        } else {
            cacheProvider.put(key1, cacheObject);
        }
    }
    private static <T extends Serializable> T getCacheObject(String key){
        return (T)cacheProvider.get(buildKey(key));
    }

    /**
     * 产品目录列表
     * @return
     */
    public List<Catalog> getCatalogs() {
        return getCacheObject("catalogs");
    }

    public void setCatalogs(List<Catalog> catalogs) {
//        this.catalogs = catalogs;
        putCacheObject("catalogs", (Serializable)(catalogs));
    }

    /**
     * 文章目录列表
     * @return
     */
    public List<Catalog> getCatalogsArticle() {
        return getCacheObject("catalogsArticle");
    }

    public void setCatalogsArticle(List<Catalog> catalogsArticle) {
        putCacheObject("catalogsArticle", (Serializable)catalogsArticle);
    }

    /**
     * 属性集合
     * @return
     */
    public List<Attribute> getAttrs() {
        return getCacheObject("attrs");
    }

    public void setAttrs(List<Attribute> attrs) {
        putCacheObject("attrs", (Serializable)attrs);
    }

    /**
     * 文章列表
     * @return
     */
    public Map<String, News> getNewsMap() {
        return getCacheObject("newsMap");
    }

    public void setNewsMap(Map<String, News> newsMap) {
        putCacheObject("newsMap", (Serializable)newsMap);
    }

    /**
     * 属性集合map
     * @return
     */
    public Map<String, Attribute> getAttrsMap() {
        return getCacheObject("attrsMap");
    }

    public void setAttrsMap(Map<String, Attribute> attrsMap) {
        putCacheObject("attrsMap", (Serializable)attrsMap);
    }


    /**
     * 会员等级列表
     * @return
     */
    public Map<String, AccountRank> getAccountRankMap() {
        return getCacheObject("accountRankMap");
    }

    public void setAccountRankMap(Map<String, AccountRank> accountRankMap) {
        putCacheObject("accountRankMap", (Serializable)accountRankMap);
    }

    /**
     * 邮件模板列表
     * @return
     */
    public Map<String, NotifyTemplate> getNotifyTemplateMap() {
        return getCacheObject("notifyTemplateMap");
    }

    public void setNotifyTemplateMap(Map<String, NotifyTemplate> notifyTemplateMap) {
        putCacheObject("notifyTemplateMap", (Serializable)notifyTemplateMap);
    }

    /**
     * 热门搜索的商品列表
     * @return
     */
    public List<Product> getHotSearchProductList() {
        return getCacheObject("hotSearchProductList");
    }

    public void setHotSearchProductList(List<Product> hotSearchProductList) {
        putCacheObject("hotSearchProductList", (Serializable)(hotSearchProductList));
    }

    /**
     * 支付宝卖家账号
     * @return
     */
    public String getAlipayConfig() {
        return getCacheObject("alipayConfig");
    }

    public void setAlipayConfig(String alipayConfig) {
        putCacheObject("alipayConfig", alipayConfig);
    }

    /**
     * 启用的评论插件代号
     * @return
     */
    public String getCommentTypeCode() {
        return getCacheObject("commentTypeCode");
    }

    public void setCommentTypeCode(String commentTypeCode) {
        putCacheObject("commentTypeCode", commentTypeCode);
    }


    /**
     * 目录的MAP形式，具有层级关系。key：主类别ID，value：主类别对象，可以通过该对象的getChildren()方法获取该主类别的所有的子类别集合
     * @return
     */
    public Map<String, Catalog> getCatalogsMap() {
        return getCacheObject("catalogsMap");
    }

    public void setCatalogsMap(Map<String, Catalog> catalogsMap) {
        putCacheObject("catalogsMap", (Serializable)catalogsMap);
    }

    /**
     * 目录的MAP形式，具有层级关系。key：主类别code，value：主类别对象，可以通过该对象的getChildren()方法获取该主类别的所有的子类别集合
     * @return
     */
    public Map<String, Catalog> getCatalogsCodeMap() {
        return getCacheObject("catalogsCodeMap");
    }

    public void setCatalogsCodeMap(Map<String, Catalog> catalogsCodeMap) {
        putCacheObject("catalogsCodeMap", (Serializable)catalogsCodeMap);
    }

    /**
     * 商品库存信息,应该使用JAVA类库中的读写锁，key:商品ID，value：商品对象
     * @return
     */
    public Map<String, ProductStockInfo> getProductStockMap() {
        return getCacheObject("productStockMap");
    }

    public void setProductStockMap(Map<String, ProductStockInfo> productStockMap) {
        putCacheObject("productStockMap", (Serializable)productStockMap);
    }

    /**
     * 促销的商品 top=10
     * @return
     */
    public List<List<Product>> getGoodsTopList() {
        return getCacheObject("goodsTopList");
    }

    public void setGoodsTopList(List<List<Product>> goodsTopList) {
        putCacheObject("goodsTopList", (Serializable)(goodsTopList));
    }

    /**
     * 友情链接
     * @return
     */
    public List<Navigation> getNavigations() {
        return getCacheObject("navigations");
    }

    public void setNavigations(List<Navigation> navigations) {
        putCacheObject("navigations", (Serializable)(navigations));
    }

    /**
     * 热门商品
     * @return
     */
    public List<Product> getHotProducts() {
        return getCacheObject("hotProducts");
    }

    public void setHotProducts(List<Product> hotProducts) {
        putCacheObject("hotProducts", (Serializable)(hotProducts));
    }

    /**
     * 浏览过的商品历史列表，仅限于当前session中存储
     * @return
     */
    public List<Product> getHistoryProducts() {
        return getCacheObject("historyProducts");
    }

    public void setHistoryProducts(List<Product> historyProducts) {
        putCacheObject("historyProducts", (Serializable)(historyProducts));
    }

    /**
     * 新品商品
     * @return
     */
    public List<Product> getNewProducts() {
        return getCacheObject("newProducts");
    }

    public void setNewProducts(List<Product> newProducts) {
        putCacheObject("newProducts", (Serializable)(newProducts));
    }

    /**
     * 特价商品
     * @return
     */
    public List<Product> getSaleProducts() {
        return getCacheObject("saleProducts");
    }

    public void setSaleProducts(List<Product> saleProducts) {
        putCacheObject("saleProducts", (Serializable)(saleProducts));
    }

    /**
     * 门户滚动图片
     * @return
     */
    public List<IndexImg> getIndexImages() {
        return getCacheObject("indexImages");
    }

    public void setIndexImages(List<IndexImg> indexImages) {
        putCacheObject("indexImages", (Serializable)(indexImages));
    }

    /**
     * 文章目录。文章目前只有一级目录
     * @return
     */
    public List<Catalog> getNewsCatalogs() {
        return getCacheObject("newsCatalogs");
    }

    public void setNewsCatalogs(List<Catalog> newsCatalogs) {
        putCacheObject("newsCatalogs", (Serializable)(newsCatalogs));
    }

    /**
     * 系统通知
     * @return
     */
    public List<News> getNoticeList() {
        return getCacheObject("noticeList");
    }

    public void setNoticeList(List<News> noticeList) {
        putCacheObject("noticeList", (Serializable)(noticeList));
    }

    /**
     * 省市区集合
     * @return
     */
    public Map<String, Area> getAreaMap() {
        return getCacheObject("areaMap");
    }

    public void setAreaMap(Map<String, Area> areaMap) {
        putCacheObject("areaMap", (Serializable)areaMap);
    }

    /**
     * 前台订单支付页面--物流列表
     * @return
     */
    public Map<String, Express> getExpressMap() {
        return getCacheObject("expressMap");
    }

    public void setExpressMap(Map<String, Express> expressMap) {
//        this.expressMap = expressMap;
        putCacheObject("expressMap", (Serializable)(expressMap));
    }

    /**
     * 广告列表
     * @return
     */
    public Map<String, Advert> getAdvertMap() {
//        return advertMap;
        return getCacheObject("advertMap");
    }

    public void setAdvertMap(Map<String, Advert> advertMap) {
//        this.advertMap = advertMap;
        putCacheObject("advertMap", (Serializable)advertMap);
    }

    /**
     * 后台发货页面物流公司列表
     * @return
     */
    public Map<String, String> getManageExpressMap() {
//        return manageExpressMap;
        Map<String,String> cachedMap = getCacheObject("manageExpressMap");
        if(cachedMap != null){
            return cachedMap;
        }
        return SystemManager.manageExpressMap;
    }

    public void setManageExpressMap(Map<String, String> manageExpressMap) {
        putCacheObject("manageExpressMap", Lists.newArrayList(manageExpressMap));
    }


    /**
     * 加载首页左侧的商品列表，此位置的商品从全局加载
     * @return
     */
    public List<Product> getIndexLeftProduct() {
//        return indexLeftProduct;
        return getCacheObject("indexLeftProduct");
    }

    public void setIndexLeftProduct(List<Product> indexLeftProduct) {
//        this.indexLeftProduct = indexLeftProduct;
        putCacheObject("indexLeftProduct", Lists.newArrayList(indexLeftProduct));
    }

    /**
     * 所有活动列表
     * @return
     */
    public Map<String, Activity> getActivityMap() {
//        return activityMap;
        return getCacheObject("activityMap");
    }

    public void setActivityMap(Map<String, Activity> activityMap) {
//        this.activityMap = activityMap;
        putCacheObject("activityMap", (Serializable)(activityMap));
    }


    /**
     * 促销活动的商品列表activity_discountType_c=c
     * key:【r:减免；d:折扣；s:双倍积分】
     * value:【商品列表ArrayList】
     */
    public Map<String, List<Product>> getActivityProductMap() {
//        return activityProductMap;
        return getCacheObject("activityProductMap");
    }

    public void setActivityProductMap(Map<String, List<Product>> activityProductMap) {
//        this.activityProductMap = activityProductMap;
        putCacheObject("activityProductMap", (Serializable)activityProductMap);
    }

    /**
     * 积分商城商品列表
     * @return
     */
    public List<Product> getActivityScoreProductList() {
//        return activityScoreProductList;
        return getCacheObject("activityScoreProductList");
    }

    public void setActivityScoreProductList(List<Product> activityScoreProductList) {
//        this.activityScoreProductList = activityScoreProductList;
        putCacheObject("activityScoreProductList", (Serializable)(activityScoreProductList));
    }

    /**
     * 团购活动商品列表
     * @return
     */
    public List<Product> getActivityTuanProductList() {
//        return activityTuanProductList;
        return getCacheObject("activityTuanProductList");
    }

    public void setActivityTuanProductList(List<Product> activityTuanProductList) {
//        this.activityTuanProductList = activityTuanProductList;
        putCacheObject("activityTuanProductList", (Serializable)(activityTuanProductList));
    }

    /////////////////后台类目查询的JSON字符串缓存///////////////////
    /**
     * 商品类目JSON字符串缓存
     * @return
     */
    public String getProductCatalogJsonStr() {
//        return productCatalogJsonStr;
        return getCacheObject("productCatalogJsonStr");
    }

    public void setProductCatalogJsonStr(String productCatalogJsonStr) {
//        this.productCatalogJsonStr = productCatalogJsonStr;
        putCacheObject("productCatalogJsonStr", productCatalogJsonStr);
    }

    /**
     * 缓存类目JSON字符串缓存
     * @return
     */
    public String getArticleCatalogJsonStr() {
//        return articleCatalogJsonStr;
        return getCacheObject("articleCatalogJsonStr");
    }

    public void setArticleCatalogJsonStr(String articleCatalogJsonStr) {
//        this.articleCatalogJsonStr = articleCatalogJsonStr;
        putCacheObject("articleCatalogJsonStr", articleCatalogJsonStr);
    }
}
