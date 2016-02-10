package testP;

import Networking.Client;
import Networking.RMIServiceInterfaceServer;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Irozin on 2016-02-10.
 */
public class JoinGameWindow {

    Component parent;
    String IP;
    String name;

    public JoinGameWindow(Component parent)
    {
        this.parent = parent;
    }

    public void show() {
        IP = JOptionPane.showInputDialog(parent, "Podaj adres serwera", null);
        if (IP != null) {
            name = JOptionPane.showInputDialog(parent, "Podaj nazwe użytkownika", null);
            connect();
        }
    }

    private void connect() {
        try {
            String serverURL = "rmi://"+IP+"/HOMMGame";
            RMIServiceInterfaceServer server = (RMIServiceInterfaceServer)Naming.lookup(serverURL);
            System.out.println("Ciekawe klient");
            Client c = new Client(name, server);
            c.sendMessage("test: od " + name);
            //server.broadcastMessage("test");
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
}
