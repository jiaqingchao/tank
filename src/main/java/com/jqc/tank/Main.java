package com.jqc.tank;

import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;

public class Main {
    public static void main(String[] args) throws Exception{

        TankFrame tf = new TankFrame();

        //new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
