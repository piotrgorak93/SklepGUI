package rmi.rmiTestClient;

import engine.Item;
import rmi.rmiTestMeeting.Constatns;
import rmi.rmiTestMeeting.IMeeting;

import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MeetingClient {
    public MeetingClient() {
        clientAction();
    }

    public void clientAction() {
        try {
            System.setSecurityManager(new RMISecurityManager());

            Registry registry = LocateRegistry.getRegistry("localhost",
                    Constatns.RMI_REGISTRY_PORT);

            Remote remote = registry.lookup(Constatns.OBJECT_ID);
            IMeeting meeting;

            if (remote instanceof IMeeting) {
                meeting = (IMeeting) remote;
                ArrayList<Item> arrayList = meeting.getAllItems();
                System.out.println("Wypisuje");
                for (Item item : arrayList) {
                    System.out.println(item);
                }

            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
