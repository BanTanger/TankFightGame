package com.bantanger.tank02_;

import javax.swing.*;

/**
 * @author bantanger 半糖
 * @version 1.0
 */

// 继承画板
public class TankGame02 extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame02 tankGame = new TankGame02();
    }
    public TankGame02() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp); // JFrame 监听 mp的键盘事件
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
