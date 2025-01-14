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

    public TwoThreeTree<K, V> PricesTree(Float price, Long timestamp) {
        LongKey left = new LongKey();
        LongKey middle = new LongKey();
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

    public void set_children(TreeNode<K,V> x, Node<E> l, Node<E> m, Node<E> r){
        x.setLeft(l);
        x.setMiddle(m);
        x.setRight(r);
        l.setParent(x);
        if(m != null){
            m.setParent(x);
        }
        if(r != null){
            r.setParent(x);
        }
        update_key(x);
    }

    public Node<E> insert_and_split(Node<E> x, Node<E> z){
        Node<E> l = x.getLeft();
        Node<E> m = x.getMiddle();
        Node<E> r = x.getRight();
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
        Node <E> y = new Node<>();
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

    public void Insert(TwoThreeTree<K,V> x){

    }

    public void TwoThreeInsert(TreeNode<K,V> z){
        Node<E> y = this.root;
        while(y.getLeft() != null){
            if(z.getKey().compareTo(y.getLeft().getKey()) == 0 || z.getKey().compareTo(y.getMiddle().getKey()) == 0 || z.getKey().compareTo(y.getRight().getKey()) == 0){
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
        Node<E> x = y.getParent();
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
            Node<E> w = new Node<>();
            set_children(w,x,z,null);
            this.root = w;
        }
    }

    public Node<E> borrow_or_merge(Node<E> y){
        Node<E> z = y.getParent();
        if(y == z.getLeft()){
            Node<E> x = z.getMiddle();
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
            Node<E> x = z.getLeft();
            if(x.getRight() != null){
                set_children(y,x.getRight(),y.getLeft(),null);
                set_children(x,x.getLeft(),x.getMiddle(),null);
            } else {
                set_children(x,x.getLeft(),x.getMiddle(),y.getLeft());
                set_children(z,x,z.getRight(),null);
            }
            return z;
        }
        Node<E> x = z.getMiddle();
        if(x.getRight() != null){
            set_children(y,x.getRight(),y.getLeft(),null);
            set_children(x,x.getLeft(),x.getMiddle(),null);
        } else {
            set_children(x,x.getLeft(),x.getMiddle(),y.getLeft());
            set_children(z,z.getLeft(),x,null);
        }
        return z;
    }

    public void delete(Node<E> x){
        Node<E> y = x.getParent();
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
                    y.getLeft().setParent(null);
                }
            }
        }
    }
}
