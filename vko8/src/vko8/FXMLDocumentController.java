/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko8;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Oski
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField inputField;
    @FXML
    private Button SaveButton;
    @FXML
    private Button loadButton;
    @FXML
    private TextField loadFileName;
    @FXML
    private TextField saveFileName;
    
    private void handleButtonAction(ActionEvent event) {
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void keyPressedAction(KeyEvent event) {
        label.setText(inputField.getText());
    }

    @FXML
    private void saveFileAction(ActionEvent event) {

    }

    @FXML
    private void loadFileAction(ActionEvent event) {
    }
    
}
