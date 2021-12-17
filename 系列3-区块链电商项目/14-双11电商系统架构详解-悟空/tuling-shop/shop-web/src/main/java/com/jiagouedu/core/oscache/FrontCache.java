package com.jiagouedu.core.oscache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jiagouedu.core.FrontContainer;
import com.jiagouedu.core.KeyValueHelper;
import com.jiagouedu.core.ManageContainer;
import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.core.pay.alipay.alipayescow.config.AlipayConfig;
import com.jiagouedu.core.util.DateTimeUtil;
import com.jiagouedu.services.front.advert.AdvertService;
import com.jiagouedu.services.front.advert.bean.Advert;
import com.jiagouedu.services.front.area.AreaService;
import com.jiagouedu.services.front.area.bean.Area;
import com.jiagouedu.services.front.attribute.AttributeService;
import com.jiagouedu.services.front.attribute.bean.Attribute;
import com.jiagouedu.services.front.catalog.CatalogService;
import com.jiagouedu.services.front.catalog.bean.Catalog;
import com.jiagouedu.services.front.comment.CommentService;
import com.jiagouedu.services.front.commentype.CommentTypeService;
import com.jiagouedu.services.front.commentype.bean.CommentType;
import com.jiagouedu.services.front.express.ExpressService;
import com.jiagouedu.services.front.express.bean.Express;
import com.jiagouedu.services.front.indexImg.IndexImgService;
import com.jiagouedu.services.front.indexImg.bean.IndexImg;
import com.jiagouedu.services.front.keyvalue.KeyvalueService;
import com.jiagouedu.services.front.keyvalue.bean.Keyvalue;
import com.jiagouedu.services.front.navigation.NavigationService;
import com.jiagouedu.services.front.navigation.bean.Navigation;
import com.jiagouedu.services.front.news.NewsService;
import com.jiagouedu.services.front.news.bean.News;
import com.jiagouedu.services.front.notifytemplate.NotifyTemplateService;
import com.jiagouedu.services.front.notifytemplate.bean.NotifyTemplate;
import com.jiagouedu.services.front.order.OrderService;
import com.jiagouedu.services.front.pay.PayService;
import com.jiagouedu.services.front.pay.bean.Pay;
import com.jiagouedu.services.front.product.ProductService;
import com.jiagouedu.services.front.product.bean.Product;
import com.jiagouedu.services.front.product.bean.ProductStockInfo;
import com.jiagouedu.services.front.systemsetting.SystemSettingService;
import com.jiagouedu.services.manage.accountrank.AccountRankService;
import com.jiagouedu.services.manage.accountrank.bean.AccountRank;
import com.jiagouedu.services.manage.activity.ActivityService;
import com.jiagouedu.services.manage.activity.bean.Activity;

import com.jiagouedu.services.manage.hotquery.HotqueryService;
import com.jiagouedu.services.manage.hotquery.bean.Hotquery;
import com.jiagouedu.services.manage.systemsetting.SystemSettingMService;
import com.jiagouedu.services.manage.systemsetting.bean.SystemSetting;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 *
 * @author wukong 图灵学院 QQ:245553999
 *
 */
public class FrontCache {
	private static final Logger logger = LoggerFactory.getLogger(FrontCache.class);

	/**
	 * manage后台
	 */
    @Autowired
	private KeyvalueService keyvalueService;
    @Autowired
	private SystemSettingService systemSettingService;

	@Autowired
	private SystemSettingMService systemSettingMService;

    @Autowired
	private NewsService newsService;
    @Autowired
	private CatalogService catalogService;
    @Autowired
	private IndexImgService indexImgService;
    @Autowired
	private NavigationService navigationService;
    @Autowired
	private AttributeService attributeService;
    @Autowired
	private PayService payService;
    @Autowired
	private CommentTypeService commentTypeService;
    @Autowired
	private AreaService areaService;
    @Autowired
	private ExpressService expressService;
    @Autowired
	private AdvertService advertService;
    @Autowired
	private NotifyTemplateService notifyTemplateService;
    @Autowired
//	private OssService ossService;
	private OrderService orderService;
    @Autowired
	private CommentService commentService;
    @Autowired
	private AccountRankService accountRankService;
    @Autowired
	private ActivityService activityService;
    @Autowired
	private HotqueryService hotqueryService;

	/**
	 * front前台
	 */
    @Autowired
	private ProductService productService;

    private static SystemManager systemManager;

    @Autowired
    public void setSystemManager(SystemManager systemManager) {
        FrontCache.systemManager = systemManager;
    }

    public HotqueryService getHotqueryService() {
		return hotqueryService;
	}

