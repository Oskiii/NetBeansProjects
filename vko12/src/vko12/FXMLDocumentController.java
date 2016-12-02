/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vko12;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author Oski
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    public AnchorPane anchor;
    @FXML
    private Pane pane;
    @FXML
    private Button drawLineButton;
    @FXML
    private TextField maxLinesField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        anchor.setOnMouseClicked(canvasOnMouseClickedEventHandler);
        anchor.setPickOnBounds(false);
    }
    
    EventHandler<MouseEvent> canvasOnMouseClickedEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            if(t.getButton() == MouseButton.PRIMARY){
                System.out.println("Lisätään piste!");
                mouseClickedAction(t);
            }
        }
    };

    private void mouseClickedAction(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        System.out.println("click");
        Point p = new Point(mouseX, mouseY, 10);

        anchor.getChildren().add(p.getCircle());
    }

    @FXML
    private void drawLineButtonAction(ActionEvent event) {
        try{
            int i = Integer.parseInt(maxLinesField.getText());
            ShapeHandler.GetInstance().SetMaxLines(i);
        }catch(NumberFormatException ex){
            System.out.println("Give a real number!");
        }
        
        if(ShapeHandler.GetInstance().GetFirstSelected() == null || ShapeHandler.GetInstance().GetSecondSelected() == null) return;
        
        double startX = ShapeHandler.GetInstance().GetFirstSelected().getCircle().getCenterX();
        double startY = ShapeHandler.GetInstance().GetFirstSelected().getCircle().getCenterY();
        double endX = ShapeHandler.GetInstance().GetSecondSelected().getCircle().getCenterX();
        double endY = ShapeHandler.GetInstance().GetSecondSelected().getCircle().getCenterY();
        Line line = ShapeHandler.GetInstance().GetNewLine();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        line.setStrokeWidth(5);
        
        if(!anchor.getChildren().contains(line)){
            anchor.getChildren().add(line);
        }
        line.toBack();

        ShapeHandler.GetInstance().AddLine(line);
    }
}
