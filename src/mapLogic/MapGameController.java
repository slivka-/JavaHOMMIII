package mapLogic;

import GraphicsProcessing.DrawGUI;
import GraphicsProcessing.Graphics;
import ImageSelection.FolderImageBox;
import ImageSelection.ImageFolderComponent;
import ImageSelection.ImageSelectionBox;
import ImageSelection.ImageSelectionController;
import Map.*;
import Map.MapObjects.Army;
import Map.MapObjects.TerrainPassability;
import Networking.Client;
import battleScreen.BattleController;
import dataClasses.BattleResult;
import dataClasses.HeroInfo;
import dataClasses.UnitInfo;
import dataClasses.UnitType;
import testP.JoinGameWindow;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by slivka on 09.02.2016.
 */
public class MapGameController
{
    private ArrayList<HeroInfo> playersList;
    private MapGrid mainMapGrid;

    private int currentPlayerID;
    private int myPlayerID;

    private MouseListener listener;
    private boolean attack = false;
    private BattleController controller;
    private SavedMap savedMap;
    private Client serverConn;

    private HashMap<Integer, UnitInfo> enemyArmy = null;

    public MapGameController(ArrayList<HeroInfo> players, int myID, SavedMap map, Client serverConn)
    {
        myPlayerID = myID;
        //currentPlayerID = 0;
        if(players == null)
        {
            launchEditor();
        }
        else
        {
            this.playersList = players;
            this.savedMap = map;
            this.serverConn = serverConn;
            launchGame();
        }
    }

    private void launchEditor()
    {
        Graphics g = new Graphics();
        ImageSelectionBox isb = new ImageSelectionBox(g.getListImages());
        FolderImageBox fib = new FolderImageBox();
        fib.setDirectoriesNames(g.getAllDirectoriesName());
        ImageSelectionController isc = new ImageSelectionController(isb, fib);
        ImageFolderComponent imgFolders = new ImageFolderComponent(fib, isb);

        mainMapGrid = new MapGrid(g, isb);
        mainMapGrid.initializeGrid(MapSize.SMALL);


        MapEditorMenuBar menuBar = new MapEditorMenuBar(mainMapGrid,this);
        MapGridContainer mgc = new MapGridContainer(mainMapGrid);
        final DrawGUI draw = new DrawGUI(mgc, menuBar, imgFolders);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                draw.drawAndShowMap();
            }
        });
    }

    private void launchGame()
    {
        Graphics g = new Graphics();
        mainMapGrid = new MapGrid(g,this);
        mainMapGrid.initializeGrid(MapSize.SMALL);
        MapGridContainer mgc = new MapGridContainer(mainMapGrid);
        final DrawGUI draw = new DrawGUI(mgc);
        mainMapGrid.readSavedMap(savedMap);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                draw.drawAndShowMap();
            }
        });
        mainMapGrid.drawHeroes(playersList);
        this.listener = mainMapGrid.getMouseListener();
        nextTurn();
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
                mainMapGrid.readSavedMap(loadedMap);
                JOptionPane.showMessageDialog(null,"Wczytanie udane!","Wczytano",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,ex.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void drawCurrentHeroRange()
    {
        HeroInfo currentHero = playersList.get(currentPlayerID);
        mainMapGrid.drawHeroRange(currentHero.currentPosition,currentHero.heroRange,this.listener);
    }

    public void moveHeroSend(Point target)
    {
        serverConn.moveHeroSend(target);
    }

    public void moveHero(Point target)
    {
        mainMapGrid.clearHeroRange();
        HeroInfo currentPlayer = playersList.get(currentPlayerID);
        mainMapGrid.moveHero(currentPlayer,target);
        currentPlayer.currentPosition = target;
        waitForAnimation();
    }

    public void AttackUnit(Point target,HeroInfo hero)
    {
        attack = true;
        mainMapGrid.clearHeroRange();
        HeroInfo currentPlayer = playersList.get(currentPlayerID);
        mainMapGrid.moveHero(currentPlayer,target);
        currentPlayer.currentPosition = target;
        enemyArmy = hero.getArmy();
        waitForAnimation();
    }

    public void AttackUnit(Point target,Army army)
    {
        attack = true;
        mainMapGrid.clearHeroRange();
        HeroInfo currentPlayer = playersList.get(currentPlayerID);
        mainMapGrid.moveHero(currentPlayer,target);
        currentPlayer.currentPosition = target;
        enemyArmy = army.army;
        waitForAnimation();
    }


    private void waitForAnimation()
    {
        Timer timer = new Timer(20, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(playersList.get(currentPlayerID).heroDisplay.getIsMoveFinished())
                {
                    Timer t = (Timer)e.getSource();
                    t.stop();
                    if(attack)
                    {
                        attack = false;
                        LaunchBattle();
                    }
                    else
                    {
                        endOfTurn();
                    }
                }
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    public void nextTurn()
    {
        System.out.println("JA:"+myPlayerID+", A:"+currentPlayerID);
        if(currentPlayerID == myPlayerID)
        {
            this.drawCurrentHeroRange();
        }
    }

    public void setCurrentPlayer(int playerID)
    {
        this.currentPlayerID = playerID;
        nextTurn();
    }

    private void endOfTurn()
    {
        try
        {
            if(currentPlayerID == myPlayerID)
            {
                serverConn.gameMapEndOfTurn();
                System.out.println("KONCZE TURE");
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void LaunchBattle()
    {
        controller = new BattleController(1,playersList.get(currentPlayerID),enemyArmy);
        controller.BattleInit();
        Timer timer = new Timer(100, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(controller.isBattleOver)
                {
                    Timer t = (Timer)e.getSource();
                    t.stop();
                    battleEnded(controller.EndBattle());
                }
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    private void battleEnded(BattleResult result)
    {
        controller = null;
        if(result.vsAI)
        {
            if(result.winner == null)
            {
                playersList.set(currentPlayerID,result.looser);
                playersList.get(currentPlayerID).backToHomeTown();
            }
            else
            {
                playersList.set(currentPlayerID,result.winner);
                mainMapGrid.cells[result.winner.currentPosition.x][result.winner.currentPosition.y].deleteMapObject();
            }
        }
        else
        {
            //TODO: walka 2 graczy
        }
        endOfTurn();
    }
}
