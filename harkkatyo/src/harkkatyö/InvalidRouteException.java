/*
 * Oskari Liukku
 * 0435843
 */
package harkkatyö;

/**
 *
 * @author Oskari Liukku
 */
public class InvalidRouteException extends Exception {
    public InvalidRouteException (){}
    
    public InvalidRouteException (String message){
        super(message);
    }
}
