package com.bantanger.TankFight;

import java.io.*;
import java.util.Vector;

/**
 * @author bantanger 半糖
 * @version 0.7
 * <p>
 * 用于记录相关玩家数据信息，与文件交互
 * 相关说明：
 * static使用原因是因为要保证在类加载时就加载出玩家数据
 * 获取集合Vector的所有坦克坐标 --> (OOP思想)
 * 通过在类中设置一个集合属性，用setXxx方法将其他文件的坦克成员传入到本类中
 */
public class Recorder {
    // 定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    // 定义IO对象
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static String recordFile = "properties\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    // 创建node节点集合，每个节点存放一个上局幸存坦克的对象坐标
    private static Vector<Node> nodes = new Vector<>();

    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            String readLen = "";
            BufferedReader br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            while ((readLen = br.readLine()) != null) {
                String[] Point = readLen.split(" ");
                Node node = new Node(Integer.parseInt(Point[0]), Integer.parseInt(Point[1]),
                        Integer.parseInt(Point[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\n");
            // 用序列化记录对象可行吗？ --> 读文件时会出错
            // 记录当前还存活的坦克坐标
            for (int i = 0; i < enemyTanks.size(); i++) {
                // 取出坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " "
                            + enemyTank.getDirect();
                    bw.write(record + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    // 我方击毁坦克后就让记录自增
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
