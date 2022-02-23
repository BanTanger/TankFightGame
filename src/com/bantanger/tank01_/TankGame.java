package com.bantanger.tank01_;

import javax.swing.*;

/**
 * @author bantanger 半糖
 * @version 1.0
 */

// 继承画板
public class TankGame extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    public static void main(String[] args) {

        TankGame tankGame = new TankGame();
    }
    public TankGame() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
