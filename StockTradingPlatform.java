import java.util.*;

class Stock {
    String name;
    double price;

    Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Transaction {
    String type;
    String stockName;
    int quantity;
    double price;

    Transaction(String type, String stockName, int quantity, double price) {
        this.type = type;
        this.stockName = stockName;
        this.quantity = quantity;
        this.price = price;
    }

    public String toString() {
        return type + " | " + stockName + " | Qty: " + quantity + " | Rs." + price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();
    double balance;

    Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    void buyStock(Stock stock, int qty) {
        double totalCost = stock.price * qty;
        if (totalCost <= balance) {
            balance -= totalCost;
            holdings.put(stock.name, holdings.getOrDefault(stock.name, 0) + qty);
            transactions.add(new Transaction("BUY", stock.name, qty, stock.price));
            System.out.println("Bought " + qty + " shares of " + stock.name);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    void sellStock(Stock stock, int qty) {
        int ownedQty = holdings.getOrDefault(stock.name, 0);
        if (ownedQty >= qty) {
            balance += stock.price * qty;
            holdings.put(stock.name, ownedQty - qty);
            transactions.add(new Transaction("SELL", stock.name, qty, stock.price));
            System.out.println("Sold " + qty + " shares of " + stock.name);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    void showPortfolio() {
        System.out.println("\nYour Portfolio:");
        for (String stock : holdings.keySet()) {
            int qty = holdings.get(stock);
            if (qty > 0) {
                System.out.println(stock + ": " + qty + " shares");
            }
        }
        System.out.println("Available Balance: Rs." + balance);
    }

    void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Stock> market = new ArrayList<>();
        market.add(new Stock("TCS", 3600));
        market.add(new Stock("INFY", 1450));
        market.add(new Stock("RELIANCE", 2800));
        market.add(new Stock("HDFC", 1700));

        Portfolio user = new Portfolio(10000); // Initial Rs.10,000 balance

        int choice;
        do {
            System.out.println("\n====== Stock Trading Platform ======");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Stocks:");
                    for (int i = 0; i < market.size(); i++) {
                        System.out.println((i + 1) + ". " + market.get(i).name + " - Rs." + market.get(i).price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock number to buy: ");
                    int buyIndex = scanner.nextInt() - 1;
                    if (buyIndex >= 0 && buyIndex < market.size()) {
                        System.out.print("Enter quantity to buy: ");
                        int buyQty = scanner.nextInt();
                        user.buyStock(market.get(buyIndex), buyQty);
                    } else {
                        System.out.println("Invalid stock selection.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock number to sell: ");
                    int sellIndex = scanner.nextInt() - 1;
                    if (sellIndex >= 0 && sellIndex < market.size()) {
                        System.out.print("Enter quantity to sell: ");
                        int sellQty = scanner.nextInt();
                        user.sellStock(market.get(sellIndex), sellQty);
                    } else {
                        System.out.println("Invalid stock selection.");
                    }
                    break;

                case 4:
                    user.showPortfolio();
                    break;

                case 5:
                    user.showTransactionHistory();
                    break;

                case 0:
                    System.out.println("Exiting... Thank you for using the Stock Trading Platform.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
