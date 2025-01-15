package main;

import jdk.nashorn.api.tree.Tree;

public class TreeNode<K extends Comparable<K>, V> {

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

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
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

    public void updateParent(TreeNode<K, V> left, TreeNode<K, V> middle, TreeNode<K, V> right) {
        this.setLeft(left);
        this.setMiddle(middle);
        this.setRight(right);
        if (left != null)
            left.setParent(this);
        if (middle != null)
            middle.setParent(this);
        if (right != null)
            right.setParent(this);
    }

    public void updateChildren(TreeNode<K, V> parent) {
        this.setParent(parent);
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
        LongKey middle_key = new LongKey();
        LongKey root_key = new LongKey(timestamp, price);
        root_key.initializeTree(left_key, middle_key);
        this.left.setKey((K) left_key);
        this.middle.setKey((K) middle_key);
        this.setKey((K) root_key);
    }
}


