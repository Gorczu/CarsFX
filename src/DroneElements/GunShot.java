/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneElements;

import javafx.scene.layout.Border;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Krzysiek
 */
public class GunShot extends Circle{
    
    private Vector2d volocityDirection;
    private double baseX = 0;
    private double baseY = 0;
    private boolean isEnemyShot = true;
   
    public GunShot(double x, double y, Vector2d direction, boolean isEnemyShot){
       
        super(x, y, 3, Color.BLACK);
        this.baseX = x;
        this.baseY = y;
        this.isEnemyShot = isEnemyShot;
        volocityDirection = new Vector2d(direction.getX(), direction.getY());
        
    }
    
    private double deltaLenght = 5.0;

    /**
     * @return the baseX
     */
    public double getBaseX() {
        return baseX;
    }

    /**
     * @param baseX the baseX to set
     */
    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }

    /**
     * @return the baseY
     */
    public double getBaseY() {
        return baseY;
    }

    /**
     * @param baseY the baseY to set
     */
    public void setBaseY(double baseY) {
        this.baseY = baseY;
    }

    /**
     * @return the deltaLenght
     */
    public double getDeltaLenght() {
        return deltaLenght;
    }

    
    /**
     * @param deltaLenght the deltaLenght to set
     */
    public void setDeltaLenght(double deltaLenght) {
        this.deltaLenght = deltaLenght;
    }

    
    /**
     * @return the volocityDirection
     */
    public Vector2d getVolocityDirection() {
        return volocityDirection;
    }

    /**
     * @param volocityDirection the volocityDirection to set
     */
    public void setVolocityDirection(Vector2d volocityDirection) {
        this.volocityDirection = volocityDirection;
    }

    void move(double animationRate) {
        volocityDirection.normalize();
        Vector2d deltaVector2d =  volocityDirection.multiply(deltaLenght);
        baseX -= deltaVector2d.getX();
        baseY -= deltaVector2d.getY();
        super.centerXProperty().set(baseX);      
        super.centerYProperty().set(baseY);

    }

    /**
     * @return the isEnemyShot
     */
    public boolean getIsEnemyShot() {
        return isEnemyShot;
    }

    /**
     * @param isEnemyShot the isEnemyShot to set
     */
    public void setIsEnemyShot(boolean isEnemyShot) {
        this.isEnemyShot = isEnemyShot;
    }
}
