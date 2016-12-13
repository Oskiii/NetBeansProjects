/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Oskari Liukku
 */
public class FXMLMapViewWindowController implements Initializable {

    @FXML
    private WebView webView;
    
    @FXML
    private ComboBox<Package> packagesInStorageCombo;
    
    @FXML
    private ComboBox<String> machineCitiesCombo;

    @FXML
    private TextArea logTextArea;
    
    private ArrayList<PackageMachine> machinesOnMap;
    
    private static FXMLMapViewWindowController instance;
    public static FXMLMapViewWindowController GetInstance(){
        return instance;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load index.html
        webView.getEngine().load(getClass().getResource("index.html").toExternalForm());
        
        //initialize list of package machines
        PackageMachineManager.GetInstance().initMachineList();
        
        //init combobox with list of unique cities
        for(String s : PackageMachineManager.GetInstance().getUniqueCities()){
            machineCitiesCombo.getItems().add(s);
        }
        
        instance = this;
        machinesOnMap = new ArrayList<>();
        
        //write init message to log
        Utilities.GetInstance().writeToLog("Initialized TIMO with " + 
                PackageMachineManager.GetInstance().getMachineList().size() + 
                " machines.");
    }
    
    public String getLogText(){
        return logTextArea.getText();
    }
    
    //add row to log screen
    public void addLogRow(String text){
        logTextArea.setText(logTextArea.getText() + "\n" + text);
    }

    //add machines at selected city onto map
    @FXML
    private void addMachinesOntoMapButtonAction(ActionEvent event) {
        
        //get city from citycombo
        String selectedCity = machineCitiesCombo.getSelectionModel().getSelectedItem();
        //get list of machines at that city from manager
        ArrayList<PackageMachine> machines = PackageMachineManager.GetInstance().getMachinesAtCity(selectedCity);
        
        //add it to map
        for(PackageMachine machine : machines){
            System.out.println(machine.toString());
            GeoPoint location = machine.getLocation();
            Address address = location.getAddress();
            String street = address.getStreetAddress();
            String postcode = Integer.toString(address.getPostcode());
            String city = address.getCity();
            webView.getEngine().executeScript(
                    "document.goToLocation('" + 
                            street + ", " + 
                            postcode + " " + 
                            city + "', '" + 
                            machine.toString() /*+ " " + machine.GetAvailability()*/ + 
                            "', 'red')"
            );
            
            machinesOnMap.add(machine);
            Utilities.GetInstance().writeToLog("Added machine onto map: " + machine.toString());
        }
    }
    
    //refresh items in storage combo
    public void updateStorageCombo(){
        packagesInStorageCombo.setItems(FXCollections.observableArrayList(Storage.GetInstance().getPackages()));
    }
    
    //refresh individual package
    public void updatePackageInStorageCombo(int index, Package p){
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
        stage.setTitle("TIMO Package Creation");

        
        stage.show();
    }

    //when user presses send package
    @FXML
    private void sendButtonAction(ActionEvent event) {
        //get selected package
        Package i = packagesInStorageCombo.getSelectionModel().getSelectedItem();
        //if nothing was selected
        if(i == null){
            Utilities.GetInstance().showError("Select package first!");
            return;
        }
        
        //check if origin and destination machine nodes are on map, if not: error
        if(!machinesOnMap.contains(i.getDestinationMachine()) || !machinesOnMap.contains(i.getDestinationMachine())){
            Utilities.GetInstance().showError("Add " + 
                    i.getOriginMachine().getLocation().getAddress().getCity() + 
                    " and " +
                    i.getDestinationMachine().getLocation().getAddress().getCity() + 
                    " onto the map first!");
            return;
        }
        
        //figure out route details
        GeoPoint origLoc = i.getOriginMachine().getLocation();
        GeoPoint destLoc = i.getDestinationMachine().getLocation();
        double origLat = origLoc.getLatitude();
        double origLong = origLoc.getLongitude();
        double destLat = destLoc.getLatitude();
        double destLong = destLoc.getLongitude();
        ArrayList<Double> route = new ArrayList<>();
        int speed = i.getSpeed();
        
        route.add(origLat);
        route.add(origLong);
        route.add(destLat);
        route.add(destLong);
        
        //get route length
        Object length;
        length = webView.getEngine().executeScript("document.createPath(" + route + ", 'red', " + speed + ")");
        System.out.println(length.toString());
        
        //error if route too long (even though it's already sent)
        if(Float.parseFloat(length.toString()) > i.getMaxDistance()){
            Utilities.GetInstance().showError("Journey too long! (I sure wish I could stop you!)");
        }
        
        //write to log that we sent package
        Utilities.GetInstance().writeToLog("Shipped package " + i.toString());

        //remove sent package from storage
        Storage.GetInstance().getPackages().remove(i);
        updateStorageCombo();
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
            Utilities.GetInstance().showError("Select package to edit!");
            return;
        }
        
        
        System.out.println(packagesInStorageCombo.getSelectionModel().getSelectedIndex() + "th item is " + Storage.GetInstance().getPackages().get(packagesInStorageCombo.getSelectionModel().getSelectedIndex()));
        
        //open edit window
        openPackageCreationWindow();
        //load package info into edit window
        FXMLNewPacketWindowController.GetInstance().loadPackageInfo(
                packagesInStorageCombo.getSelectionModel().getSelectedItem(), 
                packagesInStorageCombo.getSelectionModel().getSelectedIndex()
        );
    }
    
}
