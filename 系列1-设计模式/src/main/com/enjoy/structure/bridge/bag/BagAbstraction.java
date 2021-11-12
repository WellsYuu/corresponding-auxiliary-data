package com.enjoy.structure.bridge.bag;


import com.enjoy.structure.bridge.material.Material;

/**
 * 采摘容器
 */
public abstract class BagAbstraction {
    protected Material material;
//    protected Material material;

    public void setMaterial(Material material){
        this.material = material;
    }

    //采摘
    public abstract void pick();

}
