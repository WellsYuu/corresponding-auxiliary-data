package com.gupao.framework.velocity;

import com.gupao.framework.common.FrameworkConstants;
import com.gupao.framework.exception.EduFrameWorkException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.StringValueResolver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * velocity 模式加载 properties
 * </p>
 * <p>
 * 支持 properties 文件使用 velocity 标签控制，注入 VelocityContext 可定义标签内容。
 * </p>
 * @author qingyin
 */
public class VelocityPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private String charset = FrameworkConstants.UTF_8;

	private static VelocityContext velocityContext = null;

	private Resource[] locations;

	private RunEnvironment runEnvironment;

	/* 处理未找到占位内容 */
	private String placeholderValue = "";


	@Override
	public void setLocation( Resource location ) {
		this.locations = new Resource[ ] { location };
	}


	@Override
	public void setLocations( Resource... locations ) {
		this.locations = locations;
	}


	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props )
		throws BeansException {
		StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);
		doProcessProperties(beanFactoryToProcess, valueResolver);
	}

	private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

		private final PropertyPlaceholderHelper helper;

		private final PlaceholderResolver resolver;


		public PlaceholderResolvingStringValueResolver( Properties props ) {
			this.helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator,
					ignoreUnresolvablePlaceholders);
			this.resolver = new PropertyPlaceholderConfigurerResolver(props);
		}


		@Override
		public String resolveStringValue( String strVal ) throws BeansException {
			String value = this.helper.replacePlaceholders(strVal, this.resolver);
			if ( value.contains(placeholderPrefix) && value.equals(strVal) ) {
				return placeholderValue;
			}
			return (value.equals(nullValue) ? null : value);
		}
	}


	private class PropertyPlaceholderConfigurerResolver implements PlaceholderResolver {

		private final Properties props;


		private PropertyPlaceholderConfigurerResolver( Properties props ) {
			this.props = props;
		}


		@Override
		public String resolvePlaceholder( String placeholderName ) {
			return VelocityPropertyPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, props,
				SYSTEM_PROPERTIES_MODE_FALLBACK);
		}
	}


	public void fillMergeProperties( Properties prop, InputStream input ) {
		try {
			StringWriter writer = new StringWriter();
			BufferedReader br = new BufferedReader(new InputStreamReader(input, getCharset()));
			if ( velocityContext == null ) {
				/*
				 * 设置环境变量判断逻辑
				 */
				Map<Object, Object> context = new HashMap<Object, Object>();
				context.put("env", this.getRunEnvironment());
				context.putAll(System.getProperties());
				velocityContext = new VelocityContext(context);
			}
			Velocity.evaluate(velocityContext, writer, "VelocityPropertyPlaceholderConfigurer", br);
			prop.load(new StringReader(writer.toString()));
		} catch ( Exception e ) {
			throw new EduFrameWorkException(e);
		}
	}


	@Override
	protected void loadProperties( Properties props ) throws IOException {
		if ( this.locations != null && props != null ) {
			for ( Resource location : this.locations ) {
				if ( logger.isInfoEnabled() ) {
					logger.info("Loading properties file from " + location);
				}
				this.fillMergeProperties(props, location.getInputStream());
			}
		}
	}


	public RunEnvironment getRunEnvironment() {
		if ( runEnvironment == null ) {
			return new RunEnvironment();
		}
		return runEnvironment;
	}


	public void setRunEnvironment( RunEnvironment runEnvironment ) {
		this.runEnvironment = runEnvironment;
	}


	public String getCharset() {
		return charset;
	}


	public void setCharset( String charset ) {
		this.charset = charset;
	}


	public String getPlaceholderValue() {
		return placeholderValue;
	}


	public void setPlaceholderValue( String placeholderValue ) {
		this.placeholderValue = placeholderValue;
	}

}
