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
    
    private ArrayList<PackageMachine> machines;
    
    public void InitMachineList(){
        machines = new ArrayList<>();
            DataBuilder.GetInstance().ReadXML();
            //sorts by city name
            Collections.sort(machines);
    }
    
    public ArrayList<PackageMachine> GetMachineList(){
        return machines;
    }
    
    public void AddMachine(PackageMachine m){
        machines.add(m);
    }
    
    public ArrayList<String> GetUniqueCities(){
        ArrayList<String> cities = new ArrayList<>();
        String city;
        for(PackageMachine p : machines){
            city = p.GetLocation().GetAddress().GetCity();
            if(!cities.contains(city)){
                cities.add(city);
            }
        }
        return cities;
    }
    
    public ArrayList<PackageMachine> GetMachinesAtCity(String city){
        ArrayList<PackageMachine> m = new ArrayList<>();
        for(PackageMachine machine : machines){
            if(machine.GetLocation().GetAddress().GetCity().equals(city)){
                m.add(machine);
            }
        }
        return m;
    }
}
