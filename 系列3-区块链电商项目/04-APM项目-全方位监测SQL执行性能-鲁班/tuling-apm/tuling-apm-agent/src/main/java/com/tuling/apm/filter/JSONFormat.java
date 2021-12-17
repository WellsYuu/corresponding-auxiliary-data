package com.tuling.apm.filter;

import com.tuling.apm.IFilter;
import com.tuling.apm.common.JsonUtil;

import java.io.Serializable;

/**
 * Created by Tommy on 2018/3/8.
 */
public class JSONFormat implements IFilter {
    @Override
    public Object doFilter(Object value) {
        if (value == null)
            return null;
        else if (!(value instanceof Serializable)) {
            return null;
        }
        return JsonUtil.toJson(value);
    }
}
