import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 *
 * @author marin
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
    private final int DRS           = 7;
    private final int TIME_0        = 8;
    private final int TIME          = 9;
    
   
    
    private final LineChart grafico;
    //private XYChart.Series puntiGrafico;
    private XYChart.Series puntiGrafico;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    
    private ArrayList<String> valoriRigheFile;  
    private static int numeroDiGrafici = 0;
    private static String colore1;
    private static String colore2;
    
    
    private static VBox box;
    
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
    
    AreaGrafico(InterfacciaF1Telemetry interF1Telemetry, ParametriDiConfigurazioneXML config)
    {
        this.interfacciaF1Telemetry = interF1Telemetry;
        this.configurazione = config;
        
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        
        grafico = new LineChart<Number,Number>(xAxis,yAxis);
        grafico.setPrefWidth(1000);
        grafico.setPrefHeight(500);
        
        //grafico.setMaxWidth(4000);
        
        box = new VBox();
        box.setAlignment(Pos.CENTER);
        // box.setMaxWidth(500);
        box.getChildren().add(grafico);
    }
    
    
    public void creaGraficoAreaGrafico(String path, String tipoGrafico)
    {
        puntiGrafico = new XYChart.Series();
        String[] values = null;
       
        leggiFileCSV(path);
        
        for (int i=0; i<valoriRigheFile.size(); i++)
        {
            values = valoriRigheFile.get(i).split(";");
            
            if (tipoGrafico.equals("speed") || tipoGrafico.equals("Speed"))
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
    
    
    
    private void stileGrafico (String tla)
    {        
        if (numeroDiGrafici == 0)
        {
            colore1 = scegliColore(tla);
            grafico.setStyle(String.format("CHART_COLOR_1: %s;", colore1)); 
        }
        else if (numeroDiGrafici == 1)
        {
            colore2 = scegliColore(tla);
            grafico.setStyle(String.format("CHART_COLOR_1: %s; CHART_COLOR_2: %s;", colore1, colore2));
        }
                
        grafico.setCreateSymbols(false);
        numeroDiGrafici++;
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
    
    
    
    public void eliminaTuttiGrafici()
    {
        grafico.getData().clear();
        numeroDiGrafici = 0;
    }
    
    
    
    public void leggiFileCSV(String filePath)
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
    
    
    
    public LineChart getLineChart()
    {
        return grafico;
    }
}