	public void setHotqueryService(HotqueryService hotqueryService) {
		this.hotqueryService = hotqueryService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	public AccountRankService getAccountRankService() {
		return accountRankService;
	}

	public void setAccountRankService(AccountRankService accountRankService) {
		this.accountRankService = accountRankService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setNotifyTemplateService(NotifyTemplateService notifyTemplateService) {
		this.notifyTemplateService = notifyTemplateService;
	}

	public void setAdvertService(AdvertService advertService) {
		this.advertService = advertService;
	}

	public void setExpressService(ExpressService expressService) {
		this.expressService = expressService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	public void setCommentTypeService(CommentTypeService commentTypeService) {
		this.commentTypeService = commentTypeService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public void setIndexImgService(IndexImgService indexImgService) {
		this.indexImgService = indexImgService;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setSystemSettingService(
			SystemSettingService systemSettingService) {
		this.systemSettingService = systemSettingService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	public void setKeyvalueService(KeyvalueService keyvalueService) {
		this.keyvalueService = keyvalueService;
	}

	/**
	 * 加载key-value键值对
	 */
	public void loadKeyValue() {
		logger.info("load...");
		KeyValueHelper.load(keyvalueService.selectList(new Keyvalue()));
	}

	/**
	 * 加载插件配置
	 */
	public void loadPlugConfig() {

		/**
		 * 加载支付宝配置
		 */
		Pay pay = new Pay();
		pay.setCode(Pay.pay_code_alipayescow);
		pay = payService.selectOne(pay);
		systemManager.setAlipayConfig(pay.getSeller());

		AlipayConfig.partner = pay.getPartner();
		AlipayConfig.key = pay.getKey1();

//		logger.error("SystemManager.alipayConfig="+SystemManager.alipayConfig);

		/**
		 * 加载评论配置
		 */
		CommentType commentType = new CommentType();
		commentType.setStatus(CommentType.commentType_status_y);
		commentType = commentTypeService.selectOne(commentType);
		String commentTypeCode = commentType.getCode();
        systemManager.setCommentTypeCode(commentTypeCode);
        logger.info("SystemManager.commentTypeCode=" + systemManager.getCommentTypeCode());
	}

	/**
	 * 加载所有的属性列表
	 */
	public void loadAttributeList() {
		List<Attribute> attrs = attributeService.selectList(new Attribute());
//		SystemManager.attrs = attrs;
        systemManager.setAttrs(attrs);

		/**
		 * 转换成map的形式存储
		 */
        Map<String, Attribute> attrsMap = Maps.newHashMap();
        if(attrs!=null && attrs.size()>0){
			Map<String, Integer> map = new HashMap<String, Integer>();

			for(int i=0;i<attrs.size();i++){
				Attribute mainAttr = attrs.get(i);
				if(mainAttr.getPid()==0){
					map.put(mainAttr.getId(), mainAttr.getCatalogID());
				}else{
					Attribute newAttr = new Attribute();
					newAttr.setPid(mainAttr.getPid());
                    attrsMap.put(mainAttr.getId(), newAttr);
				}
			}
			if(map.size()>0){
				for(Iterator<Entry<String, Attribute>> it = attrsMap.entrySet().iterator();it.hasNext();){
					Entry<String, Attribute> entry = it.next();
					String id = String.valueOf(entry.getValue().getPid());
//					logger.error("id="+id);
					if(map.get(id)!=null){
						entry.getValue().setCatalogID(map.get(id));
					}
				}
			}
		}
        systemManager.setAttrsMap(attrsMap);
	}

	/**
	 * 加载文章目录
	 */
	public void loadNewCatalogs() {
		Catalog c = new Catalog();
		c.setType("a");
		c.setPid("0");
		List<Catalog> newCatalogs = catalogService.selectList(c);
		if(newCatalogs!=null && newCatalogs.size()>0){
			for(int i=0;i<newCatalogs.size();i++){
				Catalog item = newCatalogs.get(i);

				//加载此目录下的所有文章列表
				News news = new News();
				news.setCatalogID(item.getId());
				List<News> newsList = newsService.selectList(news);
				item.setNews(newsList);
			}
		}
//		SystemManager.newCatalogs = newCatalogs;
        systemManager.setNewsCatalogs(newCatalogs);
	}

	/**
	 * 加载文章列表
	 */
	public void loadNews() {
		List<News> news = newsService.selectList(new News());
        Map<String, News> newsMap = Maps.newHashMap();
		if(news!=null && news.size()>0){
			for(int i=0;i<news.size();i++){
				News newsObj = news.get(i);
                newsMap.put(newsObj.getCode(),newsObj);
			}
		}
        systemManager.setNewsMap(newsMap);
	}

	/**
	 * 如果类别ID是是主类别，则返回该主类别的下面所有子类别
	 * @param catalogID
	 * @return
	 */
	public static List<Catalog> loadCatalogChildren(String catalogID) {
		try {
			logger.info(">>catalogID=" + catalogID);
			if(StringUtils.isBlank(catalogID)){
				throw new NullPointerException();
			}

//			Catalog catalog = SystemManager.catalogsMap.get(catalogID);
            Catalog catalog = systemManager.getCatalogsMap().get(catalogID);
			if(catalog==null){
				throw new NullPointerException();
			}

			if(catalog.getPid().equals("0")){
				if(catalog.getChildren()==null){
					logger.info(">>主类别catalog.getChildren()=0");
				}else{
					logger.info(">>主类别catalog.getChildren()=" + catalog.getChildren().size());
				}
				//主类别
				return catalog.getChildren();
			}else{
				//子类别

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPid(String catalogID){
		if(StringUtils.isBlank(catalogID)){
			throw new NullPointerException();
		}
        Map<String, Catalog> catalogsMap = systemManager.getCatalogsMap();
        Catalog catalog = catalogsMap.get(catalogID);
		if(catalog==null){
			throw new NullPointerException();
		}
		if(catalog.getPid().equals("0")){
			return catalog.getId();
		}else{
			catalog = catalogsMap.get(catalog.getPid());
			return catalog.getId();
		}
	}

	/**
	 * 根据商品目录ID加载属性列表
	 *
	 * @param catalogID
	 */
	public static List<Attribute> loadAttrByCatalogID(int catalogID) {
        List<Attribute> attrs = systemManager.getAttrs();
		if (attrs == null || attrs.size() == 0) {
			return null;
		}

		List<Attribute> targetAttrs = new LinkedList<Attribute>();
		for (int i = 0; i < attrs.size(); i++) {
			Attribute attr = attrs.get(i);
			if (attr.getPid() == -1) {
				continue;
			}

			if (attr.getCatalogID() == catalogID) {
                targetAttrs.add(attr);// 添加主属性
				attr.getAttrList().clear();
				int id = Integer.valueOf(attr.getId());
				// 添加子属性列表
				for (int j = 0; j < attrs.size(); j++) {
					Attribute attr2 = attrs.get(j);
					if (attr2.getPid() == id) {
						attr.getAttrList().add(attr2);// 添加子属性到主属性的内部集合
					}
				}
			}
		}

		logger.info("attrs=" + targetAttrs);
		return targetAttrs;
	}

	/**
	 * 加载目录列表,树形结构
	 * @param loadProduct 是否加载商品数据。true：加载，false:不加载
	 * @throws Exception
	 */
	public void loadCatalogs(boolean loadProduct) throws Exception {
		logger.info("load...");
//		Catalog c = new Catalog();
//		c.setType("p");
//		c.setPid("0");
//		List<Catalog> catalogs = catalogService.loadRoot(c);
//
//		System.out.println("result:" + catalogs);
//
//		SystemManager.catalogs = catalogs;

//		loadCatalogs2();

		List<Catalog> catalogs = loadCatalogs2("p");
        systemManager.setCatalogs(catalogs);
		List<Catalog> catalogsArticle = loadCatalogs2("a");
        systemManager.setCatalogsArticle(catalogsArticle);

//		logger.info("SystemManager.catalogs=" + SystemManager.catalogs.size());
//		logger.info("SystemManager.catalogsArticle="+SystemManager.catalogsArticle.size());

//		SystemManager.catalogsMap.clear();
//		SystemManager.catalogsCodeMap.clear();

        Map<String, Catalog> catalogsMap = Maps.newHashMap();
        Map<String, Catalog> catalogsCodeMap = Maps.newHashMap();
		putToMap(systemManager.getCatalogs(), loadProduct, catalogsMap, catalogsCodeMap);
        systemManager.setCatalogsMap(catalogsMap);
        systemManager.setCatalogsCodeMap(catalogsCodeMap);
	}

	/**
	 * 将商品目录结构转化为map的形式。
	 * @param catalogs
	 * @throws Exception
	 */
	public void putToMap(List<Catalog> catalogs,boolean loadProduct, Map<String, Catalog> catalogsMap, Map<String, Catalog> catalogsCodeMap) throws Exception{
		if(catalogs==null || catalogs.size()==0){
			return;
		}
		for(int i=0;i<catalogs.size();i++){
			Catalog item = catalogs.get(i);

			if(loadProduct){
				//超级菜单里面的推荐商品
				loadsuperMenuProducts(item);

				//加载每个目录下的热门推荐商品列表
				loadHotProductByCatalog(item);
			}

			catalogsMap.put(item.getId(),item);

			if(catalogsCodeMap.get(item.getCode())!=null){
				logger.error("item.code = " + item.getCode());
				throw new Exception("错误：商品类别code重复!");
			}

			catalogsCodeMap.put(item.getCode(),item);
			if(item.getChildren()!=null && item.getChildren().size()>0){

				//递归调用
				putToMap(item.getChildren(),loadProduct, catalogsMap, catalogsCodeMap);
			}
		}
	}

	/**
	 * 加载超级菜单的位置显示的热门推荐商品列表
	 * @param item
	 */
	private void loadsuperMenuProducts(Catalog item){
		if(!item.getPid().equals("0")){
			//子目录则不加载任何数据
			return;
		}

		if(item.getChildren()==null || item.getChildren().size()==0){
			return;
		}
		List<String> ids = new LinkedList<String>();
		for(int j=0;j<item.getChildren().size();j++){
			ids.add(item.getChildren().get(j).getId());
		}

		Product product = new Product();
		product.setTop(3);//显示的最大个数
		product.setProductIds(ids);//目录集合
		List<Product> superMenuProducts = productService.loadHotProductShowInSuperMenu(product);

//		if(superMenuProducts==null || superMenuProducts.size()==0){
//			logger.info("superMenuProducts = 0" + ",catalogCode = " + item.getCode());
//		}else{
//			logger.info("superMenuProducts = " + superMenuProducts.size()  + ",catalogCode = " + item.getCode());
//		}
		if(superMenuProducts!=null && superMenuProducts.size()>0){
			item.setSuperMenuProducts(superMenuProducts);
		}
	}

	/**
	 * 加载每个目录下的热门推荐商品列表（包括子目录）
	 * @param item
	 */
	private void loadHotProductByCatalog(Catalog item){
		if(item.getPid().equals("0") && (item.getChildren()==null || item.getChildren().size()==0)){
			return;
		}

		Product p = new Product();
		p.setTop(FrontContainer.default_page_left_product_size);

		if(item.getPid().equals("0")){
			List<Integer> ids = new LinkedList<Integer>();
			for(int j=0;j<item.getChildren().size();j++){
				ids.add(Integer.valueOf(item.getChildren().get(j).getId()));
			}
			p.setQueryCatalogIDs(ids);
		}else{
			p.setCatalogID(item.getId());
		}
		List<Product> hotProducts = productService.selectPageLeftHotProducts(p);

		if(hotProducts==null || hotProducts.size()==0){
			logger.info("loadHotProductByCatalog.hotProducts = 0" + ",catalogCode = " + item.getCode());
		}else{
			logger.info("loadHotProductByCatalog.hotProducts = " + hotProducts.size()  + ",catalogCode = " + item.getCode());
		}

		if(hotProducts!=null && hotProducts.size()>0){
			item.setHotProducts(hotProducts);
		}
	}

	/**
	 * 原来递归的方式修改为非递归方式。
	 * 非递归方法查询商品/文章目录结构，并且自动排序。
	 * @param type
	 */
	private List<Catalog> loadCatalogs2(String type){
        List<Catalog> catalogs = Lists.newLinkedList();
		Catalog cc = new Catalog();
		cc.setType(type);
		List<Catalog> catalogsList = catalogService.selectList(cc);
		if(catalogsList!=null){

			Map<String, Catalog> map = new HashMap<String, Catalog>();
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();

				if(StringUtils.isNotBlank(item.getPid()) && item.getPid().equals("0")){
					//是否在导航栏显示中文化
					if(item.getShowInNav().equals(Catalog.catalog_showInNav_y)){
						item.setShowInNavStr("是");
					}

					map.put(item.getId(), item);
					it.remove();
				}
			}

			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				if(StringUtils.isNotBlank(item.getPid())){
//							list.add(item);
					Catalog rootItem = map.get(item.getPid());
					if(rootItem!=null){
						if(rootItem.getChildren()==null){
							rootItem.setChildren(new LinkedList<Catalog>());
						}
						rootItem.getChildren().add(item);
					}
					it.remove();
				}
			}

			for(Iterator<Entry<String, Catalog>> it = map.entrySet().iterator();it.hasNext();){
				catalogs.add(it.next().getValue());
			}

			//对主类别和子类别进行排序
			Collections.sort(catalogs, new Comparator<Catalog>() {
				public int compare(Catalog o1, Catalog o2) {
					if (o1.getOrder1() > o2.getOrder1()) {
						return 1;
					} else if (o1.getOrder1() < o2.getOrder1()) {
						return -1;
					}
					return 0;
				}
			});

			for(int i=0;i<catalogs.size();i++){
				if(catalogs.get(i).getChildren()==null){
					continue;
				}
				Collections.sort(catalogs.get(i).getChildren(), new Comparator<Catalog>() {
					public int compare(Catalog o1, Catalog o2) {
						if (o1.getOrder1() > o2.getOrder1()) {
							return 1;
						} else if (o1.getOrder1() < o2.getOrder1()) {
							return -1;
						}
						return 0;
					}
				});
			}
		}
        return catalogs;
	}

	/**
	 * 加载门户滚动图片列表
	 */
	public void loadIndexImgs() {
		logger.info("loadIndexImgs...");
		IndexImg c = new IndexImg();
		List<IndexImg> indexImages = indexImgService.selectList(c);
        systemManager.setIndexImages(indexImages);
	}

	/**
	 * 加载促销的商品
	 */
	public void selectTopProducts() {
		Product e = new Product();
		e.setStatus(1);
		e.setTop(8);

		List<Product> result = productService.selectList(e);
		if (result == null || result.size() == 0) {
			return;
		}

		System.out.println("result=" + result);

		List<List<Product>> list = new LinkedList<List<Product>>();
		int nn = 0;
		int maxLen = 4;
		int i = 0;
		List<Product> row = new LinkedList<Product>();
		for (Iterator<Product> it = result.iterator(); it.hasNext();) {
			Product ee = (Product) it.next();
			row.add(ee);
			nn++;
			i++;
			if (nn == maxLen) {
				list.add(row);
				nn = 0;
				row = new LinkedList<Product>();
			}

			if ((result.size() == 1 + i)) {
				if (row.size() == 0) {
					row.add(ee);
				}
				list.add(row);
				break;
			}
		}
//		SystemManager.goodsTopList = list;// productTypeService.selectList(e);
        systemManager.setGoodsTopList(list);
		System.out.println("list====" + list);
	}

	/**
	 * 加载门户导航菜单
	 */
	public void loadNavigations() {
		logger.info("load...");
		Navigation nav = new Navigation();
		nav.setPosition("bottom");
		List<Navigation> navigations = navigationService.selectList(nav);
//		SystemManager.navigations = navigations;
        systemManager.setNavigations(navigations);
	}

	/**
	 * 加载特价商品、热门商品、新品商品  显示到首页的中下方位置
	 */
	public void loadProductsShowInIndex() {
//		SystemManager.newProducts = loadProducts(1);
//        SystemManager.saleProducts = loadProducts(2);
//        SystemManager.hotProducts = loadProducts(3);
        systemManager.setNewProducts(loadProducts(1));
        systemManager.setSaleProducts(loadProducts(2));
        systemManager.setHotProducts(loadProducts(3));
	}

	/**
	 * 加载随机推荐的商品
	 */
//	public void loadSuijiProducts() {
////		SystemManager.suijiProducts = loadProducts(2);
//		Product p = new Product();
//		p.setTop(10);
//		SystemManager.suijiProducts = productService.selectList(p);
//	}

	// 加载商品
	private List<Product> loadProducts(int type) {
		Product p = new Product();
		p.setTop(8);
		if (type == 1) {
			p.setIsnew(Product.Product_isnew_y);//最新
		} else if (type == 2) {
			p.setSale(Product.Product_sale_y);//特价
		} else if (type == 3) {
			p.setHot(true);//热门
		}
		return productService.selectList(p);
	}

	/**
	 * 加载系统通知
	 */
	private void loadNotices() {
//		Notice notice = new Notice(7);
//		notice.setOffset(0);
//		notice.setPageSize(7);
//		SystemManager.noticeList = noticeService.selectList(notice);

		News news = new News();
		news.setOffset(0);
		news.setPageSize(7);
		List<News> noticeList = newsService.selectNoticeList(news);
        systemManager.setNoticeList(noticeList);
	}

	/**
	 * 加载商品内存库存
	 */
	public void loadProductStock() {
		List<ProductStockInfo> list = productService.selectStockList(new Product());
        Map<String, ProductStockInfo> productStockMap = Maps.newHashMap();
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ProductStockInfo p = list.get(i);
                productStockMap.put(p.getId(), p);
            }
        }
        systemManager.setProductStockMap(productStockMap);
	}

	/**
	 * 加载指定商品的库存到内存
	 */
	public void loadProductStockByID(String productID) {
		if(StringUtils.isNotBlank(productID)){
			throw new NullPointerException("商品ID不能为空！");
		}

		Product p = new Product();
		p.setId(productID);
		List<ProductStockInfo> list = productService.selectStockList(p);
		if(list!=null && list.size()>0){
            Map<String, ProductStockInfo> stockInfoMap = systemManager.getProductStockMap();
            stockInfoMap.put(productID,list.get(0));
            systemManager.setProductStockMap(stockInfoMap);
		}
	}

	/**
	 * 加载物流列表
	 */
	private void loadExpress(){
		List<Express> expressList = expressService.selectList(new Express());
        Map<String, Express> expressMap = Maps.newHashMap();
		if(expressList!=null && expressList.size()>0){
			for(int i=0;i<expressList.size();i++){
				Express item = expressList.get(i);
				expressMap.put(item.getCode(), item);
			}
		}
        systemManager.setExpressMap(expressMap);
	}

	/**
	 * 加载广告列表
	 */
	public void loadAdvertList(){
		Advert advert = new Advert();
		advert.setStatus(Advert.advert_status_y);
		List<Advert> advertList = advertService.selectList(advert);
        Map<String, Advert> advertMap = Maps.newHashMap();
		if(advertList!=null && advertList.size()>0){
			for(int i=0;i<advertList.size();i++){
				Advert item = advertList.get(i);
				advertMap.put(item.getCode(), item);
			}
		}
        systemManager.setAdvertMap(advertMap);
	}

	/**
	 * 加载热门搜索列表
	 */
//	private void loadHotSearch(){
//		String productIDs = SystemManager.systemSetting.getProductIDs();
//		if(StringUtils.isNotBlank(productIDs)){
//			String[] arr = productIDs.split("\\|");
//			Product p = new Product();
//			if(p.getProductIds()==null){
//				p.setProductIds(new LinkedList<String>());
//			}else{
//				p.getProductIds().clear();
//			}
//
//			for(int i=0;i<arr.length;i++){
//				p.getProductIds().add(arr[i]);
//			}
//			SystemManager.hotSearchProductList = productService.selectHotSearch(p);
//		}
//	}

    /**
     * 加载省市区数据
     */
    public void loadArea(){
        logger.error("loadArea...");
        Area area = new Area();
//        area.setPcode("0");
        List<Area> areas = areaService.selectList(area);
        List<Area> rootAreas = Lists.newArrayList();
        for(Area a : areas){
            if("0".equals(a.getPcode())){
                rootAreas.add(a);
            }
        }
        if(rootAreas.size() == 0){
            return ;
        }

        for(Area a : rootAreas){
            getAreaByDigui2(a, areas);
        }

        Map<String, Area> map = new TreeMap<String, Area>();
        for(Area a : rootAreas){
            map.put(a.getCode(), a);
        }
        systemManager.setAreaMap(map);
//        SystemManager.areaMap = map;

//		logger.error("SystemManager.areaMap=="+SystemManager.areaMap);

//        String json = JSON.toJSONString(SystemManager.areaMap);
////		logger.error("json="+json);
//        try {
//            //写到文件
//            File file = new File("__area.txt");
//            logger.error(file.getAbsolutePath());
//            FileUtils.writeStringToFile(new File("__area.json"), json, "utf-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

	/**
	 * 递归加载省份下的：城市、区域、以后还会有街道的数据
	 * @param item
     * @param areas 所有的地区列表
	 */
	private void getAreaByDigui2(Area item, final List<Area> areas){
		List<Area> children = Lists.newArrayList();
        for(Area a : areas) {
            if(item.getCode().equals(a.getPcode())){
                children.add(a);
            }
        }

		item.setChildren(children);
        if(children.size() == 0){
            return ;
        }

		for(Area a : children){
			getAreaByDigui2(a, areas);
		}
	}
	/**
	 * 读取本地区域数据
	 */
	public void readJsonArea(){
		long start = System.currentTimeMillis();
		try {
			String path = FrontCache.class.getResource("/").getPath();
			logger.info("path = " + path);
			File file = new File(path + "__area.json");
			logger.info(file.getAbsolutePath());
			List<String> list = FileUtils.readLines(file, "utf-8");
			logger.info("list.size()="+list.size());

			Map<String, Area> areaMap = JSON.parseObject(list.get(0),new TypeReference<Map<String,Area>>(){});
            systemManager.setAreaMap(areaMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("readJsonArea time = " + (System.currentTimeMillis() - start));
	}

	/**
	 * 加载邮件模板列表
	 */
	public void loadNotifyTemplate(){
		List<NotifyTemplate> list = notifyTemplateService.selectList(null);
        Map<String, NotifyTemplate> notifyTemplateMap = Maps.newHashMap();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				NotifyTemplate item = list.get(i);
				notifyTemplateMap.put(item.getCode(), item);
			}
		}
        systemManager.setNotifyTemplateMap(notifyTemplateMap);
	}

	/**
	 * 加载会员等级列表
	 */
	private void loadAccountRank(){
		List<AccountRank> list = accountRankService.selectList(new AccountRank());
        Map<String, AccountRank> accountRankMap = Maps.newHashMap();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				AccountRank item = list.get(i);
				accountRankMap.put(item.getCode(), item);
			}
		}
        systemManager.setAccountRankMap(accountRankMap);
	}

	/**
	 * 加载热卖商品列表，此数据将会在门户的超级菜单上显示出来。
	 */
//	private void loadHotProductShowInSuperMenu(){
//		for(int i=0;i<SystemManager.catalogs.size();i++){
//			Catalog item = SystemManager.catalogs.get(i);
//
//			if(item.getChildren()==null || item.getChildren().size()==0){
//				continue;
//			}
//			List<String> ids = new LinkedList<String>();
//			for(int j=0;j<item.getChildren().size();j++){
//				ids.add(item.getChildren().get(j).getId());
//			}
//
//			Product product = new Product();
//			product.setTop(3);//显示的最大个数
////			product.setCatalogID(item.getId());
//			product.setProductIds(ids);
//			List<Product> superMenuProducts = productService.loadHotProductShowInSuperMenu(product);
//
//			if(superMenuProducts==null || superMenuProducts.size()==0){
//				logger.info("superMenuProducts = 0");
//			}else{
//				logger.info("superMenuProducts = " + superMenuProducts.size());
//			}
//			if(superMenuProducts!=null && superMenuProducts.size()>0){
//				item.setSuperMenuProducts(superMenuProducts);
//			}
//		}
//	}

	/**
	 * 加载首页左侧的商品列表，此位置的商品从全局加载
	 */
	private void loadIndexLeftProduct(){
		Product p = new Product();
		p.setTop(FrontContainer.default_page_left_product_size);
		List<Product> indexLeftProduct = productService.selectPageLeftHotProducts(p);
        systemManager.setIndexLeftProduct(indexLeftProduct);
	}

	/**
	 * 加载促销活动的商品
	 */
	public void loadActivityProductList(){
        Map<String, List<Product>> activityProductMap = Maps.newHashMap();
        Map<String, Activity> activityMap = systemManager.getActivityMap();
		logger.info("加载loadActivityProductList  >activity size:"+activityMap.size());
		if(activityMap.size()==0){
			return;
		}
		List<Product> list = productService.selectActivityProductList(null);
		if(list==null){
			logger.info("loadActivityProductList.list=0");
		}else{
			logger.info("loadActivityProductList.list="+list.size());
			for(int i=0;i<list.size();i++){
				Product p = list.get(i);
				Activity activity = activityMap.get(p.getActivityID());
				if(activity==null){
					logger.info(" p = " + p.getId());
					continue;
				}

				if(activity.getActivityType().equals(Activity.activity_activityType_c)){
					if(activity.getDiscountType().equals(Activity.activity_discountType_r)){
						addProductByDiscountType(p,activity, activityProductMap);
					}else if(activity.getDiscountType().equals(Activity.activity_discountType_d)){
						addProductByDiscountType(p,activity, activityProductMap);
					}else if(activity.getDiscountType().equals(Activity.activity_discountType_s)){
						addProductByDiscountType(p,activity, activityProductMap);
					}
				}
			}
		}
        systemManager.setActivityProductMap(activityProductMap);
	}

	//根据此商品的优惠类型,进行分组
	private void addProductByDiscountType(Product p,Activity activity, Map<String, List<Product>> activityProductMap) {
		List<Product> valueList = activityProductMap.get(activity.getDiscountType());
		if(valueList == null){
			valueList = new LinkedList<Product>();
			activityProductMap.put(activity.getDiscountType(), valueList);
		}

		//复制活动的属性过来
		p.setFinalPrice(String.valueOf(p.caclFinalPrice()));
		p.setExpire(activity.isExpire());
		p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
		p.setDiscountFormat(activity.getDiscountFormat());
		p.setMaxSellCount(activity.getMaxSellCount());
		valueList.add(p);
	}

	/**
	 * 加载积分商城商品列表
	 */
	public void loadActivityScoreProductList(){
		logger.info("loadActivityScoreProductList...");
        Map<String, Activity> activityMap = systemManager.getActivityMap();
        if(activityMap.size()==0){
			return;
		}

		List<String> productIds = new LinkedList<String>();
		for(Iterator<Entry<String, Activity>> it = activityMap.entrySet().iterator();it.hasNext();){
			Entry<String, Activity> entry = it.next();
			if(entry.getValue().getActivityType().equals(Activity.activity_activityType_j) &&
					StringUtils.isNotBlank(entry.getValue().getProductID())){
				String[] arr = entry.getValue().getProductID().split("\\|");
				for(int i=0;i<arr.length;i++){
					productIds.add(arr[i]);
				}
			}
		}
		logger.info("loadActivityScoreProductList...productIds="+productIds.toString());
        List<Product> activityScoreProductList = Lists.newArrayList();
        if(productIds.size()>0){
			Product queryProduct = new Product();
			queryProduct.setProductIds(productIds);
			activityScoreProductList = productService.selectActivityProductList(queryProduct);
			//从活动中拷贝属性过去
			for(int i=0;i<activityScoreProductList.size();i++){
				Product p = activityScoreProductList.get(i);
				Activity activity = activityMap.get(p.getActivityID());
				if(activity==null){
					logger.info(" p = " + p.getId());
					continue;
				}

				if(!activity.getActivityType().equals(Activity.activity_activityType_c)){
					p.setExchangeScore(activity.getExchangeScore());
					p.setExpire(activity.isExpire());
					p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
//					p.setDiscountFormat(activity.getDiscountFormat());
					p.setMaxSellCount(activity.getMaxSellCount());
				}
			}

		}
        systemManager.setActivityScoreProductList(activityScoreProductList);
	}
	/**
	 * 加载团购活动商品列表
	 */
	public void loadActivityTuanProductList(){
		logger.info("loadActivityTuanProductList...");
        Map<String, Activity> activityMap = systemManager.getActivityMap();
        if(activityMap.size()==0){
			return;
		}

		List<String> productIds = new LinkedList<String>();
		for(Iterator<Entry<String, Activity>> it = activityMap.entrySet().iterator();it.hasNext();){
			Entry<String, Activity> entry = it.next();
			if(entry.getValue().getActivityType().equals(Activity.activity_activityType_t) &&
					StringUtils.isNotBlank(entry.getValue().getProductID())){
				String[] arr = entry.getValue().getProductID().split("\\|");
				for(int i=0;i<arr.length;i++){
					productIds.add(arr[i]);
				}
			}
		}
		logger.info("loadActivityScoreProductList...productIds="+productIds.toString());
        List<Product> activityTuanProductList = Lists.newArrayList();
		if(productIds.size()>0){
			Product queryProduct = new Product();
			queryProduct.setProductIds(productIds);
			activityTuanProductList = productService.selectActivityProductList(queryProduct);

			//从活动中拷贝属性过去
			for(int i=0;i<activityTuanProductList.size();i++){
				Product p = activityTuanProductList.get(i);
				Activity activity = activityMap.get(p.getActivityID());
				if(activity==null){
					logger.info(" p = " + p.getId());
					continue;
				}

				if(activity.getActivityType().equals(Activity.activity_activityType_t)){
					p.setExchangeScore(activity.getExchangeScore());
					p.setExpire(activity.isExpire());
					p.setActivityEndDateTime(activity.getEndDate());//activity.getActivityEndDateTime());
//					p.setDiscountFormat(activity.getDiscountFormat());
					p.setMaxSellCount(activity.getMaxSellCount());
					p.setMinGroupCount(activity.getMinGroupCount());
					p.setTuanPrice(activity.getTuanPrice());
					p.setHasBuyGroupPerson(3212);//activity.getHasBuyGroupPerson());
				}
			}
		}
        systemManager.setActivityTuanProductList(activityTuanProductList);
	}

	/**
	 * 加载所有的活动列表
	 */
	public void loadActivityMap(){
        Map<String, Activity> activityMap = Maps.newHashMap();
		List<Activity> list = activityService.selectList(new Activity());
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Activity activity = list.get(i);
				activity.setExpire(activity.checkActivity());
				if(!activity.isExpire()){
					//计算活动多久结束，是否已结束
					activity.setActivityEndDateTime(DateTimeUtil.getActivityEndDateTimeString(activity.getEndDate()));
				}
				//折扣类型 ，则进行折扣计算
				if(activity.getDiscountType().equals(Activity.activity_discountType_d)){
					activity.setDiscountFormat(String.valueOf(Double.valueOf(activity.getDiscount()) / 10D));
				}
				activityMap.put(activity.getId(), activity);
			}
		}
		logger.info("加载loadActivityMap  >activity size:"+activityMap.size());
        systemManager.setActivityMap(activityMap);
	}

	/**
	 * 加载热门查询列表
	 */
	public void loadHotquery(){
		List<Hotquery> hotqueryList = hotqueryService.selectList(new Hotquery());
        systemManager.setHotqueryList(hotqueryList);
	}

	/**
	 * 加载全部的缓存数据
	 * @throws Exception
	 */
	public void loadAllCache() throws Exception {
		logger.info("loadAllCache...");
		loadHotquery();
		loadCatalogs(true);
		loadIndexLeftProduct();
		loadAttributeList();
		loadIndexImgs();
		loadKeyValue();
		loadNavigations();
	    loadSystemSetting();
		loadPlugConfig();
		loadArea();
		loadExpress();
		loadAdvertList();

		loadNewCatalogs();
		loadNews();

//		loadHotProducts();
//		loadNewProducts();
//		loadSaleProducts();
//		loadSuijiProducts();
		loadProductsShowInIndex();
		loadNotices();

		loadProductStock();
		loadAccountRank();
//		loadHotSearch();

//		readJsonArea();
		loadNotifyTemplate();


		//加载所有的活动列表
		loadActivityMap();
		//加载促销活动的商品
		loadActivityProductList();
		//加载积分商城商品列表
		loadActivityScoreProductList();
		//加载团购活动的商品列表
		loadActivityTuanProductList();

		//loadSystemSetting();
		logger.info("前台缓存加载完毕!");
	}

	/**
	 * 加载系统配置信息
	 */
	public void loadSystemSetting() {
		SystemSetting systemSetting = systemSettingMService.selectOne(new SystemSetting());
		if (systemSetting == null) {
			throw new NullPointerException("未设置本地环境变量，请管理员在后台进行设置");
		}

		//从环境变量中分解出图集来。
		if (StringUtils.isNotBlank(systemSetting.getImages())) {
			String[] images = systemSetting.getImages().split(ManageContainer.product_images_spider);
			if (systemSetting.getImagesList() == null) {
				systemSetting.setImagesList(new LinkedList<String>());
			} else {
				systemSetting.getImagesList().clear();
			}

			for (int i = 0; i < images.length; i++) {
				systemSetting.getImagesList().add(images[i]);
			}
		}
		systemManager.setSystemSetting(systemSetting);
	}
	public static void main(String[] args) {
		String str = "10280|10281|10282";
		String[] arr = str.split("\\|");
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
}
