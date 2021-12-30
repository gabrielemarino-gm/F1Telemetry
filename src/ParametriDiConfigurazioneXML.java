import com.thoughtworks.xstream.*;

/**
 *
 * @author marin
 */
class ParametriDiConfigurazioneXML 
{
    final static double LARGHEZZA_DEFAULT_FINESTRA = 970.0, 
                        ALTEZZA_DEFAULT_FINESTRA = 650.0, 
                        ALTEZZA_RIGA_DEFAULT = 30.0,
                        SPAZIATURA_DEFAULT = 10.0,
                        DIMENSIONE_LABEL_DEFAULT = 0.0,
                        DIMENSIONE_TITOLO_DEFAULT = 0.0; 
    
    final static int    NUMERO_RIGHE_DEFAULT = 16,  
                        PORTA_SERVER_LOG_DEFAULT = 6789, 
                        DIMENSIONE_FONT_DEFAULT = 12;

    final static String FONT_DEFAULT = "Arial",  
                        COLORE_SFONDO_DEFAULT = "WHITE";  
    
    final String font; 
    final double dimensioneFont; 
    final double[] dimensioni = new double[2]; 
    final double altezzaRiga;
    final double spaziatura;
    final double dimensioneLabel;
    final double dimensioneTitolo;
    final String coloreSfondo; 
    final int numeroRighe; 
    final String ipClient; 
    final String ipServerLog; 
    final int portaServerLog;
    
    public ParametriDiConfigurazioneXML(String fileXML)
    {
        if(fileXML == null || fileXML.compareTo("") == 0) 
        {
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioni[0] = LARGHEZZA_DEFAULT_FINESTRA;
            dimensioni[1] = ALTEZZA_DEFAULT_FINESTRA;
            coloreSfondo = COLORE_SFONDO_DEFAULT;
            numeroRighe = NUMERO_RIGHE_DEFAULT;
            ipClient = "127.0.0.1";
            ipServerLog = "127.0.0.1";
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            altezzaRiga = ALTEZZA_RIGA_DEFAULT;
            dimensioneTitolo = DIMENSIONE_TITOLO_DEFAULT;
            dimensioneLabel = DIMENSIONE_LABEL_DEFAULT;
            spaziatura = SPAZIATURA_DEFAULT;
            
        } 
        else 
        {
            ParametriDiConfigurazioneXML p = (ParametriDiConfigurazioneXML)creaXStream().fromXML(fileXML); 
            font = p.font;
            dimensioneFont = p.dimensioneFont;
            dimensioni[0] = p.dimensioni[0];
            dimensioni[1] = p.dimensioni[1];
            coloreSfondo = p.coloreSfondo;
            numeroRighe = p.numeroRighe;
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
            altezzaRiga = p.altezzaRiga;
            dimensioneTitolo= p.dimensioneTitolo;
            dimensioneLabel = p.dimensioneLabel;
            spaziatura = p.spaziatura;
        }
    }
    
    
    public final XStream creaXStream() 
    {
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "numeroRighe"); 
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "ipClient"); 
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "ipServerLog");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "portaServerLog");
        xs.useAttributeFor(ParametriDiConfigurazioneXML.class, "numeroRighe");
        return xs;
    }
    
    
    
    public String toString() {return creaXStream().toXML(this);}
}
