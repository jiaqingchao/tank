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

2.详解observer设计模式

3.详解decorator设计模式

4.Flyweight?