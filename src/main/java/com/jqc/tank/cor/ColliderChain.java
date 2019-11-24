package com.jqc.tank.cor;

import com.jqc.tank.bean.GameObject;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.strategy.FireStrategy;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider{

    private final static ColliderChain INSTANCE = new ColliderChain();
    public static ColliderChain getInstance(){
        return INSTANCE;
    }
    private List<Collider> colliders = new LinkedList<>();

    private ColliderChain(){
//        Collider btCollider = BulletTankCollider.getInstance();
//        Collider ttCollider = TankTankCollider.getInstance();
//        add(btCollider);
//        add(ttCollider);
        initColliders();
    }

    public void add(Collider c){
        colliders.add(c);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2){
        for (Collider collider : colliders){
            if(!collider.collide(o1, o2)){
                return false;
            }
        }
        return true;
    }

    public void initColliders() {
        String collidersName = PropertyMgr.getString(CONSTANTS.PROPERTY_COLLIDERS);
        String[] clazzNames = collidersName.split(",");
        Collider collider;
        try {
            for (String clazzName : clazzNames){
                collider = (Collider) Class.forName(clazzName).getMethod("getInstance").invoke(null);
                add(collider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
