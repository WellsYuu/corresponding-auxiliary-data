package cn.enjoy.product.pojo;

/**
 * Created by VULCAN on 2018/7/28.
 */
public class Product {
    private  String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product() {
    }
}
