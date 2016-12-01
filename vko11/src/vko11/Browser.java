/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Oski
 */
public class Browser {
    private static Browser instance;
    public static Browser GetInstance(){
        if(instance == null){
            instance = new Browser();
        }
        return instance;
    }
    
    private ListIterator itr;
    private Page currentPage;
    private boolean lastMoveRight;
    
    private Browser(){
        pageHistory = new ArrayList<>();
        itr = pageHistory.listIterator();
    }
    
    private ArrayList<Page> pageHistory;
    public ArrayList<Page> GetHistory(){
        return pageHistory;
    }
    
    public Page GetCurrentPage(){
        return currentPage;
    }
    
    public void SetCurrentPage(Page p){
        currentPage = p;
        System.out.println("Current page: " + currentPage.GetURL());
    }
    
    public Page GetNextPage(){
        
        if(itr.hasNext()){
            if(!lastMoveRight)
            //if(!itr.hasPrevious()){
                itr.next();
            //}
            lastMoveRight = true;
            return (Page)itr.next();
        }
        lastMoveRight = true;
        return null;
    }
    
    public Page GetPreviousPage(){
        
        if(itr.hasPrevious()){
            if(lastMoveRight)
            //if(!itr.hasNext()){
                itr.previous();
            //}
            lastMoveRight = false;
            return (Page)itr.previous();
        }
        lastMoveRight = false;
        return null;
    }
    
    public void AddPageToHistory(String url){
        //remove all items to the right from history
        if(itr.hasNext() && !lastMoveRight)
            itr.next();
        while(itr.hasNext()){
            itr.next();
            itr.remove();
        }
        
        while(itr.hasNext()){
            itr.next();
        }
        
        //add new page to history
        itr.add(new Page(url));
        PrintHistory();
        
        if(pageHistory.size() >= 21){
            RemoveFirst();
        }
        lastMoveRight = true;
    }
    
    void RemoveFirst(){
        while(itr.hasPrevious()){
            itr.previous();
        }
        itr.remove();
        PrintHistory();
        while(itr.hasNext()){
            itr.next();
        }
    }
    
    void PrintHistory(){
        System.out.println("History:");
        for(Page p : pageHistory){
            System.out.println(p.GetURL());
        }
        System.out.println("Current page: " + currentPage.GetURL());
    }
}
