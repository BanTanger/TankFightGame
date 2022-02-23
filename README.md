# README.md

## 说明：这是一个坦克大战的源代码

##### 使用JAVA语言实现 

### 如何下载并启动？

1.  点击 **Code** 按钮，并在其中找到 **Download ZIP**

1.  下载并解压到你随意的路径

3. 下载完成后在本地使用 IDEA 运行，打开IDEA左上角File -> Open... 找到文件按右下角的OK。（或许使用其他支持JAVA语言的其他软件也可运行，我没测试过）

   ！![image-20220223222031322](C:\Users\12902\AppData\Roaming\Typora\typora-user-images\image-20220223222031322.png)!![image-20220223222241141](C:\Users\12902\AppData\Roaming\Typora\typora-user-images\image-20220223222241141.png)

4. 本项目使用jdk8，在 IDEA 中可通过 设置 **run -> Edit Configurations 找到 Build and run 更改 jdk 至 java 8**  **！！！为了确保项目可正常运行，请将工作路径Working directory设置成 $MODULE_DIR$ **

   !![image-20220223185523403](C:\Users\12902\AppData\Roaming\Typora\typora-user-images\image-20220223185523403.png)

   !![image-20220223185955619](C:\Users\12902\AppData\Roaming\Typora\typora-user-images\image-20220223185955619.png)

5. 找到文件夹中**TankFight**目录的**TankFightGame 按右键的Run TankFight main()**或者是**使用快捷键Ctrl+Shift+F10**

***



### 版本更新迭代

+ tank01 到 tank02 坦克从画出到可以移动 ，增添敌人坦克
+ tank03 增添线程功能，己方可发射子弹，击中敌人会让敌方单位消失
+ tank04 新增爆炸效果，击中敌方会导致敌方爆炸动效
+ tank05 敌方坦克可以自由移动，新增边界处理机制，己方敌方都不可越过黑色边界
+ tank06 修改己方坦克子弹发射问题，单发子弹，多发子弹，以及无限子弹射击思路（最终版演示的是多发子弹射击，最多5颗），新增爆炸效果，敌方坦克击中己方会导致己方爆炸动效
+ tank07 修复坦克重叠bug（还有少许瑕疵，在坦克转向时会出现重叠情况），记录玩家成绩，并保存到配置文件供下一次记录保存，记录退出游戏时敌方坦克坐标（非己方），新增可选项：**开启新游戏还是继续上局游戏**（并没有使用**SWing**组件库，需要手动输入）
+ 最终版TankFightGame 新增音乐功能，新增己方坦克被摧毁后退出游戏功能

***

### 本项目使用到的技术

+ **Vector集合的使用：**敌方多单位，子弹集合（Shot类，MyPanel类），Node节点集合保存上局剩余坦克的坐标方向和当前积累杀敌数

  > 使用原因：线程安全
  >
  > 注意：遍历的时候要注意线程安全问题，所以要使用普通for遍历，用迭代器容易出问题
  >
  > ​			每击杀一个敌人就要在集合中remove移除单位，否则会造成幽灵坦克的bug

+ **IO流：**创建文件File类，包装流Buffered（Reader & Writer），以及配合包装流使用的节点流File（Reader & Writer）

  > 使用原因：用于记录存档
  >
  > 优化：当前读取和写入感觉有点麻烦，需要实现创造一个Node节点类存放tank类，感觉这个点可以优化成使用序列化和反序列化（使用序列化记得让序列化的对象实现接口）的知识，直接将一个坦克对象写入配置文件中，再通过对象流反序列化出来，自身没有实现。查资料说是会卡在加载坦克对象的时候？
  >
  > 注意：流在操作完一定要记得关闭 close（），不然会导致文件无法写入
  >
  > ​			相对路径的设置
  >
  > ​			做好判断文件是否存在的机制处理
  >
  > ​			创建空流 bw = null 时，后面设置流的节点流一定要注意变量前面是否有类型
  >
  > ​			！会导致空流关闭。![image-20220223212521256](C:\Users\12902\AppData\Roaming\Typora\typora-user-images\image-20220223212521256.png)			

+ 多文件间传入某一类到想要类里：
  + 通过在类中设置一个集合属性，用setXxx方法将其他文件的坦克成员传入到本类中
+ **线程：**（使用Runnable接口实现，可达到多继承的目的）子弹发射，坦克移动，画板重绘（repaint()放在run()内）
  + 敌人坦克的子弹线程部分写在敌人类里，这样的一个目的是在敌方单位死亡的时候可以连同敌方单位一起移除子弹，避免线程堵塞的极端情况。
  + 坦克移动：为了避免坦克原地不动的情况，使用随机数函数 ( int ) Math.random * 4 来设置上下左右四个方向,也为了避免坦克原地转圈的情况，设置每个方向至少走三十步的限制（30.fori & Thread.sleep(50))的限制。
  + 守护线程（synchronized）用于维护爆炸动效（好像没什么作用）
  + 然后将绘画类MyPanel也设置成了线程类，因为需要重复的去绘制repaint画像，如果不是多线程，在子弹飞到边界之前是无法绘制的，写了线程就要在调用类里去实现，这里不针对main，只要是调用到了线程类都需要添加对象实例，例如让自己的坦克添加子弹射击功能，就需要Thread（shot）.start(),(在自己坦克Hero类添加子弹创建实例功能，然后在监听方法里调用子弹射击类)。
+ static的妙用：
  + 使用static操作Recorder的所有属性和方法，可避免new新建文件。
  + 如果有些数据是隐私的，使用private static 的目的可能是为了在类加载时就加载出想要的数据。

***

### 一些BUG（已经更改，记录问题）

+ 在敌人己方受弹识别问题中:
  + 一开始因为把hitTank函数形参写成Tank父类，想着直接调用子类作为向上转型
  + 但实现过程中一直是子弹打中坦克只爆炸但不摧毁
  + 后来经过很长时间的调查，才知道原来是Tank父类书写了判断坦克是否存活的布尔变量，但在子类也书写了
  + 根据java机制，属性没有动态绑定，只有方法才有，于是hitTank函数修改的只是父类的isLive变量，子类的并没有修改，在画出坦克的判断过程中，自然会继续绘画。将子类的isLive去掉后就修改了问题。
+ 卡顿：
  + 是因为线程run 的休眠时间太长，修改成50ms就很流畅 
+ 子弹连发：
  + （放在线程中处理）先是创建了Shot的vector集合。
  + 如果是己方坦克，每次按下j键就创建一个shot对象（分成四个方向），将创建的shot对象add到shots集合中
  + 如果是敌方坦克，对子弹先进行一个判空，限定子弹最多发射几个
  + 因为没有对子弹或者坦克进行判空处理，导致出现坦克莫名爆炸的现象，实际上是因为将子弹的属性isLive设置成false，停止绘画子弹，实际上子弹依然存在在面板上，成为幽灵子弹。




​	

