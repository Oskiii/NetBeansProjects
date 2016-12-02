/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko12;

import java.util.HashSet;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Oski
 */
public class Point {
    String name;
    Circle circle;
    Boolean selected;
    
    Point(double mouseX, double mouseY, int r){
        circle = new Circle(mouseX, mouseY, r, Color.RED);
        circle.setOnMouseClicked(pointOnMouseClickedEventHandler);
        ShapeHandler.GetInstance().AddPoint(this);
    }
    
    public Circle getCircle(){
        return circle;
    }
    
    public void Select(){
        selected = true;
        circle.setFill(Color.GREEN);
        
    }
    
    public void Deselect(){
        selected = false;
        circle.setFill(Color.RED);
    }
    
    EventHandler<MouseEvent> pointOnMouseClickedEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            if(t.getButton() == MouseButton.SECONDARY){
                System.out.println("Hei, olen piste!");
                
                ShapeHandler.GetInstance().AddSelected(Point.this);
            }
        }
    };
}
