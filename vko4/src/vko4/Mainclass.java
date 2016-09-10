/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko4;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 *
 * @author Oski
 */
public class Mainclass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReadAndWriteIO reader = new ReadAndWriteIO();
        reader.readAndWrite("zipinput.zip");
    }
    
}

class ReadAndWriteIO{
    String readFile(String path){
        String text = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader(path));
            String c;
            
            while((c = in.readLine()) != null){
                text += c + "\n";
            }
            
        }catch(IOException ex){
            System.out.println("File not found!");
        }
        return text;
    }
    
    void readAndWrite(String inPath){
        String text = "";
        
        ZipFile zipFile;
        ZipEntry entry;
        InputStream input;
        BufferedReader in;
        
        try{
            zipFile = new ZipFile(inPath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            while(entries.hasMoreElements()){
                entry = entries.nextElement();
                if(!entry.isDirectory()){
                    if(entry.getName().endsWith(".txt")){
                        input = zipFile.getInputStream(entry);
                        in = new BufferedReader(new InputStreamReader(input));
                        
                        String c;
                        while((c = in.readLine()) != null){
                            System.out.println(c);

                        }
                    }
                }
            }
            
        }catch(IOException ex){
            System.out.println("File not found!");
        }
    }
}
