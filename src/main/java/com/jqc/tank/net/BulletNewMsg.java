package com.jqc.tank.net;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Bullet;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{

    UUID playerID;
    UUID id;
    int x, y;
    Dir dir;
    Group group;


    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet) {
        this.playerID = bullet.getPlayerID();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
        this.id = bullet.getId();
    }

    public BulletNewMsg(UUID playerID, UUID id, int x, int y, Dir dir, Group group) {
        this.playerID = playerID;
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerID() {
        return playerID;
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
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeLong(playerID.getMostSignificantBits());
            dos.writeLong(playerID.getLeastSignificantBits());

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
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.playerID = new UUID(dis.readLong(), dis.readLong());

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
        return "BulletNewMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if(this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }
        Bullet b = new Bullet(this.playerID,this.x,this.y,this.dir,this.group);
        b.setId(this.id);
        TankFrame.INSTANCE.addBullet(b);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
