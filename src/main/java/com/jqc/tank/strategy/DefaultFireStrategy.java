package com.jqc.tank.strategy;

import com.jqc.tank.bean.Audio;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Group;

public class DefaultFireStrategy implements FireStrategy<Tank>{

    @Override
    public void fire(Tank tank) {
        int bX = 0;
        int bY = 0;

        switch (tank.getDir()){
            case DOWN:
                bY = tank.getY() + Tank.HEIGHT;
                bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
                break;
            case UP:
                bY = tank.getY() - Bullet.HEIGHT;
                bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
                break;
            case LEFT:
                bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
                bX = tank.getX() - Bullet.WIDTH;
                break;
            case RIGHT:
                bX = tank.getX() + Tank.WIDTH;
                bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
                break;
            default:
                break;
        }

        new Bullet(bX, bY, tank.getDir(), tank.getGroup(), tank.getGm());

        if(tank.getGroup() == Group.RED){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }

    }
}
