package com.bantanger.tank07_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author bantanger 半糖
 * @version 1.0
 * Tank绘图区域
 */
@SuppressWarnings({"all"})
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义操作人tank
    Hero hero = null;
    // 定义敌人坦克，放到Vector中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    // 定义Node集合存放上局坦克数据
    Vector<Node> nodes = new Vector<>();
    int enemyTankSize = 3;
    // 定义一个Vector ,用于存放炸弹
    // 当子弹击中坦克是,加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();

    // 三张图演示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        // 用nodes 接收 上局坦克对象
        nodes = Recorder.getNodesAndEnemyTankRec();
        // 在游戏启动时就要调用setEnemyTanks方法，不然数据没有传输到Recorder导致空指针异常
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500, 100); // 初始化自己坦克
        hero.setSpeed(5); // 自己坦克速度

        switch (key){
            case "1":
                // 初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    // 创建敌方坦克
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    // 将enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    // 设置方向
                    enemyTank.setDirect(2);
                    // 启动敌人坦克线程
                    new Thread(enemyTank).start();
                    // 给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    // 把子弹计入Vector中.
                    enemyTank.shots.add(shot);
                    // 启动 shot 对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                // 初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    // 创建敌方坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    // 将enemyTanks 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    // 设置方向
                    enemyTank.setDirect(node.getDirect());
                    // 启动敌人坦克线程
                    new Thread(enemyTank).start();
                    // 给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    // 把子弹计入Vector中.
                    enemyTank.shots.add(shot);
                    // 启动 shot 对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误...");
        }
        synchronized (this){
            image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
            image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
            image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        }
    }

    public void hitEnemyTank() {
        // 判断是否击中敌人坦克
        // 先对hero.shot判空,避免空指针错误
        // 导致的结果,只有坦克动 子弹才能射出,否则射不出
        if (hero.shot != null && hero.shot.isLive) {
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);
                // 当前子弹存活,for循环遍历敌方tank
                for (int J = 0; J < enemyTanks.size(); J++) {
                    EnemyTank enemyTank = enemyTanks.get(J);
//                        hitTank(hero.shot, enemyTank);
                    // 把hero去掉改成shot，即为遍历shots集合里面的shot判断
                    hitTank(shot, enemyTank);
                }
            }

            // bug 代码， 连发只能让最后一个子弹产生实际作用
//                if (hero.shot != null && hero.shot.isLive) {
            //当前子弹存活,for循环遍历敌方tank
//                    for (int J = 0; J < enemyTanks.size(); J++) {
//                        EnemyTank enemyTank = enemyTanks.get(J);
//                        hitTank(hero.shot, enemyTank);
//                    }
//                }
        }
    }

    // 子弹是否打中己方
    public void hitHero(){
        // 遍历所有敌方坦克的所有子弹，寻找是否有子弹已经处于坦克的生命范围（击中坦克）
        for (int i = 0; i < enemyTanks.size(); i++) {
            // 取出敌方所有坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            // 遍历enemyTank 对象里的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                // 取出子弹
                Shot shot = enemyTank.shots.get(j);
                // 判断shot是否击中hero
                if (hero.isLive && shot.isLive){
                    hitTank(shot,hero);
                }
                if (!hero.isLive){
                    // 调用Record,保存文件
                    Recorder.keepRecord();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        }
    }
    /*public void hitTank(Shot s,Hero tank) {
        switch (tank.getDirect()) {
            case 0:// 向上
            case 2:// 向下
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    // 创建Bombs对象,加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:// 向右
            case 3:// 向左
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    // 创建Bombs对象,加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }*/
    // 子弹是否击中敌人
    // 怎么调用,在哪调用 --> run方法
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0:// 向上
            case 2:// 向下
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    // 我方机会敌方坦克，对数据allEnemyTankNum ++ ，先进行类型判断，摧毁的是敌方坦克才增加
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    // 创建Bombs对象,加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:// 向右
            case 3:// 向左
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    // 我方机会敌方坦克，对数据allEnemyTankNum ++ ，先进行类型判断，摧毁的是敌方坦克才增加
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    // 创建Bombs对象,加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    public void GameOver(Graphics g){
        g.setColor(Color.WHITE);
        Font font = new Font("宋体", Font.BOLD, 65);
        g.setFont(font);

        // 定位坐标
        g.drawString("!!!GAME OVER!!!",100,375);
    }
    public void showInfo(Graphics g){

        // 画出玩家的总成绩
        // 定义画笔颜色为黑色，字号为25，宋体
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        // (1020.30) 坐标定位
        g.drawString("玩家累计击毁坦克",1020,30);
        drawTank(1020,60,g,0,0);

        g.setColor(Color.black); //  再次修改画笔颜色，因为坦克是蓝色更改了画笔颜色
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
        // drawString 第一参数要求String 但getAllEnemyTankNum返回的是int，
        // 所以需要将int 转换成 String  --> 直接加一个“”
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        showInfo(g); // 显示玩家数据

        //画出坦克-封装方法
        if (hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }else{
            GameOver(g);
        }

        // 画出hero射击出的子弹
//        if (hero.shot != null && hero.shot.isLive == true) {
//            g.fill3DRect(hero.shot.x, hero.shot.y, 3, 3, false);
//        }

        // 将hero的子弹集合shots 遍历取出绘制
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                g.fill3DRect(shot.x, shot.y, 3, 3, false);
            } else { // 如果该shot对象已经无效，就从shots集合中拿掉
                hero.shots.remove(shot);
            }
        }

        // 如果bombs 集合中有对象,就画出
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                // this 代表在当前界面画
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            // 炸弹生命值减少
            bomb.lifeDown();
            // 如果bombs life 为 0
            if (bomb.life == 0) {
                bombs.remove(bomb);// 移除炸弹
            }
        }
        // 画出敌人坦克，遍历Vector
        // for (int i = 0; i < enemyTankSize; i++)
        // 错误 ， enemyTankSize是定量，不能改变，如果敌方坦克被销毁还是会循环3个的
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            // 判断当前敌方坦克是否存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                // 画出敌人子弹,遍历,不要用迭代器,会引发线程安全问题
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    // 从子弹集合Vector中 取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    // 绘制
                    if (shot.isLive) {
                        g.fill3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        // 从Vector 里面移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    //编写方法，画出坦克

    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向（上下左右）
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型坦克，设置不同颜色
        switch (type) {
            case 0: //敌人的坦克，青蓝色
                g.setColor(Color.cyan);
                break;
            case 1: //我们的坦克，黄色
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，来绘制坦克
        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wdsa 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            hero.setDirect(0);//
            //修改坦克的坐标 y -= 1
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D键, 向右
            hero.setDirect(1);
            hero.moveRight();

        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S键
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A键
            hero.setDirect(3);
            hero.moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            // 发射一颗子弹
            // 我方坦克在发射的子弹消亡后，才能发射新的子弹
//            if (hero.shot == null || !hero.shot.isLive) {
//                hero.shotEnemyTank();
//            }

            // 发射多颗子弹
            hero.shotEnemyTank();
        }
        //让面板重绘
        for (int i = 0; i < 5; i++) {
            this.repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 判断己方坦克是否击中敌方
            hitEnemyTank();
            // 判断敌方坦克是否击中己方
            hitHero();
            this.repaint();
        }
    }
}
