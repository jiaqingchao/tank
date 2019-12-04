1. 为网络版坦克做好准备，学习真正生产环境中的TCPServer的写法
   1. 学习使用JDK Loggin API
      1. https://www.cnblogs.com/liaojie970/p/5582147.html
   2. 复习BIO-NIO-AIO-Netty的编程模型
      1. 网络程序的烦点：异常处理、正确关闭 ->线程的正常结结束 ->线程池的正常结束
   3. 写一个NettyServer
      1. EventLoopGroup 网络IOs事件处理线程组
      2. Netty中的任何方法都是异步模型
      3. 首先使用Channellnitailzer initChannle() 添加channel的Handler
      4. 在channelHandler中处理业务逻辑，学习使用ChannelInboundHandlerAdapter
   4. 写一个图形化的NettyServer
      1. nettyClent传递数据给nettyserver
      2. ByteBuf的使用
      3. 保存多个客户端
      4. 接收到一个客户端数据后传递给多个客户端
   5. 将NettyServer能够优雅的关闭
   6. 将项目转化为Maven项目
      1. 项目右击 configure - convert to Maven Project
      2. 在Maven中添加依赖：io netty:netty-all.4.19.Final
      3. 如果出现java.lang.Object无法解析问题，重置buildPath
2. check out master
3. 去掉NPC Non-Player Characher
   1. Main.java不再初始化
4. 为了在网络端欺负坦克，在Tank中加入UUID属性
5. 创建net package
6. 创建 net Server类，
   1. 为了调试程序，将Server做成窗口类
7. 添加辅助方法：addLeft addRight
8. 加入对BinStart的Action处理
9. 完成start方法
   1. 创建bossGroup和workerGroup
   2. 创建ServerBootstrap
   3. 加入 Channellnitializer 
   4. 每一个Channle addLast(new ServerHandler)
10. 完成ServerHander,重写方法
    1. channelRead()
    2. exceptionCaught()
11. Client端编程
    1. 连接并发送一个字符串
    2. 服务器端接收并返回
    3. 客户端接收
12. 正确结束程序
13. 自定义消息