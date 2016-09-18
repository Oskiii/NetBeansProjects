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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oski
 */
public class Bank {
    public ArrayList accounts;
    
    
    
    void AddAccount(int type){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.print("Syötä tilinumero: ");
            String num = in.readLine();
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            
            int limit = 0;
            
            switch(type){
                case 0:
                default:
                    accounts.add(new DebitAccount(num, amount));
                    break;
                case 1:
                    System.out.print("Syötä luottoraja: ");
                    limit = Integer.parseInt(in.readLine());
                    accounts.add(new CreditAccount(num, amount, limit));
                    break;
            }
            
            /*System.out.println("Tilinumero: " + num);
            System.out.println("Rahamäärä: " + amount);
            if(limit > 0) System.out.println("Luottoraja: " + limit);*/
        
            
            
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
            
            //if(account == null) return;
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            
            if(account != null){
                account.AddMoney(amount); 
            }
           
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void DeleteAccount(){
        
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Syötä poistettava tilinumero: ");
            String num = in.readLine();
            Account account = FindAccountInList(num);
            
            if(account != null){
                accounts.remove(account);   
            }
            System.out.println("Tili poistettu.");
            
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
            
            System.out.print("Syötä rahamäärä: ");
            int amount = Integer.parseInt(in.readLine());
            
            if(account != null) {
                account.TakeMoney(amount);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void PrintOneAccount(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Syötä tulostettava tilinumero: ");
            String num = in.readLine();
            PrintOneAccount(num);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void PrintOneAccount(String num){
        Account account = FindAccountInList(num);
        
        if(account == null) return;
        
        account.Print();
        
    }
    
    void PrintAllAccounts(){
        System.out.println("Kaikki tilit:");
        for(Object o : accounts){
            Account a = (Account) o;
            a.Print();
        }
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
        /*if(account == null){
            System.out.print("Tiliä ei löytynyt.");
            return null;
        }*/
        
        return account;
    }
    
}



