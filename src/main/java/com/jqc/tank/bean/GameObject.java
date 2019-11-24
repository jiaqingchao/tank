package com.jqc.tank.bean;

import java.awt.*;

public abstract class GameObject {
    protected int x,y;
    protected GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }
    public abstract void paint(Graphics g);

    public abstract boolean isLiving();
}
