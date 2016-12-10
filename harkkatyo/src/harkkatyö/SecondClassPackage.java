/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harkkatyö;

/**
 *
 * @author Oski
 */
public class SecondClassPackage extends Package {
    
    SecondClassPackage(PackageMachine source, PackageMachine dest) throws InvalidRouteException{
        super(source, dest);
        speed = 2;
        dimensions = new Float[] {10f,10f,10f};
        classString = "2nd";
        classInt = 2;
    }
    
    
}
