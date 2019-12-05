1.代码重构

​	a,tank.back()

​		i.用oldX,oldY记录上一步的位置，坦克互撞后回到上一步

​		ii.修改TankTankCollider

​	b.添加一堵墙

​		i.class Wall extends GameObject

​		ii.定义属性rect

​		iii.定义BulletWallCollider

​		IV.定义TankWallCollider

​	c.bullet.ColliderWidth(Tank)的逻辑移到BulletTankCollider中

​	d.GameModel做成单例

​	e.对于GameModel.add()方法优化

​		GameModel.getInstance().add(this); 二级耦合降为3级耦合

​		i.一级耦合：继承

​		ii.二级耦合：一个类是另一个类的属性

​		iii.三级耦合：一个类中调用了另一个类的属性或方法

2.详解observer设计模式

3.详解decorator设计模式 

​	a.为了学术而学术，一般不用

4.Flyweight?