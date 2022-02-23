 package com.bantanger.tank06_;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class Bomb {
    int x,y;
    int life = 9; // 生命周期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){ // 配合图片出现的爆炸效果
        if (life > 0){
            life--;
        }else{
            isLive = false;
        }
    }
}
