package main;

/**
 * LongKey represents a key that contains a timestamp and a price change for a stock.
 * It is used in a 2-3 tree to maintain a stock's price history.
 */
public class LongKey extends Key implements Comparable<Key> {
    private Float change;        // The change in price.
    private Long timestamp;      // The timestamp of the change.
    private boolean is_initiate; // Flag indicating if this node was used to initialize the tree.

    /**
     * Default constructor initializing with default values.
     */
    public LongKey() {
        super();
        this.change = 0f;
        this.timestamp = 0L;
        this.is_initiate = false;
    }

    /**
     * Constructs a LongKey with a given timestamp.
     *
     * @param timestamp The timestamp associated with the key.
     */
    public LongKey(Long timestamp) {
        super();
        this.change = 0f;
        this.timestamp = timestamp;
        this.is_initiate = false;
    }

    /**
     * Constructs a LongKey with a given timestamp and price change.
     *
     * @param timestamp The timestamp associated with the key.
     * @param price     The price change.
     */
    public LongKey(Long timestamp, Float price){
        super();
        this.timestamp = timestamp;
        this.change = price;
        this.is_initiate = false;
    }

    /**
     * Initializes the prices tree for a stock.
     *
     * @param left  The left sentinel node.
     * @param root  The root node.
     * @param right The right sentinel node.
     */
    public void initializeTree(LongKey left, LongKey root, LongKey right) {
        root.initializeTree(left, right); // We call for the super function in Key class.
        this.is_initiate = true; // this = the key when we insert the stock at the first time.
    }

    /**
     * Gets the price change.
     *
     * @return The change in price.
     */
    public Float getChange() {
        return change;
    }

    /**
     * Determines if this key was used to initiate the tree.
     *
     * @return true if this key was used for initialization, false otherwise.
     */
    public boolean isInitiate() {
        return is_initiate;
    }

    /**
     * Compares this LongKey with another Key.
     *
     * The comparison first handles any infinity cases via the superclass and then compares timestamps.
     *
     * @param o The key to compare with.
     * @return 1 if this key is greater, -1 if it is less, or 0 if they are equal.
     */
    @Override
    public int compareTo(Key o) {
        if (!(o instanceof LongKey)) {
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
