package com.jqc.tank.cor;

import com.jqc.tank.bean.GameObject;

public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
