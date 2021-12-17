package com.tuling.apm.common;

import com.tuling.apm.common.json.JsonWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tommy on 2018/3/8.
 */
public class JsonUtil {
    public static String toJson(Object obj) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("TYPE", false);
        item.put(JsonWriter.SKIP_NULL_FIELDS, true);
        String json = JsonWriter.objectToJson(obj, item);
        return json;
    }
}
