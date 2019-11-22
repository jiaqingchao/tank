1.将坦克换成图片
    a.关于classloader的基础知识
    b.显示图片，使用ImageIO
2.将子弹换成图片
3.将子弹从坦克中心位置打出去
    a.根据坦克图片的大小，和左上角的位置计算子弹在左上角的位置
    b.将子弹从炮筒前方的位置打出去
4.加入敌军坦克
    a.分阵营Group
5.子弹与敌军坦克的碰撞检测
    a.击毁敌军一辆坦克
6.加入多辆敌军坦克
7.使敌军坦克打出子弹
    a.碰撞检测添加阵营判断
    b.加入我方坦克和敌方子弹的碰撞检测
8.让敌军坦克动起来
9.加入爆炸
10.加入声音

作业：
1.把爆炸功能完成（已完成）
2.贪吃蛇 打飞机
3.敌人坦克随机动，随机发射子弹（已完成）
4.边界检测（已完成）

//用一个Rectangle来记录子弹或坦克的位置
Rectangle rect1 = new Rectangle(tank.getX(), tank.getY(),Tank.WIDTH,Tank.HEIGHT);
Rectangle rect2 = new Rectangle(this.x, this.y,Bullet.WIDTH,Bullet.HEIGHT);