package com.sr.chazhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 分块查找
 */
public class BlockSelect {
    private static class Block{
        private int maxValue;
        private List<Integer> list = new ArrayList<Integer>();

        public int getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "{块最大值：" + maxValue + "；块内集合：" + list.toString() + "}";
        }
    }

    public static void main(String[] args) {
        //构建符合要求的块，按块有序，块内无序
        List<Block> list = new ArrayList<Block>();
        for (int i = 0;i < 10;i++){
            Block block = new Block();
            block.setMaxValue((i+1)*5-1);
            List<Integer> list1 = new ArrayList<Integer>();
            list1.add(i*5);list1.add(i*5+2);
            list1.add(i*5+1);list1.add(i*5+4);
            list1.add(i*5+3);
            block.setList(list1);
            list.add(block);
        }
        System.out.println(list);
        selectFromBlock(list,12);
    }

    /**
     * 块集合从小到大
     * 在查找块的时候和块内查找的时候均使用的为顺序查找，可更改查找算法
     * @param list
     * @param selectValue
     */
    public static void selectFromBlock(List<Block> list,int selectValue){
        int blockIndex = -1;
        for (int i = 0;i < list.size(); i++){
            if (selectValue < list.get(i).getMaxValue()){
                blockIndex = i;
                System.out.println("查找的值可能在块 " + i + " 中");
                break;
            }
        }

        if (blockIndex < 0){
            System.out.println("块集合中不存在要查找的值！");
            return;
        }

        List<Integer> blockList = list.get(blockIndex).getList();
        for (int i = 0;i < blockList.size(); i++){
            if (blockList.get(i) == selectValue){
                System.out.println("结果：查找的值在块 " + blockIndex + " 中下标为：" + i);
                return;
            }
        }
        System.out.println("结果：查找的值不存在！");
    }


}
