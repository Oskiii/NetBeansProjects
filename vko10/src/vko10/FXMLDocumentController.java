/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Oski
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button listMoviesButton;
    @FXML
    private ComboBox<String> theatresCombo;
    @FXML
    private TextField showDateField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField movieNameField;
    @FXML
    private Button nameSearchButton;
    @FXML
    private ListView<String> listView;

    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Theatre> theatres = new ArrayList<>();

        theatres = MovieCompany.GetInstance().GetTheaters();

        for(Theatre t : theatres){
            addTheaterToCombo(t);
        }
    }    

    @FXML
    private void listMoviesButtonAction(ActionEvent event) {
        listView.getItems().clear();
        int ID = MovieCompany.GetInstance().GetTheatreByName(theatresCombo.getSelectionModel().getSelectedItem()).GetID();
        ArrayList<Showing> movies = MovieCompany.GetInstance().GetShowsAtTheatreForDay(ID);
        
        for(Showing s : movies){
            listView.getItems().add(s.GetTitle());
        }
        
    }

    @FXML
    private void nameSearchButtonAction(ActionEvent event) {
    }
    
    private void addTheaterToCombo(Theatre t){
        theatresCombo.getItems().add(t.GetName());
    }
    
    private String getTheaterURLContent(String address) throws MalformedURLException, IOException{
        URL url = new URL(address);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String content = "";
        String line;
        
        while((line = br.readLine()) != null){
            content += line + "\n";
        }
        return content;
    }
    
    private void listMovies(){
        //String URL = "http://www.finnkino.fi/xml/Schedule/?area=" + theatreID + "&dt=" + date;
    }
}
