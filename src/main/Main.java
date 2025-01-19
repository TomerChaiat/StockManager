package main;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1) Create a StockManager and initialize the system
        StockManager stockManager = new StockManager();
        stockManager.initStocks();
        System.out.println("Initialized the StockManager's 2-3 tree.\n");

        // 2) Sample data to insert
        String[] stockIds = {"A", "B", "C", "D", "E", "F", "G"};
        long[] timestamps = {1000L, 2000L, 3000L, 4000L, 5000L, 6000L, 7000L};
        float[] prices = {135.5f, 720.0f, 3205.0f, 245.1f, 2452.1f, 23.0f, 2.0f};

        // 3) Insert each stock
        for (int i = 0; i < stockIds.length; i++) {
            stockManager.addStock(stockIds[i], timestamps[i], prices[i]);
            System.out.println("Added stock: " + stockIds[i]
                    + " (timestamp=" + timestamps[i]
                    + ", price=" + prices[i] + ")");
        }

        System.out.println("\nAll stocks have been added.\n");

        // 4) Test multiple updates to a single stock
        String targetStock = "C";
        System.out.println("Testing multiple updates to stock: " + targetStock);

        // Apply updates to stock "C"
        float[] priceChanges = {500f, -200f, 100f, 300f, -50f};
        long[] updateTimestamps = {8000L, 9000L, 10000L, 11000L, 12000L};
        for (int i = 0; i < priceChanges.length; i++) {
            stockManager.updateStock(targetStock, updateTimestamps[i], priceChanges[i]);
            System.out.println("Updated stock " + targetStock + " with price change " + priceChanges[i]
                    + " at timestamp " + updateTimestamps[i]);
        }

        // Check the updated price of stock "C"
        try {
            Float updatedPrice = stockManager.getStockPrice(targetStock);
            System.out.println("Updated price of stock " + targetStock + ": " + updatedPrice);
        } catch (Exception e) {
            System.out.println("Error fetching updated price for stock " + targetStock + ": " + e.getMessage());
        }

        System.out.println("\nTesting timestamp removal for stock: " + targetStock);

        // Remove some timestamps from stock "C" and verify the updated sum
        long[] timestampsToRemove = {3000L, 8000L, 9000L, 10000L, 11000L, 12000L};
        for (long ts : timestampsToRemove) {
            try {
                stockManager.removeStockTimestamp(targetStock, ts);
                System.out.println("Removed timestamp " + ts + " from stock " + targetStock);
            } catch (Exception e) {
                System.out.println("Error removing timestamp " + ts + ": " + e.getMessage());
            }
        }

        // Check the price of stock "C" after timestamp removal
        try {
            Float updatedPriceAfterRemoval = stockManager.getStockPrice(targetStock);
            System.out.println("Price of stock " + targetStock + " after timestamp removal: " + updatedPriceAfterRemoval);
        } catch (Exception e) {
            System.out.println("Error fetching updated price for stock " + targetStock + ": " + e.getMessage());
        }

        System.out.println("\nFinal Verification of All Stocks:\n");

        // Verify the final state of all stocks
        for (String stockId : stockIds) {
            try {
                Float price = stockManager.getStockPrice(stockId);
                System.out.println("Final price of stock " + stockId + ": " + price);
            } catch (Exception e) {
                System.out.println("Error fetching price for stock " + stockId + ": " + e.getMessage());
            }
        }

        System.out.println("\nDone with comprehensive testing of the StockManager.");
    }
}

    /*
    public static void main(String[] args) {

        ArrayList<String> stockIds = new ArrayList<>();
        stockIds.add("ABCDEF");
        stockIds.add("GHIJKL");
        stockIds.add("MNOPQR");
        stockIds.add("STUVWX");
        stockIds.add("YZABCD");
        stockIds.add("EFGHIJ");
        stockIds.add("KLMNOP");
        stockIds.add("QRSTUV");
        stockIds.add("WXYZAB");
        stockIds.add("CDEFGH");

        ArrayList<Float> prices = new ArrayList<>();
        prices.add(123.45f);
        prices.add(67.89f);
        prices.add(45.67f);
        prices.add(234.56f);
        prices.add(89.01f);
        prices.add(150.34f);
        prices.add(98.76f);
        prices.add(210.45f);
        prices.add(115.99f);
        prices.add(300.12f);

        ArrayList<Long> initialTimestamps = new ArrayList<>();
        initialTimestamps.add(1708647300000L);
        initialTimestamps.add(1708650900000L);
        initialTimestamps.add(1708654500000L);
        initialTimestamps.add(1708658100000L);
        initialTimestamps.add(1708661700000L);
        initialTimestamps.add(1708665300000L);
        initialTimestamps.add(1708668900000L);
        initialTimestamps.add(1708672500000L);
        initialTimestamps.add(1708676100000L);
        initialTimestamps.add(1708679700000L);


        StockManager stockmanger = new StockManager();
        stockmanger.initStocks();
        boolean expression = false;
        for (int i = 0; i < stockIds.size(); i++) {
            stockmanger.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
            expression = stockmanger.getStockPrice(stockIds.get(i)).equals(prices.get(i));
            Assert(expression);
        }
        for (int i = 0; i < 3; i++) {
            String stockIdToRemove = stockIds.remove(0);
            prices.remove(0);
            initialTimestamps.remove(0);
            stockmanger.removeStock(stockIdToRemove);
            try{
                stockmanger.getStockPrice(stockIdToRemove) ;
                System.out.println("Test failed: Exception was not thrown");
            }
            catch (IllegalArgumentException e) {
                System.out.println("Test passed: Exception was thrown as expected");
            }
            catch (Exception e) {
                System.out.println("Test failed: An unexpected exception was thrown");
            }
            Assert(expression);
            System.out.println("Removed Stock: " + stockIdToRemove);

        }
        Map<String, ArrayList<Map.Entry<Long, Float>>> additionalData = new HashMap<>();

        // Adding additional timestamps and price changes based on original timestamps
        ArrayList<Map.Entry<Long, Float>> data1 = new ArrayList<>();
        data1.add(Map.entry(initialTimestamps.get(0) + 60000L, 5.67f)); // Add 1 minute
        data1.add(Map.entry(initialTimestamps.get(0) + 120000L, -3.21f)); // Add 2 minutes
        additionalData.put("STUVWX", data1);

        ArrayList<Map.Entry<Long, Float>> data2 = new ArrayList<>();
        data2.add(Map.entry(initialTimestamps.get(1) + 300000L, 7.89f)); // Add 5 minutes
        additionalData.put("YZABCD", data2);

        ArrayList<Map.Entry<Long, Float>> data3 = new ArrayList<>();
        data3.add(Map.entry(initialTimestamps.get(2) + 60000L, -2.34f)); // Add 1 minute
        data3.add(Map.entry(initialTimestamps.get(2) + 180000L, 4.56f)); // Add 3 minutes
        data3.add(Map.entry(initialTimestamps.get(2) + 300000L, -1.78f)); // Add 5 minutes
        additionalData.put("EFGHIJ", data3);

        for (int i = 0; i < 3; i++){
            String StockId = stockIds.get(i);
            ArrayList<Map.Entry<Long, Float>> data = additionalData.get(StockId);
            for (Map.Entry<Long, Float> entry : data){
                stockmanger.updateStock(StockId,entry.getKey() ,entry.getValue());
            }

        }
        expression = stockmanger.getStockPrice("STUVWX") == 237.02f;
        Assert(expression);
        expression = stockmanger.getStockPrice("YZABCD") == 96.9f;
        Assert(expression);
        expression = stockmanger.getStockPrice("EFGHIJ") == 150.78f;
        Assert(expression);


        for (String stockId : stockIds) {
            double price = stockmanger.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }
        if (additionalData.containsKey("STUVWX")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("STUVWX");
            stockmanger.removeStockTimestamp("STUVWX", stockData.get(1).getKey());
            stockData.remove(1);
        }
        if (additionalData.containsKey("EFGHIJ")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("EFGHIJ");
            stockmanger.removeStockTimestamp("EFGHIJ", stockData.get(2).getKey());
            stockmanger.removeStockTimestamp("EFGHIJ", stockData.get(0).getKey());
        }
        for (String stockId : stockIds) {
            double price = stockmanger.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }
        expression = stockmanger.getStockPrice("STUVWX") == 240.23f;
        Assert(expression);
        expression = stockmanger.getStockPrice("EFGHIJ") == 154.9f;
        Assert(expression);
        Float price1 = 10f;
        Float price2 = 30f;
        int stockamount = stockmanger.getAmountStocksInPriceRange(price1, price2);
        expression =  stockamount == 0;
        Assert(expression);
        System.out.println(stockmanger.getStocksInPriceRange(price1, price2));
        price1 = 50f;
        price2 = 170f;
        stockamount = stockmanger.getAmountStocksInPriceRange(price1, price2);
        expression = stockamount == 4;
        Assert(expression);
        System.out.println(stockamount);
        String[] stocksInRange = stockmanger.getStocksInPriceRange(price1, price2);
        expression = stocksInRange[0] == "YZABCD";
        Assert(expression);
        expression = stocksInRange[1] == "KLMNOP";
        Assert(expression);
        expression = stocksInRange[2] == "WXYZAB";
        Assert(expression);
        expression = stocksInRange[3] == "EFGHIJ";
        Assert(expression);
        for (String stock : stocksInRange) {
            System.out.println(stock);
        }
    }
    public static void Assert(boolean expression){
        if (!expression){
            throw new AssertionError();
        }
    }


}

     */
