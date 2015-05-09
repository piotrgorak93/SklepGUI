package rmi.rmiTestClient;

import rmi.rmiTestMeeting.Constatns;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MeetingClient {
    public IMeeting connectToServer() {
        IMeeting meeting = null;
        try {
            System.setSecurityManager(new RMISecurityManager());

            Registry registry = LocateRegistry.getRegistry("localhost",
                    Constatns.RMI_REGISTRY_PORT);

            Remote remote = registry.lookup(Constatns.OBJECT_ID);


            if (remote instanceof IMeeting) {
                meeting = (IMeeting) remote;
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return meeting;
    }
}
