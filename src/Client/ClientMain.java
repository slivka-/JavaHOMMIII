package Client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by slivka on 03.02.2016.
 */
public class ClientMain
{
    private static Executor mainClientExecutor;
    private static ClientThread mainClientThread;
    private static final int portNo = 6606;
    private static final String ip = "localhost";

    public static void main(String[] args)
    {
        mainClientExecutor = Executors.newSingleThreadExecutor();
        mainClientThread = new ClientThread(ip,portNo);
        mainClientExecutor.execute(mainClientThread);
    }
}
