/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Oskari Liukku
 */
class Mainclass{
    BottleDispenser disp;
    
    public void main(String[] args) {
        disp = new BottleDispenser();
        
    }
    
    void PrintMenu(){
        System.out.println("*** LIMSA-AUTOMAATTI ***\n" +
"1) Lisää rahaa koneeseen\n" +
"2) Osta pullo\n" +
"3) Ota rahat ulos\n" +
"4) Listaa koneessa olevat pullot\n" +
"0) Lopeta");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
    }
    
    void ListBottles(){
        for(int i = 0; i < disp.bottle_array.size(); i++){
            
        }
    }
}

class BottleDispenser {
    
    private int bottles;
    private int money;
    public  ArrayList bottle_array;
    
    public BottleDispenser() {
        bottles = 5;
        money = 0;
        bottle_array = new ArrayList();
        
        for(int i = 0;i<bottles;i++) {
            bottle_array.add(new Bottle());
        }
    }
    
    public void addMoney() {
        money += 1;
        System.out.println("Klink! Lisää rahaa laitteeseen!");
    }
    
    public void buyBottle() {
        Bottle bottle = (Bottle)bottle_array.get(0);
        if(bottles < 1){
            System.out.println("Masiina tyhjä!");
            return;
        }else if(money < bottle.price){
            System.out.println("Syötä rahaa ensin!");
            return;
        }
        
        bottles -= 1;
        System.out.println("KACHUNK! " + bottle.name + " tipahti masiinasta!");
        money -= bottle.price;
        bottle_array.remove(0);
    }
    
    public void returnMoney() {
        money = 0;
        System.out.println("Klink klink. Sinne menivät rahat!");
    }
}

class Bottle{
    public String name = "Pepsi Max";
    public String type = "Pepsi";
    public float size = 0.5f;
    public float price = 1.80f;
    
    public Bottle(){
        
    }
    
}
