package com.jqc.tank;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.net.Client;
import com.jqc.tank.net.TankDirChangedMsg;
import com.jqc.tank.net.TankStartMovingMsg;
import com.jqc.tank.net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;

public class TankFrame extends Frame {

    public static final int WIDTH = PropertyMgr.getInt(CONSTANTS.PROPERTY_GAME_WINDOW_WIDTH);
    public static final int HEIGHT = PropertyMgr.getInt(CONSTANTS.PROPERTY_GAME_WINDOW_HEIGHT);

    public final static TankFrame INSTANCE = new TankFrame();

    Random r = new Random();

    public Map<UUID,Tank> tanks = new HashMap<>();
    public Map<UUID,Bullet> bullets = new HashMap<>();
    public List<Explode> explodes = new ArrayList<>();

    Tank redTank = new Tank(r.nextInt(WIDTH),r.nextInt(HEIGHT),Dir.DOWN, Group.RED);
//    Tank redTank = new Tank(100,100,Dir.DOWN, Group.RED, this);

    private TankFrame(){

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
        bullets.clear();
        tanks.clear();
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
        for(int i = 0; i < explodes.size(); i++){
            Explode explode = explodes.get(i);
            explode.paint(g);
            if(!explode.isLiving()){
                explodes.remove(i--);
            }
        }
    }

    private void paintBullet(Graphics g) {
        Set<UUID> ids = bullets.keySet();
        Iterator<UUID> it = ids.iterator();
        while (it.hasNext()){
            UUID id = it.next();
            Bullet b = bullets.get(id);
            b.paint(g);
            if(!b.isLiving()){
                it.remove();
            }
        }
    }

    private void paintTank(Graphics g) {
        Set<UUID> ids = tanks.keySet();
        Iterator<UUID> it = ids.iterator();
        while(it.hasNext()){
            UUID id = it.next();
            Tank tank = tanks.get(id);
            tank.paint(g);
            if(!tank.isLiving()){
                it.remove();
            }
        }
    }

    private void collisonCheck() {
        for(Bullet bullet : bullets.values()){
            bullet.collisionWidth(redTank);
            for(Tank tank : tanks.values()){
                bullet.collisionWidth(tank);
            }
        }
    }
    public Tank getMainTank(){
        return this.redTank;
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }
    public void addBullet(Bullet b) {
        bullets.put(b.getId(), b);
    }
    public void addExplode(Explode e) {
        explodes.add(e);
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }
    public Bullet findBulletByUUID(UUID id) {
        return bullets.get(id);
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

        private void setMainTankDir(Tank tank){
            if(!bL && !bU && !bR && !bD){
                tank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMainTank()));
                return;
            }
            Dir oldDir = tank.getDir();
            if(bL){
                tank.setDir(Dir.LEFT);
            }
            if(bU) {
                tank.setDir(Dir.UP);
            }
            if(bR) {
                tank.setDir(Dir.RIGHT);
            }
            if(bD) {
                tank.setDir(Dir.DOWN);
            }
            if(!redTank.isMoving()){
                Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));
            }
            tank.setMoving(true);

            if(!redTank.getDir().equals(oldDir)){
                Client.INSTANCE.send(new TankDirChangedMsg(getMainTank()));
            }

        }
    }

}
