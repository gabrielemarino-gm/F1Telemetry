
import com.thoughtworks.xstream.*;
import javafx.beans.property.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marin
 */
public class DriversAndCircuit 
{
    private final SimpleStringProperty driver;
    private final SimpleStringProperty circuit;
    private final SimpleStringProperty telemetryType;
    private final SimpleStringProperty filePath;
    
    DriversAndCircuit(String d, String c, String tt, String fp) 
    {
        this.driver = new SimpleStringProperty(d);
        this.circuit = new SimpleStringProperty(c);
        this.telemetryType = new SimpleStringProperty(tt);
        this.filePath = new SimpleStringProperty(fp);
    }
    
    /*public final static XStream creaXStream()
    {
        XStream xs = new XStream();
        xs.useAttributeFor(DriversAndCircuit.class,"driver");
        xs.useAttributeFor(DriversAndCircuit.class,"circuit");
        xs.useAttributeFor(DriversAndCircuit.class,"telemetryType");
        xs.useAttributeFor(DriversAndCircuit.class,"filePath");
        return xs;
    }*/
    
    public String getDriver() {return driver.get();}
    public String getCircuit() {return circuit.get();}
    public String getTelemetryType() {return telemetryType.get();}
    public String getFilePath() {return filePath.get();}

    public void setDriver(String d) {
        this.driver.set(d);
    }

    public void setCircuit(String c) {
        this.circuit.set(c);
    }
    
    public void setTelemetryType(String tt) {
        this.telemetryType.set(tt);
    }
    
    public void setFilePath(String fp) {
        this.filePath.set(fp);
    }
}
