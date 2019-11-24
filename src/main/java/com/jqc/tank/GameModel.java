package com.jqc.tank;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameModel {
    private final static GameModel INSTANCE = new GameModel();
    private GameModel(){
        int initTankCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_TANK_COUNT);

        for(int i = 0; i < initTankCount; i++){
            tanks.add(new Tank(50 + i * 80,200, Dir.DOWN, Group.AI, this));
        }
    }
    public static GameModel getInstance(){
        return INSTANCE;
    }

    public List<Tank> tanks = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();

    public Tank redTank = new Tank(100,100, Dir.DOWN, Group.RED, this);

    public void paint(Graphics g){

        paintCount(g);

        if(!redTank.isLiving()){
            //gameOver(g);
//            return;
        }

        redTank.paint(g);

        paintExplode(g);

        paintBullet(g);

        paintTank(g);

        collisonCheck();
    }
    private void gameOver(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", TankFrame.WIDTH / 2 - 50, TankFrame.HEIGHT / 2 - 10);
        g.setColor(c);
        explodes.removeAll(explodes);
        bullets.removeAll(bullets);
        tanks.removeAll(tanks);
    }

    private void paintCount(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("敌方坦克的数量：" + tanks.size(), 10, 50);
        g.drawString("子弹的数量：" + bullets.size(), 10, 70);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 90);
        g.setColor(c);
    }

    private void paintExplode(Graphics g) {
        for(ListIterator<Explode> blastIterator = explodes.listIterator(); blastIterator.hasNext();){
            Explode explode = blastIterator.next();
            explode.paint(g);
            if(!explode.isLiving()) blastIterator.remove();
        }
    }

    private void paintBullet(Graphics g) {
        for(ListIterator<Bullet> bulletIterator = bullets.listIterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();
            bullet.paint(g);
            if(!bullet.isLiving()) bulletIterator.remove();
        }
    }

    private void paintTank(Graphics g) {
        for(ListIterator<Tank> tankListIterator = tanks.listIterator(); tankListIterator.hasNext();){
            Tank tank = tankListIterator.next();
            tank.paint(g);
            if(!tank.isLiving()) tankListIterator.remove(); // ConcurrentModificationException   //异步新增tank,数量对不上，导致报错
        }
    }

    private void collisonCheck() {
        for(ListIterator<Bullet> bulletIterator = bullets.listIterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();
            bullet.collisionWidth(redTank);
            for(ListIterator<Tank> tankListIterator = tanks.listIterator(); tankListIterator.hasNext();){
                bullet.collisionWidth(tankListIterator.next());
            }

        }
    }


    public Tank getMainTank() {
        return redTank;
    }
}
