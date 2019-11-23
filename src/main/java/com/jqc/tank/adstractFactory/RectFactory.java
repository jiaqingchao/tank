package com.jqc.tank.adstractFactory;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.*;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

public class RectFactory extends GameFactory{
    private RectFactory(){}

    private final static RectFactory INSTANCE = new RectFactory();

    public static RectFactory getInstance(){
        return INSTANCE;
    }
    @Override
    public BaseTank ceateTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectTank(x, y, dir, group, tf);
    }

    @Override
    public BaseBullet ceateBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode ceateExplode(int x, int y) {
        return new RectExplode(x, y);
    }
}
