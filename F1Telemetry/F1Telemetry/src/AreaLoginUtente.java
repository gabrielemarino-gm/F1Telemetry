
import java.util.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 *
 * @author gabriele
 * 
 */
class AreaLoginUtente 
{
    private final Label labelUser;
    private final Label labelWelcome;
    private final Label labelName;
    private final TextField textFieldUsers;
    private final Button loginButton;    
    private final HBox boxOrizzontale;
    private final HBox boxLabel;
    private final VBox boxTot;
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
    
    
    public AreaLoginUtente(InterfacciaF1Telemetry interF1Telemetry, ParametriDiConfigurazioneXML config)
    {
        this.interfacciaF1Telemetry = interF1Telemetry;
        this.configurazione = config;
        
        labelUser = new Label("User :");
        labelWelcome = new Label("");
        labelName = new Label("");
        
        textFieldUsers = new TextField();
        
        loginButton = new Button("Login");
        loginButton.setOnAction((ActionEvent in) -> {loginUtente();});

        boxOrizzontale = new HBox();
        boxOrizzontale.setAlignment(Pos.CENTER);
        boxOrizzontale.setSpacing(10);
        boxOrizzontale.setPrefHeight(50);
        boxOrizzontale.getChildren().addAll(labelUser, textFieldUsers, loginButton);    
        
        boxLabel = new HBox();
        boxLabel.getChildren().addAll(labelWelcome, labelName);
        
        boxTot = new VBox();
        boxTot.getChildren().addAll(boxOrizzontale, boxLabel);
        
        
        stile();
    }
    
    
    
    private void loginUtente() // (1)
    {   
        SerializzatoreXML.creaLog("Click sul pulsante login", configurazione); 
        interfacciaF1Telemetry.getAreaTabellaDrivers().eliminaTutteLeRighe();
        ArrayList<DriversAndCircuit> piloti = ArchivioTabellaDrivers.caricaTabellaDriversDB(textFieldUsers.getText());
        
        for (int i=0; i<piloti.size(); i++)
        {
            interfacciaF1Telemetry.getAreaTabellaDrivers().inserisciRigaDB
            (
                piloti.get(i).getDriver(),
                piloti.get(i).getCircuit(),
                piloti.get(i).getTelemetryType(),
                piloti.get(i).getFilePath()
            );
        }
        
        labelWelcome.setText("Welcome ");
        labelName.setText(textFieldUsers.getText());
        textFieldUsers.clear();
    }
    
    
    
    
    private void stile() 
    {
        labelUser.setFont(Font.font(configurazione.font, 16));
        labelWelcome.setFont(Font.font(configurazione.font, 16));
        labelName.setFont(Font.font(configurazione.font, 16));
        loginButton.setFont(Font.font(configurazione.font , configurazione.dimensioneFont));
    }
    
    
    
    public VBox getBoxTot()
    {
        return boxTot;
    }
    

    public TextField getTextFieldUser() 
    {
        return textFieldUsers;
    }
    
    public Label getLabelName() 
    {
        return labelName;
    }
    
    public Label getLabelWelcome() 
    {
        return labelWelcome;
    }
}


/* COMMENTI

(1)
    Metodo chiamato a ogni click sul bottone Login. 
    Inizializza la TableView con ciò che è presente nel DataBase relativamente all'utente che fa l'accesso

*/