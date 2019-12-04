package com.jqc.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 8){ //TCP拆包粘包的问题
            return;
        }
        byteBuf.markReaderIndex();

        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        int length = byteBuf.readInt();
        if(byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Msg msg = (Msg)Class.forName("com.jqc.tank.net." + msgType.toString()+"Msg")
                .getConstructor().newInstance();
        msg.parse(bytes);
        list.add(msg);
    }

}
