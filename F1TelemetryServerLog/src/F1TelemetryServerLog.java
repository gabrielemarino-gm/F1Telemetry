import java.io.*;
import java.net.*;

/**
 *
 * @author marin
 */
public class F1TelemetryServerLog 
{
    private final static int PORTA              =       8080; 
    private final static String FILE_LOG        =       "log.xml", 
                                SCHEMA_LOG      =       "log.xsd"; 
    
    public static void main(String[] args) 
    {
        System.out.println("Server Log: START\n");
        
        try(ServerSocket serverSocket = new ServerSocket(PORTA, 7)) 
        { 
            while (true) 
            { 
                try(Socket socket = serverSocket.accept(); 
                    ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());) 
                {
                    String log = (String)oin.readObject(); 
                    System.out.println(log); 
                    
                    if(ValidatoreXML.valida(log, SCHEMA_LOG, false)) 
                        GestoreFile.salva(log, FILE_LOG, true); 
                }
            }
        } catch(Exception e) 
        {
            System.err.println(e.getMessage());
        }
    }
}
