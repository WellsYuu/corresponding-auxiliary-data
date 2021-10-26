package com.enjoy.structure.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 10/29 029.
 */
public class DistrictNode extends Node{
    private List<Node> children = new ArrayList<>();

    public DistrictNode(String name) {
        super(name);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public void delChild(int i){
        children.remove(i);
    }


}
