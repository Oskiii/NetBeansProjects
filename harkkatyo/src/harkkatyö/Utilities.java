/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        writeToLog("\t--------- TIMO LOG ---------");
    }
    
    DateFormat dt;
    
    //show an error popup
    public void showError(String text){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    //show a message popup
    public void showMessage(String headerText, String bodyText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }
    
    //write a line to log
    public void writeToLog(String text){
        Date date = new Date();
        String d = dt.format(date);
        //write time and line to log
        FXMLMapViewWindowController.GetInstance().addLogRow("[" + d + "]\t" + text);
    }
    
    //write log and storage info to file (on close)
    public void writeFinalLogToFile(){
        String location = System.getProperty( "user.dir" ) + "/log.txt";
        
        try{
            //get file
            File file = new File(location);
            file.createNewFile();

            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            
            //write contents of log to file
            String text = FXMLMapViewWindowController.GetInstance().getLogText();
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
            for(Package p : Storage.GetInstance().getPackages()){
                bf.write("[" + d + "]\t" + p.toString());
                bf.newLine();
            }
            
            bf.close();
            
        }catch(IOException ex){
            System.out.println("File not found!");
        }
        saveState();
    }
    
    public void saveState(){
        /*try (FileOutputStream fileOut = new FileOutputStream("timo.tmo")) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new ProgState());
            out.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
        System.out.printf("Saved data in /tmp/timo.tmo");*/
    }
    
    public void loadState(){
        try {
         FileInputStream fileIn = new FileInputStream("/tmp/timo.tmo");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         Object e = in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i) {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c) {
         System.out.println("Employee class not found");
         c.printStackTrace();
         return;
      }
    }
    
    class ProgState implements Serializable{
        ArrayList<Package> storage;
        
        ProgState(){
            storage = Storage.GetInstance().getPackages();
            
        }
    }
}
