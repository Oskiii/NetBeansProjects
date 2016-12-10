/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Oskari Liukku
 */
public class Utilities {
    
    private static Utilities instance;
    public static Utilities GetInstance(){
        if(instance == null){
            instance = new Utilities();
        }
        return instance;
    }
    
    //init
    private Utilities(){
        dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        WriteToLog("\t--------- TIMO LOG ---------");
    }
    
    DateFormat dt;
    
    //show an error popup
    public void ShowError(String text){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    //show a message popup
    public void ShowMessage(String headerText, String bodyText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }
    
    //write a line to log
    public void WriteToLog(String text){
        Date date = new Date();
        String d = dt.format(date);
        //write time and line to log
        FXMLMapViewWindowController.GetInstance().AddLogRow("[" + d + "]\t" + text);
    }
    
    //write log and storage info to file (on close)
    public void WriteFinalLogToFile(){
        String location = System.getProperty( "user.dir" ) + "/log.txt";
        
        try{
            //get file
            File file = new File(location);
            file.createNewFile();

            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            
            //write contents of log to file
            String text = FXMLMapViewWindowController.GetInstance().GetLogText();
            for (String line : text.split("\\n")){
                bf.write(line);
                bf.newLine();
            }
            
            bf.newLine();
            bf.newLine();
            
            Date date = new Date();
            String d = dt.format(date);
            
            //write storage header
            bf.write("[" + d + "]\tStorage: ");
            bf.newLine();

            //write storage contents
            for(Package p : Storage.GetInstance().GetPackages()){
                bf.write("[" + d + "]\t" + p.toString());
                bf.newLine();
            }
            
            bf.close();
            
        }catch(IOException ex){
            System.out.println("File not found!");
        }
    }
}
