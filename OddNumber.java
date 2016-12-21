package com.joshuya.interview001;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joshuya on 2016/12/19.
 */
public class OddNumber {
    private static final int MAX_COUNT = 10;

    public static void main(String[] args) {
        List<Integer> numbers = genNumbers();
        printNumbers("原始数据", numbers);
        transform(numbers);
        printNumbers("处理结果", numbers);
    }
    private static void printNumbers(String title, List<Integer> numbers) {
        System.out.println(title);
        for(Integer num : numbers) {
            System.out.print(num + ", ");
        }
        System.out.println();
    }

    private static List<Integer> genNumbers() {
        List<Integer> list = new ArrayList<Integer>();
        Random rnd = new Random();
        for(int i = 0; i < MAX_COUNT; ++i)
            list.add(rnd.nextInt(100));
        return list;
    }

    private static void transform(List<Integer> list) {
        //TODO 请补充代码
        for(int i = 0; i < list.size(); i++){
            Integer num = list.get(i);
            if(num%2 == 0){
                list.remove(num);
                i--;
            }
        }
    }
}
