package com.jqc.tank.bean;

import com.jqc.tank.TankFrame;
import com.jqc.tank.adstractFactory.BaseTank;
import com.jqc.tank.common.*;
import com.jqc.tank.strategy.FireStrategy;

import java.awt.*;
import java.util.Random;

public class RectTank extends BaseTank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private int speed = PropertyMgr.getInt(CONSTANTS.PROPERTY_TANK_SPEED);

    public static int WIDTH = ResourceMgr.redTankU.getWidth();
    public static int HEIGHT = ResourceMgr.redTankU.getHeight();

    private boolean moving = false;
    private boolean living = true;
    private Group group;

    private TankFrame tf;
    private Rectangle rectangle = new Rectangle();
    private Random random = new Random();

    private FireStrategy fs = null;

    public RectTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        if(this.group == Group.AI){
            this.moving = true;
        }

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = RectTank.WIDTH;
        rectangle.height = RectTank.HEIGHT;
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

    public Dir getDir() {
        return dir;
    }

    public TankFrame getTf() {
        return tf;
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
    }

    @Override
    public void paint(Graphics g) {
        paintTank(g);

        randomFireAndDir();

        move();

    }

    private void paintTank(Graphics g) {
        Color c = g.getColor();
        g.setColor(group == Group.RED?Color.RED:Color.BLUE);
        g.fillRect(x, y, 50,50);
        g.setColor(c);

    }

    private void randomFireAndDir() {
        if(this.group != Group.AI){
            return;
        }

        // AI 随机发射子弹
        if(random.nextInt(100) > 95){
//            if(fs == null){
//                initFireStrategy();
//            }
            fire();
        }

        //AI 随机改变方向
        if(random.nextInt(100) > 95){
            randomDir();
        }
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

        tf.gf.ceateBullet(bX, bY, Dir.RIGHT, this.getGroup(), this.getTf());

        if(this.getGroup() == Group.RED){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
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
        if(this.x > TankFrame.WIDTH - RectTank.WIDTH - 2) this.x = TankFrame.WIDTH - RectTank.WIDTH - 2;
        if(this.y > TankFrame.HEIGHT - RectTank.HEIGHT - 2) this.y = TankFrame.HEIGHT - RectTank.HEIGHT - 2;
    }

}
