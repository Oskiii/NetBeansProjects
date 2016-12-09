/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

/**
 *
 * @author Oskari Liukku
 */
public class Address {
    private int postCode;
    private String city;
    private String address;
    
    Address(int p, String c, String a){
        postCode = p;
        city = c;
        address = a; 
    }
    
    public String GetCity(){
        return city;
    }
    
    public String GetAddress(){
        return address;
    }
    
    public int GetPostcode(){
        return postCode;
    }
}
