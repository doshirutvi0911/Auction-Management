import java.util.*;

class Owner extends User {
    double earnings = 0;

    Owner(int id, String name, String password) {
        super(id, name, password);
    }

    void addEarnings(double amt) {
        earnings += amt;
    }
}
