import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;
            
/**
 *
 * @author marin
 */
public class F1Telemetry extends Application 
{    
    private final static String FILE_CONFIG     =  "./config.xml",
                                SCHEMA_CONFIG   =  "./config.xsd",
                                FILE_CACHE      =  "./cache.bin"; 
    private VBox boxFinale;
    private Label titolo;
    private InterfacciaF1Telemetry interfacciaF1Telemetry;
    
    @Override
    public void start(Stage stage) 
    {
        ParametriDiConfigurazioneXML config = new ParametriDiConfigurazioneXML
        (
            ValidatoreXML.valida(GestoreFile.carica(FILE_CONFIG), SCHEMA_CONFIG, false) ?
            GestoreFile.carica(FILE_CONFIG) : null 
        );
        
        SerializzatoreXML.creaLog("Apertura applicazione", config); 
        
        interfacciaF1Telemetry = new InterfacciaF1Telemetry(config, FILE_CACHE, stage); 
        
        
        stage.setOnCloseRequest
        (
            (WindowEvent we) -> 
            { 
                SerializzatoreXML.creaLog("Chiusura applicazione", config); 
                CacheF1Telemetry.salvaCacheF1(interfacciaF1Telemetry, FILE_CACHE);
            }
        );
        
        titolo = new Label("F1 Telemetry");
        titolo.setFont(Font.font(config.font, 33));
        titolo.setStyle("-fx-font-weight: bold");
       
        boxFinale = new VBox();
        boxFinale.getChildren().addAll(titolo, interfacciaF1Telemetry.getBoxTotale());
        boxFinale.setAlignment(Pos.CENTER);
        boxFinale.setStyle("-fx-background-color: " + config.coloreSfondo);
        boxFinale.setPrefWidth(1500);
        boxFinale.setPrefHeight(700);
        boxFinale.setSpacing(25);

        Group root = new Group(boxFinale);
        root.setStyle("-fx-background-color: " + config.coloreSfondo);
        Scene scene = new Scene(root, 1500, 700);
        stage.setTitle("F1 Telemetry");
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
