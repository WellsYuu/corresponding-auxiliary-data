package com.enjoy.structure.composite;

import java.util.List;

/**
 * Created by Peter on 10/29 029.
 */
public abstract class Node {
    private String name;

    public Node(String name){
        this.name = name;
    }

    public abstract List<Node> getChildren();

    public String getName() {
        return name;
    }
}
