/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

/**
 *
 * @author Oskari Liukku
 */
public class GeoPoint {
    private Address address;
    private double latitude;
    private double longitude;
    
    GeoPoint(Address a, double lat, double lng){
        address = a;
        latitude = lat;
        longitude = lng;
    }
    
    public Address getAddress(){
        return address;
    }
    
    public double getLatitude(){
        return latitude;
    }
    
    public double getLongitude(){
        return longitude;
    }
    
    
}
