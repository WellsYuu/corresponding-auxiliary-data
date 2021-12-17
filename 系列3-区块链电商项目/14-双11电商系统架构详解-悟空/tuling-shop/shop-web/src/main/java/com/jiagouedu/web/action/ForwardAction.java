package com.jiagouedu.web.action;

/**
 * Created by dylan on 15-1-18.
 */
@Deprecated
public class ForwardAction {
    private String type = "ftl";
    private String p = "/common/404";
    public String execute(){
        if("ftl".equalsIgnoreCase(type)){
            return "freemarker";
        }
        return "forward";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
