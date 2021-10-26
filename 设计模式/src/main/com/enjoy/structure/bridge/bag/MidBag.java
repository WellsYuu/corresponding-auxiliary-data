package com.enjoy.structure.bridge.bag;

/**
 * 采摘中型袋
 * Material
 */
public class MidBag extends BagAbstraction {

    public void pick(){
        System.out.println("采摘水果开始");
        this.material.draw();
        System.out.println("采摘了一中型袋");
    }

}
