/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko9;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

/**
 *
 * @author Oski
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button addMoneyButton;
    @FXML
    private Button buyBottleButton;
    @FXML
    private static TextArea outputText;
    @FXML
    private Slider addMoneyAmountSlider;
    
    public static void printToField(String t){
        outputText.appendText("\n" + t);
    }
    @FXML
    private ComboBox<String> bottleTypeDropdown;
    @FXML
    private ComboBox<Float> bottleSizeDropdown;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        //initialize bottletype dropdown
        for (Object b : BottleDispenser.getInstance().bottle_array) {
            Bottle bottle = (Bottle)b;
            addBottleToDropdowns(bottle);
        }
        
    }    

    @FXML
    private void addMoneyButtonAction(ActionEvent event) {
        BottleDispenser.getInstance().addMoney((float)addMoneyAmountSlider.getValue());
        addMoneyAmountSlider.setValue(0);
    }

    @FXML
    private void buyBottleButtonAction(ActionEvent event) {
        
        BottleDispenser.getInstance().buyBottle(
                BottleDispenser.getInstance().GetBottleID(
                        new Bottle(
                                bottleTypeDropdown.valueProperty().getValue(), 
                                bottleSizeDropdown.valueProperty().getValue(),
                                0f
                        )
                )
        );
    }
   
    public void addBottleToDropdowns(Bottle b){
        if(!bottleTypeDropdown.getItems().contains(b.name)){
            bottleTypeDropdown.getItems().add(b.name);
        }
        
        if(!bottleSizeDropdown.getItems().contains(b.size)){
            bottleSizeDropdown.getItems().add(b.size);
        }
    }
}
