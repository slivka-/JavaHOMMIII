package testP;

import Networking.RMIServiceInterfaceClient;
import Networking.RMIServiceInterfaceServer;
import Networking.Server;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
/**
 * Created by Irozin on 2016-02-10.
 */
public class HostGameWindow {

    Component parent;
    String IP;

    public HostGameWindow(Component parent)
    {
        this.parent = parent;
    }

    public void show() {
        IP = JOptionPane.showInputDialog(parent, "Podaj port", null);
        try {
            initialize();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws RemoteException, MalformedURLException {
        RMIServiceInterfaceServer server = new Server();
        Registry reg = LocateRegistry.createRegistry(Integer.parseInt(IP));
        reg.rebind("HOMMGame", server);
        System.out.println("Ciekawe serwer");
        //Naming.rebind("HOMMGame", new Server());
    }

    private void showRoom() {

    }

}
