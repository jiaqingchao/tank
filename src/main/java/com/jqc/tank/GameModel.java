package com.jqc.tank;

import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.bean.Wall;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.cor.ColliderChain;

import java.awt.*;
import java.io.*;
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

    public void save(){
        File f = new File("H:/data/tank/tank.data");
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(redTank);
            oos.writeObject(objects);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        File f = new File("H:/data/tank/tank.data");
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            redTank  = (Tank)ois.readObject();
            objects  = (List)ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
