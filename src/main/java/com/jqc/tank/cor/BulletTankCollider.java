package com.jqc.tank.cor;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Tank;

import java.awt.*;

public class BulletTankCollider implements Collider {
    private final static BulletTankCollider INSTANCE = new BulletTankCollider();

    private BulletTankCollider(){}

    public static BulletTankCollider getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Tank){
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;
            return collideWidth(b, t);
        }else if(o1 instanceof Tank && o2 instanceof Bullet){
            return collide(o2, o1);
        }
        return true;
    }

    private boolean collideWidth(Bullet b, Tank t) {
        if(b.group == t.group){
            return true;
        }

        Rectangle tankRect = t.getRectangle();
        Rectangle bulletRect = b.getRectangle();

        if(bulletRect.intersects(tankRect)){
            t.die();
            b.die();
            return false;
        }
        return true;
    }
}
