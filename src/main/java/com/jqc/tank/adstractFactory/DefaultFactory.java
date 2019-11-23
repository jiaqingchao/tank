package com.jqc.tank.adstractFactory;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

public class DefaultFactory extends GameFactory{

    private DefaultFactory(){}

    private final static DefaultFactory INSTANCE = new DefaultFactory();

    public static DefaultFactory getInstance(){
        return INSTANCE;
    }

    @Override
    public BaseTank ceateTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank(x, y, dir, group, tf);
    }

    @Override
    public BaseBullet ceateBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode ceateExplode(int x, int y) {
        return new Explode(x, y);
    }

}
