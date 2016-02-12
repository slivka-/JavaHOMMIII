package Networking;

import Map.MapObjects.Army;
import Map.MapObjects.MapObject;
import Map.MapObjects.Towns.Loch;
import Map.MapObjects.Towns.Town;
import Map.MapObjects.Towns.Zamek;
import Map.ReadyUnitTypes;
import Map.SavedMap;
import dataClasses.*;
import GameWindows.HostGameWindow;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public class Server extends UnicastRemoteObject implements RMIServiceInterfaceServer {

    private ArrayList<RMIServiceInterfaceClient> gameClients;
    private HostGameWindow parentWindow;
    public boolean canClientsJoin;
    public ArrayList<MapObject> towns = null;
    private int currentPlayerID = 1;
    private int endCount = 1;
    private int battleCount = 1;
    private boolean isVsAi = false;



    public Server(HostGameWindow parentWindow) throws RemoteException {
        gameClients = new ArrayList<RMIServiceInterfaceClient>();
        this.parentWindow = parentWindow;
        this.canClientsJoin = true;
    }


    private synchronized void refreshClients() throws RemoteException
    {
        int i = 0;
        ArrayList<String> output = new ArrayList<>();
        while (i < gameClients.size()) {
            output.add(gameClients.get(i++).retriveClientName());
        }
        parentWindow.refreshPlayers(output);
    }

    private synchronized void connectClientsToTowns()
    {
        if(towns != null)
        {
            if (towns.size() <= gameClients.size())
            {
                int i = 0;
                while (i < towns.size())
                {
                    i++;
                }
                while (i < gameClients.size())
                {
                    gameClients.remove(i++);
                }
                canClientsJoin = false;
            }
        }
    }

    @Override
    public synchronized void sendPlayersInfo() throws RemoteException
    {
        int i = 0;
        ArrayList<HeroInfo> players = new ArrayList<>();
        while (i < gameClients.size()) {
            HeroInfo hero = new HeroInfo(gameClients.get(i).retriveClientName(),(Town)towns.get(i),i);
            UnitType t;
            if(towns.get(i).getClass().equals(Zamek.class))
            {
                t = ReadyUnitTypes.pikeman;
            }
            else if(towns.get(i).getClass().equals(Loch.class))
            {
                t = ReadyUnitTypes.troglodyte;
            }
            else
            {
                t = ReadyUnitTypes.hobgoblin;
            }
            hero.addToArmy(1,new UnitInfo(50,t,i));
            players.add(hero);
            i++;
        }
        i = 0;
        while (i < gameClients.size())
        {
            gameClients.get(i).retriveHeroInfos(players,i);
            i++;
        }
    }

    @Override
    public synchronized void gameMapEndOfTurn() throws RemoteException
    {
        if(endCount == gameClients.size())
        {
            setCurrentPlayerID();
        }
        else
        {
            endCount++;
        }
    }

    @Override
    public void attackHero(Point target, Army army) throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).attackUnit(target, army);
        }
    }

    public void attackHero(Point target, MiniHeroInfo hero) throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).attackUnit(target, hero);
        }
    }

    @Override
    public void battleMoveUnit(Point targetCell) throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).battleMoveUnit(targetCell);
        }
    }

    @Override
    public void battleEndOfTurn(int nextID) throws RemoteException
    {

        if(isVsAi)
        {
            int i = 0;
            while (i < gameClients.size())
            {
                gameClients.get(i++).battleSetNextPlayerID(nextID);
            }
        }
        else
        {
            System.out.println("Koniec tury");
            if(battleCount == 2)
            {
                System.out.println("Nowa tura");
                int i = 0;
                while (i < gameClients.size())
                {
                    gameClients.get(i++).battleSetNextPlayerID(nextID);
                }
                battleCount = 1;
            }
            else
            {
                battleCount++;
            }
        }
    }

    @Override
    public void battleIsVsAi(boolean state) throws RemoteException
    {
        this.isVsAi = state;
    }

    @Override
    public void collectible(Point p, MapObject m) throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).collectible(p, m);
        }
    }

    @Override
    public void removeClient(int ClientID) throws RemoteException
    {
        gameClients.remove(ClientID);
        if(gameClients.size() <2)
        {
            gameClients.get(0).sendWinningMessage();
        }
    }


    public synchronized void setCurrentPlayerID() throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).setCurrentPlayerID(currentPlayerID);
        }
        if(currentPlayerID<gameClients.size()-1)
        {
            currentPlayerID++;
        }
        else
        {
            currentPlayerID = 0;
        }
        endCount = 1;
    }

    @Override
    public synchronized void sendMap(SavedMap map) throws RemoteException
    {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).downloadMap(map);
        }
    }

    @Override
    public synchronized void registerClient(RMIServiceInterfaceClient client) throws RemoteException {
        if(canClientsJoin)
        {
            gameClients.add(client);
            this.connectClientsToTowns();
            this.refreshClients();
        }
    }

    @Override
    public synchronized void moveHero(Point target) throws RemoteException {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).moveHero(target);
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < gameClients.size()) {
            gameClients.get(i++).retrieveMessage(message);
        }
    }

    @Override
    public synchronized void setTownsList(ArrayList<MapObject> towns) throws RemoteException
    {
        this.towns = towns;
        this.connectClientsToTowns();
        this.refreshClients();
    }
}
