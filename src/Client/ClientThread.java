package Client;

import battleScreen.BattleController;
import dataClasses.HeroInfo;
import dataClasses.UnitCommander;
import dataClasses.UnitInfo;
import dataClasses.UnitType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by slivka on 03.02.2016.
 */
public class ClientThread implements Runnable
{
    private Socket mainClientSocket;
    private InputStream mainClinetInputStream;
    private BufferedReader mainClientReader;
    private DataOutputStream mainOutputStream;
    private int myClientNumber;
    private Scanner scanner;
    private BattleController mainController;

    public ClientThread(String address, int portNo)
    {
        try
        {
            mainClientSocket = new Socket(InetAddress.getByName(address), portNo);
            mainClinetInputStream = mainClientSocket.getInputStream();
            mainClientReader = new BufferedReader(new InputStreamReader(mainClinetInputStream));
            mainOutputStream = new DataOutputStream(mainClientSocket.getOutputStream());
            scanner = new Scanner(System.in);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendMoveInfo(Point movePoint)
    {
        String moveOutput = "M|"+movePoint.x+","+movePoint.y+"|"+mainController.isMyTurn;
        try
        {
            mainOutputStream.writeUTF(moveOutput + "\n");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(mainController.isNextMoveMine())
        {
            mainController.isMyTurn = true;
        }
        else
        {
            mainController.isMyTurn = false;
        }
    }

    @Override
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                    String inputFromServer = mainClientReader.readLine().trim();
                    System.out.println(inputFromServer);
                    if(inputFromServer.charAt(0) == 'N')
                    {
                        //getArmy
                        myClientNumber = Character.getNumericValue(inputFromServer.charAt(1));

                        HeroInfo player1 = new HeroInfo();
                        HeroInfo player2 = new HeroInfo();
                        UnitType c2 = new UnitType("halberdier","halberdier","castle",10,10,5);
                        UnitType d1 = new UnitType("troglodyte","troglodyte","dungeon",10,10,1);
                        UnitType d2 = new UnitType("infernaltroglodyte","infernaltroglodyte","dungeon",10,10,2);
                        UnitInfo u2  = new UnitInfo(7, c2,UnitCommander.PLAYER1);
                        player1.addToArmy(2, u2);
                        UnitInfo u11  = new UnitInfo(15, d1,UnitCommander.PLAYER2);
                        UnitInfo u21  = new UnitInfo(10, d2,UnitCommander.PLAYER2);
                        player2.addToArmy(2, u21);
                        player2.addToArmy(1, u11);
                        if(myClientNumber == 0)
                        {
                            mainController = new BattleController(1, player1, player2, UnitCommander.PLAYER1,this);
                            mainController.isMyTurn = true;
                            mainController.BattleInit();
                        }
                        else
                        {
                            mainController = new BattleController(1, player1, player2, UnitCommander.PLAYER2,this);
                            mainController.isMyTurn = false;
                            mainController.BattleInit();
                        }
                    }
                    if(inputFromServer.length() == 1)
                    {
                        if(Integer.parseInt(inputFromServer) == myClientNumber)
                        {
                            //terazMojaTura
                            //System.out.println("czekamNAinput");
                            //String clientOutput = scanner.nextLine();
                            //mainOutputStream.writeUTF(clientOutput+"\n");
                            mainController.isMyTurn = true;

                        }
                    }
                    if(inputFromServer.charAt(0) == 'M')
                    {
                        //ruch
                        String[] coords = inputFromServer.split("\\|")[1].split("\\,");
                        Point movePoint = new Point(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
                        mainController.MoveUnit(movePoint);
                    }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
