package com.jqc.tank.cor;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.GameObject;
import com.jqc.tank.bean.Wall;

public class BulletWallCollider implements Collider {
    private final static BulletWallCollider INSTANCE = new BulletWallCollider();

    private BulletWallCollider(){}

    public static BulletWallCollider getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Wall){
            Bullet b = (Bullet) o1;
            Wall w = (Wall) o2;
            if(b.getRectangle().intersects(w.getRectangle())){
                b.die();
                w.die();
                return false;
            }
        }else if(o1 instanceof Wall && o2 instanceof Bullet){
            return collide(o2, o1);
        }
        return true;
    }
}
