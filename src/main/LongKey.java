package main;

public class LongKey extends Key implements Comparable<Key> {
    private Float sum;
    private Float change;
    private Long timestamp;
    private boolean is_initiate;

    public LongKey() {
        super();
        this.sum = 0f;
        this.change = 0f;
        this.timestamp = 0L;
        this.is_initiate = false;
    }

    public LongKey(Long timestamp, Float price){
        super();
        this.timestamp = timestamp;
        this.sum = price;
        this.change = price;
        this.is_initiate = false;
    }

    public void initializeTree(LongKey left, LongKey root, LongKey right) {
        super.initializeTree(left, root, right);
        this.is_initiate = true;
    }

    @Override
    public int compareTo(Key o) {
        if (!(o instanceof LongKey) || o == null || this == null) {
            return 0;
        }
        LongKey other = (LongKey) o;
        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        }else{
            return this.timestamp.compareTo(other.timestamp);
        }
    }

    public Float getSum() {
        return sum;
    }

    public void updateSum(LongKey left, LongKey middle, LongKey right) {
        this.sum = this.change;
        if (left != null)
            this.sum += left.getSum();
        if (middle != null)
            this.sum += middle.getSum();
        if (right != null)
            this.sum += right.getSum();
    }

    public void updateSum(Float num){
        this.sum += num;
    }
}
