package com.jiagouedu.core.oscache;

import com.alibaba.fastjson.JSON;
import com.jiagouedu.core.ManageContainer;
import com.jiagouedu.core.TaskManager;
import com.jiagouedu.core.front.SystemManager;
import com.jiagouedu.services.manage.area.AreaService;
import com.jiagouedu.services.manage.comment.CommentService;
import com.jiagouedu.services.manage.order.OrderService;
import com.jiagouedu.services.manage.order.bean.OrdersReport;
import com.jiagouedu.services.manage.oss.OssService;
import com.jiagouedu.services.manage.oss.bean.AliyunOSS;
import com.jiagouedu.services.manage.oss.bean.Oss;
import com.jiagouedu.services.manage.product.ProductService;
import com.jiagouedu.services.manage.systemsetting.SystemSettingService;
import com.jiagouedu.services.manage.systemsetting.bean.SystemSetting;
import com.jiagouedu.services.manage.task.TaskService;
import com.jiagouedu.services.manage.task.bean.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 * 
 * @author wukong 图灵学院 QQ:245553999
 * 
 */
public class ManageCache {
	private static final Logger logger = LoggerFactory.getLogger(ManageCache.class);
	
	/**
	 * manage后台
	 */
    @Resource(name = "orderServiceManage")
	private OrderService orderService;
    @Resource(name = "productServiceManage")
	private ProductService productService;
    @Resource(name = "commentServiceManage")
	private CommentService commentService;
    @Resource(name = "areaServiceManage")
	private AreaService areaService;
    @Resource(name = "taskServiceManage")
	private TaskService taskService;
    @Resource(name = "ossServiceManage")
	private OssService ossService;
    @Autowired
    private SystemSettingService systemSettingService;
    @Autowired
    private SystemManager systemManager;
	
	public void setOssService(OssService ossService) {
		this.ossService = ossService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

    /**
	 * 加载订单报表
	 */
	public void loadOrdersReport(){
		OrdersReport ordersReport = orderService.loadOrdersReport();
		if(ordersReport==null){
			ordersReport = new OrdersReport();
		}
		//加载缺货商品数
		ordersReport.setOutOfStockProductCount(productService.selectOutOfStockProductCount());

		//加载吐槽评论数
		ordersReport.setNotReplyCommentCount(commentService.selectNotReplyCount());
		
		logger.error("SystemManager.ordersReport = " + ordersReport.toString());
        systemManager.setOrdersReport(ordersReport);
	}
	
//	/**
//	 * 加载省市区数据
//	 */
//	private void loadArea(){
//		logger.error("loadArea...");
//		Area area = new Area();
//		area.setPcode("0");
//		List<Area> rootData = areaService.selectList(area);
//		if(rootData==null){
//			return ;
//		}
//
//		for(int i=0;i<rootData.size();i++){
//			Area item = rootData.get(i);
//			getAreaByDigui2(item);
//		}
//
//		Map<String, Area> map = new TreeMap<String, Area>();
//		for(int i=0;i<rootData.size();i++){
//			Area item = rootData.get(i);
//			map.put(item.getCode(), item);
//		}
//
//		SystemManager.areaMap = map;
//
////		logger.error("SystemManager.areaMap=="+SystemManager.areaMap);
//
//		String json = JSON.toJSONString(SystemManager.areaMap);
////		logger.error("json="+json);
//		try {
//			//写到文件
//			File file = new File("__area.txt");
//			logger.error(file.getAbsolutePath());
//			FileUtils.writeStringToFile(new File("__area.json"), json, "utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	private void readJsonArea(){
//		long start = System.currentTimeMillis();
//		try {
//			String path = ManageCache.class.getResource("/").getPath();
//			logger.error("path = " + path);
//			File file = new File(path + "__area.json");
//			logger.error(file.getAbsolutePath());
//			List<String> list = FileUtils.readLines(file, "utf-8");
//			logger.error("list.size()="+list.size());
//			
//			SystemManager.areaMap = JSON.parseObject(list.get(0),new TypeReference<Map<String,Area>>(){});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		logger.error("readJsonArea time = " + (System.currentTimeMillis() - start));
//	}
	
	/**
	 * 加载云存储配置信息
	 */
	public void loadOSS() {
		Oss oss = new Oss();
		oss.setStatus(Oss.oss_status_y);
		oss.setCode(Oss.code_aliyun);
		
		oss = ossService.selectOne(oss);
		if(oss!=null){
			if(oss.getCode().equals(Oss.code_aliyun)){
				if(StringUtils.isBlank(oss.getOssJsonInfo())){
					throw new NullPointerException("阿里云存储配置不能为空！");
				}
				AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
				if(aliyunOSS==null){
					throw new NullPointerException("阿里云配置不正确，请检查！");
				}
                systemManager.setAliyunOSS(aliyunOSS);
			}
		}else{
            systemManager.setAliyunOSS(null);
		}
	}
	
	/**
	 * 加载定时任务列表
	 */
	public void loadTask(){
		List<Task> list = taskService.selectList(new Task());
		if(list!=null){
			TaskManager.taskPool.clear();
			for(int i=0;i<list.size();i++){
				Task item = list.get(i);
				TaskManager.taskPool.put(item.getCode(),item);
			}
		}
	}
	
	/**
	 * 加载全部的缓存数据
	 * @throws Exception 
	 */
	public void loadAllCache() throws Exception {
		logger.error("ManageCache.loadAllCache...");
		loadOrdersReport();
//		readJsonArea();
		loadTask();
		loadOSS();
        loadSystemSetting();
		logger.error("后台缓存加载完毕!");
	}

    /**
     * 加载系统配置信息
     */
    public void loadSystemSetting() {
        SystemSetting systemSetting = systemSettingService.selectOne(new SystemSetting());
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
