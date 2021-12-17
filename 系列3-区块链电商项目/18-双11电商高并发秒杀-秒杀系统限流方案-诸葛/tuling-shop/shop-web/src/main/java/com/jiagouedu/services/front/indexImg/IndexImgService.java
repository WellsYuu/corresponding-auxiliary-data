/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.front.indexImg;

import com.jiagouedu.core.Services;
import com.jiagouedu.services.front.indexImg.bean.IndexImg;

import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
public interface IndexImgService extends Services<IndexImg> {

	/**
	 * 加载图片显示到门户
	 * @param i
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
