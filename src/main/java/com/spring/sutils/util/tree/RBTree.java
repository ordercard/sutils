package com.spring.sutils.util.tree;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午11:38 2018/6/28 2018
 * @Modify:
 *
 *  红黑树的 时间复杂度的证明
 *  估计有很多人对书上13.1 引理不是很在意，但是我想说，这正是理解红黑树精髓的地方之一。 这条引理也是红黑树为什么效率这么高的原因。不晓得是语文差还是啥的，我看书上的介绍也没看懂，写得太简洁了，菜鸟看不懂表示很蛋疼。后来在网上看了别人的资料，终于弄懂了。(⊙o⊙)…

下面我就仔仔细细的介绍下这条引理到底是怎么得到的。
引理：一棵有n个内结点的红黑树的高度至多为2lg(n+1)。
这个引理怎么证明呢，这里需要一个工具  对以x为根的子树，它所包含的内部结点数至少为2^[bh(x)]-1。这里bh(x)（bh嘛，black height）被定义为结点x的黑高度，就是说，从结点x（不包括它本身）到它任一个叶结点的路径上所有黑色结点的个数。
下面用归纳法证明：
1)若x高度为0，那么它就是一叶子结点，它确实至少包含2^0-1=0个内部结点

2)假设x为红黑树的某一内部结点，且它高度h>0，那么它的黑高度就是bh(x)，但是它的两个孩子结点呢？这个就根据它们的颜色来判断了：
如果x有一个红色的孩子y，那么y的黑高度bh(y)=bh(x)，看看上面对黑高度的定义你就明白了——既然它是红色的，那么它的黑高度就应该和它父亲的黑高度是一样的；
如果x有一个黑色的孩子z，那么z的黑高度bh(z)=bh(x)-1，这个怎么解释呢，因为它自己就是个黑结点，那么在计算它的黑高度时，必须把它自己排除在外（还是根据定义），所以它是bh(x)-1。

3)x的孩子结点所构成的子树的高度肯定小于x这颗子树，那么对于这两个孩子，不管它们颜色如何，一定满足归纳假设的是至少hb 高度为bh(x)-1。所以，对x来说，它所包含的内部结点个数“至少”为两个孩子结点所包含的内部结点数，再加上它自己，于是就为2^[bh(x)-1]-1+2^[bh(x)-1]-1+1=2^[bh(x)]-1，归纳证明完毕。
也就是说n>=2^[bh(x)]-1---------①

把一 中红黑树性质中 4）、5）两个特性结合起来，其实我们可以得到黑节点至少是红节点的2倍。用一句话来说就是“有红必有黑，但有黑未必一定有红”。为什么这么说呢，因为从特性4）我们知道，如果有一个红结点存在，那么它的儿子结点一定是黑的，最极端的情况下，该路径上所有的结点就被红、黑两种结点给平分了那就是黑节点至少是红节点的2倍。不知这个问题我解释清楚没有，因为这是往下理解的关键。

如果一棵红黑树的高为h，那么在这个高度上（不包括根结点本身）至少有1/2h的黑结点，再结合上面对“黑高度”的定义，我们说，红黑树根结点的黑高度至少是1/2h，好了，我们拿出公式①，设n为该红黑树所包含的内部结点数，我们得出如下结论： n>=2^(1/2h)-1。 我们把它整理整理，就得到了h<=2lg(n+1)，就是我们要证明的结论：红黑树的高度最多也就是2lg(n+1)。(⊙o⊙)
 *
 *
 *
 *
 *
 *
 */
public class RBTree<T extends Comparable<T>>{

    private RBNode<T> root; //根节点
    private static final boolean RED = false; //定义红黑树标志
    private static final boolean BLACK = true;

    public class RBNode<T extends Comparable<T>>{

        boolean color; //颜色
        T key; //关键字(键值)
        RBNode<T> left; //左子节点
        RBNode<T> right; //右子节点
        RBNode<T> parent; //父节点

