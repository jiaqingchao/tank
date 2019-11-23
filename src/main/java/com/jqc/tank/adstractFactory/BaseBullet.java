package com.jqc.tank.adstractFactory;

import com.jqc.tank.bean.Tank;

import java.awt.*;

public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract boolean isLiving();

    public abstract void collisionWidth(BaseTank redTank);
}
