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
public class FirstClassPackage extends Package {
    private int maxDistance;
    
    FirstClassPackage(PackageMachine source, PackageMachine dest){
        speed = 1;
        maxDistance = 150;
        dimensions = new Float[] {30f,30f,30f};
        originMachine = source;
        destinationMachine = dest;
    }
    
    
}
