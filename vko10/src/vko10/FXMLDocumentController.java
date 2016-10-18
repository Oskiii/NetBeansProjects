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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
        int ID = MovieCompany.GetInstance().GetTheatreByID(theatresCombo.getSelectionModel().getSelectedIndex()).GetID();
        SimpleDateFormat ftDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat ftTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat ftDateTime = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
        
        
        
        ArrayList<Showing> movies = null;
        if(showDateField.getText().isEmpty() && startTimeField.getText().isEmpty() && endTimeField.getText().isEmpty()){
            movies = MovieCompany.GetInstance().GetShowsAtTheatreForDay(ID, new Date());
        }else{
            // Construct date and time objects
            Calendar dateCal = Calendar.getInstance();
            Calendar timeCal = Calendar.getInstance();

            //parsing starttime
            try{
                try{
                    dateCal.setTime(ftDate.parse(showDateField.getText()));
                }catch(ParseException ex){
                    dateCal.setTime(new Date());
                }
                timeCal.setTime(ftTime.parse(startTimeField.getText()));


                // Extract the time of the "time" object to the "date"
                dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
                dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
                dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
            }catch(ParseException ex){
                dateCal.set(Calendar.HOUR_OF_DAY, 0);
                dateCal.set(Calendar.MINUTE, 0);
                dateCal.set(Calendar.SECOND, 0);
            }
            // get starttime
            Date startDate = dateCal.getTime();

            //parsing endtime
            try{
                timeCal.setTime(ftTime.parse(endTimeField.getText()));

                // Extract the time of the "time" object to the "date"
                dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
                dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
                dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
            }catch(ParseException ex){
                dateCal.set(Calendar.HOUR_OF_DAY, 23);
                dateCal.set(Calendar.MINUTE, 59);
                dateCal.set(Calendar.SECOND, 59);
            }
            // get endtime
            Date endDate = dateCal.getTime();

            Date date;
            //parse and get showdate
            try{
                date = ftDate.parse(showDateField.getText());
            }catch(ParseException ex){
                date = new Date();
            }

            movies = MovieCompany.GetInstance().GetShowsAtTheatreForDayThatStartBetweenTimes(ID, date, startDate, endDate);

        }
        
        for(Showing s : movies){
                listView.getItems().add(s.GetTitle() + " " + ftTime.format(s.GetStartTime()));
            }

        //theatresCombo.getSelectionModel()
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
