/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Oskari Liukku
 */
public class FXMLMapViewWindowController implements Initializable {

    @FXML
    private ComboBox<PackageMachine> machinesCombo;
    @FXML
    private Button addMachineOntoMapButton;
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
    private Text errorText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webView.getEngine().load(getClass().getResource("index.html").toExternalForm());
        
        PackageMachineManager.GetInstance().InitMachineList();
        for(PackageMachine p : PackageMachineManager.GetInstance().GetMachineList()){
            machinesCombo.getItems().add(p);
        }
        DataBuilder.GetInstance();
        instance = this;
    }

    @FXML
    private void addMachineontoMapButtonAction(ActionEvent event) {
        PackageMachine machine = machinesCombo.getSelectionModel().getSelectedItem();
        GeoPoint location = machine.GetLocation();
        Address address = location.GetAddress();
        String street = address.GetAddress();
        String postcode = Integer.toString(address.GetPostcode());
        String city = address.GetCity();
        webView.getEngine().executeScript("document.goToLocation('" + street + ", " + postcode + " " + city + "', '" + machine.toString() /*+ " " + machine.GetAvailability()*/ + "', 'red')"); 
    }
    
    public void UpdateStorageCombo(){
        packagesInStorageCombo.getItems().clear();
        for(Package p : Storage.GetInstance().GetPackages()){
            packagesInStorageCombo.getItems().add(p);
        }
    }

    @FXML
    private void createPackageButtonAction(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNewPacketWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sendButtonAction(ActionEvent event) {
        Package i = packagesInStorageCombo.getSelectionModel().getSelectedItem();
        if(i == null){
            MessageWindowHandler.GetInstance().ShowError("Select package first!");
            return;
        }
        
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
        
        webView.getEngine().executeScript("document.createPath(" + route + ", 'red', " + speed + ")");
        Storage.GetInstance().GetPackages().remove(i);
        UpdateStorageCombo();
    }

    @FXML
    private void clearMapButtonAction(ActionEvent event) {
        webView.getEngine().executeScript("document.deletePaths()");
        //UpdateStorageCombo();
    }

    @FXML
    private void storageComboAction(ActionEvent event) {
        UpdateStorageCombo();
    }
    
}
