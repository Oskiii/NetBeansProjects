/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oski
 */
public class Mainclass {

    ArrayList accounts;
    
    public static void main(String[] args) {
        Mainclass mainclass = new Mainclass();
        mainclass.accounts = new ArrayList();
        boolean shutdown = false;

        do{
            shutdown = mainclass.Menu();
        }while(shutdown == false);
    }

    boolean Menu(){
        System.out.println(
                "\n*** PANKKIAUTOMAATTI ***\n" +
                "1) Lisää tavallinen tili\n" +
                "2) Lisää luotollinen tili\n" +
                "3) Tallenna tilille rahaa\n" +
                "4) Nosta tililtä\n" +
                "5) Poista tili\n" +
                "6) Tulosta tili\n" +
                "7) Tulosta kaikki tilit\n" +
                "0) Lopeta");
        System.out.print("Valintasi: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch(choice){
            case 1:
                AddAccount(0);
                break;
            case 2:
                AddAccount(1);
                break;
            case 3:
                AddMoneyToAccount();
                break;
            case 4:
                TakeMoneyFromAccount();
                break;
            case 5:
                DeleteAccount();
                break;
            case 6:
                PrintOneAccount();
                break;
            case 7:
                PrintAllAccounts();
                break;
            default:
                System.out.println("Valinta ei kelpaa.");
                break;
        }

    return false;
    }
    
    void AddAccount(int type){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.print("Syötä tilinumero: ");
            String num = in.readLine();
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            
            switch(type){
                case 0:
                default:
                    accounts.add(new DebitAccount(num, amount));
                    break;
                case 1:
                    System.out.print("Syötä luottoraja: ");
                    int limit = Integer.parseInt(in.readLine());
                    break;
            }
        
            
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void AddMoneyToAccount(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Syötä tilinumero: ");
            String num = in.readLine();
            
            Account account = FindAccountInList(num);
            
            if(account == null) return;
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            account.AddMoney(amount);
            System.out.println("Tilinumero: " + account.number);
            System.out.println("Rahamäärä: " + amount);
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void DeleteAccount(){
        
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Valitse poistettava tilinumero: ");
            String num = in.readLine();
            Account account = FindAccountInList(num);
            
            if(account != null){
                accounts.remove(account);
                System.out.println("Tilinumero: " + num);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void TakeMoneyFromAccount(){
        
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Syötä tilinumero: ");
            String num = in.readLine();
            Account account = FindAccountInList(num);
            
            if(account == null) return;
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            
            account.TakeMoney(amount);
            System.out.println("Tilinumero: " + account.number);
            System.out.println("Rahamäärä: " + amount);
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void PrintOneAccount(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Syötä tulostettava tilinumero: ");
            String num = in.readLine();
            Account account = FindAccountInList(num);
            
            if(account == null) return;
            
            System.out.println("Tilinumero: " + account.number);
            System.out.println("Rahamäärä: " + account.money);
            if(account.creditLimit > 0) System.out.println("Luottoraja: " + account.creditLimit);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void PrintAllAccounts(){
        //TODO: this function
    }
    
    Account FindAccountInList(String num){
        Account account = null;
        //find account in list
        for(Object o : accounts){
            Account a = (Account) o;

            //found it
            if(a.number.equals(num)){
                account = a;
            }
        }
        
        //never found it
        if(account == null){
            System.out.print("Tiliä ei löytynyt.");
            return null;
        }
        
        return account;
    }
    
}

class Account{
    String number;
    int money;
    int creditLimit = 0;
    
    void AddMoney(int amount){
        money += amount;
    }
    
    void TakeMoney(int amount){
        if(amount <= money + creditLimit){
            money -= amount;
        }else{
            System.out.println("Ei riittävästi rahaa tilillä!");
        }
    }
    
}

class CreditAccount extends Account{

    CreditAccount(String n, int m, int l){
        number = n;
        money = m;
        creditLimit = l;
        
        System.out.println("Tilinumero: " + number);
        System.out.println("Rahamäärä: " + money);
        System.out.println("Luotto: " + creditLimit);
    }
}

class DebitAccount extends Account{
    
    DebitAccount(String n, int m){
        number = n;
        money = m;
        
        System.out.println("Tilinumero: " + number);
        System.out.println("Rahamäärä: " + money);
    }
}
