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
    
    FirstClassPackage(PackageMachine source, PackageMachine dest) throws InvalidRouteException{
        super(source, dest);

        speed = 1;
        maxDistance = 150;
        dimensions = new Float[] {30f,30f,30f};
        classString = "1st";
        classInt = 1;
    }
    
    //set item, check for fragility
    public void setItem(Item item) throws PackageSizeException, FragilityException {
        if(item.fragile){
            throw new FragilityException();
        }
        
        super.setItem(item);
    }
}
