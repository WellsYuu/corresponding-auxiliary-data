package com.jiagouedu.core.status.freemarker.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import com.jiagouedu.core.i18n.MessageLoader;

import java.util.List;

/**
 * 国际化配置
 * @author wukong 图灵学院 QQ:245553999
 */
public class I18N implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return MessageLoader.instance().getMessage(arguments.get(0).toString());
    }

}
