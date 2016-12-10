/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harkkatyö;

import java.io.Serializable;

/**
 *
 * @author Oski
 */
public abstract class Package implements Serializable{
    protected int speed;
    protected Float[] dimensions;
    private Item containsItem;
    protected PackageMachine originMachine;
    protected PackageMachine destinationMachine;
    protected int classInt;
    protected String classString;
    protected int maxDistance;
    
    Package(PackageMachine orig, PackageMachine dest) throws InvalidRouteException{

        //if origin == destination machine, error
        if(orig == dest){
            throw new InvalidRouteException("The origin and destination are the same machine!");
        }
        originMachine = orig;
        destinationMachine = dest;
    }
    
    //returns package's max distance
    public int GetMaxDistance(){
        return maxDistance;
    }
    
    //sets item in package
    public void SetItem(Item i) throws PackageSizeException, FragilityException{
        
        //if item doesn't fit
        if(i.GetDimensions()[0] > dimensions[0] || i.GetDimensions()[1] > dimensions[1] || i.GetDimensions()[2] > dimensions[2]){
            throw new PackageSizeException("Item doesn't fit! Dimensions of " + classString + " class package are " + GetDimensionsString() + " cm!");
        }
        containsItem = i;
    }
    
    public Item GetItem(){
        return containsItem;
    }
    
    public int GetClassInt(){
        return classInt;
    }
    
    public String GetClassString(){
        return classString;
    }
    
    @Override
    public String toString(){
        return containsItem.toString() + ", " + classString + " Class";
    }
    
    public PackageMachine GetOriginMachine(){
        return originMachine;
    }
    
    public PackageMachine GetDestinationMachine(){
        return destinationMachine;
    }
    
    public int GetSpeed(){
        return speed;
    }
    
    //get dimensions in string form
    public String GetDimensionsString(){
        return dimensions[0] + "*" + dimensions[1] + "*" + dimensions[2];
    }
}
