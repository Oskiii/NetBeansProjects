/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.util.ArrayList;

/**
 *
 * @author Oskari Liukku
 */
public class Storage {
    private static Storage instance;
    public static Storage GetInstance(){
        if(instance == null){
            instance = new Storage();
        }
        return instance;
    }
    
    Storage(){
        packagesInStorage = new ArrayList<>();
    }

    private ArrayList<Package> packagesInStorage;
    
    public ArrayList<Package> GetPackages(){
        return packagesInStorage;
    }
    
}
