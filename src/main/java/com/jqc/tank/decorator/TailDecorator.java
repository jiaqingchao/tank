package com.jqc.tank.decorator;

import com.jqc.tank.bean.GameObject;

import java.awt.*;

public class TailDecorator extends GoDecorator {

    public TailDecorator(GameObject go){
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawLine(super.getX(), super.getY(), super.getX()+super.getWidth(), super.getY()+super.getHeight());
        g.setColor(c);
        super.paint(g);
    }
}
