import java.util.*;

class Seller extends User {
    double earnings = 0;

    Seller(int id, String name, String password) {
        super(id, name, password);
    }

    void addEarnings(double amt) {
        earnings += amt;
    }
}
