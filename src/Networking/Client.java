package Networking;

import Map.SavedMap;
import Map.Tile;
import dataClasses.HeroInfo;
import mapLogic.MapGameController;
import testP.JoinGameWindow;

import java.awt.*;
import java.io.Console;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public class Client extends UnicastRemoteObject implements RMIServiceInterfaceClient {

    private RMIServiceInterfaceServer server;
    private String name;
    private JoinGameWindow parentWindow;
    private MapGameController controller;

    public Client(String name, RMIServiceInterfaceServer server, JoinGameWindow parentWindow) throws RemoteException {
        this.name = name;
        this.server = server;
        this.parentWindow = parentWindow;
        server.registerClient(this);
    }

    public void sendMessage(String message) {
        try {
            server.broadcastMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void moveHeroSend(Point target)
    {
        try
        {
            server.moveHero(target);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadMap(SavedMap map) throws RemoteException {
        parentWindow.gameMap = map;
    }

    @Override
    public void retriveHeroInfos(ArrayList<HeroInfo> players, int myID) throws RemoteException
    {
        parentWindow.playersInfo = players;
        parentWindow.myID = myID;
        parentWindow.startGame();
    }

    @Override
    public void moveHero(Point target) throws RemoteException
    {
        controller.moveHero(target);
    }

    @Override
    public void deleteMapObject(int ID) throws RemoteException {

    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String retriveClientName() throws RemoteException
    {
        return name;
    }


    @Override
    public void setCurrentPlayerID(int playerID) throws RemoteException
    {
        controller.setCurrentPlayer(playerID);
    }


    public void setGameController(MapGameController controller) throws RemoteException
    {
        this.controller = controller;
    }

    @Override
    public void gameMapEndOfTurn() throws RemoteException
    {
        server.gameMapEndOfTurn();
    }
}
