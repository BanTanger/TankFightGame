package com.bantanger.tank05_;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class Tank {
    // Tank 横纵坐标
    private int x;
    private int y;
    private int direct;// tank方向 0上 1右 2左 3下
    private int speed = 1;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上右下左移动方法
    public void moveUp() {
        if (y > 0) {
            y -= speed;
        }
    }

    public void moveRight() {
        if (x < 1000) {
            x += speed;
        }
    }

    public void moveDown() {
        if (y < 750) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x > 0) {
            x -= speed;
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
