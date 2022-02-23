package com.bantanger.tank03_;

import javax.swing.*;

/**
 * @author bantanger 半糖
 * @version 1.0
 */

// 继承画板
public class TankGame03 extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame03 tankGame = new TankGame03();
    }
    public TankGame03() {
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
