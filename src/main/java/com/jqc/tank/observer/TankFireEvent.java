package com.jqc.tank.observer;

import com.jqc.tank.bean.Tank;

public class TankFireEvent{
    Tank source;

    public TankFireEvent(Tank source) {
        this.source = source;
    }

    public Tank getSource() {
        return source;
    }
}
