package com.jqc.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byte[] bytes = msg.toBytes();
        byteBuf.writeInt(msg.getMsgType().ordinal());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
