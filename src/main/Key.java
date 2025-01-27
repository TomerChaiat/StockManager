package main;

abstract class Key implements Comparable<Key> {
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

    protected void initializeTree(Key left, Key root, Key right){
        left.LeftSentinal();
        root.RightSentinal();
        right.RightSentinal();
    }

    protected void initializeTree(Key left, Key right){
        left.LeftSentinal();
        right.RightSentinal();
        this.RightSentinal();
    }

    public boolean isInf(){
        return this.is_inf;
    }

    public boolean isMinusInf(){
        return this.is_minus_inf;
    }

    public void setInf(boolean state){
        this.is_inf = state;
    }

    public void setMinusInf(boolean state){
        this.is_minus_inf = state;
    }

    @Override
    public int compareTo(Key o) {
        //if (this.is_inf && o.is_inf) return 1;
        //if (this.is_minus_inf && o.is_minus_inf) return -1;
        if (o.is_inf || this.is_minus_inf) return -1;
        if (o.is_minus_inf || this.is_inf) return 1;
        return 0;
    }
}
