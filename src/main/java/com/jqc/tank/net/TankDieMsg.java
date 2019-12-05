package com.jqc.tank.net;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.bean.Tank;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg{

    UUID bulletId;
    UUID id;

    public TankDieMsg() {
    }

    public TankDieMsg(UUID id, UUID bulletId) {
        this.bulletId = bulletId;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBulletId() {
        return bulletId;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());

            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(dos != null){
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(baos != null){
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.bulletId = new UUID(dis.readLong(), dis.readLong());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(dis != null){
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(bais != null){
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "bulletId=" + bulletId +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {

        System.out.println("we got a tank die:" + id);
        System.out.println("and my tank is:" + TankFrame.INSTANCE.getMainTank().getId());
        Tank tt = TankFrame.INSTANCE.findTankByUUID(id);
        System.out.println("i found a tank with this id:" + tt);


        Bullet b = TankFrame.INSTANCE.findBulletByUUID(bulletId);

        if(b != null) {
            b.die();
        }

        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            TankFrame.INSTANCE.getMainTank().die();
        }else {
            Tank t = TankFrame.INSTANCE.findTankByUUID(id);
            if(t != null){
                t.die();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }
}
