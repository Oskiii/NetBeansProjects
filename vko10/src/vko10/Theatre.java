/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko10;

/**
 *
 * @author Oski
 */
public class Theatre {
    private String name;
    private int ID;
    
    public Theatre(String n, int id){
        ID = id;
        name = n;
    }
    
    public String GetName(){
        return name;
    }
}