        public RBNode(){ }
        public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    public RBTree() {
        root = null;
    }

    public RBNode<T> parentOf(RBNode<T> node) { //获得父节点
        return node != null? node.parent : null;
    }

    public void setParent(RBNode<T> node, RBNode<T> parent) { //设置父节点
        if(node != null)
            node.parent = parent;
    }


    public boolean colorOf(RBNode<T> node) { //获得节点的颜色
        return node != null? node.color : BLACK;
    }

    public boolean isRed(RBNode<T> node) { //判断节点的颜色
        return (node != null)&&(node.color == RED)? true : false;
    }

    public boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }

    public void setRed(RBNode<T> node) { //设置节点的颜色
        if(node != null)
            node.color = RED;
    }

    public void setBlack(RBNode<T> node) {
        if(node != null) {
            node.color = BLACK;
        }
    }

    public void setColor(RBNode<T> node, boolean color) {
        if(node != null)
            node.color = color;
    }
    /***************** 前序遍历红黑树 *********************/
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(RBNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    /***************** 中序遍历红黑树 *********************/
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(RBNode<T> tree) {
        if(tree != null) {
            preOrder(tree.left);
            System.out.print(tree.key + " ");
            preOrder(tree.right);
        }
    }

    /***************** 后序遍历红黑树 *********************/
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(RBNode<T> tree) {
        if(tree != null) {
            preOrder(tree.left);
            preOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }

    /*************对红黑树节点x进行左旋操作 ******************/

     /*
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */

    private void leftRotate(RBNode<T> x) {

        RBNode<T> y = x.right;
        x.right = y.left;  //变化

        if(y.left!=null){
          x.right.parent=x;
        }
        y.parent=x.parent; //设置父节点

        if(x.parent==null){
            this.root=y;//设置为根节点
        }else{
            if(x==x.parent.left){//取代原来的位置
                y.parent.left=y;
            }else {
                y.parent.right=y;
            }

        }
        y.left = x;//改变引用
        x.parent = y;




    }
/**
 * 右旋转和左旋相似
 */
 /*
     * 左旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      x                   y
     *     / \                 / \
     *    y rx   ----->      ly   x
     *   / \                     / \
     * ly  ry                   ry rx
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
private void rightRotate(RBNode<T> x) {
    RBNode<T> y = x.left;
    x.left = y.right;  //变化

    if(y.right!=null){
        //x.left.parent=x;或者是这个
        y.right.parent=x;
    }
    y.parent=x.parent;
    if(x.parent==null){

        this.root=y;
    }else {
        if(x==x.parent.left){
            x.parent.left=y;
        }
        else {
            x.parent.right=y;
        }

    }

    y.right=x;

    x.parent=y;

}
    /*
         * 左旋示意图：对节点y进行右旋
         *        p                   p
         *       /                   /
         *      y                   x
         *     / \                 / \
         *    x  ry   ----->      lx  y
         *   / \                     / \
         * lx  rx                   rx ry
         * 右旋做了三件事：
         * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
         * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
         * 3. 将x的右子节点设为y，将y的父节点设为x
         */
//private void rightRotate(RBNode<T> y) {
//    //1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
//    RBNode<T> x = y.left;
//    y.left = x.right;
//
//    if(x.right != null)
//        x.right.parent = y;
//
//    //2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
//    x.parent = y.parent;
//
//    if(y.parent == null) {
//        this.root = x; //如果x的父节点为空，则将y设为父节点
//    } else {
//        if(y == y.parent.right) //如果x是左子节点
//            y.parent.right = x; //则也将y设为左子节点
//        else
//            y.parent.left = x;//否则将y设为右子节点
//    }
//
//    //3. 将y的左子节点设为x，将x的父节点设为y
//    x.right = y;
//    y.parent = x;
//}
//
    public void insert(T key) {
        RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
        if(node != null)
            insert(node);
    }

    private void insert(RBNode<T> node) {

        RBNode<T> current = null; //表示最后node的父节点
        RBNode<T> ser = this.root; //用来向下搜索用的
        while(ser != null) {  //二叉树的搜索使用
            current = ser;
            int cmp = node.key.compareTo(ser.key);
            if(cmp < 0)
                ser = ser.left;
            else
                ser =ser.right;
        }
        node.parent = current; //找到了位置，将当前current作为node的父节点
        if(current != null) { //确定插入的是左子树还是右
            int cmp = node.key.compareTo(current.key);
            if(cmp < 0)
                current.left = node;
            else
                current.right = node;
        } else {
            this.root = node;
        }
        //修复红黑树
        insertFixUp(node);

    }


    /*

    之所以要左旋和将父节点作为当前节点，其实根本原因不是红黑树的核心思想，这里体现的不是核心思想。对整个算法来说遵从了核心思想，
    而这里左旋只是为了到达Case 3的效果，为Case 3的右旋做准备，为什么这么说呢？
我认为在Case 1中，如果叔叔是红节点，我们可以只通过color changes操作，避免性质4和5的冲突，因为此时我们可以调节叔叔的颜色为黑色，
来保证叔叔path上黑色节点数增加，从而平衡。

而在Case 2中，只通过color changes操作没法保证避免性质4、5. 这时候我们只能迫于无赖采用旋转操作。
因为此时父亲节点是祖父节点的左孩子，所以我们猜测可能要对祖父节点旋转选择右旋转。（如果假设我们跳过Case2的操作，直接进行Case 3的操作），
也就是此时当前节点依然是父亲节点的右孩子，我们首先采用case3 的第一步操作，变父亲节点为黑色，消除了性质4，但是此时父亲所在路径多了一个黑节点，
我们想把父亲往上一层，然后把祖父变为红色右侧下一层，也就是所谓的对祖父进行右旋转。 注意到，祖父此时变为红色，而且父节点的右孩子也就是当前节点（红色）要变成祖父节点的左孩子。
那么变化后，祖父节点和当前节点在相邻的两层了，而且同时为红色。又带来了冲突。 最简单的方法就是在右旋转的前，把当前节点红色转移到左孩子去。这就是Case 2 为什么要这么做的原因了。
跳过case 2的操作，直接进行case 3，画画图，就知道为什么了～

     */

    private void insertFixUp(RBNode<T> node) {//红黑树的修复思想，是把红色的节点向根部移动，之后涂黑根节点，使得所有的路径下的黑节点的数量都加1

        RBNode<T> parent, gparent; //定义父节点和祖父节点

        //需要修整的条件：父节点存在，且父节点的颜色是红色，循环修订
        while (((parent = parentOf(node)) != null) && isRed(parent)){
              //父节点是爷爷节点的左节点
            gparent = parentOf(parent);//获得祖父节点不会存在祖父节点为空的情况，如果为null不会进入这个while 循环
            if(parent == gparent.left) {
                RBNode<T> uncle = gparent.right; //获得叔叔节点

                //case 1
                if(uncle!=null&&isRed(uncle)){
                    setBlack(parent); //把父节点和叔叔节点涂黑
                    setBlack(uncle);
                    setRed(gparent); //把祖父节点涂红
                    node = gparent; //将位置放到祖父节点处
                    continue; //继续while，重新判断

                }
                //case2: 叔叔节点是黑色，且当前节点是右子节点
                if(node == parent.right) {
                    leftRotate(parent); //从父节点处左旋
                    RBNode<T> tmp = parent; //然后将父节点和自己调换一下，为下面右旋做准备  //主要防止爷爷和自己颜色冲突（在直接右旋的时候，所以要避免）
                    parent = node;
                    node = tmp;//父节点和自身交换位置，为case3做准备
                }
                //case3: 叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);//爷爷节点进行右旋转，把红色节点送上去

            }else{
                RBNode<T> uncle = gparent.left;
                //case 1
                if(uncle!=null&&isRed(uncle)){
                    setBlack(parent); //把父节点和叔叔节点涂黑
                    setBlack(uncle);
                    setRed(gparent); //把祖父节点涂红
                    node = gparent; //将位置放到祖父节点处
                    continue; //继续while，重新判断

                }
                if(node==parent.left){
                    rightRotate(parent);
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;  //父子节点进行交换，为了case3做准备

                }


                //case3: 叔叔节点是黑色的，且当前节点是右子节点, 可能会出现直接就是这一步
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);//都是为了要把红节点送上去，


            }



        }

        setBlack(this.root);
    }

    private RBNode<T> search(RBNode<T> x, T key) {
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp < 0)
                x = x.left;
            else if(cmp > 0)
                x = x.right;
            else
                return x;
        }
        return x;
    }

    /*********************** 删除红黑树中的节点 **********************/
    public void remove(T key) {
        RBNode<T> node;
        if((node = search(root, key)) != null)
            remove(node);
    }

    private void remove(RBNode<T> node) {
        RBNode<T> child, parent;
        boolean color;
        //case1
          if (node.left==null&&node.right==null){
              color=node.color;
              if (node.parent!=null){ //非根节点
                  if (node==node.parent.left){
                      node.parent.left=null;
                  }else{
                      node.parent.right=null;
                  }
                  if(color==BLACK){
                      removeFixUp(null,node.parent);

                  }
              }else{
                  this.root=null;
              }



          }
          //case2
          if (node.left!=null&&node.right==null){
              color=node.color;
              if(node.parent!=null){   //非根节点
                  if (node==node.parent.left){
                      node.parent.left=node.left;
                  }else{
                      node.parent.right=node.left;
                  }
                  node.left.parent=node.parent;
              }else{
                  this.root= node.left;
                  node.left.parent=null;

              }

              if(color==BLACK){
                  removeFixUp(node.left,node.parent);

              }

          }
          //case3
        if (node.left==null&&node.right!=null){
              color=node.color;

              if (node.parent!=null){//非根节点
                  if (node==node.parent.left){
                      node.parent.left=node.right;
                  }else{
                      node.parent.right=node.right;
                  }
                  node.right.parent=node.parent;
              }else {

                  this.root= node.right;
                  node.right.parent=null;

              }


         if(color==BLACK){
           removeFixUp(node.right,node.parent);

         }


        }

        //4

        if(node.left!=null&&node.right!=null){
            //第一种方法//应该可以进行直接的对replace和node进行一个互换交换key值应该就可以// ，然后处理replace父子节点的关系

            //  默认是第二种先找到被删除节点的后继节点，用它来取代被删除节点的位置
      RBNode replice =node.right;
       while (replice.left!=null){

           replice=replice.left;

       }
       //处理后继节点和被删除的父节点之间的关系
            if(parentOf(node)!=null)  {
                if(node == parentOf(node).left)
                    parentOf(node).left = replice;   //父亲对子的引用的改变
                else
                    parentOf(node).right = replice;
            }
            else {

           this.root=replice;

            }
            //处理后继子节点和删除节点的关系
             child=replice.right;//不存在左节点由上步可知道   左肯定是不存在的
             parent=parentOf(replice);
             color = colorOf(replice);//保存后继节点的颜色

             if(parent==node){//后继节点是被删除节点的子节点
                 parent=replice;
             }else{
                   if(child!=null){   //右节点可能存在或者不存在
                       setParent(child, parent);//设置她是child的父节点
                   }
                 parent.left = child;
                 replice.right = node.right;
                 setParent(node.right, replice);
             }
            replice.parent = node.parent;//当前节点对父亲的引用的设置
            replice.color = node.color; //保持原来位置的颜色
            replice.left = node.left;
            node.left.parent = replice;
            if(color == BLACK) { //4. 如果移走的后继节点颜色是黑色，重新修整红黑树
                removeFixUp(child, parent);//将后继节点的child和parent传进去
            }



        }

    }

