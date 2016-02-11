package testP;

import Map.SavedMap;
import Networking.RMIServiceInterfaceClient;
import Networking.RMIServiceInterfaceServer;
import Networking.Server;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 * Created by Irozin on 2016-02-10.
 */
public class HostGameWindow {

    Component parent;
    private JFrame mainHostFrame;
    private RMIServiceInterfaceServer server;
    private JLabel clLabel;
    private String hostName;
    private SavedMap loadedMap = null;



    public HostGameWindow(Component parent)
    {
        this.parent = parent;
    }

    public void show() {
        try {
            hostName = JOptionPane.showInputDialog(parent, "Podaj nick", null);
            initialize();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws RemoteException, MalformedURLException {
        server = new Server(this);
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind("HOMMGame", server);
        showRoom();
        JoinGameWindow jgw = new JoinGameWindow(mainHostFrame);
        jgw.hostConnect(hostName);

    }

    private void showRoom() {
        mainHostFrame = new JFrame("Serwer");
        mainHostFrame.setLayout(null);
        mainHostFrame.setSize(600,400);
        mainHostFrame.setVisible(true);
        mainHostFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        JLabel glabel = new JLabel("Gracze");
        glabel.setBounds(50,50,100,50);
        mainHostFrame.add(glabel);

        clLabel = new JLabel();
        clLabel.setVisible(true);
        clLabel.setBounds(50,100,400,200);
        mainHostFrame.add(clLabel);

        JButton loadMapButton = new JButton("Wczytaj mape");
        loadMapButton.setVisible(true);
        loadMapButton.setBounds(200,100,150,50);
        loadMapButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadMap();
                try
                {
                    server.setTownsList(loadedMap.getTownsOnMap());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        mainHostFrame.add(loadMapButton);

        JButton startGameButton = new JButton("rozpocznij gre");
        startGameButton.setVisible(true);
        startGameButton.setBounds(200,200,150,50);
        startGameButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if(loadedMap!=null)
                    {
                        server.sendMap(loadedMap);
                        server.sendPlayersInfo();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(mainHostFrame,"Najpierw wczytaj mape","MAPA",JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        mainHostFrame.add(startGameButton);


    }

    public void refreshPlayers(ArrayList<String> clientsNames)
    {

        String outputText = "<HTML>";
        for(String s : clientsNames)
        {
            outputText += s+"<br/>";
        }
        outputText+="</HTML>";
        clLabel.setText(outputText);
        clLabel.revalidate();
        clLabel.repaint();
    }

    public void loadMap()
    {
        JFileChooser loadMapDialog = new JFileChooser("D:\\");
        loadMapDialog.setFileFilter(new FileNameExtensionFilter("Map files (.jh3m)","jh3m"));
        if(loadMapDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File mapLoadFile = loadMapDialog.getSelectedFile();
                FileInputStream mapLoadInput = new FileInputStream(mapLoadFile);
                ObjectInputStream mapLoadObjectInput = new ObjectInputStream(mapLoadInput);
                SavedMap loadedMap = (SavedMap)mapLoadObjectInput.readObject();
                mapLoadObjectInput.close();
                mapLoadInput.close();
                this.loadedMap = loadedMap;
                JOptionPane.showMessageDialog(null,"Wczytanie udane!","Wczytano",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,ex.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
