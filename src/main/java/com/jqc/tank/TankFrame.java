package com.jqc.tank;

import com.jqc.tank.adstractFactory.*;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.strategy.SquareFireStrategy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TankFrame extends Frame {

    public static int WIDTH = PropertyMgr.getInt(CONSTANTS.PROPERTY_GAME_WINDOW_WIDTH);
    public static int HEIGHT = PropertyMgr.getInt(CONSTANTS.PROPERTY_GAME_WINDOW_HEIGHT);

    public List<BaseTank> tanks = new ArrayList<>();
    public List<BaseBullet> bullets = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();

    public GameFactory gf = DefaultFactory.getInstance();

    public BaseTank redTank = gf.ceateTank(100,100,Dir.DOWN, Group.RED, this);

    public TankFrame(){

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setTitle("tank war");
        setResizable(false);

        addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

    }

//  处理双缓冲，解决闪烁问题
    Image offScreenImage = null;
    @Override
    public void update(Graphics g){
        if(offScreenImage == null){
            offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g){
        paintCount(g);

        if(!redTank.isLiving()){
            //gameOver(g);
//            return;
        }

        redTank.paint(g);

        paintExplode(g);

        paintBullet(g);

        paintTank(g);

        collisonCheck();


    }

    private void gameOver(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", WIDTH / 2 - 50, HEIGHT / 2 - 10);
        g.setColor(c);
        explodes.removeAll(explodes);
        bullets.removeAll(bullets);
        tanks.removeAll(tanks);
    }

    private void paintCount(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("敌方坦克的数量：" + tanks.size(), 10, 50);
        g.drawString("子弹的数量：" + bullets.size(), 10, 70);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 90);
        g.setColor(c);
    }

    private void paintExplode(Graphics g) {
        for(ListIterator<BaseExplode> blastIterator = explodes.listIterator(); blastIterator.hasNext();){
            BaseExplode explode = blastIterator.next();
            explode.paint(g);
            if(!explode.isLiving()) blastIterator.remove();
        }
    }

    private void paintBullet(Graphics g) {
        for(ListIterator<BaseBullet> bulletIterator = bullets.listIterator(); bulletIterator.hasNext();){
            BaseBullet bullet = bulletIterator.next();
            bullet.paint(g);
            if(!bullet.isLiving()) bulletIterator.remove();
        }
    }

    private void paintTank(Graphics g) {
        for(ListIterator<BaseTank> tankListIterator = tanks.listIterator(); tankListIterator.hasNext();){
            BaseTank tank = tankListIterator.next();
            tank.paint(g);
            if(!tank.isLiving()) tankListIterator.remove(); // ConcurrentModificationException   //异步新增tank,数量对不上，导致报错
        }
    }

    private void collisonCheck() {
        for(ListIterator<BaseBullet> bulletIterator = bullets.listIterator(); bulletIterator.hasNext();){
            BaseBullet bullet = bulletIterator.next();
            bullet.collisionWidth(redTank);
            for(ListIterator<BaseTank> tankListIterator = tanks.listIterator(); tankListIterator.hasNext();){
                bullet.collisionWidth(tankListIterator.next());
            }

        }
    }

    class MyKeyListener extends KeyAdapter{

        boolean bL;
        boolean bU;
        boolean bR;
        boolean bD;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case  KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir(redTank);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case  KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_SPACE:
                    redTank.fire();
                default:
                    break;
            }
            setMainTankDir(redTank);
        }

        private void setMainTankDir(BaseTank tank){
            if(!bL && !bU && !bR && !bD) tank.setMoving(false);
            else tank.setMoving(true);

            if(bL) tank.setDir(Dir.LEFT);
            if(bU) tank.setDir(Dir.UP);
            if(bR) tank.setDir(Dir.RIGHT);
            if(bD) tank.setDir(Dir.DOWN);
        }
    }

}
