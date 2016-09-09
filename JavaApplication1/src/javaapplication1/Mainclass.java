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
    BottleDispenser disp = new BottleDispenser();
    public static void main(String[] args) {
        
        Mainclass mainclass = new Mainclass();
        boolean shutdown = false;
        
        do{
            shutdown = mainclass.Menu();
        }while(shutdown == false);
    }
    
    boolean Menu(){
        System.out.println(
                "\n*** LIMSA-AUTOMAATTI ***\n" +
                "1) Lisää rahaa koneeseen\n" +
                "2) Osta pullo\n" +
                "3) Ota rahat ulos\n" +
                "4) Listaa koneessa olevat pullot\n" +
                "0) Lopeta");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch(choice){
            case 1:
                disp.addMoney();
                break;
            case 2:
                BuyBottle();
                break;
            case 3:
                disp.returnMoney();
                break;
            case 4:
                ListBottles();
                break;
            default:
                return true;
             
        }
        return false;
    }
    
    void BuyBottle(){
        ListBottles();
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        disp.buyBottle(choice - 1);
    }
    
    void ListBottles(){
        Bottle b;
        for(int i = 0; i < disp.bottle_array.size(); i++){
            b = (Bottle)disp.bottle_array.get(i);
            System.out.println((i+1) + ". Nimi: " + b.name);
            System.out.println("\tKoko: " + b.size + "\tHinta: " + b.price);
        }
    }
}

class BottleDispenser {
    
    private float money;
    public  ArrayList bottle_array;
    
    public BottleDispenser() {
        money = 0;
        bottle_array = new ArrayList();
        
        bottle_array.add(new Bottle("Pepsi Max", 0.5f, 1.8f));
        bottle_array.add(new Bottle("Pepsi Max", 1.5f, 2.2f));
        bottle_array.add(new Bottle("Coca-Cola Zero", 0.5f, 2.0f));
        bottle_array.add(new Bottle("Coca-Cola Zero", 1.5f, 2.5f));
        bottle_array.add(new Bottle("Fanta Zero", 0.5f, 1.95f));
        bottle_array.add(new Bottle("Fanta Zero", 0.5f, 1.95f));
    }
    
    public void addMoney() {
        money += 1;
        System.out.println("Klink! Lisää rahaa laitteeseen!");
    }
    
    public void buyBottle(int loc) {
        Bottle bottle = (Bottle)bottle_array.get(loc);
        if(bottle_array.size() < 1){
            System.out.println("Masiina tyhjä!");
            return;
        }else if(money < bottle.price){
            System.out.println("Syötä rahaa ensin!");
            return;
        }
        
        System.out.println("KACHUNK! " + bottle.name + " tipahti masiinasta!");
        money -= bottle.price;
        bottle_array.remove(loc);
    }
    
    public void returnMoney() {
        
        System.out.println("Klink klink. Sinne menivät rahat! Rahaa tuli ulos " + String.format("%.2f", money) + "€");
        money = 0;
    }
}

class Bottle{
    public String name = "Pepsi Max";
    public float size = 0.5f;
    public float price = 1.80f;
    
    public Bottle(String n, float s, float p){
        name = n;
        size = s;
        price = p;
    }
    
}
