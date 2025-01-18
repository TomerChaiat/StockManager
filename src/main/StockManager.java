package main;

public class StockManager {
    private TwoThreeTree<StringKey, TwoThreeTree<LongKey, Float>> stocks;

    public StockManager() {
        this.stocks = new TwoThreeTree<>();
    }

    // 1. Initialize the system
    public void initStocks() {
        this.stocks.StocksTreeInitiate();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        TwoThreeTree<LongKey, Float> stockTree = new TwoThreeTree<>();
        stockTree.PricesTreeInitiate(timestamp, price);
        this.stocks.TwoThreeInsert(new TreeNode(new StringKey(stockId), stockTree));
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        stocks.delete(desired);
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        TwoThreeTree<LongKey, Float> stock_tree = desired.getValue();
        stock_tree.TwoThreeInsert(new TreeNode(new LongKey(timestamp, priceDifference), priceDifference));
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        StringKey searchKey = new StringKey(stockId);
        TreeNode<StringKey, TwoThreeTree<LongKey, Float>> desired = stocks.search(stocks.getRoot(), searchKey);
        if (desired == null) {
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        TwoThreeTree<LongKey, Float> stock_tree = desired.getValue();
        return stock_tree.getRoot().getValue();
    }

    // 6. Remove a specific timestamp from a stock's history
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
            throw new IllegalArgumentException("Stock ID '" + stockId + "' not found.");
        }
        search_key = desired_timestamp.getKey();
        if (search_key.isInitiate()){
            throw new IllegalArgumentException("Timestamp '" + timestamp + "' can't be deleted.");
        }
        stock_tree.delete(desired_timestamp);
    }
    /*

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        // add code here
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        // add code here
    }

 */
}


