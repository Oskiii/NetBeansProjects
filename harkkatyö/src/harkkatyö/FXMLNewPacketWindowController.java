/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harkkatyö;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 *
 * @author Oski
 */
public class FXMLNewPacketWindowController implements Initializable {
    ToggleGroup toggleGroup;
    private Label label;
    @FXML
    private ComboBox<Item> prebuiltCombo;
    @FXML
    private TextField newItemNameField;
    @FXML
    private TextField newItemSizeField;
    @FXML
    private TextField newItemWeightField;
    @FXML
    private CheckBox newItemFragileCheck;
    @FXML
    private RadioButton firstClassCheck;
    @FXML
    private RadioButton secondClassCheck;
    @FXML
    private RadioButton thirdClassCheck;
    @FXML
    private Button classInfoButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createPacket;
    @FXML
    private ComboBox<String> sourceCityCombo;
    @FXML
    private ComboBox<PackageMachine> sourceMachineCombo;
    @FXML
    private ComboBox<String> destinationCityCombo;
    @FXML
    private ComboBox<PackageMachine> destinationMachineCombo;
    @FXML
    private Text errorText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleGroup = new ToggleGroup();
        firstClassCheck.setToggleGroup(toggleGroup);
        secondClassCheck.setToggleGroup(toggleGroup);
        thirdClassCheck.setToggleGroup(toggleGroup);
        addItemsToPrebuiltCombo();
        addItemsToCityCombos();
    }
    
    private void addItemsToPrebuiltCombo(){
        prebuiltCombo.getItems().add(new Teddybear());
    }
    
    private void addItemsToCityCombos(){
        for(String s : PackageMachineManager.GetInstance().GetUniqueCities()){
            sourceCityCombo.getItems().add(s);
            destinationCityCombo.getItems().add(s);
        }
    }

    @FXML
    private void classInfoButtonAction(ActionEvent event) {
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void createPacketButtonAction(ActionEvent event) {
        int packetClass = getPacketClass();
        Package packet;
        
        switch(packetClass){
            case 1:
                packet = new FirstClassPackage(
                        sourceMachineCombo.getSelectionModel().getSelectedItem(),
                        destinationMachineCombo.getSelectionModel().getSelectedItem());
                break;
            case 2:
                packet = new SecondClassPackage(
                        sourceMachineCombo.getSelectionModel().getSelectedItem(),
                        destinationMachineCombo.getSelectionModel().getSelectedItem());
                break;
            case 3:
            default:
                packet = new ThirdClassPackage(
                        sourceMachineCombo.getSelectionModel().getSelectedItem(),
                        destinationMachineCombo.getSelectionModel().getSelectedItem());
                break;
        }
        
        System.out.println("Added item " + getItem().toString());
        packet.AddItem(getItem());
        Storage.GetInstance().GetPackages().add(packet);
        FXMLMapViewWindowController.GetInstance().UpdateStorageCombo();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private Item getItem(){
        
        if(prebuiltCombo.getSelectionModel().getSelectedItem() != null){
            return prebuiltCombo.getSelectionModel().getSelectedItem();
        }
        
        String name = newItemNameField.getText();
        Float[] dimensions = new Float[3];
        String[] stringDim = newItemSizeField.getText().split("[* ]");
        
        for(int i = 0; i < stringDim.length; i++){
            float f = Float.parseFloat(stringDim[i]);
            dimensions[i] = f;
        }
        
        Float weight = Float.parseFloat(newItemWeightField.getText());
        Boolean fragile = newItemFragileCheck.isSelected();
        
        return new Item(name, dimensions, weight, fragile);
    }

    @FXML
    private void firstClassCheckAction(ActionEvent event) {
    }

    @FXML
    private void secondClassCheckAction(ActionEvent event) {
    }

    @FXML
    private void thirdClassCheckAction(ActionEvent event) {
    }
    
    private int getPacketClass(){
        return toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle()) + 1;
    }

    @FXML
    private void originClosedAction(Event event) {
        addMachinesToOriginCombo();
    }

    @FXML
    private void destinationClosedAction(Event event) {
        addMachinesToDestinationCombo();
    }
    
    private void addMachinesToOriginCombo(){
        for(PackageMachine m : PackageMachineManager.GetInstance().GetMachinesAtCity(sourceCityCombo.getSelectionModel().getSelectedItem())){
            sourceMachineCombo.getItems().add(m);
        }
    }
    
    private void addMachinesToDestinationCombo(){
        for(PackageMachine m : PackageMachineManager.GetInstance().GetMachinesAtCity(destinationCityCombo.getSelectionModel().getSelectedItem())){
            destinationMachineCombo.getItems().add(m);
        }
    }
}
