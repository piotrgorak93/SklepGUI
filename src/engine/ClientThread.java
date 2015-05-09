package engine;

import gui.Main;
import rmi.rmiTestClient.MeetingClient;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ClientThread implements Runnable {
    @Override
    public void run() {
        new Main();
        new MeetingClient();
    }
}
