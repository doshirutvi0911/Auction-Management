import java.util.*;

class Buyer extends User {
    boolean hasPaid = false;

    Buyer(int id, String name, String password) {
        super(id, name, password);
    }

    void setHasPaid(boolean status) {
        hasPaid = status;
    }
}