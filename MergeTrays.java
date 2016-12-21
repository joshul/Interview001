package com.joshuya.interview001;

import java.util.*;

/**
 * Created by joshuya on 2016/12/19.
 */
public class MergeTrays {
    static class Tray {
        int id;
        List<Goods> goods;
    }
    static class Goods {
        int id;
        int count;
    }

    private static final int MAX_TRAYS = 10;
    private static final int MAX_TRAY_ID = 6;
    private static final int MAX_GOODS = 3;
    private static final int MAX_GOODS_ID = 10;
    private static final int MAX_GOODS_COUNT = 3;

    public static void main(String[] args) {
        List<Tray> trays = genTrays();
        printTrays("原始数据", trays);
        List<Tray> result = mergeTrays(trays);
        printTrays("处理结果", result);
    }
    private static List<Tray> genTrays() {
        List<Tray> trays = new ArrayList<Tray>();
        for(int i = 0; i < MAX_TRAYS; ++i) {
            Tray tray = genTray();
            trays.add(tray);
        }
        return trays;
    }
    private static Random rnd = new Random();
    private static Tray genTray() {
        Tray tray = new Tray();
        tray.id = rnd.nextInt(MAX_TRAY_ID);
        tray.goods = genGoods();
        return tray;
    }
    private static List<Goods> genGoods() {
        List<Goods> result = new ArrayList<Goods>();
        int goodsSize = rnd.nextInt(MAX_GOODS) + 1;
        for(int i = 0; i < goodsSize; ++i) {
            Goods g = new Goods();
            g.id = rnd.nextInt(MAX_GOODS_ID);
            g.count = rnd.nextInt(MAX_GOODS_COUNT) + 1;
            result.add(g);
        }
        return result;
    }

    private static void printTrays(String title, List<Tray> list) {
        System.out.println(title);
        for(Tray t : list) {
            if(t == null)
                continue;
            System.out.print("\tTray_" + t.id + ": ");
            if(t.goods != null) {
                for(Goods g : t.goods) {
                    if(g == null)
                        continue;
                    System.out.print("(Goods_" + g.id + ", " + g.count + "), ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * 方法一
     */
 /*   private static List<Tray> mergeTrays(List<Tray> list) {
        //TODO 请补充代码
        *//*for(int i = 0;i < list.size(); i++){
            //第一步把相同托盘内的商品合并
            for (int j = i+1; j <list.size(); j++ ) {

                if (list.get(i).id == list.get(j).id) {
                    list.get(i).goods.addAll(list.get(j).goods);
                    list.remove(j);
                    j--;
                }
            }
            //第二步把托盘内的相同的商品数目相加
            for(int k = 0;k < list.get(i).goods.size() -1; k++){

                for(int t = k + 1;t < list.get(i).goods.size(); t++){

                    if(list.get(i).goods.get(k).id == list.get(i).goods.get(t).id){
                        list.get(i).goods.get(k).count += list.get(i).goods.get(t).count;
                        list.get(i).goods.remove(t);
                        t--;
                    }
                }
            }
        }*//*
       return list;
    }
    */
    /**
     * 方法二
     */
    private static List<Tray> mergeTrays(List<Tray> list) {
        //TODO 请补充代码
        List<Tray> resultList = new ArrayList<Tray>();
        Map<Integer,Object> map = new HashMap<Integer,Object>();

        for(int i = 0; i < list.size(); i++){
            boolean hasTray = false;
            //
            if(i == 0){
                List<Goods> li2 = filter(list,i);
                map.put(list.get(i).id, li2);
                continue;
            }
            Set set = map.keySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Integer key = (Integer) it.next();
                Object value = map.get(key);
                /**
                 * 如果list中Tray的ID不等于map中Tray的ID，则直接与map中下一个Tray比较
                 */
                if(key != list.get(i).id){
                    continue;
                }
                /**
                 * map中已经存在该Tray,以下逻辑为：list中的Tray与map中的Tray的ID相同，继续比较Goods部分
                 */
                hasTray = true;
                //map中该Tray的Goods集合
                List<Goods> mapList = (List<Goods>) value;
                //List中该Tray的Goods集合
                List<Goods> listList = list.get(i).goods;
                /**
                 * map中该Tray下是否存在这个Good，若存在，则count相加；若不存在，则添加到map中的Tray的Goods集合中
                 */

                //遍历下一行goods集合
                for(int j = 0; j < listList.size(); j++){
                    boolean hasGood = false;
                    Goods goodJ = listList.get(j);
                    /**
                     * 将map中的goods集合与goodJ比较，
                     */
                    for(int k = 0; k < mapList.size(); k++){
                        Goods goodK = mapList.get(k);
                        //如果list中的Good和map中的Good的ID相同，则将两个Good的count相加
                        if(goodJ.id == goodK.id){
                            goodK.count += goodJ.count;
                            mapList.set(k, goodK);
                            hasGood = true;
                        }
                    }
                    //如果map中该Tray下没有这个Good，则添加到该Tray下
                    if(!hasGood){
                        mapList.add(goodJ);
                    }
                }
                map.put(key, mapList);
            }
            //map中没有该Tray，则添加到map中
            if(!hasTray){
                List<Goods> li2 = filter(list,i);
                map.put(list.get(i).id, li2);
            }
        }

        Set set2 = map.keySet();
        Iterator it2 = set2.iterator();
        while(it2.hasNext()){
            Integer key = (Integer) it2.next();
            Object value = map.get(key);

            Tray tray = new Tray();
            tray.id = key;
            tray.goods = (List) value;
            resultList.add(tray);
        }

        return resultList;
    }

    public static List<Goods>  filter(List<Tray> list,int i){
        List<Goods> li = list.get(i).goods;
        List<Goods> li2 = new ArrayList<Goods>();
        for(int j = 0; j < li.size(); j++){
            if(j == 0){
                li2.add(li.get(j));
                continue;
            }
            boolean hasGood1 = false;
            Goods good1 = li.get(j);
            for(int k = 0; k < li2.size(); k++){
                Goods good2 = li2.get(k);
                if(good1.id == good2.id){
                    good2.count += good1.count;
                    li2.set(k, good2);
                    hasGood1 = true;
                }
            }
            if(!hasGood1){
                li2.add(good1);
            }
        }
        return li2;
    }
}
