import GraphicsProcessing.DrawGUI;
import GraphicsProcessing.Graphics;
import ImageSelection.FolderImageBox;
import ImageSelection.ImageFolderComponent;
import ImageSelection.ImageSelectionBox;
import ImageSelection.ImageSelectionController;
import Map.MapEditorMenuBar;
import Map.MapGrid;
import Map.MapGridContainer;
import Map.MapSize;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by slivka on 06.02.2016.
 */
public class MainMenu
{
    private static JFrame mainMenuFrame;

    public static void showMainMenu()
    {
        mainMenuFrame = new JFrame("Menu");
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setLayout(null);
        mainMenuFrame.setSize(500, 300);
        mainMenuFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        JButton gameStart = new JButton("GRA");
        gameStart.setBounds(100, 100, 100, 50);
        gameStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launchGame();
                mainMenuFrame.setVisible(false);
                mainMenuFrame.dispose();
            }
        });
        mainMenuFrame.add(gameStart);

        JButton editorStart = new JButton("EDYTOR MAP");
        editorStart.setBounds(300, 100, 120, 50);
        editorStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launchEditor();
                mainMenuFrame.setVisible(false);
                mainMenuFrame.dispose();
            }
        });
        mainMenuFrame.add(editorStart);


    }

    private static void launchEditor()
    {
        Graphics g = new Graphics();
        ImageSelectionBox isb = new ImageSelectionBox(g.getListImages());
        FolderImageBox fib = new FolderImageBox();
        fib.setDirectoriesNames(g.getAllDirectoriesName());
        ImageSelectionController isc = new ImageSelectionController(isb, fib);
        ImageFolderComponent imgFolders = new ImageFolderComponent(fib, isb);

        MapGrid map = new MapGrid(g, isb);
        map.initializeGrid(MapSize.SMALL);

        //map.initializeGrid();

        MapEditorMenuBar menuBar = new MapEditorMenuBar(map);
        MapGridContainer mgc = new MapGridContainer(map);
        final DrawGUI draw = new DrawGUI(mgc, menuBar, imgFolders);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                draw.drawAndShowMap();
            }
        });
    }

    private static void launchGame()
    {
        Graphics g = new Graphics();
        MapGrid map = new MapGrid(g);
        map.initializeGrid(MapSize.SMALL);
        MapEditorMenuBar menuBar = new MapEditorMenuBar(map);
        MapGridContainer mgc = new MapGridContainer(map);
        final DrawGUI draw = new DrawGUI(mgc, menuBar);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                draw.drawAndShowMap();
            }
        });
    }
}
