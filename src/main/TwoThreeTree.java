package main;

/**
 * TwoThreeTree is a generic implementation of a 2-3 tree data structure.
 * It supports keys that extend Key and associated values.
 * The tree offers operations such as insertion, deletion, search, and rank determination.
 *
 * @param <K> The type of keys (extending Key).
 * @param <V> The type of associated values.
 */
public class TwoThreeTree<K extends Key, V> {
    private TreeNode<K,V> root; // The root of the tree.

    /**
     * Default constructor that initializes the tree with sentinel nodes.
     */
    public TwoThreeTree() {
        TreeNode<K,V> l = new TreeNode<>();
        TreeNode<K,V> m = new TreeNode<>();
        TreeNode<K,V> x = new TreeNode<>(l,m);
        x.updateChildren(l,m,null);
        this.root = x;
    }

    /**
     * Constructor that initializes a tree for stock prices.
     *
     * @param val A boolean flag (used to indicate special initialization).
     */
    public TwoThreeTree(boolean val) {
        TreeNode<Key, Float> l = new TreeNode<>(null, 0f);
        TreeNode<Key, Float> m = new TreeNode<>(null, 0f);
        TreeNode<Key, Float> x = new TreeNode<>(null, 0f, l, m);
        x.updateChildren(l,m,null);
        this.root = (TreeNode<K, V>) x;
    }

    /**
     * Initiates the stocks tree structure.
     */
    public void StocksTreeInitiate(){
        this.root.StocksTreeInitiate();
    }

    /**
     * Initiates the stocks prices tree structure.
     */
    public void StocksPricesTreeInitiate(){
        this.root.StocksTreePricesInitiate();
    }

    /**
     * Initializes the prices tree with the given timestamp and price.
     *
     * @param timestamp The starting timestamp.
     * @param price     The starting price.
     */
    public void PricesTreeInitiate(Long timestamp, Float price) {
        TreeNode<K,V> r = new TreeNode<>();
        this.root.updateChildren(root.getLeft(), root.getMiddle(), r);
        this.root.setPredecessorAndSuccessor(this.root.getLeft(), root.getMiddle(), r);
        this.root.PricesTreeInitiate(timestamp, price);
    }

    /**
     * Searches for a node with the specified key.
     *
     * @param x The node to start searching from.
     * @param k The key to search for.
     * @return The node containing the key, or null if not found.
     */
    public TreeNode<K, V> search(TreeNode<K, V> x, K k) {
        TreeNode<K, V> k_node = new TreeNode<>(k);
        if (x.isLeaf()) {
            if (k_node.compareTo(x) == 0) {
                return x;
            } else {
                return null;
            }
        }
        if (k_node.compareTo(x.getLeft()) <= 0) {
            return search(x.getLeft(), k);
        } else if (k_node.compareTo(x.getMiddle()) <= 0) {
            return search(x.getMiddle(), k);
        } else {
            return search(x.getRight(), k);
        }
    }

    /**
     * Returns the root node of the tree.
     *
     * @return The root node.
     */
    public TreeNode<K, V> getRoot(){
        return this.root;
    }

    /**
     * Updates the key of a node based on its children.
     *
     * @param x The node to update.
     */
    private void update_key(TreeNode<K, V> x){
        x.setKey(x.getLeft().getKey());
        if (x.getMiddle() != null) {
            x.setKey(x.getMiddle().getKey());
        }
        if (x.getRight() != null) {
            x.setKey(x.getRight().getKey());
        }
        x.updateChildren(x.getLeft(), x.getMiddle(), x.getRight());
    }

    /**
     * Sets the children of a node and updates their parent pointers.
     *
     * @param x The parent node.
     * @param l The left child.
     * @param m The middle child.
     * @param r The right child.
     */
    public void set_children(TreeNode<K,V> x, TreeNode<K, V> l, TreeNode<K, V> m, TreeNode<K, V> r){
        x.updateChildren(l,m,r);
        l.updateParent(x);
        if(m != null){
            m.updateParent(x);
        }
        if(r != null){
            r.updateParent(x);
        }
        update_key(x);
    }

    /**
     * Inserts a node into the tree and splits the node if necessary.
     *
     * @param x The node into which to insert.
     * @param z The node to insert.
     * @return A new node if a split occurs, or null otherwise.
     */
    private TreeNode<K, V> insert_and_split(TreeNode<K, V> x, TreeNode<K, V> z){
        TreeNode<K, V> l = x.getLeft();
        TreeNode<K, V> m = x.getMiddle();
        TreeNode<K, V> r = x.getRight();

        if(r == null){
            if(z.getKey().compareTo(l.getKey()) < 0){
                set_children(x, z, l, m);
            } else if(z.getKey().compareTo(m.getKey()) < 0){
                set_children(x, l, z, m);
            } else {
                set_children(x, l, m, z);
            }
            return null;
        }
        TreeNode<K, V> y = new TreeNode<>();
        if(z.getKey().compareTo(l.getKey()) < 0){
            set_children(x, z, l, null);
            set_children(y, m, r, null);
        } else if (z.getKey().compareTo(m.getKey()) < 0){
            set_children(x, l, z, null);
            set_children(y, m, r, null);
        } else if(z.getKey().compareTo(r.getKey()) < 0){
            set_children(x, l, m, null);
            set_children(y, z, r, null);
        } else {
            set_children(x, l, m, null);
            set_children(y, r, z, null);
        }

        return y;
    }

