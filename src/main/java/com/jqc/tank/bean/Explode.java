package com.jqc.tank.bean;

import com.jqc.tank.GameModel;
import com.jqc.tank.common.Audio;
import com.jqc.tank.common.ResourceMgr;

import java.awt.*;

public class Explode extends GameObject{

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int step = 0;
    private boolean living = true;

    GameModel gm;

    public Explode(int x, int y, GameModel gm){
        super(x, y);
        this.x = x;
        this.y = y;
        this.gm = gm;
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
