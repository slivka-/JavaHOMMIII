package GameWindows;

import Map.SavedMap;
import Networking.Client;
import Networking.RMIServiceInterfaceServer;
import dataClasses.HeroInfo;
import mapLogic.MapGameController;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public class JoinGameWindow {

    Component parent;
    String IP;
    String name;
    public int myID;
    public ArrayList<HeroInfo> playersInfo;
    public SavedMap gameMap;
    private Client mainClient;

    public JoinGameWindow(Component parent)
    {
        this.parent = parent;
    }

    public void show() {
        IP = JOptionPane.showInputDialog(parent, "Podaj adres serwera", null);
        if (IP != null) {
            name = JOptionPane.showInputDialog(parent, "Podaj nick", null);
            connect();
        }
    }

    public void hostConnect(String hostName)
    {
        try {
            String serverURL = "rmi://localhost/HOMMGame";
            RMIServiceInterfaceServer server = (RMIServiceInterfaceServer)Naming.lookup(serverURL);
            mainClient = new Client(hostName, server,this);
            mainClient.sendMessage("test: od " + hostName);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            String serverURL = "rmi://"+IP+"/HOMMGame";
            RMIServiceInterfaceServer server = (RMIServiceInterfaceServer)Naming.lookup(serverURL);
            mainClient = new Client(name, server,this);
            mainClient.sendMessage("test: od " + name);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void showRoom() {

    }

    public void startGame()
    {
        if(playersInfo!= null && gameMap != null)
        {
            MapGameController mainGameController = new MapGameController(playersInfo, myID, gameMap,mainClient);
            try
            {
                mainClient.setGameController(mainGameController);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
