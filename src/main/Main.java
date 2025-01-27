package main;
import java.util.*;

public class Main {

    public static void main(String[] args){
        supercalifragilisticexpialidociousTest();
    }

    public static void supercalifragilisticexpialidociousTest() {
        ArrayList<String> stockIds = new ArrayList<>();
        stockIds.add("A");
        stockIds.add("B");
        stockIds.add("C");
        stockIds.add("D");
        stockIds.add("E");
        stockIds.add("F");
        stockIds.add("G");
        stockIds.add("H");
        stockIds.add("I");
        stockIds.add("J");
        stockIds.add("Yoav");
        stockIds.add("Yuval");

        ArrayList<Float> prices = new ArrayList<>();
        prices.add(123f);
        prices.add(456f);
        prices.add(746f);
        prices.add(974f);
        prices.add(352f);
        prices.add(723f);
        prices.add(111f);
        prices.add(555f);
        prices.add(987f);
        prices.add(500f);
        prices.add(746f);
        prices.add(987f);


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
        initialTimestamps.add(1708661700000L);
        initialTimestamps.add(1708661700000L);

        StockManager stockManager = new StockManager();
        stockManager.initStocks();

        System.out.println("\n*************************************************************************************");
        System.out.println();
        System.out.println("Test 1 - Insertion:");

        for (int i = 0; i < stockIds.size() - 2; i++) {
            stockManager.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
        }

        System.out.println("YOUR OUTPUT:");
        String result[] = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));

        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);
        System.out.println();

        System.out.println("Test 1.1 - Trying to insert an existing stock..."); //by stockID
        try {
            stockManager.addStock("A", 1708661700000L, 150f);
            System.out.println("YOUR OUTPUT:");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException");

        System.out.println();

        System.out.println("Test 1.2 - Trying to insert a new stock with an existing price in system..."); //by stockID
        stockManager.addStock(stockIds.get(10), 1708661700000L, prices.get(10));
        System.out.println("YOUR OUTPUT:");
        String result2[] = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result2));

        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);
        System.out.println("Explanation: Yoav.price = F.price but Yoav > F");
        System.out.println("\n");


        System.out.println("Test 1.4 - Trying to insert a new stock with max existing price in system...");
        float max = max(prices);
        stockManager.addStock(stockIds.get(11), 1708661700000L, max);
        System.out.println("YOUR OUTPUT:");
        String result3[] = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result3));

        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);
        System.out.println("Explanation: Yuval.price = I.price but Yuval > I");
        System.out.println("\n");

        System.out.println("Test 1.5 - Trying to insert a new stock a with negative price...");
        try {
            stockManager.addStock("ADI", 1708661700000L, -10f);
            System.out.println("YOUR OUTPUT:");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException");

        System.out.println("Test 1.6 - Trying to insert a new stock a with 0 as price...");
        try {
            stockManager.addStock("ADI", 1708661700000L, 0f);
            System.out.println("YOUR OUTPUT:");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException");

        System.out.println("\n*************************************************************************************");

        System.out.println();
        System.out.println("Test 2 - Deletion:");

        System.out.println("Removing A...");
        stockManager.removeStock("A");
        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);


        System.out.println("\nTest 2.1 - Removing A again...");
        try{
            stockManager.removeStock("A");
            System.out.println("YOUR OUTPUT:");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException");

        System.out.println("\nTest 2.2 - Removing C...");
        System.out.println("");
        stockManager.removeStock("C");
        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);

        System.out.println("\nTest 2.3 - Removing G...");
        stockManager.removeStock("G");
        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);

        System.out.println("\nTest 2.4 - Removing J...");
        stockManager.removeStock("J");
        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);

        System.out.println("Adding A,C,G,J back...");
        int[] arr = {0,2,6,9};
        for (int j = 0; j < 4; j++) {
            stockManager.addStock(stockIds.get(arr[j]), initialTimestamps.get(arr[j]), prices.get(arr[j]));
        }
        System.out.println();

        System.out.println("\nTest 2.5 - Removing non existing stock...");
        System.out.println("YOUR OUTPUT:");
        try{
            stockManager.removeStock("Mika <3");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException");

        System.out.println("*************************************************************************************");


        System.out.println();
        System.out.println("Test 3 - Update:");

        for (int i = 0; i < stockIds.size(); i++) {
            stockManager.updateStock(stockIds.get(i), initialTimestamps.get(i) + 1l, i + 1f);
        }

        for (int i = 0; i < stockIds.size(); i++) {
            stockManager.updateStock(stockIds.get(i), initialTimestamps.get(i) + 2l, i + 4f);
        }

        stockManager.updateStock(stockIds.get(1), initialTimestamps.get(1) + 3l, 200f);
        stockManager.updateStock(stockIds.get(8), initialTimestamps.get(8) + 3l, -100f);
        stockManager.updateStock(stockIds.get(6), initialTimestamps.get(6) + 3l, 300f);


        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));

        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager,stockIds);
        System.out.println();


        System.out.println("Test 3.1 - Trying to add change with 0");
        try{
            stockManager.updateStock(stockIds.get(1), initialTimestamps.get(1) + 3l, 0f);
            System.out.println("YOUR OUTPUT:");
        }catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("DESIRED OUTPUT:");
        System.out.println("java.lang.IllegalArgumentException\n");


        System.out.println("*************************************************************************************");



        System.out.println();
        System.out.println("Test 4 - Removing Timestamp:");

        System.out.println("Removing Timestamp for Stock B:");
        System.out.println("Your current price of B: " + stockManager.getStockPrice("B")); //FROM LINE 126
        System.out.println("Desired current price of B: 663.0");
        stockManager.removeStockTimestamp("B", initialTimestamps.get(1) + 3l);
        System.out.println("Your current price of B after timestamp remove: " + stockManager.getStockPrice("B"));
        System.out.println("Desired current price of B timestamp remove: 463.0 \n");

        System.out.println("YOUR OUTPUT:");
        result = stockManager.getStocksInPriceRange(0f, 2000f);
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        printInOrder(stockManager, stockIds);
        System.out.println();

        System.out.println("*************************************************************************************");

        System.out.println();
        System.out.println("Test 5 - Stocks in Range:");

        System.out.println("Printing the prices...");

        printInOrderWithPrices(stockManager,stockIds);
        System.out.println();

        System.out.println("Stocks in Range [100, 800]:");
        result = stockManager.getStocksInPriceRange(100f, 800f);
        System.out.println("YOUR OUTPUT:");
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        System.out.println("[A, E, G, B, J, H, F, C, Yoav]\n");

        System.out.println("Stocks in Range [574, 574]:");
        result = stockManager.getStocksInPriceRange(574f, 574f);
        System.out.println("YOUR OUTPUT:");
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        System.out.println("[H]\n");

        System.out.println("Stocks in Range [1, 3]:");
        result = stockManager.getStocksInPriceRange(1f, 3f);
        System.out.println("YOUR OUTPUT:");
        System.out.println(Arrays.toString(result));
        System.out.println("DESIRED OUTPUT:");
        System.out.println("[]\n");

        System.out.println("Stocks in Range [3, 1]:");
        try{
            result = stockManager.getStocksInPriceRange(3f, 1f);
        }catch (IllegalArgumentException e) {
            System.out.println("YOUR OUTPUT:");
            System.out.println(e);
            System.out.println("DESIRED OUTPUT:");
            System.out.println("java.lang.IllegalArgumentException");
        }

        System.out.println("\n*************************************************************************************\n");

        System.out.println("Test 6 - Invalid Operations:\n");

        System.out.println();
        System.out.println("Trying to update non-existent stock:");
        try {
            stockManager.updateStock("Z", 1708650900000L, 50f);
        } catch (IllegalArgumentException e) {
            System.out.println("YOUR OUTPUT:");
            System.out.println(e);
            System.out.println("DESIRED OUTPUT:");
            System.out.println("java.lang.IllegalArgumentException\n");
        }

        System.out.println();
        System.out.println("Trying to get stock price of non-existent stock:");
        try {
            stockManager.getStockPrice("Z");
        } catch (IllegalArgumentException e) {
            System.out.println("YOUR OUTPUT:");
            System.out.println(e);
            System.out.println("DESIRED OUTPUT:");
            System.out.println("java.lang.IllegalArgumentException\n");
        }

        System.out.println("Trying to update time stamp that not exist...");
        try {
            stockManager.removeStockTimestamp("A", 1708654500001L);
        } catch (IllegalArgumentException e) {
            System.out.println("YOUR OUTPUT:");
            System.out.println(e);
            System.out.println("DESIRED OUTPUT:");
            System.out.println("java.lang.IllegalArgumentException");
        }

        System.out.println("\nTrying to remove the first change...");
        try{
            stockManager.removeStockTimestamp(stockIds.get(1), initialTimestamps.get(1));
        }catch (IllegalArgumentException e) {
            System.out.println("YOUR OUTPUT:");
            System.out.println(e);
            System.out.println("DESIRED OUTPUT:");
            System.out.println("java.lang.IllegalArgumentException\n");
        }


        System.out.println("*************************************************************************************");

    }

    private static float max(ArrayList<Float> prices) {
        float max = Float.MIN_VALUE;
        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i) > max) {
                max = prices.get(i);
            }
        }
        return max;
    }

    private static void printInOrderWithPrices(StockManager stockManager, ArrayList<String> stockIds) {
        // יצירת רשימה של אובייקטים שמכילים את שם המניה והמחיר שלה
        ArrayList<Map.Entry<String, Float>> stockList = new ArrayList<>();
        boolean first = true;

        for (int i = 0; i < stockIds.size(); i++) {
            try{
                Float price = stockManager.getStockPrice(stockIds.get(i));
                stockList.add(new AbstractMap.SimpleEntry<>(stockIds.get(i), price));
            }
            catch (IllegalArgumentException e) {}
        }

        // מיון הרשימה לפי המחירים (מהנמוך לגבוה)
        stockList.sort(Map.Entry.comparingByValue());

        // הדפסת המחירים בסדר הנכון
        System.out.print("[");
        for (Map.Entry<String, Float> entry : stockList) {
            if(!first){
                System.out.print(", ");
            }
            first = false;
            System.out.print("(" + entry.getKey() + " , " + entry.getValue() + ")");
        }
        System.out.println("]");
    }

    private static void printInOrder(StockManager stockManager, ArrayList<String> stockIds) {
        // יצירת רשימה של אובייקטים שמכילים את שם המניה והמחיר שלה
        boolean first = true;
        ArrayList<Map.Entry<String, Float>> stockList = new ArrayList<>();
        for (int i = 0; i < stockIds.size(); i++) {
            try{
                Float price = stockManager.getStockPrice(stockIds.get(i));
                stockList.add(new AbstractMap.SimpleEntry<>(stockIds.get(i), price));
            }
            catch (IllegalArgumentException e) {}
        }

        // מיון הרשימה לפי המחירים (מהנמוך לגבוה)
        stockList.sort(Map.Entry.comparingByValue());

        // הדפסת המחירים בסדר הנכון
        System.out.print("[");
        for (Map.Entry<String, Float> entry : stockList) {
            if(!first){
                System.out.print(", ");
            }
            first = false;
            System.out.print(entry.getKey());
        }
        System.out.println("]");
    }
}



