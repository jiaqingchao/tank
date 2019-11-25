package com.jqc.tank;

import com.jqc.tank.bean.*;
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

public class GameModel {
    private final static GameModel INSTANCE = new GameModel();
    static {
        init();
    }
    private List<GameObject> objects = new ArrayList<>();
    private Tank redTank;

    private ColliderChain chain = ColliderChain.getInstance();

    public void add(GameObject go){
        this.objects.add(go);
    }
    public void remove(GameObject object) {
        this.objects.remove(object);
    }

    private GameModel(){}
    public static GameModel getInstance(){
        return INSTANCE;
    }
    private static void init(){
        int initTankCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_TANK_COUNT);
        int initWallCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_WALL_COUNT);

        INSTANCE.redTank = new Tank(100,100, Dir.DOWN, Group.RED);

        for(int i = 0; i < initTankCount; i++){
            new Tank(50 + i * 80,200, Dir.DOWN, Group.AI);
        }
        int y = 300;
        int j = 0;
        for(int i = 0; i < initWallCount; i++){
            int x = 100 + j++ * 25;
            if(x > 800){
                y += 100;
                j = 0;
            }
            new Wall(x,y);
        }
    }

    public void paint(Graphics g){

        paintCount(g);

        paintGameObject(g);

        collisonCheck();

        if(!redTank.isLiving()){
            gameOver(g);
        }


    }
    private void gameOver(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", TankFrame.WIDTH / 2 - 50, TankFrame.HEIGHT / 2 - 10);
        g.setColor(c);

        //objects.removeAll(objects);
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
        for(int i = 0; i < objects.size() - 1; i++){
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
