package com.bantanger.tank05_;

import javax.swing.*;

/**
 * @author bantanger 半糖
 * @version 1.0
 */

// 继承画板
public class TankGame05 extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame05 tankGame = new TankGame05();
    }
    public TankGame05() {
        mp = new MyPanel();
        // 将mp 放在 Thread ,并启动
        // MyPanel本身实现了线程
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp); // JFrame 监听 mp的键盘事件
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
