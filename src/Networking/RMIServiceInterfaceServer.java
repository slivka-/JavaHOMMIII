package Networking;

import Map.MapObjects.MapObject;
import Map.SavedMap;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public interface RMIServiceInterfaceServer extends Remote {
    void registerClient(RMIServiceInterfaceClient client) throws RemoteException;
    void moveHero(Point target) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
    void setTownsList(ArrayList<MapObject> towns) throws RemoteException;
    void sendMap(SavedMap map) throws RemoteException;
    void sendPlayersInfo() throws RemoteException;
    void gameMapEndOfTurn() throws RemoteException;
}
