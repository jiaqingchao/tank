package com.jqc.tank.bean;

import java.awt.*;

public abstract class GameObject {
    protected int x,y;
    public abstract void paint(Graphics g);
    public abstract  int getWidth();
    public abstract  int getHeight();

    public abstract int getX();

    public abstract int getY();

    public abstract boolean isLiving();
}
