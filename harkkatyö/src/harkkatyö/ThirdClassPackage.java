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
public class ThirdClassPackage extends Package {
    private int maxDistance;
    
    ThirdClassPackage(PackageMachine source, PackageMachine dest){
        speed = 3;
        maxDistance = 150;
        dimensions = new Float[] {50f,50f,50f};
        originMachine = source;
        destinationMachine = dest;
    }
    
    
}
