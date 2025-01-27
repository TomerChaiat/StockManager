package main;

public class TreeNode<K extends Key, V> implements Comparable<V>{

    private K key;
    private V value;
    private TreeNode<K, V> parent;
    private TreeNode<K, V> left;
    private TreeNode<K, V> middle;
    private TreeNode<K, V> right;
    private TreeNode<K, V> successor;
    private TreeNode<K,V> predecessor;

    public TreeNode(K key, V value, TreeNode<K, V> parent, TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right, TreeNode<K, V> successor, TreeNode<K,V> predecessor) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.successor = successor;
        this.predecessor = predecessor;
    }

    public TreeNode(TreeNode<K,V> l, TreeNode<K,V> m) {
        this(null, null, null, l, m, null, null, null);
        this.getLeft().setSuccessor(m);
        this.getMiddle().setPredecessor(l);
    }

    private void setSuccessor(TreeNode<K, V> successor) {
        this.successor = successor;
    }

    private void setPredecessor(TreeNode<K, V> predecessor) {
        this.predecessor = predecessor;
    }

    public TreeNode(K key, V value, TreeNode<K,V> l, TreeNode<K,V> m){
        this(key, value, null, l, m, null, null, null);
        this.getLeft().setSuccessor(m);
        this.getMiddle().setPredecessor(l);
    }

    public TreeNode(K key, V value){
        this(key, value, null, null, null, null, null, null);
    }

    public void setPredecessorAndSuccessor(TreeNode<K,V> prev, TreeNode<K, V> new_node, TreeNode<K,V> next) {
        if (new_node == null || !new_node.isLeaf())
            return;

        if (prev != null) {
            prev.setSuccessor(new_node);
            new_node.setPredecessor(prev);
        }

        if (next != null) {
            new_node.setSuccessor(next);
            next.setPredecessor(new_node);
        }
    }

    public void setPredecessorAndSuccessor(TreeNode<K,V> prev, TreeNode<K,V> next) {
        next.setPredecessor(prev);
        prev.setSuccessor(next);
    }

    public TreeNode() {
        this(null, null, null, null, null, null, null, null);
    }
    public TreeNode(K key){
        this(key, null, null, null, null, null, null, null);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
        this.setInf(key);
    }

    public TreeNode<K, V> getParent() {
        return parent;
    }

    private void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }

    public TreeNode<K, V> getLeft() {
        return left;
    }

    private void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    public TreeNode<K, V> getMiddle() {
        return middle;
    }

    private void setMiddle(TreeNode<K, V> middle) {
        this.middle = middle;
    }

    public TreeNode<K, V> getRight() {
        return right;
    }

    private void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    private void setInf(K child) {
        Key parent_key;
        Key child_key;
        if (child == null || this.getKey() == null)
            return;
        parent_key = this.getKey();
        child_key = child;

        // Set infinity or minus infinity, depends on the child's key.
        parent_key.setInf(child_key.isInf());
    }

    public void updateChildren(TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        this.setLeft(left);
        this.setMiddle(middle);
        this.setRight(right);
        if (this.getKey() instanceof LongKey || left.getKey() instanceof LongKey || this.getKey() instanceof PriceKey || left.getKey() instanceof PriceKey){
            this.updateSum(left, middle, right);
        }
        if (left != null)
            left.setParent(this);
        if (middle != null)
            middle.setParent(this);
        if (right != null)
            right.setParent(this);
    }

    public void updateParent(TreeNode<K, V> parent) {
        this.setParent(parent);
    }

    private void updateSum(TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        if (this.isLeaf())
            return;

        Float sum = 0f;

        // Add values from left, middle, and right children if they exist.
        if (left != null && left.getValue() instanceof Float) {
            sum += (Float) left.getValue();
        }
        if (middle != null && middle.getValue() instanceof Float) {
            sum += (Float) middle.getValue();
        }
        if (right != null && right.getValue() instanceof Float) {
            sum += (Float) right.getValue();
        }

        // Safely assign the sum to this.value if it's compatible with Float.
        if (this.value instanceof Float || this.value == null) {
            this.value = (V) sum; // Safe cast to generic type V.
        } else {
            throw new IllegalArgumentException("Incompatible value type for sum calculation.");
        }
    }

    public void StocksTreeInitiate(){
        StringKey left_key = new StringKey();
        StringKey middle_key = new StringKey();
        StringKey root_key = new StringKey();

        root_key.initializeTree(left_key, middle_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.setKey((K) root_key);
    }

    public void StocksTreePricesInitiate(){
        PriceKey left_key = new PriceKey();
        PriceKey middle_key = new PriceKey();
        PriceKey root_key = new PriceKey();

        root_key.initializeTree(left_key, middle_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.setKey((K) root_key);
    }


    public void PricesTreeInitiate(Long timestamp, Float price){
        LongKey left_key = new LongKey();
        LongKey middle_key = new LongKey(timestamp, price);
        LongKey root_key = new LongKey();
        LongKey right_key = new LongKey();
        middle_key.initializeTree(left_key, root_key,right_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.middle.value = (V) price;
        this.right.setKey((K) right_key);
        this.setKey((K) root_key);
        this.updateChildren(this.left, this.middle, this.right); // To initiate the correct price
    }

    public boolean isLeaf(){
        return this.left == null;
    }

    @Override
    public int compareTo(Object o) {
        TreeNode<K, V> other = new TreeNode<>();
        if (!(o instanceof TreeNode))
            return 0;
        other = (TreeNode<K, V>) o;
        return this.key.compareTo(other.getKey());
    }

    public TreeNode<K, V> getSuccessor() {
        return this.successor;
    }

    public TreeNode<K, V> getPredecessor() {
        return this.predecessor;
    }
}