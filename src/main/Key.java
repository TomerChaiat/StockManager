package main;

public class Key implements Comparable<Key> {
    protected boolean is_inf;
    protected boolean is_minus_inf;

    protected Key(){
        this.is_inf = false;
        this.is_minus_inf = false;
    }

    protected void LeftSentinal(){
        this.is_minus_inf = true;
    }

    protected void RightSentinal(){
        this.is_inf = true;
    }

    @Override
    public int compareTo(Key o) {
        if (o.is_inf) return -1;
        if (o.is_minus_inf) return 1;
        return 0;
    }
}
