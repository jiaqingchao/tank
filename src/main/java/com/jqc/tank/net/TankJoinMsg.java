package com.jqc.tank.net;

import com.jqc.tank.TankFrame;
import com.jqc.tank.bean.Tank;
import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{

//    private static final MsgType Type = MsgType.PlayerJoin;

    int x, y;
    Dir dir;
    boolean moving;
    Group group;
    public UUID id;
//    String name;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Tank tank) {
        super();
        this.x = tank.getX();
        this.y = tank.getY();
        this.moving = tank.isMoving();
        this.dir = tank.getDir();
        this.group = tank.getGroup();
        this.id = tank.getId();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
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

    public boolean isMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
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
//            dos.writeInt(Type.ordinal());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeBoolean(moving);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
//            dos.writeUTF(name);
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
            this.moving = dis.readBoolean();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
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
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())
                || TankFrame.INSTANCE.findTankByUUID(this.id) != null){
            return;
        }
        Tank t = new Tank(this);
        TankFrame.INSTANCE.addTank(t);
        System.out.println(this);

        //send a new TankJoinMsg to the new joined Tank
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }
}
