import java.io.*;
import java.nio.file.*;
import java.util.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;


/**
 *
 * @author gabriele
 */
class AreaTabellaDrivers 
{

    private final Label titoloTabDrivers;
    private final TableView<DriversAndCircuit> tabDrivers = new TableView<>();
    private ObservableList<DriversAndCircuit> olDrivers;
    private final TableColumn colonnaDrivers;
    private final TableColumn colonnaCircuit;
    private final TableColumn colonnaTelemetryType;
    private final TableColumn colonnaFilePath;
    private final Button deleteButton;
    private final Button insertCSVButton;
    private final TextField inserisciDrivers;
    private final TextField inserisciCircuit;
    private final TextField inserisciTelemetryType;
    private final FileChooser fileChooser;
    private final HBox boxInserimentoRecord;
    private final HBox boxPulsanti;
    private final VBox boxTabAndButton;
    
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
    private final Stage stage;
    
    private String ultimoFilePath;
    
    
    AreaTabellaDrivers(InterfacciaF1Telemetry interF1Telemetry, ParametriDiConfigurazioneXML config, Stage st)
    {
        this.interfacciaF1Telemetry = interF1Telemetry;
        this.configurazione = config;
        this.stage = st;
        
        olDrivers = FXCollections.observableArrayList();
        
        tabDrivers.setItems(olDrivers);
        
        titoloTabDrivers = new Label("Insert Or Select Same Drivers");
        
        colonnaDrivers = new TableColumn("DRIVER");
        colonnaDrivers.setCellValueFactory(new PropertyValueFactory<>("driver"));
        
        colonnaCircuit = new TableColumn("CIRCUIT");
        colonnaCircuit.setCellValueFactory(new PropertyValueFactory<>("circuit"));
        
        colonnaTelemetryType = new TableColumn("TYPE");
        colonnaTelemetryType.setCellValueFactory(new PropertyValueFactory<>("telemetryType"));
        
        colonnaFilePath = new TableColumn("FILE PATH");
        colonnaFilePath.setCellValueFactory(new PropertyValueFactory<>("filePath"));
                
        tabDrivers.getColumns().addAll(colonnaDrivers, colonnaCircuit, colonnaTelemetryType, colonnaFilePath); 
        
        deleteButton = new Button("Delete Row");
        deleteButton.setOnAction((ActionEvent insertEvent) -> {eliminaRiga();});
        
        insertCSVButton = new Button("Add File CSV");
        insertCSVButton.setOnAction((ActionEvent insertEvent) -> {inserisciFileCSV(); inserisciRigaTextField();});
        
        inserisciDrivers = new TextField();
        inserisciCircuit = new TextField();
        inserisciTelemetryType = new TextField();
        
        boxInserimentoRecord = new HBox();
        boxInserimentoRecord.getChildren().addAll(inserisciDrivers, inserisciCircuit, inserisciTelemetryType);
        
        boxPulsanti = new HBox();
        boxPulsanti.getChildren().addAll(insertCSVButton, deleteButton);        
        
        boxTabAndButton = new VBox();
        boxTabAndButton.getChildren().addAll(titoloTabDrivers, tabDrivers, boxInserimentoRecord, boxPulsanti);
        
        
        fileChooser = new FileChooser();
        ultimoFilePath = " ";
        stile();
    }
    
    

    private void stile() 
    {
        titoloTabDrivers.setFont(Font.font(configurazione.font, configurazione.dimensioneFont + configurazione.dimensioneTitolo));
        
        tabDrivers.setFixedCellSize(configurazione.altezzaRiga);
        tabDrivers.prefHeightProperty().set((configurazione.numeroRighe + 1) * configurazione.altezzaRiga);
        
        colonnaDrivers.setMinWidth(82);
        colonnaCircuit.setMinWidth(82);
        colonnaTelemetryType.setMinWidth(82);
        colonnaFilePath.setMinWidth(82);
        
        deleteButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        insertCSVButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        
        inserisciDrivers.setPrefWidth(110);
        inserisciDrivers.setPromptText("Driver");
       
        inserisciCircuit.setPrefWidth(110);
        inserisciCircuit.setPromptText("Circuit");
        
        inserisciTelemetryType.setPrefWidth(110);
        inserisciTelemetryType.setPromptText("Type");     
        
        boxInserimentoRecord.setAlignment(Pos.CENTER);
        
        boxPulsanti.setAlignment(Pos.CENTER);
        boxPulsanti.setSpacing(5);
                
        boxTabAndButton.setAlignment(Pos.CENTER);
        boxTabAndButton.setSpacing(5);
    }
    
    
    
