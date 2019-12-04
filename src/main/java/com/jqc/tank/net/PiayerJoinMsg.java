package com.jqc.tank.net;

import com.jqc.tank.common.Dir;
import com.jqc.tank.common.Group;

import java.io.*;
import java.util.UUID;

public class PiayerJoinMsg extends Msg{

    private static final MsgType Type = MsgType.PlayerJoin;

    int x, y;
    Dir dir;
    boolean moving;
    Group group;
    public UUID id;
    String name;

    public PiayerJoinMsg() {
    }
    public PiayerJoinMsg(Player player) {
        super();
        this.x = player.x;
        this.y = player.y;
        this.moving = player.isMoving();
        this.dir = player.getDir();
        this.group = player.getGroup();
        this.id = player.getId();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(Type.ordinal());
            dos.writeInt(x);
            dos.writeBoolean(moving);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeUTF(name);
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(dos != null){
                    dos.close();
                }
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
            //TODO 先读TYPE信息，根据TYPE信息处理不同的消息
            //略过消息类型
            dis.readInt();

            this.x = dis.readInt();
            this.y = dis.readInt();
            this.moving = dis.readBoolean();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.name = dis.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(dis != null){
                    dis.close();
                }
                if(bais != null){
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
