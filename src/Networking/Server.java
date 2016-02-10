package Networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public class Server extends UnicastRemoteObject implements RMIServiceInterfaceServer {

    private ArrayList<RMIServiceInterfaceClient> gameClients;


    public Server() throws RemoteException {
        gameClients = new ArrayList<RMIServiceInterfaceClient>();
    }

    @Override
    public synchronized void registerClient(RMIServiceInterfaceClient client) throws RemoteException {
        gameClients.add(client);
    }

    @Override
    public synchronized void moveHero(int ID, int newX, int newY) throws RemoteException {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).moveHero(ID, newX, newY);
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).retrieveMessage(message);
        }
    }
}
