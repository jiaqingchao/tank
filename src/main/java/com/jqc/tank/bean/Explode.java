package com.jqc.tank.bean;

import com.jqc.tank.common.ResourceMgr;

import java.awt.*;

public class Explode {

    private int x;
    private int y;

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int step = 0;
    private boolean living = true;

    public Explode(int x, int y){
        this.x = x;
        this.y = y;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public boolean isLiving() {
        return living;
    }

    public void paint(Graphics g) {
        if(!isLiving()){
            return;
        }
        g.drawImage(ResourceMgr.explodes[step++], this.x, this.y,null);

        if(step >= ResourceMgr.explodes.length){
            living = false;
        }
    }
}
