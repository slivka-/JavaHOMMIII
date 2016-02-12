package Networking;

import Map.MapObjects.Army;
import Map.MapObjects.MapObject;
import Map.SavedMap;
import Map.Tile;
import battleScreen.BattleController;
import dataClasses.HeroInfo;
import dataClasses.MiniHeroInfo;
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
    private BattleController battle;

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

    public void attackHeroSend(Point target,Army army)
    {
        try
        {
            server.attackHero(target, army);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void attackHeroSend(Point target, MiniHeroInfo hero)
    {
        try
        {
            server.attackHero(target, hero);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void battleMoveSend(Point targetCell)
    {
        try
        {
            server.battleMoveUnit(targetCell);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void setBattleController(BattleController controller)
    {
        this.battle = controller;
    }

    public void battleEndOfTurn(int nextID)
    {
        try
        {
            server.battleEndOfTurn(nextID);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void isBattleVsAi(boolean state)
    {
        try
        {
            server.battleIsVsAi(state);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void sendCollectible(Point p, MapObject m)
    {
        try
        {
            server.collectible(p, m);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void removePlayer(int id)
    {
        try
        {
            server.removeClient(id);
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

    @Override
    public void attackUnit(Point target, Army army) throws RemoteException
    {
        controller.AttackUnit(target, army);
    }

    @Override
    public void attackUnit(Point target, MiniHeroInfo hero) throws RemoteException
    {
        controller.AttackUnit(target, hero);
    }

    @Override
    public void battleMoveUnit(Point targetCell) throws RemoteException
    {
        battle.MoveUnit(targetCell);
    }

    @Override
    public void battleSetNextPlayerID(int ID) throws RemoteException
    {
        battle.setCurrentPlayerID(ID);
    }

    @Override
    public void collectible(Point p, MapObject m) throws RemoteException
    {
        controller.CollectibleMove(p, m);
    }

    public void sendWinningMessage() throws RemoteException
    {
        controller.winEndGame();
    }

}
