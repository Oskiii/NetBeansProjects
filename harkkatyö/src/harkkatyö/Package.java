/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harkkatyö;

import java.util.ArrayList;

/**
 *
 * @author Oski
 */
public abstract class Package {
    protected int speed;
    protected Float[] dimensions;
    private Item containsItem;
    protected PackageMachine originMachine;
    protected PackageMachine destinationMachine;
    
    public void AddItem(Item i){
        containsItem = i;
    }
    
    @Override
    public String toString(){
        return containsItem.toString();
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
}
