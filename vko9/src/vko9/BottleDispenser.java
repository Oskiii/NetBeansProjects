/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko9;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Oski
 */
public class BottleDispenser {
    private static BottleDispenser instance = null;
    
    private float money;
    public  ArrayList bottle_array;
    private static Bottle lastPurchase;
    
    public static BottleDispenser getInstance(){
        if(instance == null){
            instance = new BottleDispenser();
        }
        return instance;
    }
    
    public BottleDispenser() {
        money = 0;
        bottle_array = new ArrayList();
        
        bottle_array.add(new Bottle("Pepsi Max", 0.5f, 1.8f));
        bottle_array.add(new Bottle("Pepsi Max", 1.5f, 2.2f));
        bottle_array.add(new Bottle("Coca-Cola Zero", 0.5f, 2.0f));
        bottle_array.add(new Bottle("Coca-Cola Zero", 1.5f, 2.5f));
        bottle_array.add(new Bottle("Fanta Zero", 0.5f, 1.95f));
        bottle_array.add(new Bottle("Fanta Zero", 1.5f, 1.95f));
        
    }
    
    public void addMoney(float amount) {
        money += amount;
        FXMLDocumentController.printToField(String.format("Klink! Laitteessa on nyt %.2f€", money));
    }
    
    public void buyBottle(int loc) {
        
        if(bottle_array.size() <= 0){
            FXMLDocumentController.printToField("Masiina tyhjä!");
            return;
        }
        
        if(loc < 0){
            FXMLDocumentController.printToField("Tuotetta ei ole jäljellä!");
            return;
        }
        
        System.out.println(loc);
        Bottle bottle = (Bottle)bottle_array.get(loc);
        if(money < bottle.price){
            FXMLDocumentController.printToField("Syötä rahaa ensin!");
            return;
        }
        
        FXMLDocumentController.printToField("KACHUNK! " + bottle.name + " tipahti masiinasta!");
        money -= bottle.price;
        bottle_array.remove(loc);
    }
    
    public void returnMoney() {
        
        FXMLDocumentController.printToField("Klink klink. Sinne menivät rahat! Rahaa tuli ulos " + String.format("%.2f", money) + "€");
        money = 0;
    }
    
    public int GetBottleID(Bottle bottleToFind){
        for(int i = 0; i < bottle_array.size(); i++){
            Bottle bottle = (Bottle)bottle_array.get(i);
            System.out.println(bottle.name + " " + bottle.size);
            System.out.println("5" + bottleToFind.name + " " + bottleToFind.size);
            if(bottle.name.equals(bottleToFind.name) && bottle.size == bottleToFind.size){
                System.out.println("bottleid: " + i);
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList GetBottleSizes(String bottleToFind){
        ArrayList foundSizes = new ArrayList();
        
        for(int i = 0; i < bottle_array.size(); i++){
            Bottle bottle = (Bottle)bottle_array.get(i);
            if(bottle.name.equals(bottleToFind)){
                foundSizes.add(bottle.size);
            }
        }
        return foundSizes;
    }
    
    void ListBottles(){
        Bottle b;
        for(int i = 0; i < bottle_array.size(); i++){
            b = (Bottle)bottle_array.get(i);
            FXMLDocumentController.printToField((i+1) + ". Nimi: " + b.name);
            FXMLDocumentController.printToField("\tKoko: " + b.size + "\tHinta: " + b.price);
        }
    }
    
    public void SetLastPurchase(Bottle b){
        lastPurchase = b;
    }
    
    public void WriteReceipt(){
        if(lastPurchase == null){
            FXMLDocumentController.printToField("Osta ensin jotain!");
            return;
        }
        
        String location = System.getProperty( "user.dir" ) + "/receipt.txt";
        DecimalFormat format = new DecimalFormat("0.#");
        
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter( location));
            String c;
            
            out.write("KUITTI\r\n");
            out.write(lastPurchase.name);
            
            out.write(" " + format.format(lastPurchase.size) + "l");
            out.write("\t" + format.format(lastPurchase.price) + "€");
            
            out.close();
            
            FXMLDocumentController.printToField("Receipt saved to " + location);
            
        }catch(IOException ex){
            System.out.println("File not found!");
        }
    }
    
    
}
