package com.jqc.tank;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.cor.BulletTankCollider;
import com.jqc.tank.cor.Collider;
import com.jqc.tank.cor.ColliderChain;
import com.jqc.tank.cor.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameModel {
    private final static GameModel INSTANCE = new GameModel();

    private List<GameObject> objects = new ArrayList<>();
    public Tank redTank = new Tank(100,100, Dir.DOWN, Group.RED, this);

    ColliderChain chain = ColliderChain.getInstance();

//    public List<Tank> tanks = new ArrayList<>();
//    public List<Bullet> bullets = new ArrayList<>();
//    public List<Explode> explodes = new ArrayList<>();


    public void add(GameObject go){
        this.objects.add(go);
    }
    public void remove(GameObject object) {
        this.objects.remove(object);
    }

    private GameModel(){
        int initTankCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_TANK_COUNT);

        for(int i = 0; i < initTankCount; i++){
            add(new Tank(50 + i * 80,200, Dir.DOWN, Group.AI, this));
        }
    }
    public static GameModel getInstance(){
        return INSTANCE;
    }

    public void paint(Graphics g){

        paintCount(g);

        if(!redTank.isLiving()){
            //gameOver(g);
//            return;
        }

        redTank.paint(g);

        paintGameObject(g);

        collisonCheck();
    }
    private void gameOver(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", TankFrame.WIDTH / 2 - 50, TankFrame.HEIGHT / 2 - 10);
        g.setColor(c);

        objects.removeAll(objects);
    }

    private void paintCount(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("敌方坦克的数量：" + objects.size(), 10, 50);
//        g.drawString("子弹的数量：" + objects.size(), 10, 70);
//        g.drawString("爆炸的数量：" + objects.size(), 10, 90);
        g.setColor(c);
    }

    private void paintGameObject(Graphics g) {
        for(int i = 0; i < objects.size(); i++){
            GameObject object = objects.get(i);

            if(object.isLiving()){
                object.paint(g);
            }else {
                remove(object);
                i--;
            }
        }
    }

    private void collisonCheck() {
        for(int i = 0; i < objects.size(); i++){
            GameObject o1 = objects.get(i);
            for(int j = i + 1; j < objects.size(); j++){
                GameObject o2 = objects.get(j);
                if(!chain.collide(o1, o2)){
                    continue;
                }
            }
        }
    }


    public Tank getMainTank() {
        return redTank;
    }
}
