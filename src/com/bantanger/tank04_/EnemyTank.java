package com.bantanger.tank04_;

import java.util.Vector;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class EnemyTank extends Tank {
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true; // 默认存活,若被子弹撞到就改成false
    public EnemyTank(int x, int y) {
        super(x, y);
    }
}
