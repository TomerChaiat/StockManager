package main;

public class StockManager {
    private TwoThreeTree<StringKey, TwoThreeTree<LongKey, Float>> stocks;
    private TwoThreeTree<PriceKey, Float> stocksPrices;

    public StockManager() {
        this.stocks = new TwoThreeTree<>();
        this.stocksPrices = new TwoThreeTree<>(true);
    }

    // 1. Initialize the system
    public void initStocks() {
        this.stocks.StocksTreeInitiate();
        this.stocksPrices.StocksPricesTreeInitiate();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
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

    // 3. Remove a stock
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

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
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

    // 7. Get the amount of stocks in a given price range
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

    // 8. Get a list of stock IDs within a given price range
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


