/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko12;

import java.util.ArrayList;
import javafx.scene.shape.Line;

/**
 *
 * @author Oski
 */
public class ShapeHandler {
    private static ShapeHandler instance;
    public static ShapeHandler GetInstance(){
        if(instance == null){
            instance = new ShapeHandler();
        }
        return instance;
    }
    
    private ArrayList<Point> points;
    private ArrayList<Line> lines;
    private Point firstSelected;
    private Point secondSelected;
    private int maxNumOfLines = 1;
    
    ShapeHandler(){
        points = new ArrayList<>();
        lines = new ArrayList<>();
    }
    
    public void SetMaxLines(int i){
        maxNumOfLines = i;
    }
    
    public Point GetFirstSelected(){
        return firstSelected;
    }
    
    public Point GetSecondSelected(){
        return secondSelected;
    }
    
    public void AddPoint(Point p){
        points.add(p);
    }
    
    public Line GetNewLine(){
        if(lines.size() >= maxNumOfLines){
            Line l = lines.get(0);
            lines.remove(0);
            return l;
        }else{
            return new Line();
        }
    }
    
    public void AddLine(Line l){
        lines.add(l);
    }
    
    public void AddSelected(Point p){
        if(firstSelected != null){
            firstSelected.Deselect();
        }
        
        firstSelected = secondSelected;
        secondSelected = p;
        p.Select();
    }
}
