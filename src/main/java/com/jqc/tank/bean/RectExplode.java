package com.jqc.tank.bean;

import com.jqc.tank.adstractFactory.BaseExplode;
import com.jqc.tank.common.ResourceMgr;

import java.awt.*;

public class RectExplode extends BaseExplode {

    private int x;
    private int y;

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int step = 0;
    private boolean living = true;

    public RectExplode(int x, int y){
        this.x = x;
        this.y = y;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    @Override
    public boolean isLiving() {
        return living;
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage(ResourceMgr.explodes[step++], this.x, this.y,null);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 10*step,10*step++);
        g.setColor(c);

        if(step >= 7){
            living = false;
        }
    }
}
