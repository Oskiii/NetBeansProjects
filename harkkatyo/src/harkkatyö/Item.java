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
public class Item {
    protected String name;
    protected Float[] dimensions;
    protected Float weight;
    protected Boolean fragile;
    
    Item(String n, Float[] d, Float w, Boolean f){
        name = n;
        dimensions = d;
        weight = w;
        fragile = f;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    //return dimensions in string form
    public String dimensionsToString(){
        return dimensions[0].toString() + "*" + dimensions[1].toString() + "*" + dimensions[2].toString();
    }
    
    public Float[] getDimensions(){
        return dimensions;
    }
    
    public Float getWeight(){
        return weight;
    }
    
    public Boolean getFragility(){
        return fragile;
    }
    
    public String getName(){
        return name;
    }
}
