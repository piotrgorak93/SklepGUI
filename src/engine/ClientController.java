package engine;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ClientController {
    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
    }
}
