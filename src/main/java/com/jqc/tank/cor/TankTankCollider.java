package com.jqc.tank.cor;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Tank;

import java.awt.*;

public class TankTankCollider implements Collider {
    private final static TankTankCollider INSTANCE = new TankTankCollider();

    private TankTankCollider(){}

    public static TankTankCollider getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Tank){
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;
            if(t1.getRectangle().intersects(t2.getRectangle())){
                t1.back();
                t2.back();
            }
        }
        return true;
    }
}
