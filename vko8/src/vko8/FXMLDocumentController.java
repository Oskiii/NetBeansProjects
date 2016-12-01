/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private TextArea inputField;
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
        try{ 
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File(saveFileName.getText())));
            for (String line : inputField.getText().split("\\n")){
                bf.write(line);
                bf.newLine();
            }
            bf.flush();
            saveFileName.clear();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadFileAction(ActionEvent event) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(loadFileName.getText())));
            inputField.clear();
            String line = bf.readLine();
            while(line != null){
                inputField.appendText(line + "\n");
                line = bf.readLine();
            }
            loadFileName.clear();

        } catch (FileNotFoundException ex) {
            loadFileName.clear();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
