package main;

public class TwoThreeTree<K extends Key, V> {

    private TreeNode<K,V> root;

    public TwoThreeTree() {
        TreeNode<K,V> l = new TreeNode<>();
        TreeNode<K,V> m = new TreeNode<>();
        TreeNode<K,V> x = new TreeNode<>();
        x.updateChildren(l,m,null);
        this.root = x;
    }

    public TwoThreeTree(boolean val) {
        TreeNode<Key, Float> l = new TreeNode<>(null, 0f);
        TreeNode<Key, Float> m = new TreeNode<>(null, 0f);
        TreeNode<Key, Float> x = new TreeNode<>(null, 0f);
        x.updateChildren(l,m,null);
        this.root = (TreeNode<K, V>) x;
    }

    public void StocksTreeInitiate(){
        this.root.StocksTreeInitiate();
    }

    public void StocksPricesTreeInitiate(){
        this.root.StocksTreePricesInitiate();
    }

    public void PricesTreeInitiate(Long timestamp, Float price) {
        TreeNode<K,V> r = new TreeNode<>();
        this.root.updateChildren(root.getLeft(), root.getMiddle(), r);
        this.root.PricesTreeInitiate(timestamp, price);
    }

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

    public TreeNode<K, V> getRoot(){
        return this.root;
    }

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

    public void TwoThreeInsert(TreeNode<K,V> z){
        TreeNode<K,V> y = this.root;
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
    }

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
    }

    public Float rank(TreeNode<K, V> x){
        Float rank = 1f;
        TreeNode<K, V> y = x.getParent();
        while(y != null){
            if(x == y.getMiddle()){
                rank = rank + (Float) y.getLeft().getValue();
            } else if(x == y.getRight()){
                rank = rank +(Float) y.getLeft().getValue() + (Float) y.getRight().getValue();
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }

}