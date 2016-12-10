/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

/**
 *
 * @author Oskari Liukku
 */
public class PackageMachine implements Comparable {
    private String name;
    private GeoPoint location;
    private String availabilityInfo;
    
    PackageMachine(String n, GeoPoint loc, String availability){
        name = n;
        location = loc;
        availabilityInfo = availability;
    }
    
    public String GetName(){
        return name;
    }
    
    public String GetAvailability(){
        return availabilityInfo;
    }
    
    @Override
    public String toString(){
        //crop "Pakettiautomaatti" out of name for printing
        String[] str = name.split(", ", 2);
        return str[1] + " - " + location.GetAddress().GetStreetAddress() + ", " + location.GetAddress().GetPostcode() + " " + location.GetAddress().GetCity();
    }
    
    public GeoPoint GetLocation(){
        return location;
    }

    //for sorting
    @Override
    public int compareTo(Object o) {
        return location.GetAddress().GetCity().compareTo(((PackageMachine)o).GetLocation().GetAddress().GetCity());
    }
}
