package main;

public class TreeNode<K extends Key, V> implements Comparable{

    private K key;
    private V value;
    private TreeNode<K, V> parent;
    private TreeNode<K, V> left;
    private TreeNode<K, V> middle;
    private TreeNode<K, V> right;

    public TreeNode(K key, V value, TreeNode<K, V> parent, TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public TreeNode(K key, V value){
        this.key = key;
        this.value = value;
        this.parent = null;
        this.left = null;
        this.middle = null;
        this.right = null;
    }

    public TreeNode() {
        this(null, null, null, null, null, null);
    }
    public TreeNode(K key){
        this(key, null, null, null, null, null);
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
        if (!(child instanceof Key) || !(this.getKey() instanceof Key))
            return;
        parent_key = this.getKey();
        child_key = child;
        if (child_key.isInf()){
            parent_key.setInf(true);
        }else if (child_key.isMinusInf()){
            parent_key.setMinusInf(true);
        }else if (!(child_key.isInf())){
            parent_key.setInf(false);
        }else if (!(child_key.isMinusInf())){
            parent_key.setMinusInf(false);
        }
    }

    public void updateChildren(TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        this.setLeft(left);
        this.setMiddle(middle);
        this.setRight(right);
        if (this.getKey() instanceof LongKey || left.getKey() instanceof LongKey){
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
        LongKey parent_key = null;
        if (this.getKey() instanceof LongKey) {
            parent_key = (LongKey) this.getKey();
        }

        Float sum;

        // Start with the parent's change value.
        if (parent_key == this.getKey() && parent_key != null){
            sum = parent_key.getChange();
        }else{
            sum = 0f;
        }

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
}


