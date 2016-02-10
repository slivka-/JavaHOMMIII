package Networking;

import Map.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Irozin on 2016-02-10.
 */
public interface RMIServiceInterfaceClient extends Remote {
    void downloadMap(Tile[] map) throws RemoteException;
    void moveHero(int ID, int newX, int newY) throws RemoteException;
    void deleteMapObject(int ID) throws RemoteException;
    void retrieveMessage(String message) throws RemoteException;
}
