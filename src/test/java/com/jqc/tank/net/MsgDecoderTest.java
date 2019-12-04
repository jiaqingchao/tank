package com.jqc.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MsgDecoderTest {
    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        //Msg msg = new TankJoinMsg(5,10, Dir.RIGHT,true, Group.BLUE,id);
//        Msg msg = new TankStartMovingMsg(id, 5,10, Dir.RIGHT);
        Msg msg = new TankStopMsg(id, 5,10);
        ch.pipeline().addLast(new MsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

//        assertEquals(33, length);

        int x = buf.readInt();
        int y = buf.readInt();
//        boolean moving = buf.readBoolean();
//        Dir dir = Dir.values()[buf.readInt()];
//        Group group = Group.values()[buf.readInt()];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        Assert.assertEquals(5,x);
        Assert.assertEquals(10,y);
//        Assert.assertEquals(true,moving);
//        Assert.assertEquals(Dir.RIGHT,dir);
//        Assert.assertEquals(Group.BLUE,group);
        Assert.assertEquals(id,uuid);
    }

    @Test
    void decode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        //Msg msg = new TankJoinMsg(5,10, Dir.RIGHT,true, Group.BLUE,id);
        Msg msg = new TankStopMsg(id, 5,10);
        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msg.getMsgType().ordinal());
        buf.writeInt(msg.toBytes().length);
        buf.writeBytes(msg.toBytes());

        ch.writeInbound(buf.duplicate());
//        TankJoinMsg msgR = ch.readInbound();
        TankStopMsg msgR = ch.readInbound();

        Assert.assertEquals(5,msgR.getX());
        Assert.assertEquals(10,msgR.getY());
//        Assert.assertEquals(true,msgR.isMoving());
//        Assert.assertEquals(Dir.RIGHT,msgR.getDir());
//        Assert.assertEquals(Group.BLUE,msgR.getGroup());
        Assert.assertEquals(id,msgR.getId());
    }
}