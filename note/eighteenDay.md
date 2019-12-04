1. checkout master
   1. 项目右键 - team -switch to - master
2. 改造现有程序
   1. 去掉敌人坦克，注释掉main里面初始化敌方坦克的内容
   2. 复制Client Server ServerFrame TankMsg TankMsgDecoder TankMsgEncoder
   3. 引入Netty依赖
      1. 将项目转换为maven项目
      2. 添加依赖Io.netty:netty-alt4.1.9Final
      3. 这时候有可能出现jre lib依赖丢失的问题，在项目上右击 - build path - add lib 选择jre重新加入
      4. 注释掉客户端ClientFrame的部分
3. 将TankMsg 修改为TankJoinMsg
   1. 将Tank加入UUID属性 标识独一无二的id号
   2. 写单元测试！！！！重要！！！！
4. 写通过TankJoinMsg环路
   1. 阅读源码历史版本
5. 接收到TankJoinMsg的逻辑处理
   1. 是不是自己
   2. 列表是不是已经有了
   3. 发自己的一个TankJoinMsg
6. 代码重构
   1. 用HashMap代替LinkedList
   2. 把Client设置为单例
   3. 在TankJoinMsg中处理接收
7. 处理多种消息（作业）
   1. 分析有哪些消息‘
   2. 处理各种各样的消息

