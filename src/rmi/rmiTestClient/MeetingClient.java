package rmi.rmiTestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.rmiTestMeeting.Constatns;
import rmi.rmiTestMeeting.IMeeting;

public class MeetingClient {
    public MeetingClient(){
        main();
    }
    public void main() {
        try {
            System.setSecurityManager(new RMISecurityManager());

            Registry registry = LocateRegistry.getRegistry("localhost",
                    Constatns.RMI_REGISTRY_PORT);

            Remote remote = registry.lookup(Constatns.OBJECT_ID);

            String string = null;
            IMeeting meeting;

            if (remote instanceof IMeeting) {
                meeting = (IMeeting) remote;
                string = meeting.getDate();
                System.out.println("Data z systemu zdalnego: " + string);
                System.out.print("\n\nPodaj Date (dzien-miesiac-rok):");

                BufferedReader bis = new BufferedReader(new InputStreamReader(
                        System.in));
                String line = bis.readLine();
                meeting.setDate(line);
                System.out.println("\n\nDate zmieniono !!!!\n\n");
                System.out.println("Data z systemu zdalnego: "
                        + meeting.getDate() + "\n\n");

                bis.readLine();
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
