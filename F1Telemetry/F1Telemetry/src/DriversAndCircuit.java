import javafx.beans.property.*;

/**
 *
 * @author gabriele
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
