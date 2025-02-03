package main;

/**
 * StockManager manages a collection of stocks and their price histories.
 * It uses two 2-3 trees:
 * <ul>
 *   <li>One maps a stock ID (using StringKey) to its price history (another 2-3 tree).</li>
 *   <li>The other manages stock prices (using PriceKey) for range queries.</li>
 * </ul>
 */
public class StockManager {
    private TwoThreeTree<StringKey, TwoThreeTree<LongKey, Float>> stocks; // Maps stock IDs to their price histories.
    private TwoThreeTree<PriceKey, Float> stocksPrices;                   // Tree for stock prices (for range queries).

    /**
     * Default constructor that initializes the stock manager.
     */
    public StockManager() {
        this.stocks = new TwoThreeTree<>();
        this.stocksPrices = new TwoThreeTree<>(true);
    }

    /**
     * Initializes the system by setting up the stocks and stock prices trees.
     */
    public void initStocks() {
        this.stocks = new TwoThreeTree<>();
        this.stocks.StocksTreeInitiate();
        this.stocksPrices = new TwoThreeTree<>(true);
        this.stocksPrices.StocksPricesTreeInitiate();
    }

    /**
     * Adds a new stock.
     *
     * @param stockId   The unique stock identifier.
     * @param timestamp The timestamp of the initial price.
     * @param price     The initial price of the stock.
     * @throws IllegalArgumentException if the stock already exists or parameters are invalid.
     */
    public void addStock(String stockId, long timestamp, Float price) {
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp cannot be negative");
        if (price <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired != null)
            throw new IllegalArgumentException("Stock ID '" + stockId + "' is already in the system.");
        TwoThreeTree<LongKey, Float> stockTree = new TwoThreeTree<>();
        stockTree.PricesTreeInitiate(timestamp, price);
        this.stocks.TwoThreeInsert(new TreeNode<>(new StringKey(stockId), stockTree));
        this.stocksPrices.TwoThreeInsert(new TreeNode<>(new PriceKey(stockId, price), 1f));
    }

    /**
     * Removes a stock from the system.
     *
     * @param stockId The stock identifier.
     * @throws IllegalArgumentException if the stock is not found.
     */
    public void removeStock(String stockId) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        Float f = getStockPrice(stockId);
        PriceKey price_key = new PriceKey(stockId, f);

        stocks.delete(desired);
        stocksPrices.delete(stocksPrices.search(stocksPrices.getRoot(), price_key));
    }

    /**
     * Updates a stock's price.
     *
     * @param stockId         The stock identifier.
     * @param timestamp       The timestamp of the update.
     * @param priceDifference The amount by which the price changes.
     * @throws IllegalArgumentException if the stock is not found or parameters are invalid.
     */
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp cannot be negative");
        if (priceDifference == 0)
            throw new IllegalArgumentException("Price difference cannot be zero");
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        TwoThreeTree<LongKey, Float> stock_tree = desired.getValue();
        Float old_price = stock_tree.getRoot().getValue();

        PriceKey price_key = new PriceKey(stockId, old_price);
        TreeNode<PriceKey, Float> desired_price = stocksPrices.search(stocksPrices.getRoot(), price_key);
        stocksPrices.delete(desired_price);
        stocksPrices.TwoThreeInsert(new TreeNode<>(new PriceKey(stockId, old_price + priceDifference), 1f));
        stock_tree.TwoThreeInsert(new TreeNode(new LongKey(timestamp, priceDifference), priceDifference));
    }

    /**
     * Retrieves the current price of a stock.
     *
     * @param stockId The stock identifier.
     * @return The current price.
     * @throws IllegalArgumentException if the stock is not found.
     */
    public Float getStockPrice(String stockId) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        TwoThreeTree<LongKey, Float> stock_tree = desired.getValue();
        return stock_tree.getRoot().getValue();
    }

    /**
     * Removes a specific timestamp from a stock's history.
     *
     * @param stockId   The stock identifier.
     * @param timestamp The timestamp to remove.
     * @throws IllegalArgumentException if the stock or timestamp is not found or cannot be deleted.
     */
    public void removeStockTimestamp(String stockId, long timestamp) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        TwoThreeTree<LongKey, Float> stock_tree = desired.getValue();
        LongKey search_key = new LongKey(timestamp);
        TreeNode<LongKey, Float> desired_timestamp = stock_tree.search(stock_tree.getRoot(), search_key);
        if (desired_timestamp == null) {
            throw new IllegalArgumentException("TimeStamp '" + timestamp + "' not found.");
        }
        search_key = desired_timestamp.getKey();
        if (search_key.isInitiate()){
            throw new IllegalArgumentException("Timestamp '" + timestamp + "' can't be deleted.");
        }
        Float old_price = stock_tree.getRoot().getValue();
        PriceKey price_key = new PriceKey(stockId, old_price);
        TreeNode<PriceKey, Float> desired_price = stocksPrices.search(stocksPrices.getRoot(), price_key);
        this.stocksPrices.delete(desired_price);
        this.stocksPrices.TwoThreeInsert(new TreeNode<>(new PriceKey(stockId, old_price - search_key.getChange()), 1f));
        stock_tree.delete(desired_timestamp);
    }

    /**
     * Returns the number of stocks in the given price range.
     *
     * @param price1 The lower bound of the price range.
     * @param price2 The upper bound of the price range.
     * @return The count of stocks within the range.
     * @throws IllegalArgumentException if the price range is invalid.
     */
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2) {
            throw new IllegalArgumentException("Price 1 must be greater than Price 2");
        }
        PriceKey left = new PriceKey(new StringKey(null), price1, false);
        PriceKey right = new PriceKey(new StringKey(null), price2, true);

        TreeNode<PriceKey, Float> left_node = new TreeNode<>(left, 1f);
        TreeNode<PriceKey, Float> right_node = new TreeNode<>(right, 1f);
        this.stocksPrices.TwoThreeInsert(left_node);
        this.stocksPrices.TwoThreeInsert(right_node);

        int total = Math.round(this.stocksPrices.rank(right_node)) - Math.round(this.stocksPrices.rank(left_node)) - 1;
        this.stocksPrices.delete(right_node);
        this.stocksPrices.delete(left_node);
        return total;
    }

    /**
     * Returns an array of stock identifiers within the given price range.
     *
     * @param price1 The lower bound of the price range.
     * @param price2 The upper bound of the price range.
     * @return An array of stock IDs.
     * @throws IllegalArgumentException if the price range is invalid.
     */
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2) {
            throw new IllegalArgumentException("Price 1 must be greater than Price 2");
        }
        int length = getAmountStocksInPriceRange(price1, price2);
        String[] stocks_in_range = new String[length];
        PriceKey left = new PriceKey(new StringKey(null), price1, false);
        TreeNode<PriceKey, Float> left_node = new TreeNode<>(left, 1f);
        this.stocksPrices.TwoThreeInsert(left_node);
        TreeNode<PriceKey, Float> right_brother = left_node.getSuccessor();
        for (int i = 0; i < length; i++) {
            stocks_in_range[i] = right_brother.getKey().getName();
            right_brother = right_brother.getSuccessor();
        }
        this.stocksPrices.delete(left_node);
        return stocks_in_range;
    }
}


