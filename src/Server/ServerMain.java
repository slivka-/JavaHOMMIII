package Server;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by slivka on 03.02.2016.
 */
public class ServerMain
{
    private static Executor mainServerExecutor;
    private static ServerThread mainServerThread;
    private static ServerGameThread mainServerGameThread;
    private static final int portNo = 6606;
    private static boolean waitForClients = true;

    public static void main(String[] args)
    {
        mainServerExecutor = Executors.newCachedThreadPool();
        mainServerThread = new ServerThread(portNo);
        mainServerExecutor.execute(mainServerThread);
        refreshClients();
        try
        {
            System.in.read();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        startConnections(mainServerThread.getUsers());
    }

    public static void startConnections(ArrayList<Socket> clientsList)
    {
        mainServerGameThread = new ServerGameThread(clientsList);
        mainServerExecutor.execute(mainServerGameThread);
    }

    private static void refreshClients()
    {
        Timer timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!waitForClients)
                {
                    System.out.println(mainServerThread.getUsersNumber());
                    Timer t = (Timer) e.getSource();
                    t.stop();
                }


            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }


}
