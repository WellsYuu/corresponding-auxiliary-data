package com.enjoy.structure.composite;

import java.util.List;

/**
 * Created by Peter on 10/29 029.
 */
public class LeafNode extends Node{
    public LeafNode(String name) {
        super(name);
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

}
