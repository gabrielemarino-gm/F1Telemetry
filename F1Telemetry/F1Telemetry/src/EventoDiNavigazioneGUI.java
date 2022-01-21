import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;
/**
 *
 * @author gabriele
 */
class EventoDiNavigazioneGUI implements Serializable // (1)
{
    private final String nomeApplicazione = "F1Telemetry";
    private final String nomeEvento;
    private final String indirizzoIpClient;
    private final String dataOraCorrente;
    
    EventoDiNavigazioneGUI(String nomeEvento, String indirizzoIpClient) 
    {
        this.nomeEvento = nomeEvento;
        this.indirizzoIpClient = indirizzoIpClient;
        this.dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    }
    
    
    @Override
    public String toString() 
    { 
        XStream xs = new XStream();
        xs.useAttributeFor(EventoDiNavigazioneGUI.class,"nomeApplicazione");
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "nomeEvento"); 
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "indirizzoIpClient"); 
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "dataOraCorrente"); 
        return xs.toXML(this) + "\n"; 
    }
    
}

/* COMMENTI

(1) 
    Classe che rappresenta l'oggetto che si scambiano l'applicativo e il server di log

*/