package com.jqc.tank.bean;

import com.jqc.tank.GameModel;
import com.jqc.tank.common.ResourceMgr;

import java.awt.*;

public class Wall extends GameObject{

    private boolean living = true;
    public static int WIDTH = 25;
    public static int HEIGHT = 25;
    private Rectangle rectangle = new Rectangle();

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Wall(int x, int y, GameModel gm) {
        super(x, y);

        gm.add(this);

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = Wall.WIDTH;
        rectangle.height = Wall.HEIGHT;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(this.x, this.y, Wall.WIDTH, Wall.HEIGHT);
        g.setColor(c);

    }

    @Override
    public boolean isLiving() {
        return living;
    }
    public void die(){
        this.living = false;
    }
}
