package com.sr.chazhao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 二分查找
 */
public class TwoPointsSelect {

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
//        int index = select(list,selectValue);
        int index = select2(list,selectValue);
        System.out.println("下标为：" + index);
    }


    /**
     * 非递归写法
     * @param list
     * @param selectValue
     * @return
     */
    private static int select2(List<Integer> list, int selectValue) {
        if (list == null || list.isEmpty()){
            return -1;
        }
        int start = 0;
        int end = list.size() - 1;
        while (end > start) {
            int middleIndex = (end + start)/2;
            if (list.get(middleIndex) > selectValue) {
                end = middleIndex;
            } else if (list.get(middleIndex) < selectValue) {
                start = middleIndex + 1;
            } else {
                return middleIndex;
            }
        }

        return -1;
    }



    /**
     * 递归写法
     * @param list
     * @param selectValue
     * @return
     */
    private static int select(List<Integer> list, int selectValue) {
        if (list == null || list.isEmpty()){
            return -1;
        }
        return loopSelect(list, 0, list.size() - 1 , selectValue);
    }

    private static int loopSelect(List<Integer> list, int startIndex, int endIndex, int selectValue){
        if (startIndex == endIndex) {
            return list.get(startIndex) == selectValue ? startIndex : -1;
        }

        int middleIndex = (endIndex + startIndex)/2;

        if (list.get(middleIndex) > selectValue){
            return loopSelect(list, startIndex, middleIndex, selectValue);
        }else if (list.get(middleIndex) < selectValue) {
            return loopSelect(list, middleIndex + 1, endIndex, selectValue);
        }

        return middleIndex;
    }
}
