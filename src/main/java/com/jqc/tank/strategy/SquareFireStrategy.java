package com.jqc.tank.strategy;

import com.jqc.tank.adstractFactory.BaseTank;
import com.jqc.tank.bean.Audio;
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
        tank.tf.gf.ceateBullet(bX, bY, Dir.DOWN, tank.getGroup(), tank.getTf());

        bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        bY = tank.getY() - Bullet.HEIGHT;
        tank.tf.gf.ceateBullet(bX, bY, Dir.UP, tank.getGroup(), tank.getTf());

        bX = tank.getX() - Bullet.WIDTH;
        bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        tank.tf.gf.ceateBullet(bX, bY, Dir.LEFT, tank.getGroup(), tank.getTf());

        bX = tank.getX() + Tank.WIDTH;
        bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        tank.tf.gf.ceateBullet(bX, bY, Dir.RIGHT, tank.getGroup(), tank.getTf());

        if(tank.getGroup() == Group.RED){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
