package com.bantanger;

/**
 * @author bantanger 半糖
 * @version 1.0
 */
public class test {
    public static void main(String[] args) {
        Hero hero = new Hero();
        Enemy enemy = new Enemy();
        Tank tank = new Tank();
        A(hero);
        A(enemy);
        A(tank);
        B(hero);
        B(enemy);
        B(tank);
    }
    public static void A(Tank tank){
        tank.a = false;
    }
    public static void B(Tank tank){
        System.out.println(tank.a);
    }
}
class Tank {
    public boolean a = true;
}
class Hero extends Tank{
    public boolean a = true;
}
class Enemy extends Tank{
    public boolean a = true;
}
