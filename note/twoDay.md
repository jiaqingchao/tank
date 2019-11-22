1.如何定义主战坦克的方向
    a.Enum Dir
2.根据按键改变主战坦克的方向
    a.setMainTankDir()
3.根据方向进行坦克的移动
4.怎处理坦克的静止状态
    a.moving = false
5.想象如何给出更多坦克，以及子弹
    a.将坦克封装成类
6.用双缓冲解决闪烁问题
    a.repaint - update
    b.截获update(重写)
    c.首先把该画出来的东西（坦克，子弹）先画在内存的图片中，图片大小和游戏画面一致
    d.吧内存中的图片一次性画到屏幕上（内存的内容复制到显存）
7.打出一颗子弹
    a.按下“空格”键，主战坦克打出一颗子弹
    b.用面向对象的思想考虑
8.打出一串子弹
    a.将子弹装在容器中
    

我没有想到的
    a.使用Enum控制方向
        我是直接使用4个while让他动起了
    b.坦克和子弹都是在frame上显示，那么frame应该有其引用，然后调用对象的画图方法就行了
        我是给设置增加坦克的子弹的数量，然后判断子弹数量去画图，没有实现，思路都是错了


清除子弹时使用forEach循环，中间remove对象， 抛java.util.ConcurrentModificationException
 
    ListIterator<Bullet> iterator = bullets.listIterator();
    while(iterator.hasNext()){
        Bullet bullet = iterator.next();
        bullet.paint(g);

        if(!bullet.isLiving()) iterator.remove();

    }
    
作业: 搞出一个排的敌人的坦克，挨排儿干掉他们

new tank 放在坦克List
可以打子弹，（2s一个/先不可以打）



使用线程自动敌方坦克，iterator.next() 抛java.util.ConcurrentModificationException

