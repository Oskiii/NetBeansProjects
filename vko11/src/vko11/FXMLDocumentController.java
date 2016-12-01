/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko11;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

/**
 *
 * @author Oski
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private WebView webView;
    @FXML
    private TextField urlField;
    @FXML
    private Button previousButton;
    @FXML
    private Button reloadButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button shoutoutButton;
    @FXML
    private Button resetButton;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void loadNewSite(Page p){
        String url = "";
        if(p == null){
            url = urlField.getText();
        }else{
            url = p.GetURL();
        }
        
        if("index.html".equals(url)){
            webView.getEngine().load(FXMLDocumentController.class.getResource("index.html").toExternalForm());
            Browser.GetInstance().SetCurrentPage(new Page(url));
            Browser.GetInstance().AddPageToHistory(url);
            return;
        }
        
        if(!urlField.getText().startsWith("http://") || urlField.getText().startsWith("https://")){
            if(!url.startsWith("www.")){
                url = "www." + url;
            }
            url = "http://" + url;
        }
        urlField.setText(url);
        webView.getEngine().load(url);
        Browser.GetInstance().SetCurrentPage(new Page(url));
        Browser.GetInstance().AddPageToHistory(url);
        
    }
    
    void loadHistorySite(Page p){
        String url = p.GetURL();
        
        urlField.setText(url);
        webView.getEngine().load(url);
        Browser.GetInstance().SetCurrentPage(p);
    }

    @FXML
    private void urlDoneAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            loadNewSite(null);
    }

    @FXML
    private void refreshAction(ActionEvent event) {
        loadHistorySite(Browser.GetInstance().GetCurrentPage());
    }

    @FXML
    private void prevButtonAction(ActionEvent event) {
        try{
            loadHistorySite(Browser.GetInstance().GetPreviousPage());
            
        }catch(NullPointerException ex){
            //there's no previous page
        }
    }

    @FXML
    private void nextButtonAction(ActionEvent event) {
        try{
            loadHistorySite(Browser.GetInstance().GetNextPage());
        }catch(NullPointerException ex){
            //there's no next page
        }
    }

    @FXML
    private void shoutoutButtonAction(ActionEvent event) {
        if("index.html".equals(Browser.GetInstance().GetCurrentPage().GetURL())){
            webView.getEngine().executeScript("document.shoutOut()");
        }
    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
        if("index.html".equals(Browser.GetInstance().GetCurrentPage().GetURL())){
            webView.getEngine().executeScript("initialize()");
        }
    }
}
