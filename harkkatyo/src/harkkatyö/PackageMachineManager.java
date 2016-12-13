/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Oskari Liukku
 */
public class PackageMachineManager {
    private static PackageMachineManager instance;
    public static PackageMachineManager GetInstance(){
        if(instance == null){
            instance = new PackageMachineManager();
        }
        return instance;
    }
    
    //list of packagemachines
    private ArrayList<PackageMachine> machines;
    
    //init list, find machines
    public void initMachineList(){
        machines = new ArrayList<>();
            DataBuilder.GetInstance().readXML("http://smartpost.ee/fi_apt.xml");
            //sorts by city name
            Collections.sort(machines);
            
    }
    
    public ArrayList<PackageMachine> getMachineList(){
        return machines;
    }
    
    public void addMachine(PackageMachine m){
        machines.add(m);
    }
    
    //return all unique cities in machine list
    public ArrayList<String> getUniqueCities(){
        ArrayList<String> cities = new ArrayList<>();
        String city;
        for(PackageMachine p : machines){
            city = p.getLocation().getAddress().getCity();
            if(!cities.contains(city)){
                cities.add(city);
            }
        }
        return cities;
    }
    
    //return list of machines at x city
    public ArrayList<PackageMachine> getMachinesAtCity(String city){
        ArrayList<PackageMachine> m = new ArrayList<>();
        for(PackageMachine machine : machines){
            if(machine.getLocation().getAddress().getCity().equals(city)){
                m.add(machine);
            }
        }
        return m;
    }
}
