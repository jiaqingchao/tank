multiplayerchat项目

1. 改造Client

2. 暴露调用接口 new Client.connect()

3. 窗口显示完毕后调用newClient.connect()；

4. Client类保存并初始化Channel

5. 封装C冷天.send(String msg) 函数

   1. 保存Client中Channel对象，连接完毕后进行初始化
   2. 添加send(String msg) 函数
   3. 用初始化好的Chanel进行转换

6. ClientFrame类保存并初始化Client

7. client端接收到channelRead后更新界面

8. 把ClientFrame做成单例

9. 在main中显示

10. CLientHandler接收到数据后更新Frame中的ta

11. 重构actionPerformed

12. 完成了基础的多人聊天功能

13. Server端的异常处理

14. 删除clients中保存的channel

15. 客户端关闭

    1. 通知服务器退出
    2. 服务器接收到特定消息进行处理
    3. 服务器移除channel并关闭ctx

16. 加入ServeFram

17. 设为单例

18. 添加server的ServerStart方法，并在成功启动后给出提示

19. 去掉按钮的监听

20. 加入updateClientMsg 在接收到客户端消息的时候调用

21. 学习使用Codec

    1. 定义TankMsg x,y
    2. TankMsgEncoder负责编码
    3. TankMsgDecoder负责解码
    4. 将Encoder加入客户端Channel处理器
    5. 将Decoder加入服务器Channel处理器
    6. 在客户端channelctive的时候发送给一个TankMsg
    7. 观察服务器是否接收正确

22. 学习使用EmbeddedChannel进行Codec的单元测试

    