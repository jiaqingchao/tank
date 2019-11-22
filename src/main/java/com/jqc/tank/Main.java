package com.jqc.tank;

import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;

public class Main {
    public static void main(String[] args) throws Exception{

        TankFrame tf = new TankFrame();

        int initTankCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_TANK_COUNT);

        for(int i = 0; i < initTankCount; i++){
            tf.tanks.add(new Tank(50 + i * 80,200, Dir.DOWN, Group.AI, tf));
        }

        //new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
