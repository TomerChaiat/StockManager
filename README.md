# Stock Manager with 2-3 Tree

This project is a Java-based stock management system that uses a generic 2-3 tree data structure to efficiently manage stock data. It supports operations such as adding, updating, and removing stocks, as well as querying stocks by price range.

## Features

- **Stock Management:**  
  - Add new stocks with an initial timestamp and price.
  - Remove stocks from the system.
  - Update stock prices.
  - Remove specific timestamps from a stock's history.
  
- **Price Queries:**  
  - Retrieve the current price of a stock.
  - Count the number of stocks within a given price range.
  - Retrieve a list of stock IDs within a specified price range.
  
- **Generic 2-3 Tree Implementation:**  
  A robust, generic 2-3 tree implementation that uses custom key classes:
  - **PriceKey:** Differentiates stocks by price (and by name when prices are equal).
  - **LongKey:** Maintains timestamp and price-change data for a stock's history.
  - **StringKey:** Wraps a stock identifier.
  - **Key:** An abstract base class providing common functionality and special sentinels (infinity and minus infinity).

## Repository Structure

- **src/main/**  
  Contains all Java source files. Each file uses the package declaration `package main;`.

- **README.md**  
  This file.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Cloning the Repository

You can clone the repository using the following commands:

```bash
git clone https://github.com/TomerChaiat/EfficientDataStructure-For-StockManager.git
cd EfficientDataStructure-For-StockManager
