package com.jqc.tank.adstractFactory;

import com.jqc.tank.TankFrame;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

public abstract class GameFactory {

    public abstract BaseTank ceateTank(int x, int y, Dir dir, Group group, TankFrame tf);

    public abstract BaseBullet ceateBullet(int x, int y, Dir dir, Group group, TankFrame tf);

    public abstract BaseExplode ceateExplode(int x, int y);
}
