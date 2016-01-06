/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneElements;

import java.io.Serializable;

/**
 *
 * @author Krzysiek
 */
public class Point2d implements Serializable{
    
    private float x = 0;
    private float y = 0;

    public Point2d(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }
    
}
