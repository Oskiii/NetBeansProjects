/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harkkatyö;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static FXMLNewPacketWindowController instance;
    public static FXMLNewPacketWindowController GetInstance(){
        return instance;
    }
    
    private int editingItemIndex;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleGroup = new ToggleGroup();
        firstClassCheck.setToggleGroup(toggleGroup);
        secondClassCheck.setToggleGroup(toggleGroup);
        thirdClassCheck.setToggleGroup(toggleGroup);
        addItemsToPrebuiltCombo();
        addItemsToCityCombos();
        instance = this;
        editingItemIndex = -1;
    }
    
    //add prebuilt items
    private void addItemsToPrebuiltCombo(){
        prebuiltCombo.getItems().add(new Teddybear());
        prebuiltCombo.getItems().add(new BarrelOfLube());
        prebuiltCombo.getItems().add(new MingVase());
        prebuiltCombo.getItems().add(new TableLamp());
        prebuiltCombo.getItems().add(new CanOfWorms());
        
    }
    
    //populate city combos
    private void addItemsToCityCombos(){
        for(String s : PackageMachineManager.GetInstance().GetUniqueCities()){
            sourceCityCombo.getItems().add(s);
            destinationCityCombo.getItems().add(s);
        }
    }

    //show info about classes
    @FXML
    private void classInfoButtonAction(ActionEvent event) {
        Utilities.GetInstance().ShowMessage("Package Class Information: ",
                "1st Class: Quick but has a max. range of 150 km\n"
                + "2nd Class: Best for fragile shipments. Max. size: 10*10*10 cm\n"
                + "3rd Class: Long range, but slower");
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    
    @FXML
    private void createPacketButtonAction(ActionEvent event) {
        int packetClass = getPacketClass();
        Package packet;
        Item item = getItem();
        
        //if item had an error, don't create a package
        if(item == null) return;
        
        //error checking for machine selection
        if(sourceMachineCombo.getSelectionModel().getSelectedItem() == null){
            Utilities.GetInstance().ShowError("Set origin machine first!");
            return;
        }else if(destinationMachineCombo.getSelectionModel().getSelectedItem() == null){
            Utilities.GetInstance().ShowError("Set destination machine first!");
            return;
        }
        try{
        //do things based on package class
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
                    packet = new ThirdClassPackage(
                            sourceMachineCombo.getSelectionModel().getSelectedItem(),
                            destinationMachineCombo.getSelectionModel().getSelectedItem());
                    break;
                default:
                    Utilities.GetInstance().ShowError("Select package class first!");
                    return;
            }
        }catch(InvalidRouteException ex){
            Utilities.GetInstance().ShowError(ex.getMessage());
            return;
        }
        
        try {
            //add item into package
            packet.SetItem(item);
        }catch(PackageSizeException ex){
            Utilities.GetInstance().ShowError(ex.getMessage());
            return;
        } catch (FragilityException ex) {
            //tried to add into unsuitable package
            Utilities.GetInstance().ShowError("Class isn't suitable for fragile items!");
        }

        
        //if we're editing an item
        if(editingItemIndex != -1){
            System.out.println("Edited item " + getItem().toString());
            
            Package old = Storage.GetInstance().GetPackages().get(editingItemIndex);
            
            //add info row to log
            Utilities.GetInstance().WriteToLog("Edited package: " + 
                    packet + 
                    " [" + 
                    packet.GetOriginMachine().GetLocation().GetAddress().GetCity() + 
                    " -> " + 
                    packet.GetDestinationMachine().GetLocation().GetAddress().GetCity() + 
                    "]\t(Old: " + 
                    old + 
                    " [" + 
                    old.GetOriginMachine().GetLocation().GetAddress().GetCity() + 
                    " -> " + 
                    old.GetDestinationMachine().GetLocation().GetAddress().GetCity() + 
                    "])"
                    );
            
            //replace package in storage with edited one
            Storage.GetInstance().GetPackages().set(editingItemIndex, packet);
            
        }else{
            System.out.println("Added item " + getItem().toString());
            //add package to storage
            Storage.GetInstance().GetPackages().add(packet);
            Utilities.GetInstance().WriteToLog("Created package: " + 
                    packet + 
                    " (" + 
                    packet.GetOriginMachine().GetLocation().GetAddress().GetCity() + 
                    " -> " + 
                    packet.GetDestinationMachine().GetLocation().GetAddress().GetCity() + 
                    ")");
        }
        
        //update packages in storage combobox
        FXMLMapViewWindowController.GetInstance().UpdateStorageCombo();
        //close window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        editingItemIndex = -1;
    }

    //get item, either from prebuilt list or custom fields
    private Item getItem(){
        
        //if something has been selected from prebuilt list, return that
        if(prebuiltCombo.getSelectionModel().getSelectedItem() != null){
            Item item = prebuiltCombo.getSelectionModel().getSelectedItem();
            prebuiltCombo.getSelectionModel().clearSelection();
            return item;
        }
        
        //get custom item info from input
        String name = newItemNameField.getText();
        Float[] dimensions = new Float[3];
        String[] stringDim = newItemSizeField.getText().split("[* ]");
        Float weight = 0f;
        
        //if name is empty, error
        if(name.length() == 0){
            Utilities.GetInstance().ShowError("Set item name first!");
            return null;
        }
        
        //if item dimensions aren't positive floats, error
        try{
            for(int i = 0; i < stringDim.length; i++){
                float f = Float.parseFloat(stringDim[i]);
                dimensions[i] = f;
                if(f <= 0f){
                    throw new NumberFormatException();
                }
            }
        }catch(NumberFormatException ex){
            Utilities.GetInstance().ShowError("Invalid item dimensions!");
            return null;
        }
        
        //if weight isn't a float
        try{
            weight = Float.parseFloat(newItemWeightField.getText());
        }catch(NumberFormatException ex){
            Utilities.GetInstance().ShowError("Invalid weight!");
            return null;
        }
        
        //get fragility
        Boolean fragile = newItemFragileCheck.isSelected();
        
        //return custom item
        return new Item(name, dimensions, weight, fragile);
    }

    //returns index of checked class (1,2,3)
    private int getPacketClass(){
        return toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle()) + 1;
    }

    //when origin city combo closes, get machines at that city
    @FXML
    private void originClosedAction(Event event) {
        addMachinesToOriginCombo();
    }

    //when destination city combo closes, get machines at that city
    @FXML
    private void destinationClosedAction(Event event) {
        addMachinesToDestinationCombo();
    }
    
    //add machines to origin machine combo
    private void addMachinesToOriginCombo(){
        //clear the combo
        sourceMachineCombo.getSelectionModel().clearSelection();
        sourceMachineCombo.getItems().clear();
        //add all machines at selected city into combo
        for(PackageMachine m : PackageMachineManager.GetInstance().GetMachinesAtCity(sourceCityCombo.getSelectionModel().getSelectedItem())){
            sourceMachineCombo.getItems().add(m);
        }
    }
    
    //add machines to origin machine combo
    private void addMachinesToDestinationCombo(){
        //clear the combo
        destinationMachineCombo.getSelectionModel().clearSelection();
        destinationMachineCombo.getItems().clear();
        //add all machines at selected city into combo
        for(PackageMachine m : PackageMachineManager.GetInstance().GetMachinesAtCity(destinationCityCombo.getSelectionModel().getSelectedItem())){
            destinationMachineCombo.getItems().add(m);
        }
    }

    //when combo of prebuilt items is used, set custom fields to equal that item
    @FXML
    private void prebuiltComboAction(ActionEvent event) {
        //if nothing's selected, ignore
        if(prebuiltCombo.getSelectionModel().getSelectedItem() == null) return;
        
        Item item = prebuiltCombo.getSelectionModel().getSelectedItem();
        
        //set info fields
        newItemNameField.setText(item.toString());
        newItemSizeField.setText(item.DimensionsToString());
        newItemWeightField.setText(item.GetWeight().toString());
        newItemFragileCheck.setSelected(item.GetFragility());
    }
    
    //Load pre-existing package's info back in
    //This is for editing packages
    public void LoadPackageInfo(Package p, int index){
        
        editingItemIndex = index;
        
        //toggle class toggle equal to package's class
        toggleGroup.selectToggle(toggleGroup.getToggles().get(p.GetClassInt() - 1));
        
        //load cities into comboboxes
        sourceCityCombo.getSelectionModel().select(p.originMachine.GetLocation().GetAddress().GetCity());
        addMachinesToOriginCombo();
        destinationCityCombo.getSelectionModel().select(p.destinationMachine.GetLocation().GetAddress().GetCity());
        addMachinesToDestinationCombo();
        
        //load machines into comboboxes
        sourceMachineCombo.getSelectionModel().select(p.GetOriginMachine());
        destinationMachineCombo.getSelectionModel().select(p.GetDestinationMachine());

        //set item info fields
        Item item = p.GetItem();
        newItemNameField.setText(item.toString());
        newItemSizeField.setText(item.DimensionsToString());
        newItemWeightField.setText(item.GetWeight().toString());
        newItemFragileCheck.setSelected(item.GetFragility());
    }
}
