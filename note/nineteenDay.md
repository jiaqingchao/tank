1. 加入Msg体系后的代码修正
   1. 加入重写方法
   2. 加入MsgType 类
   3. Client.send(盖屋Msg类型)
   4. ChannelRead0 改为Msg类型
   5. Encoder和Decoder
   6. 修改单元测试
2. 加入新的TankStartMovingMsg
   1. 修改TankJoinMsgDecoder为MsgDecoder Encoder 同样处理
   2. 加入TankStartMovingMsg并重写相应方法
   3. 加人TankStartMovingMsg(Tank)构造方法
   4. 修改ChannelHander channelRead()处理msg
   5. 在setMainDir中发出TankStartMoving消息
   6. 在TankStartMoving.handle中处理收到该消息的逻辑
3. 加入PlayerStopMsg
   1. 何时发出
   2. 解码处理
   3. 编解码测试
   4. 收到如何处理
4. 作业
   1. 加入其它必要的Msg，完成基本的联网功能
      1. 小bug,小卡顿startmoving发送过多
      2. 子弹的消息
      3. 坦克死掉的消息
      4. startmoving只在开始的时候发送一次
5. nagle算法把小包合并的一起发送，网络游戏一般禁用这个算法
   1.   .option(ChannelOption.TCP_NODELAY, true) 