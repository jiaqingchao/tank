package com.jqc.tank.observer;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {

    public void actionOnFire(TankFireEvent e);
}
