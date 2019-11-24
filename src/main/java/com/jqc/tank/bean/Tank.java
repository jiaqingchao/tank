package com.jqc.tank.bean;

import com.jqc.tank.GameModel;
import com.jqc.tank.TankFrame;
import com.jqc.tank.common.*;
import com.jqc.tank.strategy.FireStrategy;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject{
    private int oldX, oldY;
    private Dir dir = Dir.DOWN;
    private int speed = PropertyMgr.getInt(CONSTANTS.PROPERTY_TANK_SPEED);

    public static int WIDTH = ResourceMgr.redTankU.getWidth();
    public static int HEIGHT = ResourceMgr.redTankU.getHeight();

    private boolean moving = false;
    private boolean living = true;
    public Group group;

    private GameModel gm;
    private Rectangle rectangle = new Rectangle();
    private Random random = new Random();

    private FireStrategy fs = null;

    public Tank(int x, int y, Dir dir, Group group, GameModel gm) {
        super(x, y);
        this.oldX = x;
        this.oldY = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;
        if(this.group == Group.AI){
            this.moving = true;
        }

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = Tank.WIDTH;
        rectangle.height = Tank.HEIGHT;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public Dir getDir() {
        return dir;
    }

    public GameModel getGm() {
        return gm;
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

        gm.add(new Explode(eX, eY, gm));
    }

    public void paint(Graphics g) {
        paintTank(g);

        randomFireAndDir();

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

    private void randomFireAndDir() {
        if(this.group != Group.AI){
            return;
        }

        // AI 随机发射子弹
        if(random.nextInt(100) > 95){
            if(fs == null){
                initFireStrategy();
            }
            fs.fire(this);
        }

        //AI 随机改变方向
        if(random.nextInt(100) > 95){
            randomDir();
        }
    }

    private void initFireStrategy() {
        String defaultFSName = PropertyMgr.getString(CONSTANTS.PROPERTY_DEFAULT_FIRE);
        try {
            fs = (FireStrategy) Class.forName(defaultFSName).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void randomDir() {
        int randomNum = random.nextInt(4);
        switch (randomNum){
            case 0 :
                dir = Dir.UP;
                break;
            case 1 :
                dir = Dir.DOWN;
                break;
            case 2:
                dir = Dir.LEFT;
                break;
            case 3:
                dir = Dir.RIGHT;
                break;
            default:
                break;
        }
    }

    private void move() {
        if(!this.moving) return;

        this.oldX = this.x;
        this.oldY = this.y;

        setXYForDirSpeed();

        boudsCheck();

        this.rectangle.x = this.x;
        this.rectangle.y = this.y;

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
    public void stop(){
        this.moving = false;
    }

}
