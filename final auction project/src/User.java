import java.util.*;
/* ===================== USER CLASSES ===================== */
class User {
    int id;
    String name;
    String password;

    User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    boolean login(String pass) {
        return password.equals(pass);
    }
}
