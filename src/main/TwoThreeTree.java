package main;

public class TwoThreeTree<K extends Comparable<K>, V> {

    TreeNode<K,V> root;

    public TwoThreeTree() {
        TreeNode<K,V> l = new TreeNode<>();
        TreeNode<K,V> m = new TreeNode<>();
        TreeNode<K,V> x = new TreeNode<>();
        x.updateParent(l,m,null);
        this.root = x;
    }

    public void StocksTreeInitiate(){
        this.root.StocksTreeInitiate();
    }

    public void PricesTreeInitiate(Long timestamp, Float price) {
        this.root.PricesTreeInitiate(timestamp, price);
    }

    public TreeNode<K, V> search(TreeNode<K, V> x, K k) {
        if (x.getLeft() == null) {
            if (x.getKey().compareTo(k) == 0) {
                return x;
            } else {
                return null;
            }
        }
        if (k.compareTo(x.getLeft().getKey()) <= 0) {
            return search(x.getLeft(), k);
        } else if (k.compareTo(x.getMiddle().getKey()) <= 0) {
            return search(x.getMiddle(), k);
        } else {
            return search(x.getRight(), k);
        }
    }

    public void update_key(TreeNode<K, V> x){
        x.setKey(x.getLeft().getKey());
        if(x.getMiddle() != null){
            x.setKey(x.getMiddle().getKey());
        }
        if(x.getRight() != null){
            x.setKey(x.getRight().getKey());
        }
    }

    public void set_children(TreeNode<K,V> x, TreeNode<K, V> l, TreeNode<K, V> m, TreeNode<K, V> r){
        x.updateParent(l,m,r);
        l.updateChildren(x);
        if(m != null){
            m.updateChildren(x);
        }
        if(r != null){
            r.updateChildren(x);
        }
        update_key(x);
    }

    public TreeNode<K, V> insert_and_split(TreeNode<K, V> x, TreeNode<K, V> z){
        TreeNode<K, V> l = x.getLeft();
        TreeNode<K, V> m = x.getMiddle();
        TreeNode<K, V> r = x.getRight();
        if(r == null){
            if(z.getKey().compareTo(l.getKey()) < 0){
                set_children(x,z,l,m);
            } else if(z.getKey().compareTo(m.getKey()) < 0){
                set_children(x,l,z,m);
            } else {
                set_children(x,l,m,z);
            }
            return null;
        }
        TreeNode<K, V> y = new TreeNode<>();
        if(z.getKey().compareTo(l.getKey()) < 0){
            set_children(x,z,l,null);
            set_children(y,m,r,null);
        } else if (z.getKey().compareTo(m.getKey()) < 0){
            set_children(x,l,z,null);
            set_children(y,m,r,null);
        } else if(z.getKey().compareTo(r.getKey()) < 0){
            set_children(x,l,m,null);
            set_children(y,z,r,null);
        } else {
            set_children(x,l,m,null);
            set_children(y,r,z,null);
        }
        return y;
    }

    public void TwoThreeInsert(TreeNode<K,V> z){
        TreeNode<K,V> y = this.root;
        while(y.getLeft() != null){

            if(y.getLeft().getKey().compareTo(z.getKey()) == 0 || y.getMiddle().getKey().compareTo(z.getKey()) == 0 || y.getRight().getKey().compareTo(z.getKey()) == 0){
                throw new IllegalArgumentException();
            }
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

    public TreeNode<K, V> borrow_or_merge(TreeNode<K, V> y){
        TreeNode<K, V> z = y.getParent();
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
                    y.getLeft().updateChildren(null);
                }
            }
        }
    }
}