    private void inserisciFileCSV() // (1)
    {
        SerializzatoreXML.creaLog("Click sul pulsante Insert CSV file", configurazione);
        fileChooser.setTitle("Insert File CSV");
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        fileChooser.getExtensionFilters().addAll
        (
            new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
            
        File fileCSV = fileChooser.showOpenDialog(stage);
        ultimoFilePath = fileCSV.getPath();
        System.out.println("PATH: " + ultimoFilePath);
    }
    
    
    
    private void inserisciRigaTextField() // (2)
    {
        DriversAndCircuit row = new DriversAndCircuit
        (
            inserisciDrivers.getText(),
            inserisciCircuit.getText(),
            inserisciTelemetryType.getText(),
            ultimoFilePath
        );
        
        olDrivers.add(row);
        ArchivioTabellaDrivers.aggiungiRecordUtenteDB(interfacciaF1Telemetry.getAreaLoginUtente().getLabelName().getText(), row);
        
        
        inserisciDrivers.clear();
        inserisciCircuit.clear();
        inserisciTelemetryType.clear();    
    }
    
    
    
    public void inserisciRigaDB(String d, String c, String tt, String fp)
    {
        olDrivers.add(new DriversAndCircuit(d, c, tt, fp));
    }
    
    
    
    private void eliminaRiga() // (3)
    {
        SerializzatoreXML.creaLog("Click sul pulsante Delete", configurazione);
        DriversAndCircuit selectDriver = tabDrivers.getSelectionModel().getSelectedItem();
        int i = tabDrivers.getItems().indexOf(selectDriver);
        ArchivioTabellaDrivers.eliminaRecordUtenteDB(interfacciaF1Telemetry.getAreaLoginUtente().getLabelName().getText(), selectDriver);        
        
        eliminaTutteLeRighe();
        ArrayList<DriversAndCircuit> piloti = ArchivioTabellaDrivers.caricaTabellaDriversDB(interfacciaF1Telemetry.getAreaLoginUtente().getLabelName().getText());
        
        for (int k=0; k<piloti.size(); k++)
        {
            inserisciRigaDB
            (
                piloti.get(k).getDriver(),
                piloti.get(k).getCircuit(),
                piloti.get(k).getTelemetryType(),
                piloti.get(k).getFilePath()
            );
        }
    }
    
    
    
    
    public void eliminaTutteLeRighe() // (4)
    {
        olDrivers.clear();
    }

    
    
    public VBox getBoxTabAndButton()
    {
        return boxTabAndButton;
    }
    
    public TextField getTextFieldDriver()
    {
        return inserisciDrivers;
    }
    
    public TextField getTextFieldCircuit()
    {
        return inserisciCircuit;
    }
    
    public TextField getTextFieldTelemetryType()
    {
        return inserisciTelemetryType;
    }
    
    public TableView getTabDrives()
    {
        return tabDrivers;
    }
    
    public ObservableList getOlDriver()
    {
        return olDrivers;
    }
    
    /*public Button getCreateGraphButton()
    {
        return createGraphButton;
    }
    
    public Button getClearGraphButton()
    {
        return clearGraphButton;
    }*/
}


/* COMMENTI

(1)
    Usa la classe FileChoosen per consentire all'utente di scegliere il file da cui ricavare i grafici.
    Viene mandato in esecuzione sul click del bottone Insert CSV file

(2)
    Aggiunge un'elemento alla ObservableList, e quindi alla TableView.
    Chiama anche il metodo aggiungiRecordUtenteDB() per aggiurnare il DataBase MySQL.

(3)
    Elimina la riga sia dalla TableView che dal DataBase

(4)
    Elimina tutte le righe solo dalla TableView. Utile quando si cambia utente, così da poer inserire le nuove righe.

(5)
    Metodo che collega le classi AreaTabellaDrivers e AreaGrafico, in modo che si possa creare il grafico a partire dalla selezione fatta nella TableView
*/