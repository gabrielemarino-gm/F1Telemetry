import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
            
/**
 *
 * @author gabriele
 */
public class F1Telemetry extends Application 
{    
    private final static String FILE_CONFIG     =  "./files/config.xml",
                                SCHEMA_CONFIG   =  "./files/config.xsd",
                                FILE_CACHE      =  "./files/cache.bin"; 
    private InterfacciaF1Telemetry interfacciaF1Telemetry;
    
    private VBox boxFinale;
    
    @Override
    public void start(Stage stage) 
    {
        ParametriDiConfigurazioneXML config = new ParametriDiConfigurazioneXML // (1)
        (
            ValidatoreXML.valida(GestoreFile.carica(FILE_CONFIG), SCHEMA_CONFIG, false) ?
            GestoreFile.carica(FILE_CONFIG) : null 
        );
        
        SerializzatoreXML.creaLog("Apertura applicazione", config); 
        
        interfacciaF1Telemetry = new InterfacciaF1Telemetry(config, FILE_CACHE, stage); // (2)
        
        
        stage.setOnCloseRequest // (3)
        (
            (WindowEvent we) -> 
            { 
                SerializzatoreXML.creaLog("Chiusura applicazione", config); 
                CacheF1Telemetry.salvaCacheF1(interfacciaF1Telemetry, FILE_CACHE);
            }
        );
        
        boxFinale = new VBox();
        boxFinale.getChildren().addAll(interfacciaF1Telemetry.getBoxTotale());
        boxFinale.setAlignment(Pos.CENTER);
        boxFinale.setStyle("-fx-background-color: " + config.coloreSfondo);
        boxFinale.setPrefWidth(config.dimensioniFinestra[0]);
        boxFinale.setPrefHeight(config.dimensioniFinestra[1]);
        boxFinale.setSpacing(25);

        Group root = new Group(boxFinale);
        root.setStyle("-fx-background-color: " + config.coloreSfondo);
        Scene scene = new Scene(root, config.dimensioniFinestra[0], config.dimensioniFinestra[1]);
        stage.setTitle("F1 Telemetry");
        
        stage.getIcons().add(new Image("file:../../files/icon.png"));
        stage.setScene(scene);
        stage.show();
    }
}


/* COMMENTI

(1)
    Creo l'oggetto ParametriDiConfigurazioneXML a partire dal file config.xml, che viene caricato solo se valido.

(2)
    Creo l'interfaccia utente

(3)
    Alla chiusura dell'applicativo devo inviare l'evento al server di Log, e salvare in cache

*/