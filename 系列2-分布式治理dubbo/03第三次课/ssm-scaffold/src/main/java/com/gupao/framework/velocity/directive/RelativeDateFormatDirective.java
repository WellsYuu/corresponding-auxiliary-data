package com.gupao.framework.velocity.directive;

import com.gupao.framework.common.RelativeDateFormat;
import com.gupao.framework.velocity.AbstractDirective;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * <p>
 * velocity 相对日期格式标签
 * </p>
 * <p>
 * 例如： #relativeDateFormat($!{testDate}) 【 输出：1小时前 】 
 * </p>
 * @author qingyin
 * @Date 2016-08-19
 */
public class RelativeDateFormatDirective extends AbstractDirective {

	@Override
	public String getName() {
		return "relativeDateFormat";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	protected boolean doRender(InternalContextAdapter internalContext, ViewToolContext context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode sn = (SimpleNode) node.jjtGetChild(0);
		Date date = (Date) sn.value(internalContext);
		writer.write(RelativeDateFormat.format(date));
		return true;
	}

}
