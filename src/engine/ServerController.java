package engine;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ServerController {
    public static void main(String[] args) {
        new Thread(new ServerThread()).start();
    }
}
