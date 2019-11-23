package com.jqc.tank.bean;

import com.jqc.tank.TankFrame;
import com.jqc.tank.adstractFactory.BaseBullet;
import com.jqc.tank.adstractFactory.BaseTank;
import com.jqc.tank.common.*;

import java.awt.*;


public class RectBullet extends BaseBullet {

    private int x;
    private int y;
    private Dir dir;
    private int speed = PropertyMgr.getInt(CONSTANTS.PROPERTY_BULLET_SPEED);
    private boolean living = true;
    private Group group;

    public static int WIDTH = ResourceMgr.bulletU.getWidth();
    public static int HEIGHT = ResourceMgr.bulletU.getHeight();

    private Rectangle rectangle = new Rectangle();
    private TankFrame tf;

    public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = Tank.WIDTH;
        rectangle.height = Tank.HEIGHT;

        this.tf.bullets.add(this);
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

    @Override
    public void paint(Graphics g) {
        paintBullet(g);
        move();

    }

    private void paintBullet(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20,20);
        g.setColor(c);
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

    public void collisionWidth(BaseTank tank) {

        if(this.group == tank.getGroup()){
            return;
        }

        Rectangle tankRect = tank.getRectangle();
        Rectangle bulletRect = this.getRectangle();

        if(bulletRect.intersects(tankRect)){
            this.die();
            tank.die();

            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;

            this.tf.explodes.add(tf.gf.ceateExplode(eX, eY));
        }
    }
}
