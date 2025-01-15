package main;

public class StockManager {
    private TwoThreeTree<String, TwoThreeTree<Long, Float>> stocks;

    public StockManager() {
        this.stocks = new TwoThreeTree<>();
    }

    // 1. Initialize the system
    public void initStocks() {
        this.stocks.StocksTreeInitiate();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        TwoThreeTree<Long, Float> stock = new TwoThreeTree<>();
        stock.PricesTreeInitiate(timestamp, price);
        this.stocks.TwoThreeInsert(new TreeNode(stockId, stock));
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        // add code here
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        // add code here
    }
/*
    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        // add code here
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        // add code here
    }

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


