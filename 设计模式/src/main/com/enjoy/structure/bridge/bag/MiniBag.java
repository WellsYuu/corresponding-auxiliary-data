package com.enjoy.structure.bridge.bag;

/**
 * 采摘迷你袋
 * Material
 */
public class MiniBag extends BagAbstraction {

    public void pick(){
        System.out.println("采摘水果开始");
        this.material.draw();
        System.out.println("采摘了一迷你袋");
    }

}
