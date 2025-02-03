package main;

/**
 * StringKey encapsulates a stock identifier (a string) and is used as a key in the 2-3 tree.
 * It extends the abstract Key class and implements Comparable based on the string value.
 */
public class StringKey extends Key implements Comparable<Key> {
    private String name;

    /**
     * Default constructor initializing with null.
     */
    public StringKey() {
        super();
        this.name = null;
    }

    /**
     * Constructs a StringKey with the given stock identifier.
     *
     * @param name The stock's identifier.
     */
    public StringKey(String name) {
        super();
        this.name = name;
    }

    /**
     * Initializes the tree by setting up left and right sentinels.
     *
     * @param left  The left sentinel.
     * @param right The right sentinel.
     */
    public void initializeTree(StringKey left, StringKey right) {
        super.initializeTree(left, right);
    }

    /**
     * Returns the stock identifier.
     *
     * @return The stock's name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Compares this StringKey with another Key.
     *
     * First, the superclass compares infinity flags; if those are equal,
     * then the string values are compared.
     *
     * @param o The key to compare to.
     * @return A negative integer, zero, or a positive integer as this key is less than,
     *         equal to, or greater than the specified key.
     */
    @Override
    public int compareTo(Key o) {
        if (!(o instanceof StringKey)) {
            return 0;
        }
        StringKey other = (StringKey) o;
        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        }else{
            return this.name.compareTo(other.name);
        }
    }
}
