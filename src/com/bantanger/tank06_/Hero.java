package com.bantanger.tank06_;

import java.util.Vector;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class Hero extends Tank {
    // 定义shot对象,表示一个射击
    Shot shot = null;
    // boolean isLive = true;
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }
    // 射击,获得当前坦克方法
    public void shotEnemyTank(){
        // 根据当前Hero对象的位置和方向来创建Shot对象
        switch (getDirect()){
            case 0: // 向上
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() + 20,1);
                break;
            case 2:
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3:
                shot = new Shot(getX()  ,getY() + 20,3);
                break;
        }
        // 把shot放到shots集合中，连发效果
        // 最多发射5颗
        if (shots.size() < 5){
            shots.add(shot);
        }

        // 无限连发
//        shots.add(shot);

        // 线程启动
        new Thread(shot).start();
    }
}
