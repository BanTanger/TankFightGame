package com.bantanger.tank06_;

import javax.swing.*;

/**
 * @author bantanger 半糖
 * @version 0.6
 *
 * 新增功能
 *  我方坦克在发射的子弹消亡后，才能发射新的子弹 （ 扩展，多颗子弹怎么发射 并控制在面板上最多只能五颗）
 *  敌方坦克在发射的子弹消亡后，可以继续发射新的子弹
 *  当敌方坦克击中我方坦克，我方坦克消失，并且出现爆炸效果
 */

// 继承画板
public class TankGame06 extends JFrame {
    //MyPanel定义
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame06 tankGame = new TankGame06();
    }
    public TankGame06() {
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
