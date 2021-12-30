import java.util.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 *
 * @author marin
 * 
 */
class AreaLoginUtente 
{
    private final HBox boxOrizzontale;
    private final Label labelUser;
    private final TextField textFieldUsers;
    private final Button loginButton;    
    private final InterfacciaF1Telemetry interfacciaF1Telemetry;
    private final ParametriDiConfigurazioneXML configurazione;
    
    
    AreaLoginUtente(InterfacciaF1Telemetry interF1Telemetry, ParametriDiConfigurazioneXML config)
    {
        this.interfacciaF1Telemetry = interF1Telemetry;
        this.configurazione = config;
        
        labelUser = new Label("User :");
        
        textFieldUsers = new TextField();
        
        loginButton = new Button("Login");
        loginButton.setOnAction((ActionEvent in) -> {loginUtente();});

        impostaStile();
        boxOrizzontale = new HBox();
        boxOrizzontale.setAlignment(Pos.CENTER);
        boxOrizzontale.setSpacing(10);
        boxOrizzontale.setPrefHeight(50);
        boxOrizzontale.getChildren().addAll(labelUser, textFieldUsers, loginButton);        
    }
    
    
    
    private void loginUtente()
    {   
        SerializzatoreXML.creaLog("Click sul pulsante login", configurazione);
        ArchivioTabellaDrivers.caricaTabellaDriversDB(textFieldUsers.getText(), interfacciaF1Telemetry.getAreaTabellaDrivers()); 
    }
    
    
    
    
    private void impostaStile() 
    {
        labelUser.setFont(Font.font(configurazione.font, 16));
    }
    
    
    
    public HBox getBoxOrizzontale()
    {
        return boxOrizzontale;
    }
    

    public TextField getTextFieldUser() 
    {
        return textFieldUsers;
    }
}
