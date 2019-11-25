package com.jqc.tank.observer;

public class TankFireHandler implements TankFireObserver{

    public void actionOnFire(TankFireEvent e){
        e.getSource().fire();
    }
}
