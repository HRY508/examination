package com.examination.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description 用于生成随机数
 * @Author he
 * @Data 2022/10/18 20:55
 */

public class RandomUtil {
    //number指定生成随机数的个数,array是指定从此数组中生成随机数,返回随机生成的integer类型list
    public static ArrayList<Integer> random(Integer number,Integer arry[]){
        int[] randomArr = new int[number];
        //储存不同的随机数
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        while(list.size() < number){
            //0~array.length-1      (max-min+1)+min
            Integer index = random.nextInt(arry.length);
            if(!list.contains(arry[index])){
                list.add(arry[index]);
            }
        }
        return list;
    }
}
