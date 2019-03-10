package com.sr.chazhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 哈希查找
 * 类似于简单的hashMap
 * 在下面的实现没有体现以空间换时间的效果，
 * 单纯的是为了找到元素的位置，
 * hashMap是一个完整的哈希查找的实现，
 */
public class HashSelect {
    private static class DefineHashMap{

        private final List<List<Integer>> list = new ArrayList<List<Integer>>(10);

        {
            //初始化
            for (int i = 0;i < 10;i++){
                list.add(new ArrayList<Integer>());
            }
        }

        public void add(int value){
            list.get(getHashCode(value)).add(value);
        }

        public void printIndex(int value){
            List<Integer> list2 = list.get(getHashCode(value));
            boolean flag = false;
            for (int i = 0;i < list2.size();i++){
                if (list2.get(i) == value){
                    flag = true;
                    System.out.println("查找的值在" + getHashCode(value) + "，" + i);
                    break;
                }
            }
            if (!flag){
                System.out.println("查找的值不存在！");
            }
        }

        public int getHashCode(int value) {
            return value%10;
        }

        @Override
        public String toString() {
            return list.toString();
        }
    }

    public static void main(String[] args) {
        //初始化集合
        DefineHashMap hashMap = new DefineHashMap();
        hashMap.add(1);hashMap.add(5);
        hashMap.add(4);hashMap.add(8);
        hashMap.add(3);hashMap.add(6);
        hashMap.add(15);hashMap.add(13);
        System.out.println(hashMap);
        //查找元素的具体位置并打印。
        hashMap.printIndex(15);
    }
}
