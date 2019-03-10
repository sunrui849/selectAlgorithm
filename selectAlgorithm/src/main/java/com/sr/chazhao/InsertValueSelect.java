package com.sr.chazhao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 插值查找
 */
public class InsertValueSelect {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(456);list.add(156);list.add(134);list.add(852);list.add(165);
        list.add(235);list.add(146);list.add(568);list.add(341);list.add(342);
        list.add(564);list.add(765);list.add(864);list.add(445);list.add(667);
        list.add(223);list.add(445);list.add(653);list.add(434);list.add(656);
        Collections.sort(list);
        int selectValue = 342;
        System.out.println(list);
        System.out.println("查找的值：" + selectValue);
        int index = select(list,selectValue);
        System.out.println("下标为：" + index);
    }

    /**
     * 非递归写法
     * ************注意java实现时，要使用除法保留小数，否则每次middle都只加一*************
     * @param list
     * @param selectValue
     * @return
     */
    private static int select(List<Integer> list, int selectValue) {
        if (list == null || list.isEmpty()){
            return -1;
        }
        int start = 0;
        int end = list.size() - 1;

        while (end > start){
            int middle = start + (int)Math.floor(division((selectValue - list.get(start)),(list.get(end) - list.get(start))) * (end - start));
            if (list.get(middle) > selectValue){
                end = middle - 1;
            } else if ((list.get(middle) < selectValue)){
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 除法，保留两位小数
     */
    private static double division(int value1,int value2) {
        if (value2 == 0){
            return 0;
        }
        BigDecimal v1 = new BigDecimal(value1);
        BigDecimal v2 = new BigDecimal(value2);
        return v1.divide(v2,2, RoundingMode.HALF_UP).doubleValue();
    }
}
