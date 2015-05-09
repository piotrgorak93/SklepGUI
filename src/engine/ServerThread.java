package engine;

import rmi.rmiTestServer.MeetingServer;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class ServerThread implements Runnable {

    @Override
    public void run() {
        new MeetingServer();
    }
}
