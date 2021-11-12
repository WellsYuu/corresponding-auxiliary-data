package com.enjoy.structure.bridge.bag;

/**
 * 采摘大袋
 */
public class BigBag extends BagAbstraction {

    public void pick(){
        System.out.println("采摘水果开始");
        this.material.draw();
        System.out.println("采摘了一大袋");
    }

}
