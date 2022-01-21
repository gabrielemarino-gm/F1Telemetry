import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 *
 * @author gabriele
 */
class AreaGrafico 
{
    private final int TLA           = 0;
    private final int SPACE         = 1;
    private final int SPEED         = 2;
    private final int GEAR          = 3;
    private final int RPM           = 4;
    private final int THROTTLE      = 5;
    private final int BRAKE         = 6;    
   
    
    private final LineChart grafico;
    //private XYChart.Series puntiGrafico;
    private XYChart.Series puntiGrafico;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    
    private ArrayList<String> valoriRigheFile;  
    private static int numeroDiGrafici = 0;
    private static String colore;
    
    private final Button createGraphButton;
    private final Button clearGraphButton;
    
    private static HBox boxPulsanti;
    private static VBox box;
    
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
 
    
    public AreaGrafico(InterfacciaF1Telemetry interF1Telemetry, ParametriDiConfigurazioneXML config)
    {
        this.interfacciaF1Telemetry = interF1Telemetry;
        this.configurazione = config;
        
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        
        grafico = new LineChart<Number,Number>(xAxis,yAxis);
        grafico.setPrefWidth(1200);
        grafico.setPrefHeight(500);
        
        createGraphButton = new Button("Create Graph");
        createGraphButton.setOnAction((ActionEvent insertEvent) -> {creaGrafico();});
        
        clearGraphButton = new Button("Clear Graph");
        clearGraphButton.setOnAction((ActionEvent insertEvent) -> {eliminaTuttiGrafici();});
        
        boxPulsanti = new HBox();
        boxPulsanti.getChildren().addAll(createGraphButton, clearGraphButton);
        boxPulsanti.setAlignment(Pos.CENTER);
        
        
        box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(grafico, boxPulsanti);
        box.setSpacing(25);
        
        colore = "";
        
        stile();
    }
    
    
    
    private void stile()
    {
        createGraphButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        clearGraphButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        
        boxPulsanti.setAlignment(Pos.CENTER);
        boxPulsanti.setSpacing(5);
    }
    
    
    
    private void creaGrafico() // (1)
    {
        String path, tipoGrafico;
        
        SerializzatoreXML.creaLog("Click sul pulsante Create Graph", configurazione);
        DriversAndCircuit selectDriver = (DriversAndCircuit) interfacciaF1Telemetry.getAreaTabellaDrivers().getTabDrives().getSelectionModel().getSelectedItem();
        path = selectDriver.getFilePath();
        tipoGrafico = selectDriver.getTelemetryType();
        
        puntiGrafico = new XYChart.Series();
        String[] values = null;
       
        leggiFileCSV(path);
        
        for (int i=0; i<valoriRigheFile.size(); i++)
        {
            values = valoriRigheFile.get(i).split(";");
            
            if (tipoGrafico.equals("speed") || tipoGrafico.equals("Speed")) // (2)
            {
                puntiGrafico.getData().add(new XYChart.Data(Double.parseDouble(values[SPACE]), Double.parseDouble(values[SPEED])));
                grafico.setTitle("Speed");
                xAxis.setLabel("Meters");
                yAxis.setLabel("km/h");
            }
            else if (tipoGrafico.equals("throttle") || tipoGrafico.equals("Throttle"))
            {
                puntiGrafico.getData().add(new XYChart.Data(Double.parseDouble(values[SPACE]), Double.parseDouble(values[THROTTLE])));
                grafico.setTitle("Throttle");
                xAxis.setLabel("Meters");
                yAxis.setLabel("%");
            }
            else if (tipoGrafico.equals("brake") || tipoGrafico.equals("Brake"))
            {
                puntiGrafico.getData().add(new XYChart.Data(Double.parseDouble(values[SPACE]), Double.parseDouble(values[BRAKE])));
                grafico.setTitle("Brake");
                xAxis.setLabel("Meters");
                yAxis.setLabel("%");
            }
            else if (tipoGrafico.equals("gear") || tipoGrafico.equals("Gear"))
            {
                puntiGrafico.getData().add(new XYChart.Data(Double.parseDouble(values[SPACE]), Double.parseDouble(values[GEAR])));
                grafico.setTitle("Gear");
                xAxis.setLabel("Meters");
                yAxis.setLabel("%");
            }
            else if (tipoGrafico.equals("rpm") || tipoGrafico.equals("RPM") || tipoGrafico.equals("Rpm"))
            {
                puntiGrafico.getData().add(new XYChart.Data(Double.parseDouble(values[SPACE]), Double.parseDouble(values[RPM])));
                grafico.setTitle("RPM");
                xAxis.setLabel("Meters");
                yAxis.setLabel("RPM");
            }
        }
        
        puntiGrafico.setName(values[TLA]);
        stileGrafico (values[TLA]);
        grafico.getData().add(puntiGrafico);
    }
    
    
    
