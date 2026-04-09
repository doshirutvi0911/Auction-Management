import java.sql.SQLOutput;
import java.util.*;

/* ===================== AUCTION SYSTEM ===================== */
public class AuctionSystem {

    Scanner sc = new Scanner(System.in);

    Owner[] owners = new Owner[1];
    Seller[] sellers = new Seller[1];
    Buyer[] buyers = new Buyer[20];
    Item[] items = new Item[20];

    int buyerCount = 0;
    int itemCount = 0;
    int loggedBuyerIndex = -1;
    int buyerIdCounter = 1;
    int itemIdCounter = 1;


    void start() {
        while (true) {
            System.out.println("\n===== ENTER YOUR IDENTITY =====");
            System.out.println("1. Owner");
            System.out.println("2. Seller");
            System.out.println("3. Buyer");
            System.out.println("4. Exit");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: ownerSection(); break;
                case 2: sellerSection(); break;
                case 3: buyerSection(); break;
                case 4:
                    System.out.println("THANKS FOR VISITING OUR STORE...");
                    System.out.println("VISIT AGAIN.");
                    return;
                default: System.out.println("Invalid Choice");
            }
        }
    }

    boolean strongPassword(String p) {
        boolean letter = false, digit = false, special = false;
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (Character.isLetter(c)) letter = true;
            else if (Character.isDigit(c)) digit = true;
            else if (c=='@'||c=='#'||c=='$'||c=='_'||c=='&'||c=='*'||c=='^') special = true;
        }
        return letter && digit && special;
    }

    /* ================= OWNER ================= */
    void ownerSection() {
        if (owners[0] == null) ownerRegister();
        ownerLogin();
        ownerMenu();
    }

    void ownerRegister() {
        sc.nextLine();
        System.out.println("Register Owner");
        System.out.print("Owner Name: ");
        String name = sc.nextLine();
        String pass;
        do {
            System.out.print("Create Strong Password (It should contain alphabets,digits and special characters like @,#,$,_,&,*,^): ");
            pass = sc.nextLine();
        } while (!strongPassword(pass));
        owners[0] = new Owner(1, name, pass);
        System.out.println("Owner Registered Successfully");
    }

    void ownerLogin() {
        System.out.println("Press enter to login/");
        sc.nextLine();
        while (true) {
            System.out.print("Enter Password: ");
            if (owners[0].login(sc.nextLine())) break;
            System.out.println("Wrong Password");
        }
    }

    void ownerMenu() {
        while (true) {
            System.out.println("\n--- OWNER MENU ---");
            System.out.println("1. Appoint Seller to sell your item with just 5% commission");
            System.out.println("2. Add Item");
            System.out.println("3. View Items");
            System.out.println("4. View Earnings");
            System.out.println("5. View Buyers");
            System.out.println("6. Close Auction");
            System.out.println("7. Logout");

            int c = sc.nextInt();
            switch (c) {
                case 1: appointSeller(); break;
                case 2: addItem(); break;
                case 3: viewItems(); break;
                case 4: System.out.println("Owner Earnings: " + owners[0].earnings); break;
                case 5: viewBuyers(); break;
                case 6: closeAuction(); break;
                case 7: return;
                default : System.out.println("Invalid choice! Please select number from 1 to 7");
            }
        }
    }

    void appointSeller() {
        if (sellers[0] != null) {
            System.out.println("Seller is already appointed. Cannot appoint more than one seller.");
            return;
        }

        sc.nextLine();
        System.out.print("Seller Name: ");
        String name = sc.nextLine();
        String pass;
        do {
            System.out.print("Create Strong Password (It should contain alphabets,digits and special characters like @,#,$,_,&,*,^): ");
            pass = sc.nextLine();
        } while (!strongPassword(pass));
        sellers[0] = new Seller(1, name, pass);
        System.out.println("Seller Appointed Successfully");
    }

    // =================== addItem METHOD ===================
    void addItem() {
        sc.nextLine();
        System.out.println("You can add electronics items,antiques,jewellery,etc");
        System.out.print("Item Name: ");
        String name = sc.nextLine();

        // Check for duplicate item name
        for (int i = 0; i < itemCount; i++) {
            if (items[i].name.equalsIgnoreCase(name)) {
                System.out.println("Item with this name already exists! Cannot add duplicate.");
                return;
            }
        }

        System.out.print("Base Price: ");
        double price = sc.nextDouble();
        items[itemCount++] = new Item(itemIdCounter++, name, price, 0);
        System.out.println("Item Added");
    }

    /* ================= SELLER ================= */
    void sellerSection() {
        if (sellers[0] == null) {
            System.out.println("No seller appointed yet.");
            return;
        }
        System.out.println("Login Seller");

        sc.nextLine();
        System.out.print("Password: ");
        if (!sellers[0].login(sc.nextLine())) {
            System.out.println("Invalid Login");
            return;
        }

        while (true) {
            System.out.println("\n--- SELLER MENU ---");
            System.out.println("1. View Items");
            System.out.println("2. View Earnings");
            System.out.println("3. View Buyers");
            System.out.println("4. Logout");

            int c = sc.nextInt();
            switch (c) {
                case 1: viewItems(); break;
                case 2: System.out.println("Seller Earnings: " + sellers[0].earnings); break;
                case 3: viewBuyers(); break;
                case 4: return;
                default : System.out.println("Invalid choice! Please select number from 1 to 4");
            }
        }
    }

    /* ================= BUYER ================= */
    void buyerSection() {
        if (buyerCount == 0) {
            System.out.println("No buyers available. Please register first.");
            buyerRegister();
            buyerLogin();
            return;
        }

        sc.nextLine();
        String response;
        while (true) {
            System.out.print("Are you a new buyer? (yes/no): ");
            response = sc.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("no")) break;
            System.out.println("Invalid input! Please enter 'yes' or 'no'.");
        }

        if (response.equals("yes")) {
            buyerRegister();
            buyerLogin();
        } else {
            buyerLogin();
        }
    }

    // =================== buyerRegister METHOD ===================
    void buyerRegister() {
        System.out.println("Register Buyer(Press Enter)");
        sc.nextLine();
        String name, pass;

        do {
            System.out.print("Name: ");
            name = sc.nextLine();
            System.out.print("Create Strong Password (It should contain alphabets,digits and special characters like @,#,$,_,&,*,^): ");
            pass = sc.nextLine();

            // Check if same name and password already exists
            boolean exists = false;
            for (int i = 0; i < buyerCount; i++) {
                if (buyers[i].name.equals(name) && buyers[i].login(pass)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Buyer with same name and password already exists! Try again.");
            } else if (!strongPassword(pass)) {
                System.out.println("Password is not strong enough. Try again.");
            } else {
                break; // valid
            }

        } while (true);

        buyers[buyerCount++] = new Buyer(buyerIdCounter++, name, pass);
        System.out.println("Registration Successful");
    }

    void buyerLogin() {
        System.out.println("Press enter to login");
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        for (int i = 0; i < buyerCount; i++) {
            if (buyers[i].name.equals(name) && buyers[i].login(pass)) {
                loggedBuyerIndex = i;
                buyerMenu();
                return;
            }
        }
        System.out.println("Invalid Login");
    }

    void buyerMenu() {
        while (true) {
            System.out.println("\n--- BUYER MENU ---");
            System.out.println("1. View Items");
            System.out.println("2. Place Bid");
            System.out.println("3. Pay");
            System.out.println("4. Logout");

            int c = sc.nextInt();
            switch (c) {
                case 1: viewItems(); break;
                case 2: placeBid(); break;
                case 3: makePayment(); break;
                case 4: return;
                default : System.out.println("Invalid choice! Please select number from 1 to 4");
            }
        }
    }

    /* ================= COMMON ================= */
    void closeAuction() {
        System.out.print("Enter Item ID to close: ");
        int id = sc.nextInt();

        for (int i = 0; i < itemCount; i++) {
            if (items[i].itemId == id) {
                items[i].closeAuction();
                if (items[i].highestBuyerIndex != -1) {
                    items[i].setSold(true);
                    Buyer b = buyers[items[i].highestBuyerIndex];
                    System.out.println("Winner Name: " + b.name);
                    System.out.println("Winner ID: " + b.id);
                    System.out.println("Winning Amount: " + items[i].highestBid);
                } else {
                    System.out.println("No bids received");
                }
                return;
            }
        }
        System.out.println("Item not found");
    }

    void placeBid() {
         if (sellers[0] == null) {
            System.out.println("No seller appointed yet.You cannot place bid until the seller is appointed.");
            return;
        }
        System.out.print("Enter Item ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Bid Amount: ");
        double amt = sc.nextDouble();

        for (int i = 0; i < itemCount; i++) {
            if (items[i].itemId == id && items[i].isOpen) {
                double minBid = Math.max(items[i].basePrice, items[i].highestBid);
                if (amt > minBid) {
                    items[i].placeBid(amt, loggedBuyerIndex);
                    System.out.println("Bid Placed Successfully");
                } else {
                    System.out.println("Bid must be greater than base price and current bid");
                }
                return;
            }
        }
        System.out.println("Item not available");
    }

    void makePayment() {
    for (int i = 0; i < itemCount; i++) {

        if (items[i].isSold && !items[i].isPaid &&
                items[i].highestBuyerIndex == loggedBuyerIndex) {

            double winAmount = items[i].highestBid;

            System.out.println("\n--- PAYMENT MENU ---");
            System.out.println("Winning Amount: ₹" + winAmount);
            System.out.println("1. Cash");
            System.out.println("2. Card");
            System.out.println("3. Online (UPI)");
            System.out.print("Select Payment Method: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1: // CASH
                    System.out.println("Cash will be collected on delivery.");
                    System.out.println("Payment Successful (Cash Mode)");
                    break;

                case 2: // CARD
                    System.out.print("Enter Card Holder Name: ");
                    String cardName = sc.nextLine();

                    System.out.print("Enter Card Number: ");
                    String cardNumber = sc.nextLine();

                    System.out.print("Enter CVV: ");
                    String cvv = sc.nextLine();

                    System.out.print("Enter Amount: ");
                    double cardAmount = sc.nextDouble();

                    if (cardAmount != winAmount) {
                        System.out.println("Entered amount does not match winning amount!");
                        return;
                    }

                    System.out.println("Payment Successful (Card)");
                    break;

                case 3: // ONLINE
                    System.out.print("Enter UPI ID: ");
                    int upi = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    double onlineAmount = sc.nextDouble();

                    if (onlineAmount != winAmount) {
                        System.out.println("Entered amount does not match winning amount!");
                        return;
                    }

                    System.out.println("Payment Successful (Online)");
                    break;

                default:
                    System.out.println("Invalid payment option!");
                    return;
            }

            // Earnings Distribution
            sellers[0].addEarnings(winAmount * 0.05);
            owners[0].addEarnings(winAmount * 0.95);

            items[i].setPaid(true);
            buyers[loggedBuyerIndex].setHasPaid(true);

            System.out.println("₹" + winAmount + " paid successfully.");
            return;
        }
    }

    System.out.println("No payment pending");
}

    void viewItems() {
        if (sellers[0] == null) {
            System.out.println("No seller appointed yet.You cannot view items.");
            return;
        }
        if (itemCount == 0) {
            System.out.println("No items available. Items not added yet.");
            return;
        }

        boolean anyOpen = false;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].isOpen) {
                anyOpen = true;
                break;
            }
        }

        if (!anyOpen) {
            System.out.println("No auctions are currently open.");
        }

        System.out.println("\nITEM LIST");
        for (int i = 0; i < itemCount; i++) {
            System.out.println("ID: " + items[i].itemId);
            System.out.println("Name: " + items[i].name);
            System.out.println("Base Price: " + items[i].basePrice);
            System.out.println("Current Bid: " +
                    (items[i].highestBuyerIndex == -1 ? "No bids" : items[i].highestBid));
            System.out.println("Status: " + (items[i].isOpen ? "OPEN" : "CLOSED"));
            System.out.println("---------------------");
        }
    }

    void viewBuyers() {
        System.out.println("\n--- BUYER LIST ---");
        System.out.println("Total Buyers: " + buyerCount);

        if (buyerCount == 0) {
            System.out.println("No buyers registered");
            return;
        }

        for (int i = 0; i < buyerCount; i++) {
            System.out.println("Buyer ID: " + buyers[i].id +
                    " | Name: " + buyers[i].name);
        }
    }
}