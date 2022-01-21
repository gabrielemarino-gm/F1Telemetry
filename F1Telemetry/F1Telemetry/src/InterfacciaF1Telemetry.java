import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 *
 * @author gabriele
 */
public class InterfacciaF1Telemetry 
{
    private final AreaGrafico vistaGrafico;
    private final AreaTabellaDrivers vistaTabellaInsertOrSelectDrives;
    private final AreaLoginUtente vistaLoginUtente;
    
    private final Label titolo;
    
    private final VBox boxSinistra;
    private final HBox boxTabGraph;
    private final VBox boxTotale;

        
    InterfacciaF1Telemetry(ParametriDiConfigurazioneXML config, String fileCache, Stage stage) // (1)
    {
        vistaLoginUtente = new AreaLoginUtente(this, config);
        vistaTabellaInsertOrSelectDrives = new AreaTabellaDrivers(this, config, stage);
        vistaGrafico = new AreaGrafico(this, config);
        
        CacheF1Telemetry.caricaElementiSalvatiInCache(this, fileCache);
        System.out.println("Elementi nella chache caricati");
        
        boxSinistra = new VBox();
        boxSinistra.getChildren().addAll(vistaLoginUtente .getBoxTot(), vistaTabellaInsertOrSelectDrives.getBoxTabAndButton());
        
        titolo = new Label("F1 Telemetry");
        titolo.setFont(Font.font(config.font, 33));
        titolo.setStyle("-fx-font-weight: bold");
        
        boxTabGraph = new HBox();
        boxTabGraph.setAlignment(Pos.CENTER);
        boxTabGraph.setSpacing(25);
        boxTabGraph.getChildren().addAll(boxSinistra, vistaGrafico.getVBox());
        
        boxTotale = new VBox();
        boxTotale.setAlignment(Pos.CENTER);
        boxTotale.setSpacing(25);
        boxTotale.getChildren().addAll(titolo, boxTabGraph);
    }
    
    public AreaGrafico getAreaGrafico() {return vistaGrafico;}
    public AreaTabellaDrivers getAreaTabellaDrivers() {return vistaTabellaInsertOrSelectDrives;}
    public AreaLoginUtente getAreaLoginUtente() {return vistaLoginUtente;}
    public VBox getBoxTotale() {return boxTotale;}
}

/* COMMENTI

(1)
    Creo l'interfaccia utente usando le classi AreaLogin, AreaGrafico e AreaTabellaDrivers
 
*/