/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Oskari Liukku
 */
public class MessageWindowHandler {
    
    private static MessageWindowHandler instance;
    public static MessageWindowHandler GetInstance(){
        if(instance == null){
            instance = new MessageWindowHandler();
        }
        return instance;
    }
    
    /**
     * Initializes the controller class.
     * @param text message body text
     */
    public void ShowError(String text){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    /**
     * Initializes the controller class.
     * @param headerText message header text
     * @param bodyText message body text
     */
    public void ShowMessage(String headerText, String bodyText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }
}