    private void stileGrafico (String tla) // (3)
    {   
        numeroDiGrafici++;
        colore += "CHART_COLOR_" + numeroDiGrafici + ": " + scegliColore(tla) + "; ";
        grafico.setStyle(String.format(colore));
        grafico.setCreateSymbols(false);
    }
    
    
    
    private String scegliColore(String tla)
    {
        if (tla.equals("LEC"))
            return "red";
        else if (tla.equals("SAI"))
            return "yellow";
        else if (tla.equals("VER"))
            return "blue";
        else if (tla.equals("PER"))
            return "#10009e";
        else if (tla.equals("HAM"))
            return "#00b7ff";
        else if (tla.equals("BOT"))
            return "#006188";
        else if (tla.equals("NOR"))
            return "#ffd000";
        else if (tla.equals("RIC"))
            return "#ff9100ee";
        else if (tla.equals("ALO"))
            return "#0084ffee";
        else if (tla.equals("OCO"))
            return "#004b91ee";
        else if (tla.equals("VET"))
            return "#006421ee;";
        else if (tla.equals("STR"))
            return "#ff7ce3ee";
        else if (tla.equals("GIO"))
            return "#80000000";
        else if (tla.equals("RAI"))
            return "#3a0000";
        else if (tla.equals("RUS"))
            return "#0077ff;";
        else if (tla.equals("LAT"))
            return "#004594";
        else if (tla.equals("SMC"))
            return "#ffffff";
        else if (tla.equals("MAZ"))
            return "#757575";
        else if (tla.equals("GAS"))
            return "#012f6b;";
        else if (tla.equals("TSU"))
            return "#004eb4";
        else
            return "black";
    }
    
    
    
    private void eliminaTuttiGrafici() // (4)
    {
        SerializzatoreXML.creaLog("Click sul pulsante Clear Graph", configurazione);
        grafico.getData().clear();
        numeroDiGrafici = 0;
    }
    
    
    
    public void leggiFileCSV(String filePath) // (5)
    {
        File file = new File(filePath);
        valoriRigheFile = new ArrayList<String>();
        try
        {
            Scanner inputStream = new Scanner(file);
            inputStream.next();
            while(inputStream.hasNext())
            {
                String riga = inputStream.next();
                valoriRigheFile.add(riga);
            }
            
            inputStream.close();
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
    
    
    
    public VBox getVBox()
    {
        return box;
    }
}



/* COMMENTI

(1)
    Metodo utile a popolare il grafico con i dati trovati nel file specificato dall'utente

(2)
    A seconda del tipo di grafico richiesto, vanno inseriti dati diversi situati nello stesso file CSV

(3)
    Setta lo stile dei primi due grafici inseriti, a seconda del nome del Pilota

(4)
    Elimina tutte le serie inserite nel grafico

(5)
    Metodo utile a estrapolare tutti i dati dal file CSV, e a inserirli nell' ArraryList<String> valoriRigheFile
*/