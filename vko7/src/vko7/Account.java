/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko7;

/**
 *
 * @author Oski
 */
public class Account{
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
    
    void Print(){
        
        if(creditLimit > 0){
            System.out.println("Tilinumero: " + number + " Tilillä rahaa: " + money + " Luottoraja: " + creditLimit);
        }else{
            System.out.println("Tilinumero: " + number + " Tilillä rahaa: " + money);
        }
    }
    
}

class CreditAccount extends Account{

    CreditAccount(String n, int m, int l){
        number = n;
        money = m;
        creditLimit = l;
        
        System.out.println("Tili luotu.");
    }
}

class DebitAccount extends Account{
    
    DebitAccount(String n, int m){
        number = n;
        money = m;
        
        System.out.println("Tili luotu.");

    }
}
