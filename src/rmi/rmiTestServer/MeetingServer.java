package rmi.rmiTestServer;

import rmi.rmiTestMeeting.Constatns;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class MeetingServer {
    public MeetingServer() {
        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main() throws Exception {
        System.setSecurityManager(new RMISecurityManager());

        MeetingImpl impl = new MeetingImpl();

        Registry registry = LocateRegistry.createRegistry(Constatns.RMI_REGISTRY_PORT);

        registry.rebind(Constatns.OBJECT_ID, impl);
        System.out.println("Serwer wystartowal !!! ");
    }
}
