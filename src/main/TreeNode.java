package main;

/**
 * TreeNode represents a node in a 2-3 tree.
 * It contains a key, an associated value, and pointers to its parent and children.
 * It also maintains pointers to its in-order predecessor and successor.
 *
 * @param <K> The key type (extending Key).
 * @param <V> The value type.
 */
public class TreeNode<K extends Key, V> implements Comparable<V>{
    private K key;                        // The key stored in this node.
    private V value;                      // The associated value.
    private TreeNode<K, V> parent;        // The parent node.
    private TreeNode<K, V> left;          // The left child.
    private TreeNode<K, V> middle;        // The middle child.
    private TreeNode<K, V> right;         // The right child.
    private TreeNode<K, V> successor;     // The in-order successor.
    private TreeNode<K, V> predecessor;   // The in-order predecessor.

    /**
     * Default constructor.
     */
    public TreeNode() {
        this(null, null, null, null, null, null, null, null);
    }

    /**
     * Constructs a node with the given key.
     *
     * @param key The key.
     */
    public TreeNode(K key){
        this(key, null, null, null, null, null, null, null);
    }

    /**
     * Constructs an internal node with two children.
     *
     * @param l The left child.
     * @param m The middle child.
     */
    public TreeNode(TreeNode<K,V> l, TreeNode<K,V> m) {
        this(null, null, null, l, m, null, null, null);
        this.getLeft().setSuccessor(m);
        this.getMiddle().setPredecessor(l);
    }

    /**
     * Constructs a node with the given key and value.
     *
     * @param key   The key.
     * @param value The value.
     */
    public TreeNode(K key, V value){
        this(key, value, null, null, null, null, null, null);
    }

    /**
     * Constructs a node with all fields specified.
     *
     * @param key         The key.
     * @param value       The value.
     * @param parent      The parent node.
     * @param left        The left child.
     * @param middle      The middle child.
     * @param right       The right child.
     * @param successor   The in-order successor.
     * @param predecessor The in-order predecessor.
     */
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

    /**
     * Constructs a node with the given key, value, and two children.
     *
     * @param key   The key.
     * @param value The value.
     * @param l     The left child.
     * @param m     The middle child.
     */
    public TreeNode(K key, V value, TreeNode<K, V> l, TreeNode<K, V> m){
        this(key, value, null, l, m, null, null, null);
        this.getLeft().setSuccessor(m);
        this.getMiddle().setPredecessor(l);
    }

    private void setSuccessor(TreeNode<K, V> successor) {
        this.successor = successor;
    }

    private void setPredecessor(TreeNode<K, V> predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * Sets the predecessor and successor for a newly inserted leaf.
     *
     * @param prev     The predecessor.
     * @param new_node The new leaf node.
     * @param next     The successor.
     */
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

    /**
     * Sets the predecessor and successor for two adjacent nodes. (after deletion)
     *
     * @param prev The predecessor.
     * @param next The successor.
     */
    public void setPredecessorAndSuccessor(TreeNode<K,V> prev, TreeNode<K,V> next) {
        next.setPredecessor(prev);
        prev.setSuccessor(next);
    }

    /**
     * Returns the key of this node.
     *
     * @return The key.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key for this node and updates infinity flags if necessary.
     *
     * @param key The new key.
     */
    public void setKey(K key) {
        this.key = key;
        this.setInf(key);
    }

    /**
     * Returns the value associated with this node.
     *
     * @return The value.
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns the parent node.
     *
     * @return The parent.
     */
    public TreeNode<K, V> getParent() {
        return parent;
    }

    private void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }

    /**
     * Returns the left child.
     *
     * @return The left child.
     */
    public TreeNode<K, V> getLeft() {
        return left;
    }

    private void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    /**
     * Returns the middle child.
     *
     * @return The middle child.
     */
    public TreeNode<K, V> getMiddle() {
        return middle;
    }

    private void setMiddle(TreeNode<K, V> middle) {
        this.middle = middle;
    }

    /**
     * Returns the right child.
     *
     * @return The right child.
     */
    public TreeNode<K, V> getRight() {
        return right;
    }

    private void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * Updates the infinity flag based on the child's key.
     *
     * @param child The child key.
     */
    private void setInf(K child) {
        Key parent_key;
        Key child_key;
        if (child == null || this.getKey() == null)
            return;
        parent_key = this.getKey();
        child_key = child;

        // Set infinity or minus infinity based on the child's key.
        parent_key.setInf(child_key.isInf());
    }

    /**
     * Updates the children pointers of this node.
     *
     * @param left   The left child.
     * @param middle The middle child.
     * @param right  The right child.
     */
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

    /**
     * Updates the parent pointer for this node.
     *
     * @param parent The parent node.
     */
    public void updateParent(TreeNode<K, V> parent) {
        this.setParent(parent);
    }

    /**
     * Updates the sum value stored in this node based on its children.
     * This is used in trees that maintain aggregated sums (for rank calculation or price of a stock).
     *
     * @param left   The left child.
     * @param middle The middle child.
     * @param right  The right child.
     */
    private void updateSum(TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        if (this.isLeaf())
            return;

        Float sum = 0f;

        // Sum the values from children (if they are of type Float).
        if (left != null && left.getValue() instanceof Float) {
            sum += (Float) left.getValue();
        }
        if (middle != null && middle.getValue() instanceof Float) {
            sum += (Float) middle.getValue();
        }
        if (right != null && right.getValue() instanceof Float) {
            sum += (Float) right.getValue();
        }

        // Set the aggregated sum as the value for this node.
        if (this.value instanceof Float || this.value == null) {
            this.value = (V) sum; // Safe cast to generic type V.
        } else {
            throw new IllegalArgumentException("Incompatible value type for sum calculation.");
        }
    }

    /**
     * Initializes the stocks tree with default StringKey sentinels.
     */
    public void StocksTreeInitiate(){
        StringKey left_key = new StringKey();
        StringKey middle_key = new StringKey();
        StringKey root_key = new StringKey();

        root_key.initializeTree(left_key, middle_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.setKey((K) root_key);
    }

    /**
     * Initializes the stocks prices tree with default PriceKey sentinels.
     */
    public void StocksTreePricesInitiate(){
        PriceKey left_key = new PriceKey();
        PriceKey middle_key = new PriceKey();
        PriceKey root_key = new PriceKey();

        root_key.initializeTree(left_key, middle_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.setKey((K) root_key);
    }

    /**
     * Initializes the prices tree with a given timestamp and price.
     *
     * @param timestamp The initial timestamp.
     * @param price     The initial price.
     */
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

    /**
     * Checks whether this node is a leaf.
     *
     * @return true if this node has no children, false otherwise.
     */
    public boolean isLeaf(){
        return this.left == null;
    }

    /**
     * Returns the in-order successor of this node.
     *
     * @return The successor node.
     */
    public TreeNode<K, V> getSuccessor() {
        return this.successor;
    }

    /**
     * Returns the in-order predecessor of this node.
     *
     * @return The predecessor node.
     */
    public TreeNode<K, V> getPredecessor() {
        return this.predecessor;
    }

    /**
     * Compares this node to another node based on its key.
     *
     * @param o The object to compare to.
     * @return The result of comparing the keys.
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof TreeNode))
            return 0;
        TreeNode<K, V> other = (TreeNode<K, V>) o;
        return this.key.compareTo(other.getKey());
    }
}