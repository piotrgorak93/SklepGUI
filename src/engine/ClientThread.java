package engine;

import rmi.rmiTestClient.MeetingClient;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ClientThread implements Runnable {
    @Override
    public void run() {
//        new Main();
        new MeetingClient();
    }
}
