package com.sr.chazhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 顺序查找
 */
public class OrderSelect {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(456);list.add(156);list.add(134);list.add(852);list.add(165);
        list.add(235);list.add(146);list.add(568);list.add(342);list.add(342);
        list.add(564);list.add(765);list.add(864);list.add(445);list.add(667);
        list.add(223);list.add(445);list.add(653);list.add(434);list.add(656);
        int selectValue = 342;
        System.out.println(list);
        System.out.println("查找的值：" + selectValue);
        int index = select(list,selectValue);
        System.out.println("下标为：" + index);
    }

    /**
     * 顺序查找
     * @param list
     * @param selectValue
     * @return
     */
    private static int select(List<Integer> list, int selectValue) {
        if (list == null || list.isEmpty()){
            return -1;
        }

        for(int i = 0;i < list.size(); i++) {
            if (selectValue == list.get(i)) {
                return i;
            }
        }

        return -1;
    }
}
