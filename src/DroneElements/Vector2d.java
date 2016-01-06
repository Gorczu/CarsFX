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
public class Vector2d implements Serializable {
 
    private float x, y;
      public Vector2d(float x, float y) {
          this.x = x;
          this.y = y;
    }

    /**
      * Constructs and initializes a Vector2d from the specified Vector2d.
      * @param v1 the Vector2d containing the initialization x y data
      */
    public Vector2d(Vector2d v1) {
        this.x = v1.x;
        this.y = v1.y;
    }

    /**
      * Computes the dot product of the this vector and vector v1.
      * @param  v1 the other vector
      */
    public final double dot(Vector2d v1) {
        return getX()*v1.getX() + getY()*v1.getY();
    }

    /**
      * Returns the length of this vector.
      * @return the length of this vector
      */
    public final float length() {
        return (float)Math.sqrt(getX()*getX() + getY()*getY());
    }

    /**
      * Returns the squared length of this vector.
      * @return the squared length of this vector
      */
    public final double lengthSquared() {
        return getX()*getX() + getY()*getY();
    }

    /**
      * Normalizes this vector in place.
      */
    public final void normalize() {
        double d = length();

        // zero-div may occur.
        setX((float) (getX() / d));
        setY((float) (getY() / d));
    }

    /**
      * Returns the angle in radians between this vector and
      * the vector parameter; the return value is constrained to the
      * range [0,PI].
      * @param v1  the other vector
      * @return the angle in radians in the range [0,PI]
      */
    public final double angle(Vector2d v1) {
        // stabler than acos
        return Math.abs(Math.atan2(getX()*v1.getY() - getY()*v1.getX() , dot(v1)));
    }
    
   
    
    public static final Vector2d VERTICAL_VECTOR = new Vector2d(0,1);
    
    public static final Vector2d HORIZONTAL_VECTOR = new Vector2d(1,0);

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
    
    public Vector2d multiply(double lenght){
        return new Vector2d(x * (float)lenght, y * (float)lenght);
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

}
