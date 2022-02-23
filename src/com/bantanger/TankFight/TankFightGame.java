package com.bantanger.TankFight;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author bantanger 半糖
 * @version 1.0
 * <p>
 * 最终版
 * 新增音乐功能 --> AePlayWave
 * 新增己方坦克被毁后退出游戏功能
 */

// 继承画板
public class TankFightGame extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankFightGame tankGame = new TankFightGame();
    }

    public TankFightGame() {
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
