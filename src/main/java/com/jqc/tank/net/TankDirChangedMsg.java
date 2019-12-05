package com.jqc.tank.net;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Dir;

import java.io.*;
import java.util.UUID;

public class TankDirChangedMsg extends Msg{

    int x, y;
    Dir dir;
    public UUID id;

    public TankDirChangedMsg() {
    }

    public TankDirChangedMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.id = tank.getId();
    }

    public TankDirChangedMsg( UUID id ,int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
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
            dos.writeInt(dir.ordinal());
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
            this.dir = Dir.values()[dis.readInt()];
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
        return "TankDirChangedMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }

        Tank t = TankFrame.INSTANCE.findTankByUUID(id);
        if(t != null){
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDirChanged;
    }
}
