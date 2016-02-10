package Networking;

import Map.Tile;

import java.io.Console;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Irozin on 2016-02-10.
 */
public class Client extends UnicastRemoteObject implements RMIServiceInterfaceClient {

    private RMIServiceInterfaceServer server;
    private String name;

    public Client(String name, RMIServiceInterfaceServer server) throws RemoteException {
        this.name = name;
        this.server = server;
        server.registerClient(this);
    }

    public void sendMessage(String message) {
        try {
            server.broadcastMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void poruszBOhaterem(int x, int y)
    {
        //server.moveHero(x, y);
    }

    @Override
    public void downloadMap(Tile[] map) throws RemoteException {
        //utwórz mapę
    }

    @Override
    public void moveHero(int ID, int newX, int newY) throws RemoteException {

    }

    @Override
    public void deleteMapObject(int ID) throws RemoteException {

    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
