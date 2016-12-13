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
    
    public String getCity(){
        return city;
    }
    
    public String getStreetAddress(){
        return address;
    }
    
    public int getPostcode(){
        return postCode;
    }
    
    public String toString(){
        return address + ", " + postCode + " " + city;
    }
}
