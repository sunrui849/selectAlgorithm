package com.sr.chazhao;

/**
 * 红黑树
 */
public class RedBlackTree {
    private RedBlackTree parentNode;
    private RedBlackTree leftNode;
    private RedBlackTree rightNode;
    private Color color;
    private Integer value;

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insertNode(10);
        tree.insertNode(5);
        tree.insertNode(4);
        tree.insertNode(6);
        tree.insertNode(3);
        System.out.println(tree);
        tree.delete(4);
        System.out.println(tree);
    }
    /**
     * 右旋
     * @param tree_x
     */
    private void rightRotate(RedBlackTree tree_x){
        if (tree_x == null || tree_x.leftNode == null){
            return;
        }
        RedBlackTree tree_y = tree_x.getLeftNode();

        //增加x和y的右节点的关联
        tree_x.leftNode = tree_y.rightNode;
        if (tree_y.rightNode != null){
            tree_y.rightNode.parentNode = tree_x;
        }

        //修改x\y 和 父节点的关系
        tree_y.parentNode = tree_x.parentNode;
        if (tree_x.parentNode == null){
            int value = tree_y.value;
            this.value = tree_y.value;
            tree_x.value = value;
        }else if(tree_x.parentNode.leftNode == tree_x){
            tree_x.parentNode.leftNode = tree_y;
        }else {
            tree_x.parentNode.rightNode = tree_y;
        }

        //修改x和y的关系
        tree_y.rightNode = tree_x;
        tree_x.parentNode = tree_y;
    }

    /**
     *左旋
     * @param tree_x
     */
    public void leftRotate(RedBlackTree tree_x){
        if (tree_x == null || tree_x.rightNode == null){
            return;
        }

        RedBlackTree tree_y = tree_x.rightNode;

        tree_x.rightNode = tree_y.leftNode;
        if (tree_y.leftNode != null){
            tree_y.leftNode.parentNode = tree_x;
        }

        tree_y.parentNode = tree_x.parentNode;
        if (tree_x.parentNode == null){
            int value = tree_y.value;
            this.value = tree_y.value;
            tree_x.value = value;
        } else if (tree_x.parentNode.leftNode == tree_x){
            tree_x.parentNode.leftNode = tree_y;
        } else {
            tree_x.parentNode.rightNode = tree_y;
        }

        tree_y.leftNode = tree_x;
        tree_x.parentNode = tree_y;
    }

    public boolean insertNode(int value){
        if (this.value == null){
            this.value = value;
            return true;
        }
        RedBlackTree tree = this;
        RedBlackTree insertNode;
        while (true){
            if (tree.value > value){
                if (tree.leftNode == null){
                    insertNode = new RedBlackTree(tree,null,null,Color.RED,value);
                    tree.leftNode = insertNode;
                    break;
                }
                tree = tree.leftNode;
            }else if (tree.value < value){
                if (tree.rightNode == null){
                    insertNode = new RedBlackTree(tree,null,null,Color.RED,value);
                    tree.rightNode = insertNode;
                    break;
                }
                tree = tree.rightNode;
            }else {
                insertNode = new RedBlackTree(tree,null,tree.rightNode,Color.RED,value);
                tree.rightNode = insertNode;
                break;
            }
        }
        insertFixUp(insertNode);

        return true;
    }

    /**
     * 新增节点修复
     * @param insertNode
     */
    private void insertFixUp(RedBlackTree insertNode) {

        while (insertNode.parentNode != null && insertNode.parentNode.color==Color.RED){
            if (insertNode.parentNode == insertNode.parentNode.parentNode.leftNode){
                RedBlackTree uncleNode = insertNode.parentNode.parentNode.rightNode;
                if (uncleNode == null){
                    insertNode.parentNode.color = Color.BLACK;
                    insertNode.parentNode.parentNode.color = Color.RED;
                    insertNode = insertNode.parentNode.parentNode;
                    continue;
                }
                if (uncleNode.color == Color.RED) {//情况1，叔叔节点是红色
                    insertNode.parentNode.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parentNode.parentNode.color = Color.RED;
                    insertNode = insertNode.parentNode.parentNode;
                    continue;
                }else if (insertNode == insertNode.parentNode.rightNode){//情况2，是右节点   左旋父节点后，insertNode指向的还是子节点
                    insertNode = insertNode.parentNode;
                    leftRotate(insertNode);
                }
                //情况3，是左节点   右旋爷爷节点后，变为  红/黑\红，结束循环
                insertNode.parentNode.color = Color.BLACK;
                insertNode.parentNode.parentNode.color = Color.RED;
                rightRotate(insertNode.parentNode.parentNode);
            }else {
                RedBlackTree uncleNode = insertNode.parentNode.parentNode.leftNode;
                if (uncleNode == null){
                    insertNode.parentNode.color = Color.BLACK;
                    insertNode.parentNode.parentNode.color = Color.RED;
                    insertNode = insertNode.parentNode.parentNode;
                    continue;
                }
                if (uncleNode.color == Color.RED){
                    insertNode.parentNode.color = Color.BLACK;
                    uncleNode.color = Color.BLACK;
                    insertNode.parentNode.parentNode.color = Color.RED;
                    insertNode = insertNode.parentNode.parentNode;
                    continue;
                }else if (insertNode == insertNode.parentNode.leftNode){
                    insertNode = insertNode.parentNode;
                    rightRotate(insertNode);
                }
                insertNode.parentNode.color = Color.BLACK;
                insertNode.parentNode.parentNode.color = Color.RED;
                leftRotate(insertNode.parentNode.parentNode);
            }
        }
        color = Color.BLACK;
    }

    public void delete(int value){
        RedBlackTree deleteNode = get(value);
        if (deleteNode != null){
            delete(deleteNode);
        }
    }

    private  void transplant(RedBlackTree u,RedBlackTree v){
        if (u.parentNode == null){
            this.value = v == null ? null : v.value;
            this.color = v == null ? null : v.color;
            this.leftNode = v == null ? null : v.leftNode;
            this.rightNode = v == null ? null : v.rightNode;
        }else if (u == u.parentNode.leftNode){
            u.parentNode.leftNode = v;
        }else {
            u.parentNode.rightNode = v;
        }
        if (v != null){
            v.parentNode = u.parentNode;
        }
    }

    private void delete(RedBlackTree deleteNode) {
        RedBlackTree rightMinNode = deleteNode;//y记录的应该是删除节点右树上最小的节点(算法导论中的y)
        RedBlackTree x;//记录着可能会违反红黑树性质的节点
        Color yOriginalColor = deleteNode.color;//标记删除的节点的颜色
        if (deleteNode.leftNode == null){
            x = deleteNode.rightNode;
            transplant(deleteNode,deleteNode.rightNode);
        }else if (deleteNode.rightNode == null){
            x = deleteNode.leftNode;
            transplant(deleteNode,deleteNode.leftNode);
        }else {
            //下面这段代码是拿到删除节点右树上最小节点赋值给rightMinNode
            RedBlackTree deleteRightNode = deleteNode.rightNode;
            while (deleteRightNode.leftNode != null){
                rightMinNode = deleteRightNode.leftNode;
                deleteRightNode = deleteNode.leftNode;
            }

            yOriginalColor = rightMinNode.color;//后续会将y的颜色变成删除节点的颜色，也就是缺少了一个y的颜色的节点
            x = rightMinNode.rightNode;//后面将右树最小节点的右节点移动到最小节点位置，可能会违反红黑树性质
            if (rightMinNode.parentNode != deleteNode){
                transplant(rightMinNode,rightMinNode.rightNode);//将最小节点的右节点替换到最小节点处，此时最小节点游离出来
                rightMinNode.rightNode = deleteNode.rightNode;//将删除节点的整个右树（此时已经不包含最小节点）移动到最小节点的右树
                rightMinNode.rightNode.parentNode = rightMinNode;//修改右树的父节点
            }
            transplant(deleteNode,rightMinNode);//将删除节点删除掉，其右树最小节点移动到删除节点位置
            rightMinNode.leftNode = deleteNode.leftNode;//并将删除节点的左树赋值成右树最小节点的左树
            rightMinNode.leftNode.parentNode = rightMinNode;//修改左树的父节点
            rightMinNode.color = deleteNode.color;//将最小节点的颜色变成删除节点的颜色，这时节点颜色只对右树有影响，其他节点原来的五条性质不变
        }

        if (yOriginalColor == Color.BLACK){//删除节点的颜色是黑色，违反了性质
            deleteFixup(x);
        }
    }

    private void deleteFixup(RedBlackTree fixNode) {
        boolean isContinue = true;
        while (isContinue && fixNode.parentNode != null && fixNode.color == Color.BLACK){
            if (fixNode == fixNode.parentNode.leftNode){
                RedBlackTree brotherNode = fixNode.parentNode.rightNode;
                if (brotherNode == null){
                    fixNode = fixNode.parentNode;
                    continue;
                }
                if (brotherNode.color == Color.RED){//情况一，兄弟节点是红色则父节点一定是黑色，转为另三种情况中的一种，即兄弟节点是黑色
                    fixNode.parentNode.color = Color.RED;
                    brotherNode.color = Color.BLACK;
                    leftRotate(fixNode.parentNode);
                } else if ((brotherNode.leftNode == null ||brotherNode.leftNode.color == Color.BLACK)
                            && (brotherNode.rightNode == null || brotherNode.rightNode.color == Color.BLACK)){
                    //情况二，兄弟节点的子节点都是黑色，那么将兄弟节点改成红色（路径-1），相当于左右都减一了，那么只需在上面的节点做处理即可
                    fixNode = fixNode.parentNode;
                    brotherNode.color = Color.RED;
                } else if (brotherNode.leftNode != null && brotherNode.leftNode.color == Color.RED){
                    //情况三，兄弟节点的左节点是红色，右节点是黑色，转为情况四
                    brotherNode.leftNode.color = Color.BLACK;
                    brotherNode.color = Color.RED;
                    rightRotate(brotherNode);
                }else{
                    //情况四，兄弟节点的右节点是红色,左旋父节点，修改兄弟节点为父节点的颜色，
                    //父节点为黑色，兄弟节点的右节点为黑色，左旋后，父节点的左树+1，结束
                    brotherNode.color = fixNode.parentNode.color;
                    fixNode.parentNode.color = Color.BLACK;
                    if (brotherNode.rightNode != null){
                        brotherNode.rightNode.color = Color.BLACK;
                    }
                    leftRotate(fixNode.parentNode);
                    isContinue = false;
                }
            }else {
                RedBlackTree brotherNode = fixNode.parentNode.leftNode;
                if (brotherNode == null){
                    fixNode = fixNode.parentNode;
                    continue;
                }
                if (brotherNode.color == Color.RED){
                    fixNode.parentNode.color = Color.RED;
                    brotherNode.color = Color.BLACK;
                    rightRotate(fixNode.parentNode);
                } else if ((brotherNode.leftNode == null ||brotherNode.leftNode.color == Color.BLACK)
                        && (brotherNode.rightNode == null || brotherNode.rightNode.color == Color.BLACK)){
                    fixNode = fixNode.parentNode;
                    brotherNode.color = Color.RED;
                } else if (brotherNode.rightNode != null && brotherNode.rightNode.color == Color.RED){
                    brotherNode.rightNode.color = Color.BLACK;
                    brotherNode.color = Color.RED;
                    leftRotate(brotherNode);
                }else{
                    brotherNode.color = fixNode.parentNode.color;
                    fixNode.parentNode.color = Color.BLACK;
                    if (brotherNode.leftNode != null){
                        brotherNode.leftNode.color = Color.BLACK;
                    }
                    rightRotate(fixNode.parentNode);
                    isContinue = false;
                }
            }
        }
        fixNode.color = Color.BLACK;
    }

    /**
     * 查找第一个出现该值的节点
     * @param value
     * @return
     */
    public RedBlackTree get(int value){
        RedBlackTree tree = this;

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

    public RedBlackTree() {
    }

    public RedBlackTree(RedBlackTree parentNode, RedBlackTree leftNode, RedBlackTree rightNode, Color color, Integer value) {
        this.parentNode = parentNode;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.color = color;
        this.value = value;
    }

    public RedBlackTree getParentNode() {
        return parentNode;
    }

    public void setParentNode(RedBlackTree parentNode) {
        this.parentNode = parentNode;
    }

    public RedBlackTree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(RedBlackTree leftNode) {
        this.leftNode = leftNode;
    }

    public RedBlackTree getRightNode() {
        return rightNode;
    }

    public void setRightNode(RedBlackTree rightNode) {
        this.rightNode = rightNode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
enum Color{
    RED,BLACK;
}
