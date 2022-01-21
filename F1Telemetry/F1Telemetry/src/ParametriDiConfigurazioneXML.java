import com.thoughtworks.xstream.*;
import java.io.*;

/**
 *
 * @author marin
 */
class ParametriDiConfigurazioneXML implements Serializable
{
    final static double LARGHEZZA_DEFAULT_FINESTRA = 1700.0, 
                        ALTEZZA_DEFAULT_FINESTRA = 700.0, 
                        ALTEZZA_RIGA_DEFAULT = 30.0,
                        DIMENSIONE_TITOLO_DEFAULT = 0.0; 
    
    final static int    PORTA_SERVER_LOG_DEFAULT = 8080, 
                        DIMENSIONE_FONT_DEFAULT = 12,
                        NUMERO_RIGHE_DEFAULT = 10;

    final static String FONT_DEFAULT = "Arial",  
                        COLORE_SFONDO_DEFAULT = "WHITE",
                        URL_DATABASE_DEFAULT = "jdbc:mysql://localhost:3306/f1telemetry",
                        USER_DATABASE_DEFAULT = "root",
                        PASSWORD_DATABASE_DEFAULT = "";
                        
    
    final String font; 
    final double dimensioneFont; 
    final double[] dimensioniFinestra = new double[2]; 
    final double altezzaRiga;
    final int numeroRighe;
    final double dimensioneTitolo;
    final String coloreSfondo; 
    final String ipClient; 
    final String ipServerLog; 
    final int portaServerLog;
    final String URLDataBase;
    final String userDataBase;
    final String passwordDataBase;
    
    public ParametriDiConfigurazioneXML(String fileXML)
    {
        if(fileXML == null || fileXML.compareTo("") == 0) // (1)
        {
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioniFinestra[0] = LARGHEZZA_DEFAULT_FINESTRA;
            dimensioniFinestra[1] = ALTEZZA_DEFAULT_FINESTRA;
            coloreSfondo = COLORE_SFONDO_DEFAULT;
            numeroRighe = NUMERO_RIGHE_DEFAULT;
            ipClient = "127.0.0.1";
            ipServerLog = "127.0.0.1";
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            altezzaRiga = ALTEZZA_RIGA_DEFAULT;
            dimensioneTitolo = DIMENSIONE_TITOLO_DEFAULT;
            URLDataBase = URL_DATABASE_DEFAULT;
            userDataBase = USER_DATABASE_DEFAULT;
            passwordDataBase = PASSWORD_DATABASE_DEFAULT;
        } 
        else 
        {
            ParametriDiConfigurazioneXML p = (ParametriDiConfigurazioneXML)creaXStream().fromXML(fileXML); 
            font = p.font;
            dimensioneFont = p.dimensioneFont;
            dimensioniFinestra[0] = p.dimensioniFinestra[0];
            dimensioniFinestra[1] = p.dimensioniFinestra[1];
            coloreSfondo = p.coloreSfondo;
            numeroRighe = p.numeroRighe;
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
            altezzaRiga = p.altezzaRiga;
            dimensioneTitolo= p.dimensioneTitolo;
            URLDataBase = p.URLDataBase;
            userDataBase = p.userDataBase;
            passwordDataBase = p.passwordDataBase;
        }
    }
    
    
    public final XStream creaXStream() 
    {
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "ipClient"); 
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "ipServerLog");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "portaServerLog");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "URLDataBase");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "userDataBase");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "passwordDataBase");
        return xs;
    }
    
    public String toString() {return creaXStream().toXML(this);}
}

/* COMMENTI

(1)
    Se il file XML non ha avuto una convalida positiva, allora carico dei valori di default

*/
