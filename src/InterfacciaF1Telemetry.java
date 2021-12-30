import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author marin
 */
public class InterfacciaF1Telemetry 
{
    private final AreaGrafico vistaGrafico;
    private final AreaTabellaDrivers vistaTabellaInsertOrSelectTwoDrives;
    private final AreaLoginUtente vistaLoginUtente;
    
    private final HBox boxTotale;
    private final VBox boxSinistra;

        
    InterfacciaF1Telemetry(ParametriDiConfigurazioneXML config, String fileCache, Stage stage)
    {
        vistaGrafico = new AreaGrafico(this, config);
        vistaTabellaInsertOrSelectTwoDrives = new AreaTabellaDrivers(this, config, stage);
        vistaLoginUtente = new AreaLoginUtente(this, config);
        
        CacheF1Telemetry.caricaElementiSalvatiInCache(this, fileCache);
        System.out.println("Elementi nella chache caricati");
        
        boxSinistra = new VBox();
        boxSinistra.getChildren().addAll(vistaLoginUtente.getBoxOrizzontale(), vistaTabellaInsertOrSelectTwoDrives.getBoxVerticale());
        
        boxTotale = new HBox();
        boxTotale.setAlignment(Pos.CENTER);
        boxTotale.setSpacing(25);
        boxTotale.getChildren().addAll(boxSinistra, vistaGrafico.getVBox());
    }
    
    public AreaGrafico getAreaGrafico() {return vistaGrafico;}
    public AreaTabellaDrivers getAreaTabellaDrivers() {return vistaTabellaInsertOrSelectTwoDrives;}
    public AreaLoginUtente getAreaLoginUtente() {return vistaLoginUtente;}
    public HBox getBoxTotale() {return boxTotale;}
}

