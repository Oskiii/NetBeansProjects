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
    
    ThirdClassPackage(PackageMachine source, PackageMachine dest) throws InvalidRouteException{
        super(source, dest);
        speed = 3;
        dimensions = new Float[] {50f,50f,50f};
        classString = "3rd";
        classInt = 3;
    }
    
    //set item, check for fragility
    public void setItem(Item item) throws PackageSizeException, FragilityException {
        if(item.fragile){
            throw new FragilityException();
        }
        
        super.setItem(item);
    }
}
