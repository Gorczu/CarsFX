/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneElements;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

/**
 *
 * @author Krzysiek
 */
public class Propeller extends Pane {

    private double w = 14;
    private double h = 14;
    private int numberOfWheeleArc = 4;
    private double radius = Math.min(w, h) * 0.45;
    private Arc arc[] = new Arc[numberOfWheeleArc];
    private double startAngle = 30;
    private Circle circle = new Circle(w / 2, h / 2, getRadius());

    
    private double increment = 8;
    public Propeller(double radius, double centerX, double centerY,  int numberOfWheeleArc, double w, double h) {

        this.w = w;
        this.h = h;
        radius = Math.min(w, h) * 0.45;
        circle = new Circle(w / 2, h / 2, getRadius());
        this.numberOfWheeleArc = numberOfWheeleArc;
        arc = new Arc[numberOfWheeleArc];
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        getChildren().add(circle);

        for (int i = 0; i < numberOfWheeleArc; i++) {
            arc[i] = new Arc(centerX, centerY, radius, radius, startAngle + i * 90, 35);
            arc[i].setFill(Color.RED); // Set fill color
            arc[i].setType(ArcType.ROUND);
            
            getChildren().addAll(arc[i]);
        }
        /*
        MotionBlur mb  = new MotionBlur();
        mb.setRadius(12.0f);
        mb.setAngle(2.0f);
 
        this.setEffect(mb);
        */
    }

    public void reverse(double centerX, double centerY, double rotationMultiplicator) {

        setStartAngle(centerX, centerY, startAngle + (rotationMultiplicator * increment));
    }

    public void move(double centerX, double centerY, double rotationMultiplicator) {
        setStartAngle(centerX, centerY, startAngle - (rotationMultiplicator * increment));
    }

    public void setStartAngle(double centerX, double centerY, double angle) {
        startAngle = angle;
        setValues(centerX, centerY);
    }

    public void setValues(double centerX, double centerY) {
        setRadius(Math.min(w, h) * 0.45);
        circle.setRadius(getRadius());
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);

        for (int i = 0; i < 4; i++) {
            arc[i].setRadiusX(getRadius() * 0.9);
            arc[i].setRadiusY(getRadius() * 0.9);
            arc[i].setCenterX(centerX);
            arc[i].setCenterY(centerY);
            arc[i].setStartAngle(startAngle + i * 90);
        }
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

}
