package com.jqc.tank;

import com.jqc.tank.net.Client;

public class Main {
    public static void main(String[] args) throws Exception{

        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);

//        int initTankCount = PropertyMgr.getInt(CONSTANTS.PROPERTY_INIT_TANK_COUNT);
//
//        for(int i = 0; i < initTankCount; i++){
//            tf.tanks.add(new Tank(50 + i * 80,200, Dir.DOWN, Group.AI, tf));
//        }

        //new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }
}
