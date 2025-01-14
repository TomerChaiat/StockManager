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
    }

    public void initializeTree(LongKey left, LongKey right) {
        left.LeftSentinal();
        right.RightSentinal();
        this.RightSentinal();
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
