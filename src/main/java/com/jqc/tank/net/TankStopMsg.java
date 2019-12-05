package com.jqc.tank.net;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Tank;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg{
    int x, y;
    public UUID id;

    public TankStopMsg() {
    }

    public TankStopMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.id = tank.getId();
    }

    public TankStopMsg(UUID id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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

            this.x = dis.readInt();
            this.y = dis.readInt();
            this.id = new UUID(dis.readLong(), dis.readLong());

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
        return "TankStopMsg{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }
        Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);
        if(t != null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
