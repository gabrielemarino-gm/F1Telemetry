import java.sql.*;
import java.util.*;

/**
 *
 * @author gabriele
 */
public class ArchivioTabellaDrivers 
{   
    private final static String FILE_CONFIG     =  "./files/config.xml",
                                SCHEMA_CONFIG   =  "./files/config.xsd";
    
    private static ParametriDiConfigurazioneXML config = new ParametriDiConfigurazioneXML 
    (
        ValidatoreXML.valida(GestoreFile.carica(FILE_CONFIG), SCHEMA_CONFIG, false) ?
        GestoreFile.carica(FILE_CONFIG) : null 
    );
    
    
    public static void aggiungiRecordUtenteDB(String utente, DriversAndCircuit d) // (1)
    {
        try( Connection co = DriverManager.getConnection(config.URLDataBase, config.userDataBase, config.passwordDataBase);  
                PreparedStatement ps = co.prepareStatement("INSERT INTO drivers_for_users(user, driver, circuit, telemetry_type, file_path) VALUES (?,?,?,?,?)");) 
        {
            ps.setString(1, utente);
            ps.setString(2, d.getDriver());
            ps.setString(3, d.getCircuit());
            ps.setString(4, d.getTelemetryType());
            ps.setString(5, d.getFilePath());
            System.out.println("Aggiungo il record alla tabella drivers_for_user:");
            System.out.println( "User: " + utente + 
                                ", Driver: " + d.getDriver() + 
                                ", Circuit: " + d.getCircuit() + 
                                ", Telemetry Type: " + d.getTelemetryType() + 
                                ", File Path: " + d.getFilePath());
            ps.execute();            
        }    
        catch (SQLException ex){System.err.println(ex.getMessage());}
    }
     
     
    
    public static void eliminaRecordUtenteDB(String utente, DriversAndCircuit d) // (2)
    {
        try(Connection co = DriverManager.getConnection(config.URLDataBase, config.userDataBase, config.passwordDataBase);  
            PreparedStatement ps = co.prepareStatement("DELETE FROM drivers_for_users WHERE user = ? AND driver = ? AND circuit = ? AND telemetry_type = ?");) 
        {
            
            ps.setString(1, utente);
            ps.setString(2, d.getDriver());
            ps.setString(3, d.getCircuit());
            ps.setString(4, d.getTelemetryType());
            System.out.println("Row affected: " + ps.executeUpdate());
            
        }    
        catch (SQLException ex){System.err.println(ex.getMessage());}
    }
     
    
    
    public static ArrayList<DriversAndCircuit> caricaTabellaDriversDB(String utente) // (3)
    {        
        ArrayList<DriversAndCircuit> piloti = new ArrayList();
        
        try( Connection co = DriverManager.getConnection(config.URLDataBase, config.userDataBase, config.passwordDataBase); 
             PreparedStatement ps = co.prepareStatement("SELECT * FROM drivers_for_users WHERE user = ?");)
        {
            ps.setString(1, utente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
                piloti.add(new DriversAndCircuit(rs.getString("driver"), rs.getString("circuit"), rs.getString("telemetry_type"), rs.getString("file_path")));
        } 
        catch (SQLException e) { System.err.println(e.getMessage());}
        
        return piloti;
    }
    
}


/* COMMENTI
(1)
    Metodo che inserisce un nuovo record alla tabella drivers_for_users nel DataBase MySQL

(2)
    Metodo che elimina il record specificato dall'oggetto DriversAndCircuit, dalla tabella drivers_for_users nel DataBase MySQL

(3)
    Ogni volta che fa login un nuovo utente, bisogna ricaricare i dati della tabella relativi all'utente loggato
*/