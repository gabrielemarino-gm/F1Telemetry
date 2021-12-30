import java.io.File;
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
 * @author marin
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
    private final Button createGraphButton;
    private final Button deleteButton;
    private final Button deleteAllGraphButton;
    private final Button insertCSVButton;
    private final HBox boxInserimentoRecord;
    private final HBox boxPulsanti;
    private final VBox boxTabAndButton;
    private final TextField inserisciDrivers;
    private final TextField inserisciCircuit;
    private final TextField inserisciTelemetryType;
    private final FileChooser fileChooser;
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
    private final Stage stage;
    
    private String ultimoFilePath = " ";
    
    
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
        
        createGraphButton = new Button("Create Graph");
        createGraphButton.setOnAction((ActionEvent insertEvent) -> {creaGrafico();});
        
        deleteButton = new Button("Delete");
        deleteButton.setOnAction((ActionEvent insertEvent) -> {eliminaRiga();});
        
        deleteAllGraphButton = new Button("Clear Graph");
        deleteAllGraphButton.setOnAction((ActionEvent insertEvent) -> {interfacciaF1Telemetry.getAreaGrafico().eliminaTuttiGrafici();});
        
        insertCSVButton = new Button("Insert CSV file");
        insertCSVButton.setOnAction((ActionEvent insertEvent) -> {inserisciFileCSV(); inserisciRigaTextField();});
        
        inserisciDrivers = new TextField();
        inserisciCircuit = new TextField();
        inserisciTelemetryType = new TextField();
        
        boxInserimentoRecord = new HBox();
        boxInserimentoRecord.setAlignment(Pos.CENTER);
        boxInserimentoRecord.getChildren().addAll(inserisciDrivers, inserisciCircuit, inserisciTelemetryType, insertCSVButton, deleteButton);
        
        boxPulsanti = new HBox();
        boxPulsanti.setAlignment(Pos.CENTER_RIGHT);
        boxPulsanti.getChildren().addAll(createGraphButton, deleteAllGraphButton);        
        
        boxTabAndButton = new VBox();
        boxTabAndButton.setPadding(new Insets(10, 0, 0, 10));
        boxTabAndButton.setAlignment(Pos.CENTER);
        boxTabAndButton.getChildren().addAll(titoloTabDrivers, tabDrivers, boxInserimentoRecord, boxPulsanti);
        
        
        fileChooser = new FileChooser();
        
        stile();
    }
    

    private void stile() 
    {
        titoloTabDrivers.setFont(Font.font(configurazione.font, configurazione.dimensioneFont + configurazione.dimensioneTitolo));
        
        tabDrivers.setFixedCellSize(configurazione.altezzaRiga);
        tabDrivers.prefHeightProperty().set((configurazione.numeroRighe + 1) * configurazione.altezzaRiga);
        tabDrivers.setMaxHeight(1300);
        tabDrivers.setMaxWidth(1000);
        tabDrivers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                
        createGraphButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        deleteButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        insertCSVButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
        
        inserisciDrivers.setMaxWidth(colonnaDrivers.getPrefWidth());
        inserisciDrivers.setMaxHeight(5);
        inserisciDrivers.setPromptText("Driver");
        
        inserisciCircuit.setMaxWidth(colonnaCircuit.getPrefWidth());
        inserisciCircuit.setMaxHeight(5);
        inserisciCircuit.setPromptText("Circuit");
        
        inserisciTelemetryType.setMaxWidth(colonnaTelemetryType.getPrefWidth());
        inserisciTelemetryType.setMaxHeight(5);
        inserisciTelemetryType.setPromptText("Type");
        
    }
    
    
    private void inserisciFileCSV()
    {
        SerializzatoreXML.creaLog("Click sul pulsante Insert CSV file", configurazione);
        fileChooser.setTitle("File CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll
        (
            new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
            
        File fileCSV = fileChooser.showOpenDialog(stage);
        ultimoFilePath = fileCSV.getAbsolutePath();
    }
    
    
    private void inserisciRigaTextField()
    {
        DriversAndCircuit row = new DriversAndCircuit
        (
            inserisciDrivers.getText(),
            inserisciCircuit.getText(),
            inserisciTelemetryType.getText(),
            ultimoFilePath
        );
        
        olDrivers.add(row);
        ArchivioTabellaDrivers.aggiungiRecordUtenteDB(interfacciaF1Telemetry.getAreaLoginUtente().getTextFieldUser().getText(), row);
        
        
        inserisciDrivers.clear();
        inserisciCircuit.clear();
        inserisciTelemetryType.clear();    
    }
    
    
    
    public void inserisciRigaDB(String d, String c, String tt, String fp)
    {
        olDrivers.add(new DriversAndCircuit(d, c, tt, fp));
    }
    
    
    
    private void eliminaRiga()
    {
        SerializzatoreXML.creaLog("Click sul pulsante Delete", configurazione);
        DriversAndCircuit selectDriver = tabDrivers.getSelectionModel().getSelectedItem();
        int i = tabDrivers.getItems().indexOf(selectDriver);
        // tabDrivers.getItems().set(i, selectDriver);
        ArchivioTabellaDrivers.eliminaRecordUtenteDB(interfacciaF1Telemetry.getAreaLoginUtente().getTextFieldUser().getText(), selectDriver);
        ArchivioTabellaDrivers.caricaTabellaDriversDB(interfacciaF1Telemetry.getAreaLoginUtente().getTextFieldUser().getText(), this);
    }
    
    
    
    public void eliminaTutteLeRighe()
    {
        olDrivers.clear();
    }
    
    
    public void creaGrafico()
    {
        SerializzatoreXML.creaLog("Click sul pulsante Create Graph", configurazione);
        DriversAndCircuit selectDriver = tabDrivers.getSelectionModel().getSelectedItem();
        interfacciaF1Telemetry.getAreaGrafico().creaGraficoAreaGrafico(selectDriver.getFilePath(), selectDriver.getTelemetryType());
    }
  
    
    public void creaOL()
    {
        olDrivers = FXCollections.observableArrayList();
    }
    
    
    
    public VBox getBoxVerticale()
    {
        return boxTabAndButton;
    }
    
    public TextField getTextFieldDriver ()
    {
        return inserisciDrivers;
    }
    
    public TextField getTextFieldCircuit ()
    {
        return inserisciCircuit;
    }
    
    public TextField getTextFieldTelemetryType ()
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
}
