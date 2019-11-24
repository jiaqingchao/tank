package com.jqc.tank.cor;

import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.bean.Wall;

public class TankWallCollider implements Collider {
    private final static TankWallCollider INSTANCE = new TankWallCollider();

    private TankWallCollider(){}

    public static TankWallCollider getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Wall){
            Tank t = (Tank) o1;
            Wall w = (Wall) o2;
            if(t.getRectangle().intersects(w.getRectangle())){
                t.setX(t.getOldX());
                t.setY(t.getOldY());
            }
        }else if(o1 instanceof Wall && o2 instanceof Tank){
            return collide(o2, o1);
        }
        return true;
    }
}