/*

 node表示待修正的节点，即后继节点的子节点（因为后继节点被挪到删除节点的位置去了）被替换的位置上的平衡保持不变
 失去平衡的可能回事 删除的节点的位置



 */

    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {

        RBNode<T> other;
        //触发的条件，传入的child为null或者黑色节点的时候，null为空的的时候为叶子节点默认叶子节点为null
        while((node == null || isBlack(node)) && (node != this.root)) {
         if(parent.left==node){
             other = parent.right; //node的兄弟节点
             //case1: node的兄弟节点other是红色的-----》转换成234这三种情况中的其中一个
             if(isRed(other)) {
                 setBlack(other);
                 setRed(parent);
                 leftRotate(parent);//左旋
                 other = parent.right;//重新定义下她的兄弟节点
             }
           //node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
             if((other.left == null || isBlack(other.left)) &&(other.right == null || isBlack(other.right))) {
                 setRed(other);
                 node = parent;
                 parent = parentOf(node);
             }
             else{
                 //case3: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点是黑色
                 if(other.right == null || isBlack(other.right)) {
                     setBlack(other.left);
                     setRed(other);
                     rightRotate(other);
                     other = parent.right;
                 }

                 //case4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点任意颜色
                 setColor(other, colorOf(parent));
                 setBlack(parent);
                 setBlack(other.right);
                 leftRotate(parent);
                 node = this.root;
                 break;


             }
         }else { //与上面的对称
             other = parent.left;

             if (isRed(other)) {
                 // Case 1: node的兄弟other是红色的
                 setBlack(other);
                 setRed(parent);
                 rightRotate(parent);
                 other = parent.left;
             }

             if ((other.left==null || isBlack(other.left)) &&
                     (other.right==null || isBlack(other.right))) {
                 // Case 2: node的兄弟other是黑色，且other的俩个子节点都是黑色的
                 setRed(other);
                 node = parent;
                 parent = parentOf(node);
             } else {

                 if (other.left==null || isBlack(other.left)) {
                     // Case 3: node的兄弟other是黑色的，并且other的左子节点是红色，右子节点为黑色。
                     setBlack(other.right);
                     setRed(other);
                     leftRotate(other);
                     other = parent.left;
                 }

                 // Case 4: node的兄弟other是黑色的；并且other的左子节点是红色的，右子节点任意颜色
                 setColor(other, colorOf(parent));
                 setBlack(parent);
                 setBlack(other.left);
                 rightRotate(parent);
                 node = this.root;
                 break;
             }
         }

        }


       if (node!=null){
            setBlack(node);
            }
    }




    public void clear() {
        destroy(root);
        root = null;
    }
    private void destroy(RBNode<T> tree) {
        if(tree == null)
            return;
        if(tree.left != null)
            destroy(tree.left);
        if(tree.right != null)
            destroy(tree.right);
        tree = null;
    }
    /******************* 打印红黑树 *********************/
    public void print() {
        if(root != null) {
            print(root, root.key, 0);
        }
    }
    /*
     * key---节点的键值
     * direction--- 0:表示该节点是根节点
     *              1:表示该节点是它的父节点的左子节点
     *              2:表示该节点是它的父节点的右子节点
     */
    private void print(RBNode<T> tree, T key, int direction) {
        if(tree != null) {
            if(0 == direction)
            {
                System.out.printf("%2d(B) is root\n", tree.key);
            }
            else
            {
                System.out.printf("%2d(%s) is %2d'的儿子 %6s child  父亲是 %6d\n", tree.key, isRed(tree)?"红":"黑", key, direction == 1?"right":"left",tree.parent.key!=null?tree.parent.key:-1);
            }
            print(tree.left, tree.key, -1);
            print(tree.right, tree.key, 1);
        }
    }






}
