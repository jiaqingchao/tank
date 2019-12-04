package com.jqc.tank;

import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Explode;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.CONSTANTS;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;
import com.jqc.tank.common.PropertyMgr;
import com.jqc.tank.net.Client;
import com.jqc.tank.net.TankStartMovingMsg;
import com.jqc.tank.net.TankStopMsg;
import com.jqc.tank.strategy.SquareFireStrategy;

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
    public List<Bullet> bullets = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();

    Tank redTank = new Tank(r.nextInt(WIDTH),r.nextInt(HEIGHT),Dir.DOWN, Group.RED, this);
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
        bullets.removeAll(bullets);
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
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.paint(g);
            if(!bullet.isLiving()){
                bullets.remove(i--);
            }
        }
    }

    private void paintTank(Graphics g) {
        Collection<Tank> tankList = tanks.values();
        Iterator<Tank> iterator = tankList.iterator();
        while(iterator.hasNext()){
            Tank tank = iterator.next();
            tank.paint(g);
            if(!tank.isLiving()){
                tanks.remove(tank);
            }
        }

//        for (Tank tank : tanks.values()) {
//            tank.paint(g);
//            tanks.remove(tank);
//        }
//        tanks.values().stream().forEach((tank)->{
//            tank.paint(g);
//            tanks.remove(tank);
//        });
    }

    private void collisonCheck() {
        for(ListIterator<Bullet> bulletIterator = bullets.listIterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();
            bullet.collisionWidth(redTank);
            for(UUID id : tanks.keySet()){
                bullet.collisionWidth(tanks.get(id));
            }

        }
    }
    public Tank getMainTank(){
        return this.redTank;
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }

    public Tank findByUUID(UUID id) {
        return tanks.get(id);
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
                    SquareFireStrategy.getInstance().fire(redTank);
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
        }
    }

}
