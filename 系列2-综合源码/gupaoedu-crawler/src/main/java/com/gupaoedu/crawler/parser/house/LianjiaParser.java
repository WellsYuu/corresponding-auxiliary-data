package com.gupaoedu.crawler.parser.house;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.core.common.Page;
import javax.core.common.config.CustomConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gupaoedu.crawler.common.constants.LianjiaParam;
import com.gupaoedu.crawler.model.House;
import com.gupaoedu.crawler.parser.IParser;
import com.gupaoedu.crawler.support.DownLoader;
import com.gupaoedu.crawler.support.Param;
import com.gupaoedu.crawler.support.ResultProcess;

/**
 * 链家
 * @author Tom
 *
 */
public class LianjiaParser implements IParser  {
	/**
	 * 链家搜索结果解析
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<House> parse(String keyword,int pageNo,int pageSize) throws Exception{
		String charset = CustomConfig.getString("system.charSet");
		String url = new Param()
					.data(LianjiaParam.wd, URLEncoder.encode(keyword, charset))
					.data(LianjiaParam.ie, charset.toLowerCase())
					.data(LianjiaParam.rn, "" + pageSize)
					.data(LianjiaParam.pn, "" + ((pageNo - 1) * pageSize))
					.data(LianjiaParam.tn, LianjiaParam.tn_baidulocal)
					.data(LianjiaParam.cl, "" + 3)
					.getUrl("http://cs.fang.lianjia.com");
		InputStream in = ResultProcess.httpStream(url, 3 * 1000, 1000);
		Document doc = null;
		if(null != in){
			doc = Jsoup.parse(in, charset, "");
			in.close();
		}
		return parse(doc,pageNo,pageSize);
	}
	
	
	
	
	/**
	 * 解析结果内容
	 * @param doc
	 * @return
	 */
	private Page<House> parse(Document doc,int pageNo,int pageSize){
		if(null == doc) return new Page<House>();
		
		List<House> list = new ArrayList<House>();
		
		Elements xinfang = doc.select(".xinfang-all .item-list li");

		String imageSavePath = CustomConfig.getString("crawler.img.tmp");
		imageSavePath += "lianjia.com/";
		File dir = new File(imageSavePath);
		if(!dir.exists()){ dir.mkdirs(); }
		
		for (Element item : xinfang) {
			House house = new House();
			
			String cover = item.select(".lj-lazy").attr("src");
			Matcher m = Pattern.compile(".+?(\\.[a-zA-z]+)\\?").matcher(cover);
			String ext = ".png";
			if(m.find()){
				ext = m.group(1);
			}
			DownLoader.download("", cover, imageSavePath + System.currentTimeMillis() + ext);
			house.setCover(cover);
			house.setName(item.select(".title").text());
			house.setAddress(item.select(".s-info").text());
			house.setPrice(item.select(".folor").text().trim());
			house.setType(item.select(".type").text());
			house.setLayout(item.select(".jushi").text());
			list.add(house);
		}
		int total = Integer.parseInt(doc.select(".all i").text());
		return new Page<House>(pageNo, total, pageSize, list);
	}
}
