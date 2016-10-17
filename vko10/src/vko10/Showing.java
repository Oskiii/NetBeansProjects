/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko10;

import java.util.Date;

/**
 *
 * @author Oski
 */
public class Showing {
    private String title;
    private Date startTime;
    private Date endTime;

    public Showing(String name, Date start, Date end){
        title = name;
        startTime = start;
        endTime = end;
    }
    
    public String GetTitle(){
        return title;
    }
    
    public Date GetStartTime(){
        return startTime;
    }
    
    public Date GetEndTime(){
        return endTime;
    }
}
