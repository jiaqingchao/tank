package com.jqc.tank.bean;

import com.jqc.tank.TankFrame;
import com.jqc.tank.common.*;
import com.jqc.tank.net.BulletNewMsg;
import com.jqc.tank.net.Client;
import com.jqc.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private int speed = PropertyMgr.getInt(CONSTANTS.PROPERTY_TANK_SPEED);

    public static int WIDTH = ResourceMgr.redTankU.getWidth();
    public static int HEIGHT = ResourceMgr.redTankU.getHeight();

    private boolean moving = false;
    private boolean living = true;
    private Group group;

    private Rectangle rectangle = new Rectangle();
    private Random random = new Random();

    private UUID id = UUID.randomUUID();//正式的环境应该在服务器上生成

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        if(this.group == Group.AI){
            this.moving = true;
        }

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = Tank.WIDTH;
        rectangle.height = Tank.HEIGHT;
    }

    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.moving = msg.isMoving();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.id = msg.getId();
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLiving() {
        return living;
    }

    public Group getGroup() {
        return group;
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public void die() {
        this.living = false;

        int eX = this.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
        int eY = this.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;

        TankFrame.INSTANCE.explodes.add(new Explode(eX, eY));
    }

    public void paint(Graphics g) {
        paintTank(g);
        move();
    }

    private void paintTank(Graphics g) {
        switch (this.dir){
            case LEFT :
                g.drawImage(this.group == Group.RED ? ResourceMgr.redTankL : ResourceMgr.aiTankL, this.x, this.y,null);
                break;
            case UP :
                g.drawImage(this.group == Group.RED ? ResourceMgr.redTankU : ResourceMgr.aiTankU, this.x, this.y,null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.RED ? ResourceMgr.redTankR : ResourceMgr.aiTankR, this.x, this.y,null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.RED ? ResourceMgr.redTankD : ResourceMgr.aiTankD, this.x, this.y,null);
                break;
            default:
                break;
        }
    }

    private void move() {
        if(!this.moving) return;

        setXYForDirSpeed();

        boudsCheck();

        rectangle.x = this.x;
        rectangle.y = this.y;

        if(this.group == Group.RED) {
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }
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
            case DOWN:
                this.y += this.speed;
                break;
            default:
                break;
        }
    }

    private void boudsCheck() {//边界检测

        if(this.x < 2) this.x = 2;
        if(this.y < 28) this.y = 28;
        if(this.x > TankFrame.WIDTH - Tank.WIDTH - 2) this.x = TankFrame.WIDTH - Tank.WIDTH - 2;
        if(this.y > TankFrame.HEIGHT - Tank.HEIGHT - 2) this.y = TankFrame.HEIGHT - Tank.HEIGHT - 2;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void fire() {
        int bX = 0;
        int bY = 0;

        switch (this.getDir()){
            case DOWN:
                bY = this.getY() + Tank.HEIGHT;
                bX = this.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
                break;
            case UP:
                bY = this.getY() - Bullet.HEIGHT;
                bX = this.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
                break;
            case LEFT:
                bY = this.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
                bX = this.getX() - Bullet.WIDTH;
                break;
            case RIGHT:
                bX = this.getX() + Tank.WIDTH;
                bY = this.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
                break;
            default:
                break;
        }

        Bullet b = new Bullet(this.getId(), bX, bY, this.getDir(), this.getGroup());

        TankFrame.INSTANCE.addBullet(b);

        Client.INSTANCE.send(new BulletNewMsg(b));

        if(this.getId().equals(TankFrame.INSTANCE.getMainTank().getId())){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
