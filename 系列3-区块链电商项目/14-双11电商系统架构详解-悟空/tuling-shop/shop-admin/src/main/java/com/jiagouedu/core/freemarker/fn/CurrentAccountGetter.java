package com.jiagouedu.core.freemarker.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import com.jiagouedu.web.util.LoginUserHolder;

import java.util.List;

/**
 * 获取当前登录的用户(前端用户)
 * @author wukong 图灵学院 QQ:245553999
 */
public class CurrentAccountGetter implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return LoginUserHolder.getLoginAccount();
    }
}
