package com.jqc.tank.decorator;

import com.jqc.tank.GameModel;
import com.jqc.tank.bean.GameObject;

import java.awt.*;

public class RectDecorator extends GoDecorator {

    public RectDecorator(GameObject go){
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        g.setColor(c);
        super.paint(g);
    }
}
