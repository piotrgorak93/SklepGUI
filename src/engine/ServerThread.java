package engine;

import rmi.rmiTestServer.MeetingServer;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ServerThread implements Runnable {

    @Override
    public void run() {
        new MeetingServer();
    }
}
