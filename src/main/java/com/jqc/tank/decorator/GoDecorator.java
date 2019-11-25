package com.jqc.tank.decorator;

import com.jqc.tank.bean.GameObject;

import java.awt.*;

public class GoDecorator extends GameObject {

    protected GameObject go;

    @Override
    public int getWidth() {
        return go.getWidth();
    }

    @Override
    public int getHeight() {
        return go.getHeight();
    }

    @Override
    public boolean isLiving() {
        return go.isLiving();
    }

    public GoDecorator(GameObject go){
        this.go = go;
    }
    @Override
    public void paint(Graphics g) {
        this.go.paint(g);
    }

    @Override
    public int getX() {
        return this.go.getX();
    }

    @Override
    public int getY() {
        return this.go.getY();
    }
}
