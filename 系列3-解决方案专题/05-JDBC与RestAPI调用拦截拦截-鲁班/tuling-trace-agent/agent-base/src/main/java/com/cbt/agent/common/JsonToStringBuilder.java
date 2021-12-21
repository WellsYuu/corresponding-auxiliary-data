package com.cbt.agent.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class JsonToStringBuilder {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String str;
    private boolean isFirst;

    public JsonToStringBuilder(Object o) {
        isFirst = true;
        str = "";
    }

    public JsonToStringBuilder appendItem(String variableName, Object value) {
        if (!isFirst) {
            str += ", ";
        }
        isFirst = false;
        str += "\"" + variableName + "\"" + ":";
        str += "\"" + value + "\"";
        return this;
    }

    public JsonToStringBuilder appendItem(String variableName, Number value) {
        if (!isFirst) {
            str += ", ";
        }
        isFirst = false;
        str += "\"" + variableName + "\"" + ":";
        str += value;
        return this;
    }


    public JsonToStringBuilder appendItem(String variableName, Calendar value) {
        if (!isFirst) {
            str += ", ";
        }
        isFirst = false;
        str += "\"" + variableName + "\"" + ":";
        str += "\"" + new SimpleDateFormat(DATE_FORMAT).format(value.getTime()) + "\"";
        return this;
    }



    @Override
    public String toString() {
        return "{" + str + "}";
    }

    public static String toJson(JsonSerializable[] s){
        StringBuilder j =new StringBuilder();
        for (JsonSerializable jsonSerializable : s) {
            j.append(",");
            j.append(jsonSerializable.toString());
        }
        if (j.length() > 0) {
            j.delete(0, 1);
        }
        j.insert(0,"[");
        j.append("]");
        return j.toString();
    }
}