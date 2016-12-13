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
    public int getMaxDistance(){
        return maxDistance;
    }
    
    //sets item in package
    public void setItem(Item i) throws PackageSizeException, FragilityException{
        
        //if item doesn't fit
        if(i.getDimensions()[0] > dimensions[0] || i.getDimensions()[1] > dimensions[1] || i.getDimensions()[2] > dimensions[2]){
            throw new PackageSizeException("Item doesn't fit! Dimensions of " + classString + " class package are " + getDimensionsString() + " cm!");
        }
        containsItem = i;
    }
    
    public Item getItem(){
        return containsItem;
    }
    
    public int getClassInt(){
        return classInt;
    }
    
    public String getClassString(){
        return classString;
    }
    
    @Override
    public String toString(){
        return containsItem.toString() + ", " + classString + " Class";
    }
    
    public PackageMachine getOriginMachine(){
        return originMachine;
    }
    
    public PackageMachine getDestinationMachine(){
        return destinationMachine;
    }
    
    public int getSpeed(){
        return speed;
    }
    
    //get dimensions in string form
    public String getDimensionsString(){
        return dimensions[0] + "*" + dimensions[1] + "*" + dimensions[2];
    }
}
