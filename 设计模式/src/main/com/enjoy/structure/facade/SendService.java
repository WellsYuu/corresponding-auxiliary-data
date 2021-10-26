package com.enjoy.structure.facade;

import com.alibaba.fastjson.JSON;
import com.enjoy.structure.composite.DistrictNode;
import com.enjoy.structure.composite.LeafNode;

/**
 * 快递服务
 */
public class SendService {

    /**
     * 指定目的地，发送快递
     */
    public void doSend(){
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

        System.out.println("请选定目的地："+JSON.toJSON(root));
        //以下物流运输业务。。。。
        System.out.println("本次快递目的地：北京-朝阳区-三里屯");
    }

    public void doOther(){
        System.out.println("其它服务");
    }
}