    /**
     * Inserts a new node into the 2-3 tree.
     *
     * @param z The node to be inserted.
     */
    public void TwoThreeInsert(TreeNode<K,V> z){
        TreeNode<K,V> y = this.root;
        TreeNode<K,V> insert = z;
        while(!y.isLeaf()){
            if(z.getKey().compareTo(y.getLeft().getKey()) < 0){
                y = y.getLeft();
            } else if(z.getKey().compareTo(y.getMiddle().getKey()) < 0){
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        TreeNode<K, V> x = y.getParent();
        z = insert_and_split(x, z);
        while(x != this.root){
            x = x.getParent();
            if(z != null){
                z = insert_and_split(x, z);
            } else {
                update_key(x);
            }
        }
        if(z != null){
            TreeNode<K, V> w = new TreeNode<>();
            set_children(w,x,z,null);
            this.root = w;
        }

        this.root.setPredecessorAndSuccessor(this.predecessor(insert), insert, this.successor(insert));
    }

    /**
     * Finds the successor of a given node.
     *
     * @param x The node whose successor is to be found.
     * @return The successor node, or null if none exists.
     */
    private TreeNode<K,V> successor(TreeNode<K,V> x){
        TreeNode<K,V> z = x.getParent();
        TreeNode<K, V> y;
        while ((x == z.getRight()) || ((z.getRight() == null) && (x == z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }

        if (x == z.getLeft()){
            y = z.getMiddle();
        }else{
            y = z.getRight();
        }
        if (y == null)
            return null;

        while(!y.isLeaf()){
            y = y.getLeft();
        }
        return y;
    }

    /**
     * Finds the predecessor of a given node.
     *
     * @param x The node whose predecessor is to be found.
     * @return The predecessor node, or null if none exists.
     */
    private TreeNode<K,V> predecessor(TreeNode<K,V> x){
        TreeNode<K,V> z = x.getParent();
        TreeNode<K, V> y;
        while ((x == z.getLeft()) || ((z.getLeft() == null) && (x == z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }

        if (x == z.getRight()){
            y = z.getMiddle();
        }else{
            y = z.getLeft();
        }
        if (y == null)
            return null;

        while(!y.isLeaf()){
            if (y.getRight() != null) {
                y = y.getRight();
            }else{
                y = y.getMiddle();
            }
        }
        return y;
    }

    /**
     * Balances the tree by borrowing or merging nodes during deletion.
     *
     * @param y The node to be rebalanced.
     * @return The parent node after rebalancing.
     */
    private TreeNode<K, V> borrow_or_merge(TreeNode<K, V> y){
        TreeNode<K, V> z = y.getParent();
        if (z == null){
            return null;
        }
        if(y == z.getLeft()){
            TreeNode<K, V> x = z.getMiddle();
            if(x.getRight() != null){
                set_children(y,y.getLeft(),x.getLeft(),null);
                set_children(x,x.getMiddle(),x.getRight(),null);
            } else {
                set_children(x, y.getLeft(), x.getLeft(), x.getMiddle());
                set_children(z, x, z.getRight(), null);
            }
            return z;
        }
        if (y == z.getMiddle()){
            TreeNode<K, V> x = z.getLeft();
            if(x.getRight() != null){
                set_children(y,x.getRight(),y.getLeft(),null);
                set_children(x,x.getLeft(),x.getMiddle(),null);
            } else {
                set_children(x,x.getLeft(),x.getMiddle(),y.getLeft());
                set_children(z,x,z.getRight(),null);
            }
            return z;
        }
        TreeNode<K, V> x = z.getMiddle();
        if(x.getRight() != null){
            set_children(y,x.getRight(),y.getLeft(),null);
            set_children(x,x.getLeft(),x.getMiddle(),null);
        } else {
            set_children(x,x.getLeft(),x.getMiddle(),y.getLeft());
            set_children(z,z.getLeft(),x,null);
        }
        return z;
    }

    /**
     * Deletes a node from the tree.
     *
     * @param x The node to be deleted.
     */
    public void delete(TreeNode<K, V> x){
        TreeNode<K, V> y = x.getParent();
        if(x == y.getLeft()){
            set_children(y,y.getMiddle(),y.getRight(),null);
        } else if(x == y.getMiddle()){
            set_children(y,y.getLeft(),y.getRight(),null);
        } else {
            set_children(y,y.getLeft(),y.getMiddle(),null);
        }
        while(y != null){
            if(y.getMiddle() != null){
                update_key(y);
                y = y.getParent();
            } else {
                if(y != this.root){
                    y = borrow_or_merge(y);
                } else {
                    this.root = y.getLeft();
                    y.getLeft().updateParent(null);
                }
            }
        }
        this.root.setPredecessorAndSuccessor(x.getPredecessor(), x.getSuccessor());
    }

    /**
     * Determines the rank of a leaf node (i.e., the number of leaves that are smaller than it).
     *
     * @param x The leaf node.
     * @return The rank as a Float value.
     */
    public Float rank(TreeNode<K, V> x){
        Float rank = 1f;
        TreeNode<K, V> y = x.getParent();
        while(y != null){
            // If arriving at the parent via the middle, add all nodes from the left child.
            if(x == y.getMiddle()){
                rank += (Float) y.getLeft().getValue();
            } else if(x == y.getRight()){
                // If arriving via the right, add nodes from both left and middle.
                rank += (Float) y.getLeft().getValue() + (Float) y.getMiddle().getValue();
            }
            // There is no otherwise, since if arriving via the left we are not greater than anyone (at this level).
            x = y;
            y = y.getParent();
        }
        return rank;
    }

}