package com.jqc.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    public static ChannelGroup cliens = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        new Server().serverStart();
    }

    public void serverStart(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();//此实例是netty服务端应用开发的入口

        try {
            ChannelFuture f = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer())
                    .bind(8888).sync();

            ServerFrame.INSTANCE.updateServerMsg("servet started");

            f.channel().closeFuture().sync(); //close() -> ChannelFuture,  //ChannelFuture调用close()时执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class ServerChannelInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline(); // 责任链模式
        channelPipeline
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ServerHandler());
    }
}

class ServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.cliens.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        Server.cliens.writeAndFlush(msg);

//        try{
//            TankJoinMsg tm = (TankJoinMsg)msg;
//            ServerFrame.INSTANCE.updateServerText(tm.toString());
//        }finally {
//            ReferenceCountUtil.release(msg);
//        }

//        try{
//            ByteBuf buf = (ByteBuf) msg;
//            String s = buf.toString(CharsetUtil.UTF_8);
//            if(s.equals("_bye_")){
//                ServerFrame.INSTANCE.updateServerMsg("客户端要求退出");
//                Server.cliens.remove(ctx.channel());
//            }else {
//                Server.cliens.write(msg);
//            }
//            ServerFrame.INSTANCE.updateServerText(s);
//        }finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //删除出现异常的客户端channel,并关闭连接
        Server.cliens.remove(ctx.channel());
        ctx.close();
    }
}