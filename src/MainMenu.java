import mapLogic.MapGameController;
import GameWindows.HostGameWindow;
import GameWindows.JoinGameWindow;

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
    private static MapGameController mainGameController;

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
        /*
        JButton gameStart = new JButton("GRA");
        gameStart.setBounds(100, 100, 100, 50);
        gameStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: Dodać menu z wyborem gracza, pobierać graczy od innych klientów, dodawać do arrayList
                HeroInfo player1 = new HeroInfo("Zbyszek",new Loch(null));
                UnitType d1 = new UnitType("Troglodyta","troglodyte","dungeon",10,10,1);
                UnitInfo u11  = new UnitInfo(10, d1, UnitCommander.PLAYER1);
                player1.addToArmy(1, u11);
                ArrayList<HeroInfo> ar1 = new ArrayList<HeroInfo>();
                ar1.add(player1);
                mainGameController = new MapGameController(ar1);
                mainMenuFrame.setVisible(false);
                mainMenuFrame.dispose();
            }
        });
        mainMenuFrame.add(gameStart);
        */
        //HOSTOWANIE
        JButton hostGame = new JButton("HOSTUJ GRE");
        hostGame.setBounds(100, 100, 100, 50);
        hostGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                HostGameWindow hgw = new HostGameWindow(mainMenuFrame);
                hgw.show();
                mainMenuFrame.setVisible(false);
                mainMenuFrame.dispose();
            }
        });
        mainMenuFrame.add(hostGame);

        //DOLACZANIE
        JButton joinGame = new JButton("DOLACZ DO GRY");
        joinGame.setBounds(100, 200, 100, 50);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JoinGameWindow jgw = new JoinGameWindow(mainMenuFrame);
                mainMenuFrame.dispose();
                jgw.show();

            }
        });
        mainMenuFrame.add(joinGame);

        JButton editorStart = new JButton("EDYTOR MAP");
        editorStart.setBounds(300, 100, 120, 50);
        editorStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Odpala edytor map
                mainGameController = new MapGameController(null,0,null,null);
                mainMenuFrame.setVisible(false);
                mainMenuFrame.dispose();
            }
        });
        mainMenuFrame.add(editorStart);
    }
}
