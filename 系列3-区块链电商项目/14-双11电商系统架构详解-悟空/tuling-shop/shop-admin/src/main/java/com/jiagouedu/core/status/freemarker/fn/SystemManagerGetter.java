package com.jiagouedu.core.status.freemarker.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import com.jiagouedu.core.front.SystemManager;

import java.util.List;

/**
 * 获取系统管理
 * @author wukong 图灵学院 QQ:245553999
 */
public class SystemManagerGetter implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return SystemManager.getInstance();
    }
}
