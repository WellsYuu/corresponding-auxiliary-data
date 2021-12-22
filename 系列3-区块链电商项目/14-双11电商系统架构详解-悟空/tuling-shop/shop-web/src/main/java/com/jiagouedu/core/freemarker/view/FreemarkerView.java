package com.jiagouedu.core.freemarker.view;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.IncludePage;
import freemarker.template.SimpleHash;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by dylan on 15-3-22.
 */
public class FreemarkerView extends FreeMarkerView {
    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash hash = super.buildTemplateModel(model, request, response);
        hash.put(FreemarkerServlet.KEY_INCLUDE, new IncludePage(request, response));
        return hash;
    }
}
