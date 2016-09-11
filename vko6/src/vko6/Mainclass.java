/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko6;

import java.util.Scanner;

/**
 *
 * @author Oski
 */
public class Mainclass {

    Character currentChar;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Mainclass mainclass = new Mainclass();
        boolean shutdown = false;
        mainclass.currentChar = null;
        
        do{
            shutdown = mainclass.Menu();
        }while(shutdown == false);
    }
    
    boolean Menu(){
        System.out.println(
                "*** TAISTELUSIMULAATTORI ***\n" +
                "1) Luo hahmo\n" +
                "2) Taistele hahmolla\n" +
                "0) Lopeta");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch(choice){
            case 1:
                currentChar = CreateChar();
                break;
            case 2: 
                if(currentChar != null){
                    currentChar.fight();
                }else{
                    System.out.println("Luo ensin hahmo!");
                }
                
                break;
            case 0:
            default:
                return true;
        }
        return false;
    }
    
    Character CreateChar(){
        Character chara = null;
        WeaponBehavior wep = null;
        
        do{
            chara = CharTypeMenu();
            if(chara == null){
                System.out.println("Ei listalla!");
            }
            
        }while (chara == null);
        
        do{
            wep = WeaponTypeMenu();
            if(wep == null){
                System.out.println("Ei listalla!");
            }
            
        }while (wep == null);
        
        chara.weapon = wep;
        
        return chara;
    }
    
    Character CharTypeMenu(){
        System.out.println(
                "Valitse hahmosi: \n" +
                "1) Kuningas\n" +
                "2) Ritari\n" +
                "3) Kuningatar\n" + 
                "4) Peikko");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch(choice){
            case 1:
                return new King();
            case 2:
                return new Knight();
            case 3:
                return new Queen();
            case 4:
                return new Troll();
            default:
                return null;
        }
    }
    
    WeaponBehavior WeaponTypeMenu(){
        System.out.println(
                "Valitse aseesi: \n" +
                "1) Veitsi\n" +
                "2) Kirves\n" +
                "3) Miekka\n" + 
                "4) Nuija");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch(choice){
            case 1:
                return new KnifeBehavior();
            case 2:
                return new AxeBehavior();
            case 3:
                return new SwordBehavior();
            case 4:
                return new ClubBehavior();
            default:
                return null;
        }
    }
    
    
}
