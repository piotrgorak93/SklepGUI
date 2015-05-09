package rmi.rmiTestServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rmi.rmiTestMeeting.IMeeting;

public class MeetingImpl extends UnicastRemoteObject implements IMeeting {
    private static final long serialVersionUID = 1L;
    private Date date = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd-MM-yyyy");

    public MeetingImpl() throws RemoteException {
    }

    public String getDate() throws RemoteException {
        return simpleDateFormat.format(date, new StringBuffer(),
                new FieldPosition(0)).toString();
    }

    public void setDate(String date) throws RemoteException {
        try {
            this.date = (Date) simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RemoteException("zly format daty! \npoprawny format: dd-MM-yyyy");
        }

    }
}
