package mapLogic;

import GraphicsProcessing.DrawGUI;
import GraphicsProcessing.Graphics;
import ImageSelection.FolderImageBox;
import ImageSelection.ImageFolderComponent;
import ImageSelection.ImageSelectionBox;
import ImageSelection.ImageSelectionController;
import Map.*;
import dataClasses.HeroInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by slivka on 09.02.2016.
 */
public class MapGameController
{
    private ArrayList<HeroInfo> playersList;
    private MapGrid mainMapGrid;
    private int currentPlayerID;
    private final int myPlayerID;
    private MouseListener listener;


    public MapGameController(ArrayList<HeroInfo> players)
    {
        myPlayerID = 0;
        currentPlayerID = 0;
        if(players == null)
        {
            launchEditor();
        }
        else
        {
            this.playersList = players;
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
        loadMap();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                draw.drawAndShowMap();
            }
        });
        mainMapGrid.drawHeroes(playersList);
        this.listener = mainMapGrid.getMouseListener();
        this.drawCurrentHeroRange();
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

    public void moveHero(Point target)
    {
        mainMapGrid.moveHero(playersList.get(currentPlayerID),target);
    }
}
