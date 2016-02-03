package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by slivka on 03.02.2016.
 */
public class ServerThread implements Runnable
{
    private ServerSocket mainServerSocket;
    private ArrayList<Socket> clientsList;

    public ServerThread(int portNo)
    {
        try
        {
            mainServerSocket = new ServerSocket(portNo);
            clientsList = new ArrayList<Socket>();
        }
        catch (IOException ex)
        {
            System.out.println("SERWER JUZ DZIALA");
            ex.printStackTrace();
        }
    }

    public int getUsersNumber()
    {
        return clientsList.size();
    }

    public ArrayList<Socket> getUsers()
    {
        return clientsList;
    }

    @Override
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                Socket clientSocket = mainServerSocket.accept();
                clientsList.add(clientSocket);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
