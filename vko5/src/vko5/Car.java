/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko5;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Oski
 */
public class Car {
    
    ArrayList parts;
    Map<String, Integer> partsInCar;
    
    Car(){
        parts = new ArrayList();
        parts.add(new Body());
        parts.add(new Chassis());
        parts.add(new Engine());
        parts.add(new Wheel());
        parts.add(new Wheel());
        parts.add(new Wheel());
        parts.add(new Wheel());
        partsInCar = new HashMap<>();
    }
    
    public void print(){
        
        for (Object i : parts){
            CarPart p = (CarPart) i;
            if(!partsInCar.containsKey(p.name)){
                partsInCar.put(p.name, 1);
            }else{
                int amount = partsInCar.get(p.name);
                partsInCar.put(p.name, amount + 1);
            }
        }
        
        System.out.println("Autoon kuuluu:");
        for(Map.Entry<String, Integer> entry : partsInCar.entrySet()){
            System.out.print("\t");
            if(entry.getValue() > 1){
                System.out.print(entry.getValue() + " ");
            }
            System.out.println(entry.getKey());
        }
    }
}

class CarPart{
    String name;
}

class Body extends CarPart{
    Body(){
        name = "Body";
        System.out.println("Valmistetaan " + name);
    }
}

class Chassis extends CarPart{
    Chassis(){
        name = "Chassis";
        System.out.println("Valmistetaan " + name);
    }
}

class Engine extends CarPart{
    Engine(){
        name = "Engine";
        System.out.println("Valmistetaan " + name);
    }
}

class Wheel extends CarPart{
    Wheel(){
        name = "Wheel";
        System.out.println("Valmistetaan " + name);
    }
}