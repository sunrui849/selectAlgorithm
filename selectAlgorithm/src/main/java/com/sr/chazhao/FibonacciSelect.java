package com.sr.chazhao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 斐波那契查找
 */
public class FibonacciSelect {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(456);list.add(156);list.add(134);list.add(852);list.add(165);
        list.add(235);list.add(146);list.add(568);list.add(341);list.add(342);
        list.add(564);list.add(765);list.add(864);list.add(445);list.add(667);
        list.add(223);list.add(445);list.add(653);list.add(434);list.add(656);
        list.add(852);list.add(164);
        Collections.sort(list);
        int selectValue = 342;
        System.out.println(list);
        System.out.println("查找的值：" + selectValue);
        int index = select(list,selectValue);
        System.out.println("下标为：" + index);
    }

    /**
     * 斐波那契数列递归法
     * @param num
     * @return
     */
    public static int getFibonacciValue(int num){
        if (num == 0){
            return 0;
        }else if (num == 1){
            return 1;
        }
        return getFibonacciValue(num-1) + getFibonacciValue(num-2);
    }

    /**
     * 斐波那契数列非递归法
     * @param num
     * @return
     */
    public static int getFibonacciValue2(int num){
        int v1 = 0;
        int v2 = 1;

        if (num == 0){
            return v1;
        }else if(num == 1){
            return v2;
        }

        int value = 0;
        for (int i = 2;i <= num; i++){
            value = v1 + v2;
            v1 = v2;
            v2 = value;
        }
        return value;
    }

    private static int select(List<Integer> list, int selectValue) {
        if (list == null || list.isEmpty()){
            return -1;
        }

        int listSize = list.size();
        int index = 0;
        while (getFibonacciValue(index)-1 < listSize){
            index++;
        }

        int endValue = list.get(listSize-1);
        //将list扩容为 F(n)-1 长度  即存在某一个斐波那契数减一大于集合的长度，最小的那个斐波那契数。
        for (int i = listSize;i < getFibonacciValue(index) - 1;i++){
            list.add(endValue);
        }

        int low = 0;
        int height = list.size() - 1;
        while (height >= low){
            //中间值左边为f(n-2)-1个值，右边为f(n-1)-1个值，一共f(n)-1个值
            int middleIndex = low + getFibonacciValue(index-1) - 1;
            if (list.get(middleIndex) > selectValue){
                height = middleIndex-1;
                index = index - 2;
            }else if (list.get(middleIndex) < selectValue){
                low = middleIndex+1;
                index = index - 1;
            }else {
                if (middleIndex >= listSize){
                    return listSize - 1;
                } else {
                    return middleIndex;
                }
            }
        }

        return -1;
    }

}
