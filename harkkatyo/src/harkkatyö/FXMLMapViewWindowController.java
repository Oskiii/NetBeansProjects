/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author Oskari Liukku
 */
public class FXMLMapViewWindowController implements Initializable {

    @FXML
    private Button createPackageButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button clearMapButton;
    @FXML
    private WebView webView;
    
    private static FXMLMapViewWindowController instance;
    public static FXMLMapViewWindowController GetInstance(){
        return instance;
    }
    @FXML
    private ComboBox<Package> packagesInStorageCombo;
    @FXML
    private ComboBox<String> machineCitiesCombo;
    @FXML
    private Button addMachinesOntoMapButton;

    
    private ArrayList<PackageMachine> machinesOnMap;
    @FXML
    private Button editPackageButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextArea logTextArea;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load index.html
        webView.getEngine().load(getClass().getResource("index.html").toExternalForm());
        
        //initialize list of package machines
        PackageMachineManager.GetInstance().InitMachineList();
        
        //init combobox with list of unique cities
        for(String s : PackageMachineManager.GetInstance().GetUniqueCities()){
            machineCitiesCombo.getItems().add(s);
        }
        
        instance = this;
        machinesOnMap = new ArrayList<>();
        
        //write init message to log
        Utilities.GetInstance().WriteToLog("Initialized TIMO with " + 
                PackageMachineManager.GetInstance().GetMachineList().size() + 
                " machines.");
    }
    
    public String GetLogText(){
        return logTextArea.getText();
    }
    
    //add row to log screen
    public void AddLogRow(String text){
        logTextArea.setText(logTextArea.getText() + "\n" + text);
    }

    //add machines at selected city onto map
    @FXML
    private void addMachinesOntoMapButtonAction(ActionEvent event) {
        
        //get city from citycombo
        String selectedCity = machineCitiesCombo.getSelectionModel().getSelectedItem();
        //get list of machines at that city from manager
        ArrayList<PackageMachine> machines = PackageMachineManager.GetInstance().GetMachinesAtCity(selectedCity);

        
        //add it to map
        for(PackageMachine machine : machines){
            System.out.println(machine.toString());
            GeoPoint location = machine.GetLocation();
            Address address = location.GetAddress();
            String street = address.GetStreetAddress();
            String postcode = Integer.toString(address.GetPostcode());
            String city = address.GetCity();
            webView.getEngine().executeScript(
                    "document.goToLocation('" + 
                            street + ", " + 
                            postcode + " " + 
                            city + "', '" + 
                            machine.toString() /*+ " " + machine.GetAvailability()*/ + 
                            "', 'red')"
            );
            
            machinesOnMap.add(machine);
            Utilities.GetInstance().WriteToLog("Added machine onto map: " + machine.toString());
        }
    }
    
    //refresh items in storage combo
    public void UpdateStorageCombo(){
        packagesInStorageCombo.setItems(FXCollections.observableArrayList(Storage.GetInstance().GetPackages()));
    }
    
    //refresh individual package
    public void UpdatePackageInStorageCombo(int index, Package p){
        packagesInStorageCombo.getItems().set(index, p);
    }

    @FXML
    private void createPackageButtonAction(ActionEvent event) throws Exception{
        openPackageCreationWindow();
    }
    
    //open package creation/edit window
    private void openPackageCreationWindow() throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNewPacketWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    //when user presses send package
    @FXML
    private void sendButtonAction(ActionEvent event) {
        //get selected package
        Package i = packagesInStorageCombo.getSelectionModel().getSelectedItem();
        //if nothing was selected
        if(i == null){
            Utilities.GetInstance().ShowError("Select package first!");
            return;
        }
        
        //check if origin and destination machine nodes are on map, if not: error
        if(!machinesOnMap.contains(i.GetDestinationMachine()) || !machinesOnMap.contains(i.GetDestinationMachine())){
            Utilities.GetInstance().ShowError("Add " + 
                    i.GetOriginMachine().GetLocation().GetAddress().GetCity() + 
                    " and " +
                    i.GetDestinationMachine().GetLocation().GetAddress().GetCity() + 
                    " onto the map first!");
            return;
        }
        
        //figure out route details
        GeoPoint origLoc = i.GetOriginMachine().GetLocation();
        GeoPoint destLoc = i.GetDestinationMachine().GetLocation();
        double origLat = origLoc.GetLatitude();
        double origLong = origLoc.GetLongitude();
        double destLat = destLoc.GetLatitude();
        double destLong = destLoc.GetLongitude();
        ArrayList<Double> route = new ArrayList<>();
        int speed = i.GetSpeed();
        
        route.add(origLat);
        route.add(origLong);
        route.add(destLat);
        route.add(destLong);
        
        //get route length
        Object length;
        length = webView.getEngine().executeScript("document.createPath(" + route + ", 'red', " + speed + ")");
        System.out.println(length.toString());
        
        //error if route too long (even though it's already sent)
        if(Float.parseFloat(length.toString()) > i.GetMaxDistance()){
            Utilities.GetInstance().ShowError("Journey too long! (tut tut)");
        }
        
        //write to log that we sent package
        Utilities.GetInstance().WriteToLog("Shipped package " + i.toString());

        //remove sent package from storage
        Storage.GetInstance().GetPackages().remove(i);
        UpdateStorageCombo();
    }

    //clear routes from map
    @FXML
    private void clearMapButtonAction(ActionEvent event) {
        webView.getEngine().executeScript("document.deletePaths()");
    }

    //edit button
    @FXML
    private void editPackageButtonAction(ActionEvent event) throws Exception{
        
        //check that some package is selected
        if(packagesInStorageCombo.getSelectionModel().getSelectedItem() == null){
            Utilities.GetInstance().ShowError("Select package to edit!");
            return;
        }
        
        
        System.out.println(packagesInStorageCombo.getSelectionModel().getSelectedIndex() + "th item is " + Storage.GetInstance().GetPackages().get(packagesInStorageCombo.getSelectionModel().getSelectedIndex()));
        
        //open edit window
        openPackageCreationWindow();
        //load package info into edit window
        FXMLNewPacketWindowController.GetInstance().LoadPackageInfo(
                packagesInStorageCombo.getSelectionModel().getSelectedItem(), 
                packagesInStorageCombo.getSelectionModel().getSelectedIndex()
        );
    }
    
}
