package com.jqc.tank.bean;

import com.jqc.tank.GameModel;

import java.awt.*;

public class Wall extends GameObject{

    private boolean living = true;
    private int width = 25;
    private int height = 25;
    private Rectangle rectangle = new Rectangle();

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Wall(int x, int y, GameModel gm) {
        super(x, y);

        GameModel.getInstance().add(this);

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = this.width;
        rectangle.height = this.height;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(this.x, this.y, this.width, this.height);
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
