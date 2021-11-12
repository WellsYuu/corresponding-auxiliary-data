package com.enjoy.structure.composite;

import com.alibaba.fastjson.JSON;

/**
 * 组合模式
 * 省市县目录组织结构
 */
public class CompositeClient {
    public static void main(String[] args){
        sendFruit();
    }

    public static void sendFruit(){

        //根目录
        DistrictNode root = new DistrictNode("根");

        //一线目录
        root.addChild(new DistrictNode("上海"));
        root.addChild(new DistrictNode("天津"));
        DistrictNode districtNode = new DistrictNode("北京");
        root.addChild(districtNode);

        //二级目录
        districtNode.addChild(new DistrictNode("海淀区"));
        districtNode.addChild(new DistrictNode("西城区"));
        DistrictNode districtNode2 = new DistrictNode("朝阳区");
        districtNode.addChild(districtNode2);

        //三级目录
        districtNode2.addChild(new LeafNode("三里屯"));
        districtNode2.addChild(new LeafNode("朝阳外街"));

        System.out.println(JSON.toJSON(root));
        //以下物流运输业务。。。。

    }



}
