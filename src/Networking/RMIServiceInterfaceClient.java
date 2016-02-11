package Networking;

import Map.SavedMap;
import Map.Tile;
import dataClasses.HeroInfo;
import mapLogic.MapGameController;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public interface RMIServiceInterfaceClient extends Remote {
    void downloadMap(SavedMap map) throws RemoteException;
    void retriveHeroInfos(ArrayList<HeroInfo> players, int myID) throws RemoteException;
    void moveHero(Point target) throws RemoteException;
    void deleteMapObject(int ID) throws RemoteException;
    void retrieveMessage(String message) throws RemoteException;
    String retriveClientName() throws RemoteException;
    void setCurrentPlayerID(int playerID) throws RemoteException;
    void setGameController(MapGameController controller) throws RemoteException;
    void gameMapEndOfTurn() throws RemoteException;
}
