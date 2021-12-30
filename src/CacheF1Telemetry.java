import java.io.*;
/**
 *
 * @author marin
 */
class CacheF1Telemetry implements Serializable
{
    private final String nomeUtente;
    private final String nomePilotaTextField;
    private final String nomeCircuitoTextField;
    private final String nomeTelemetryTypeTextField;
    private final int recordSelezionato;
    
    
    
    public CacheF1Telemetry(InterfacciaF1Telemetry interF1Telemetry) // (01)
    {
        nomeUtente = interF1Telemetry.getAreaLoginUtente().getTextFieldUser().getText();
        nomePilotaTextField = interF1Telemetry.getAreaTabellaDrivers().getTextFieldDriver().getText();
        nomeCircuitoTextField = interF1Telemetry.getAreaTabellaDrivers().getTextFieldCircuit().getText();
        nomeTelemetryTypeTextField = interF1Telemetry.getAreaTabellaDrivers().getTextFieldTelemetryType().getText();
        recordSelezionato = interF1Telemetry.getAreaTabellaDrivers().getTabDrives().getSelectionModel().getSelectedIndex();
    }
    
    
    
    public static void caricaElementiSalvatiInCache (InterfacciaF1Telemetry interF1Telemetry, String fileCache) // (02)
    {
        CacheF1Telemetry cache = caricaCacheF1(fileCache);
                
        if (cache != null)
        {
            interF1Telemetry.getAreaLoginUtente().getTextFieldUser().setText(cache.getNomeUtente());
            interF1Telemetry.getAreaTabellaDrivers().getTextFieldDriver().setText(cache.getNomePilotaTextField());
            interF1Telemetry.getAreaTabellaDrivers().getTextFieldCircuit().setText(cache.getCircuitoTextField());
            interF1Telemetry.getAreaTabellaDrivers().getTextFieldTelemetryType().setText(cache.getNomeTelemetryTypeTextField());
            interF1Telemetry.getAreaTabellaDrivers().getTabDrives().getSelectionModel().select(cache.getRecordSelezionato());
        }
    }
    
    
    
    public final static CacheF1Telemetry caricaCacheF1 (String fileCache)
    {
        
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(fileCache)))
        {
            CacheF1Telemetry c = (CacheF1Telemetry) oin.readObject();
            return c;
        }
        catch (IOException | ClassNotFoundException e)
        {
            if(fileCache.equals("cache.bin")) // (03)
            { 
                System.out.println("Primo avvio dell'applicativo, il file di cache non esiste");
            }
            else
            {      
                System.err.println(e.getMessage());
            }
        }
        
        return null;
    }
    
    
    
    public final static void salvaCacheF1 (InterfacciaF1Telemetry interF1Telemetry, String fileCache)
    {
        try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(fileCache))) 
        { 
            oout.writeObject(new CacheF1Telemetry(interF1Telemetry)); 
        } 
        catch(IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }
 
    
    
    public String getNomeUtente() {return this.nomeUtente;}
    public String getNomePilotaTextField() {return this.nomePilotaTextField;}
    public String getCircuitoTextField() {return this.nomeCircuitoTextField;}
    public String getNomeTelemetryTypeTextField() {return this.nomeTelemetryTypeTextField;}
    public int getRecordSelezionato() {return this.recordSelezionato;}   
}



/* COMMENTI

(01) 
    Costruttore della classe CacheF1Telemetry che salva gli elementi dell'interfaccia grafica che viene passata come parametro

(02) 
    Metodo che carica le informazioni salvate in cache e le inserisce al loro posto nell'interfaccia grafica

(03) 
    La prima volta che avvio l'applicazione il file di cache non esiste, e quindi arriverà un'eccezione
*/