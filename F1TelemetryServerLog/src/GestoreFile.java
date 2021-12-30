import java.io.*;
import java.nio.file.*;

/**
 *
 * @author marin
 */
public class GestoreFile 
{
    public static void salva(Object o, String file, boolean append) 
    { 
        try 
        {
            Files.write
            ( 
                Paths.get(file), 
                o.toString().getBytes(), 
                (append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING 
            );
        } 
        catch(IOException e) 
        {
            System.err.println("ERRORE");
            System.err.println(e.getMessage());
        }
    }
    
    
    
    public static void svuota(String file) 
    { 
        if(file.compareTo("cache.bin") == 0)
        {
            try 
            {
                Files.delete(Paths.get(file));
            } 
            catch (IOException ex) 
            {
                System.err.println(ex.getMessage());
            }
        }
        
        salva("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!-- " + file + " -->\n", file, false); 
    }
    
    
    
    public static String carica(String file) 
    { 
        try 
        {
            return new String(Files.readAllBytes(Paths.get(file))); 
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
        
        return null; 
    }
}
