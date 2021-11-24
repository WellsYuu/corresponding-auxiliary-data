package com.gupao.edu.controller;

import com.gupaoedu.spi.search.Search;

import java.sql.Driver;
import java.util.ServiceLoader;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MainTest {

    public static void main(String[] args) {
        ServiceLoader<Search> serviceLoader=ServiceLoader.load(Search.class);
        for(Search search:serviceLoader){
            System.out.println(search.seach());
        }
    }
}
