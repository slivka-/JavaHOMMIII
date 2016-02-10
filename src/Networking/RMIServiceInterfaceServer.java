package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Irozin on 2016-02-10.
 */
public interface RMIServiceInterfaceServer extends Remote {
    void registerClient(RMIServiceInterfaceClient client) throws RemoteException;
    void moveHero(int ID, int newX, int newY) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
}
