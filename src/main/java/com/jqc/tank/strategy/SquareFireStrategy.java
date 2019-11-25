package com.jqc.tank.strategy;

import com.jqc.tank.common.Audio;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

public class SquareFireStrategy implements FireStrategy<Tank>{
    private final static SquareFireStrategy INSTANCE = new SquareFireStrategy();

    private SquareFireStrategy(){}

    public static SquareFireStrategy getInstance(){
        return INSTANCE;
    }
    @Override
    public void fire(Tank tank) {

        int bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = tank.getY() + Tank.HEIGHT;
        new Bullet(bX, bY, Dir.DOWN, tank.getGroup());

        bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        bY = tank.getY() - Bullet.HEIGHT;
        new Bullet(bX, bY, Dir.UP, tank.getGroup());

        bX = tank.getX() - Bullet.WIDTH;
        bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        new Bullet(bX, bY, Dir.LEFT, tank.getGroup());

        bX = tank.getX() + Tank.WIDTH;
        bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        new Bullet(bX, bY, Dir.RIGHT, tank.getGroup());

        if(tank.getGroup() == Group.RED){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
