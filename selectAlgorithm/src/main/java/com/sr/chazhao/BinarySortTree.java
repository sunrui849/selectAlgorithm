package com.sr.chazhao;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉查找树又称二叉排序树即二叉搜索树
 */
public class BinarySortTree {
    private BinarySortTree parentNode;
    private BinarySortTree leftNode;
    private BinarySortTree rightNode;
    private Integer value;
    
    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree();
        tree.put(5);tree.put(1);tree.put(6);tree.put(3);
        System.out.println(tree.get(6));
        tree.remove(5);
        System.out.println(tree);
        System.out.println(tree.beforeIteration());
        System.out.println(tree.middleIteration());
        System.out.println(tree.endIteration());

    }

    /**
     * 删除第一个节点的值是该值的节点
     * @param value
     * @return
     */
    public boolean remove(int value){

        if (this.value == null){
            return false;
        }
        BinarySortTree tree = this;
        while (tree != null) {
            if (tree.value > value){
                tree = tree.leftNode;
            }else if (tree.value < value){
                tree = tree.rightNode;
            }else {
                remove(tree);
                return true;
            }
        }
        return false;
    }

    /**
     * 这个方法是自研的，效果不好，
     * 每次删除都会使高度变高，可参考红黑树的实现
     * @param tree
     */
    private void remove(BinarySortTree tree){

        //删除
        if (tree.leftNode == null && tree.rightNode == null){
            //左右节点都为空，直接把父节点指向置空即可
            if (tree.parentNode == null){
                //根节点
                tree.value = null;
            }else if (tree.parentNode.leftNode == tree){
                tree.parentNode.leftNode = null;
            }else {
                tree.parentNode.rightNode = null;
            }
        }else if (tree.leftNode == null){
            //右节点不为空，左节点为空
            if (tree.parentNode == null){
                //根节点
                tree.value = tree.rightNode.value;
                tree.leftNode = tree.rightNode.leftNode;
                tree.rightNode = tree.rightNode.rightNode;
            }else if (tree.parentNode.leftNode == tree){
                tree.parentNode.leftNode = tree.rightNode;
            }else {
                tree.parentNode.rightNode = tree.rightNode;
            }
        }else if (tree.rightNode == null){
            //左节点不为空，右节点不为空
            if (tree.parentNode == null){
                //根节点
                tree.value = tree.leftNode.value;
                tree.rightNode = tree.leftNode.rightNode;
                tree.leftNode = tree.leftNode.leftNode;
            }else if (tree.parentNode.leftNode == tree){
                tree.parentNode.leftNode = tree.leftNode;
            }else {
                tree.parentNode.rightNode = tree.leftNode;
            }
        }else{
            //左右节点都不为空，将左节点上提，右节点放到左节点最右的节点后
            if (tree.parentNode == null){
                BinarySortTree leftNodeTreeMaxNode = tree.leftNode;
                while (leftNodeTreeMaxNode.rightNode != null){
                    leftNodeTreeMaxNode = leftNodeTreeMaxNode.rightNode;
                }
                leftNodeTreeMaxNode.rightNode = tree.rightNode;
                tree.value = tree.leftNode.value;
                tree.rightNode = tree.leftNode.rightNode;
                tree.leftNode = tree.leftNode.leftNode;
            }else if (tree.parentNode.leftNode == tree){
                tree.parentNode.leftNode = tree.leftNode;
                BinarySortTree leftNodeTreeMaxNode = tree.leftNode;
                while (leftNodeTreeMaxNode.rightNode != null){
                    leftNodeTreeMaxNode = leftNodeTreeMaxNode.rightNode;
                }
                leftNodeTreeMaxNode.rightNode = tree.rightNode;
            }else {
                tree.parentNode.rightNode = tree.leftNode;
                BinarySortTree leftNodeTreeMaxNode = tree.leftNode;
                while (leftNodeTreeMaxNode.rightNode != null){
                    leftNodeTreeMaxNode = leftNodeTreeMaxNode.rightNode;
                }
                leftNodeTreeMaxNode.rightNode = tree.rightNode;
            }
        }
    }

    /**
     * 查找第一个出现该值的节点
     * @param value
     * @return
     */
    public BinarySortTree get(int value){
        BinarySortTree tree = this;

        if (this.value == null){
            return null;
        }

        while (tree != null){
            if (tree.value == value){
                return tree;
            } else if (tree.value > value){
                tree = tree.leftNode;
            } else{
                tree = tree.rightNode;
            }
        }
        return null;
    }

    /**
     *向二叉搜索树中增加一个元素
     * @param value
     * @return
     */
    public boolean put(int value){
        if (this.value == null){
            this.value = value;
            return true;
        }
        BinarySortTree tree = this;
        while (true){
            if (value > tree.value) {
                if (tree.rightNode == null){
                    tree.rightNode = new BinarySortTree(tree,null,null,value);
                    break;
                }
                tree = tree.rightNode;
            }else if(value < tree.value){
                if (tree.leftNode == null){
                    tree.leftNode = new BinarySortTree(tree,null,null,value);
                    break;
                }
                tree = tree.leftNode;
            }else {
                tree.rightNode = new BinarySortTree(tree,null,tree.rightNode,value);
                break;
            }
        }
        return true;
    }

    /********三种遍历************
     *          A
     *         / \
     *       B    E
     *        \    \
     *         C    F
     *        /    /
     *       D    G
     *          /\
     *         H  K
     *
     * 前序遍历(根左右): ABCDEFGHK
     * 中序遍历(左根右): BDCAEHGKF
     * 后序遍历(左右根): DCBHKGFEA
     *
     * */

    /**
     * 后序遍历，最后一个一定是根节点
     * @return
     */
    public List<Integer> endIteration(){
        List<Integer> list = new ArrayList<Integer>();
        endRecursion(this,list);
        return list;
    }

    private void endRecursion(BinarySortTree tree,List<Integer> list){
        if (tree == null || tree.value == null){
            return;
        }
        endRecursion(tree.leftNode, list);
        endRecursion(tree.rightNode, list);
        list.add(tree.value);
    }

    /**
     * 中序遍历,第一个是最小值，最后一个是最大值
     * @return
     */
    public List<Integer> middleIteration(){
        List<Integer> list = new ArrayList<Integer>();
        middleRecursion(this,list);
        return list;
    }

    private void middleRecursion(BinarySortTree tree,List<Integer> list){
        if (tree == null || tree.value == null){
            return;
        }
        middleRecursion(tree.leftNode, list);
        list.add(tree.value);
        middleRecursion(tree.rightNode, list);
    }


    /**
     * 前序遍历，第一个一定是根节点
     * @return
     */
    public List<Integer> beforeIteration(){
        List<Integer> list = new ArrayList<Integer>();
        beforeRecursion(this,list);
        return list;
    }

    private void beforeRecursion(BinarySortTree tree,List<Integer> list){
        if (tree == null || tree.value == null){
            return;
        }
        list.add(tree.value);
        beforeRecursion(tree.leftNode, list);
        beforeRecursion(tree.rightNode, list);
    }


    public BinarySortTree() {

    }

    public BinarySortTree(BinarySortTree parentNode, BinarySortTree leftNode, BinarySortTree rightNode, int value) {
        this.parentNode = parentNode;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.value = value;
    }

    public BinarySortTree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BinarySortTree leftNode) {
        this.leftNode = leftNode;
    }

    public BinarySortTree getRightNode() {
        return rightNode;
    }

    public void setRightNode(BinarySortTree rightNode) {
        this.rightNode = rightNode;
    }

    public BinarySortTree getParentNode() {
        return parentNode;
    }

    public void setParentNode(BinarySortTree parentNode) {
        this.parentNode = parentNode;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
