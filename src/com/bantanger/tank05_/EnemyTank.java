package com.bantanger.tank05_;

import java.util.Vector;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true; // 默认存活,若被子弹撞到就改成false
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while(true){
            // 根据坦克方向自由移动
            switch (getDirect()){
                case 0: // 向上
                    // 让坦克保持一个方向走三十步
                    for (int i = 0; i < 30; i++) {
                        moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1: // 向上
                    for (int i = 0; i < 30; i++) {
                        moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 2: // 向上
                    for (int i = 0; i < 30; i++) {
                        moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 3: // 向上
                    for (int i = 0; i < 30; i++) {
                        moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            // 休眠1000ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDirect((int)(Math.random() * 4));
            if (!isLive){
                break;
            }
        }
    }
}
