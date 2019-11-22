package com.jqc.tank.bean;

import com.jqc.tank.TankFrame;
import com.jqc.tank.common.*;

import java.awt.Rectangle;
import java.awt.Graphics;


public class Bullet {

    private int x;
    private int y;
    private Dir dir;
    private int speed = PropertyMgr.getInt(CONSTANTS.PROPERTY_BULLET_SPEED);
    private boolean living = true;
    private Group group;

    public static int WIDTH = ResourceMgr.bulletU.getWidth();
    public static int HEIGHT = ResourceMgr.bulletU.getHeight();

    private Rectangle rectangle = new Rectangle();

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = Tank.WIDTH;
        rectangle.height = Tank.HEIGHT;
    }

    public boolean isLiving() {
        return living;
    }

    private void die() {
        this.living = false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void paint(Graphics g) {
        paintBullet(g);
        move();

    }

    private void paintBullet(Graphics g) {
        switch (this.dir){
            case LEFT :
                g.drawImage(ResourceMgr.bulletL, this.x, this.y,null);
                break;
            case UP :
                g.drawImage(ResourceMgr.bulletU, this.x, this.y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, this.x, this.y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, this.x, this.y,null);
                break;
            default:
                break;
        }
    }

    public void move() {

        setXYForDirSpeed();

        boudsCheck();

        this.rectangle.x = this.x;
        this.rectangle.y = this.y;
    }

    private void setXYForDirSpeed() {
        switch (this.dir){
            case LEFT :
                this.x -= this.speed;
                break;
            case UP :
                this.y -= this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                break;
            case DOWN: this.y += this.speed;
                break;
            default:
                break;
        }
    }

    private void boudsCheck() {
        if(this.x < 0 || this.x > TankFrame.WIDTH
                || this.y < 0 || this.y > TankFrame.HEIGHT){
            this.die();
        }
    }

    public void collisionWidth(Tank tank) {

        if(this.group == tank.getGroup()){
            return;
        }

        Rectangle tankRect = tank.getRectangle();
        Rectangle bulletRect = this.getRectangle();

        if(bulletRect.intersects(tankRect)){
            this.die();
            tank.die();
        }
    }
}
