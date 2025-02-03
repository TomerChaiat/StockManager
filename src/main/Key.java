package main;

/**
 * Key is an abstract class that provides a basic implementation of a key for the 2-3 tree.
 * It supports special values for infinity and minus infinity, which are used as sentinels.
 */
abstract class Key implements Comparable<Key> {
    private boolean is_inf;       // Indicates if this key represents infinity.
    private boolean is_minus_inf; // Indicates if this key represents minus infinity.

    /**
     * Default constructor initializing the key as a normal (non-sentinel) key.
     */
    protected Key(){
        this.is_inf = false;
        this.is_minus_inf = false;
    }

    private void LeftSentinel(){
        this.is_minus_inf = true;
    }

    private void RightSentinel(){
        this.is_inf = true;
    }

    /**
     * Initializes the tree with left and right sentinel keys.
     *
     * @param left  The left sentinel (will be set to minus infinity).
     * @param right The right sentinel (will be set to infinity).
     * We will set also the root key to be infinity from here (this.RightSentinel())
     */
    protected void initializeTree(Key left, Key right){
        left.LeftSentinel();
        right.RightSentinel();
        this.RightSentinel();
    }

    /**
     * Checks if this key represents infinity.
     *
     * @return true if this key is infinity, false otherwise.
     */
    public boolean isInf(){
        return this.is_inf;
    }

    /**
     * Checks if this key represents minus infinity.
     *
     * @return true if this key is minus infinity, false otherwise.
     */
    public boolean isMinusInf(){
        return this.is_minus_inf;
    }

    /**
     * Sets the infinity flag for this key.
     *
     * @param state true to mark as infinity, false otherwise.
     */
    public void setInf(boolean state){
        this.is_inf = state;
    }

    /**
     * Sets the minus infinity flag for this key.
     *
     * @param state true to mark as minus infinity, false otherwise.
     */
    public void setMinusInf(boolean state){
        this.is_minus_inf = state;
    }

    /**
     * Compares this key with another key.
     *
     * This implementation only compares the special infinity flags.
     *
     * @param o The key to compare to.
     * @return -1, 0, or 1 based on whether this key is less than, equal to, or greater than the other.
     */
    @Override
    public int compareTo(Key o) {
        if (o.is_inf || this.is_minus_inf) return -1;
        if (o.is_minus_inf || this.is_inf) return 1;
        return 0;
    }
}
