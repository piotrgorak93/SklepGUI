package engine;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminController {
    public static void main(String[] args) {
        new Thread(new AdminThread()).start();
    }
}
