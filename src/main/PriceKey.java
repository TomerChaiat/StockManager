package main;

/**
 * PriceKey represents a key used in the prices tree.
 * It extends the abstract Key class and stores a stock's price along with its name.
 * The name is stored as a StringKey so that two stocks with the same price
 * can still be differentiated and handled the cases of infinity or minus infinity.
 */
public class PriceKey extends Key {
    private final StringKey name; // The stock name (wrapped in a StringKey).
    private Float price;          // The stock's current price.

    /**
     * Default constructor used for initializing the prices tree.
     */
    public PriceKey() {
        super();
        this.name = null;
    }

    /**
     * Constructs a PriceKey for use in leaf nodes.
     *
     * @param name The name of the stock.
     * @param price The current price of the stock.
     */
    public PriceKey(String name, Float price) {
        super();
        this.name = new StringKey(name);
        this.price = price;
    }

    /**
     * Constructs a PriceKey when inserting a temporary node into the prices tree.
     *
     * @param name   The stock name as a StringKey.
     * @param price  The current price of the stock.
     * @param is_inf true if the price should be considered as infinity,
     *               false if it should be minus infinity.
     */
    public PriceKey(StringKey name, Float price, boolean is_inf) {
        super();
        if (is_inf)
            name.setInf(true);
        else
            name.setMinusInf(true);
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the stock name.
     *
     * @return The stock name as a String.
     */
    public String getName(){
        return this.name.getName();
    }

    /**
     * Compares this PriceKey with another Key.
     *
     * The comparison first uses the infinity flags from the superclass.
     * If both keys are normal, then it compares the price values (using a small epsilon for float precision)
     * and, if they are nearly equal, compares the stock names.
     *
     * @param o The key to compare to.
     * @return 1 if this key is greater, -1 if it is less, or 0 if they are equal.
     */
    @Override
    public int compareTo(Key o) {
        if (!(o instanceof PriceKey)) {
            return 0;
        }

        // Safe cast to PriceKey.
        PriceKey other = (PriceKey) o;

        // Use the superclass compareTo to handle infinity/minus-infinity cases.
        if (super.compareTo(other) != 0){
            return super.compareTo(other);
        } else {
            // Use an epsilon for floating point comparison.
            float epsilon = 0.0001f;
            // If the prices are nearly equal, compare the stock names.
            if (Math.abs(this.price - other.price) < epsilon) {
                return this.name.compareTo(other.name);
            }
            // Otherwise, compare by price.
            return Float.compare(this.price, other.price);
        }
    }
}
