package com.bantanger.tank07_;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author bantanger 半糖
 * @version 0.7
 * <p>
 * ## 新增功能
 * 1.防止敌方坦克重叠 --> 碰撞检测函数 isTouchEnemyTank()
 * 2.记录玩家成绩，存盘退出 --> 显示函数 showInfo()
 * 3.记录当时敌人坦克坐标，存盘退出 --> Recorder类，保存玩家成绩和当前剩余坦克坐标
 * 4.玩游戏时，可选择时开启新游戏还是继续 上局游戏 --> Node类 存放坦克信息，当继续上局游戏时就读取配置文件，读取上局坦克坐标
 */

// 继承画板
public class TankGame07 extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame07 tankGame = new TankGame07();
    }

    public TankGame07() {
        // 先进行判断是否有上一局记录
        // if ()
        System.out.println("输入选择 1: 新游戏 2: 继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        // 将mp 放在 Thread ,并启动
        // MyPanel本身实现了线程
        new Thread(mp).start();
        this.add(mp);
        // this.setSize(1000, 750);
        this.setSize(1300, 750); // 扩大窗口面积用于存放玩家数据
        this.addKeyListener(mp); // JFrame 监听 mp的键盘事件
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        // JFrame 中增加退出按钮的相应
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 调用Record,保存文件
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
