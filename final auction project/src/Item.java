import java.util.*;

/* ===================== ITEM CLASS ===================== */
class Item {
    int itemId;
    String name;
    double basePrice;
    int sellerIndex;
    int highestBuyerIndex = -1;
    double highestBid = 0;
    boolean isOpen = true;
    boolean isSold = false;
    boolean isPaid = false;

    Item(int itemId, String name, double basePrice, int sellerIndex) {
        this.itemId = itemId;
        this.name = name;
        this.basePrice = basePrice;
        this.sellerIndex = sellerIndex;
    }

    void placeBid(double bid, int buyerIndex) {
        highestBid = bid;
        highestBuyerIndex = buyerIndex;
    }

    void closeAuction() {
        isOpen = false;
    }

    void setSold(boolean sold) {
        isSold = sold;
    }

    void setPaid(boolean paid) {
        isPaid = paid;
    }
}
