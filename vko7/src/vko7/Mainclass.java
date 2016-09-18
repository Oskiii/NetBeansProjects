
package vko7;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Oski
 */
public class Mainclass {
    
    public static void main(String[] args) {
        Bank bank = new Bank();
        Mainclass mainclass = new Mainclass();
        bank.accounts = new ArrayList();
        boolean shutdown = false;

        do{
            shutdown = mainclass.Menu(bank);
        }while(shutdown == false);
    }

    boolean Menu(Bank bank){
        System.out.println(
                "\n*** PANKKIJÄRJESTELMÄ ***\n" +
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
                bank.AddAccount(0);
                break;
            case 2:
                bank.AddAccount(1);
                break;
            case 3:
                bank.AddMoneyToAccount();
                break;
            case 4:
                bank.TakeMoneyFromAccount();
                break;
            case 5:
                bank.DeleteAccount();
                break;
            case 6:
                bank.PrintOneAccount();
                break;
            case 7:
                bank.PrintAllAccounts();
                break;
            case 0:
                return true;
            default:
                System.out.println("Valinta ei kelpaa.");
                break;
        }

    return false;
    }
    
}
