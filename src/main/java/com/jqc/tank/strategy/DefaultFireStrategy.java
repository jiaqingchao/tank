package com.jqc.tank.strategy;

import com.jqc.tank.GameModel;
import com.jqc.tank.common.Audio;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Group;
import com.jqc.tank.decorator.RectDecorator;
import com.jqc.tank.decorator.TailDecorator;

public class DefaultFireStrategy implements FireStrategy<Tank>{
    private final static DefaultFireStrategy INSTANCE = new DefaultFireStrategy();

    private DefaultFireStrategy(){}

    public static DefaultFireStrategy getInstance(){
        return INSTANCE;
    }
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
        GameModel.getInstance().add(new TailDecorator( new RectDecorator(new Bullet(bX, bY, tank.getDir(), tank.getGroup()))));

        if(tank.getGroup() == Group.RED){
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }

    }
}
