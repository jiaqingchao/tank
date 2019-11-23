package com.jqc.tank.adstractFactory;

import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

import java.awt.*;

public abstract class BaseTank {
    public abstract void paint(Graphics g);

    public abstract boolean isLiving();

    public abstract Group getGroup();

    public abstract Rectangle getRectangle();

    public abstract void die();

    public abstract int getX();

    public abstract int getY();

    public abstract void setMoving(boolean b);

    public abstract void setDir(Dir left);

    public abstract void fire();
}
