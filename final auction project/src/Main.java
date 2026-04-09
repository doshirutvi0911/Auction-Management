public class Main {
    public static void main(String[] args) {
        AuctionSystem system = new AuctionSystem();
        system.items[system.itemCount++] =
                new Item(system.itemIdCounter++, "Laptop", 30000, 0);

        system.items[system.itemCount++] =
                new Item(system.itemIdCounter++, "Smartphone", 15000, 0);

        system.items[system.itemCount++] =
                new Item(system.itemIdCounter++, "Headphones", 2000, 0);
        System.out.println("WELCOME TO E-AUCTION STORE");
        system.start();

    }
}
