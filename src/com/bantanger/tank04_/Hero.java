package com.bantanger.tank04_;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class Hero extends Tank {
    // 定义shot对象,表示一个射击
    Shot shot = null;
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
        // 线程启动
        new Thread(shot).start();
    }
}
