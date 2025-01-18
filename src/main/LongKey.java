package main;

public class LongKey extends Key implements Comparable<Key> {
    private Float change;
    private Long timestamp;
    private boolean is_initiate;

    public LongKey() {
        super();
        this.change = 0f;
        this.timestamp = 0L;
        this.is_initiate = false;
    }

    public LongKey(Long timestamp) {
        super();
        this.change = 0f;
        this.timestamp = timestamp;
        this.is_initiate = false;
    }

    public LongKey(Long timestamp, Float price){
        super();
        this.timestamp = timestamp;
        this.change = price;
        this.is_initiate = false;
    }

    public void initializeTree(LongKey left, LongKey root, LongKey right) {
        super.initializeTree(left, root, right);
        this.is_initiate = true;
    }

    public Float getChange() {
        return change;
    }

    public boolean isInitiate() {
        return is_initiate;
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
}
