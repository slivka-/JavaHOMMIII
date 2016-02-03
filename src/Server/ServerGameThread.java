package Server;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by slivka on 03.02.2016.
 */
public class ServerGameThread implements Runnable
{

    private ArrayList<Socket> _clientsList;
    private int currentActiveClient;
    private Socket activeClientSocket;
    private BufferedReader gameThreadReader;
    private DataOutputStream gameThreadOutput;

    public ServerGameThread(ArrayList<Socket> clientsList)
    {
        this._clientsList = clientsList;
        this.currentActiveClient = 0;
        int i=0;
        for(Socket s : _clientsList)
        {
            try
            {
                gameThreadOutput = new DataOutputStream(s.getOutputStream());
                gameThreadOutput.writeUTF("N"+i+"\n");
                i++;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
           try
           {
               for(Socket s:_clientsList)
               {
                       gameThreadOutput = new DataOutputStream(s.getOutputStream());
                       gameThreadOutput.writeUTF(currentActiveClient+"\n");
               }
               activeClientSocket = _clientsList.get(currentActiveClient);
               gameThreadReader = new BufferedReader(new InputStreamReader(activeClientSocket.getInputStream()));
               String activeClientInput = gameThreadReader.readLine().trim();
               System.out.println(activeClientInput);
               String isNextClient = activeClientInput.split("\\|")[2];
               for(Socket s:_clientsList)
               {
                   if(s!=activeClientSocket)
                   {
                       gameThreadOutput = new DataOutputStream(s.getOutputStream());
                       gameThreadOutput.writeUTF(activeClientInput+"\n");
                   }
               }
               if(!isNextClient.equals("true"))
               {

                   if (currentActiveClient == _clientsList.size() - 1)
                   {
                       currentActiveClient = 0;
                   } else
                   {
                       currentActiveClient++;
                   }
               }
           }
           catch (Exception ex)
           {
               ex.printStackTrace();
           }
        }
    }
}
