package com.gupao.framework.velocity.tools;

import com.gupao.framework.common.RelativeDateFormat;
import org.apache.velocity.tools.ConversionUtils;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.FormatConfig;

/**
 * <p>
 * velocity 相对日期格式工具类
 * <br>
 * 使用方法：$relativeDate.format($!{testDate})
 * </p>
 * <p>
 * 例如： #relativeDateFormat(传入格式日期) 【 输出：1小时前 】 
 * </p>
 * </p>
 * @author qingyin
 * @Date 2016-08-19
 */
@DefaultKey("relativeDate")
public class RelativeDateTool extends FormatConfig {

	/**
	 * <p>
	 * 日期格式为 xx 前（例如：1小时前）
	 * </p>
	 * 
	 * @param obj
	 *            待格式化日期对象
	 * @return
	 */
	public String format( Object obj ) {
		return RelativeDateFormat.format(ConversionUtils.toDate(obj));
	}

}
