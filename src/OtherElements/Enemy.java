/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtherElements;

import DroneElements.MathHelper;
import DroneElements.Point2d;
import DroneElements.Vector2d;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Krzysiek
 */
public class Enemy extends Pane{
    
    Pane corpse = new Pane();
    Polygon body = null;
    private float baseX = 0;
    private float baseY = 0;
    private final double SCALE = 4.0; 
    private double w, h;
    
    ArrayList<Point2d> pointsWithoutAnyTransform= new ArrayList<Point2d>();
    
    
    public Enemy(double x, double y, double w, double h){
        
        this.baseX = (float)x;
        this.baseY = (float)y;
        this.w = w;
        this.h = h;
        
        
        body = new Polygon(
                
          (getBaseX() - ( SCALE * 10)),   (getBaseY() - (  SCALE * 0  )),
          (getBaseX() + ( SCALE * 10)),   (getBaseY() - (  SCALE * 0  )),
          (getBaseX() + ( SCALE * 10)),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() + ( SCALE * 2 )),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() + ( SCALE * 2 )),   (getBaseY() - (  SCALE * 32 )),
          (getBaseX() - ( SCALE * 2 )),   (getBaseY() - (  SCALE * 32 )),
          (getBaseX() - ( SCALE * 2 )),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() - ( SCALE * 10)),   (getBaseY() - (  SCALE * 25 ))
        );
        String path = new java.io.File("Textures//tankTexture.png").getAbsolutePath();
        javafx.scene.image.Image imagePatt = new javafx.scene.image.Image("file:" + path);
        ImagePattern corpseOfDronePat = new ImagePattern(imagePatt);
        body.setFill(corpseOfDronePat);
        
        corpse.getChildren().addAll(body);
        this.getChildren().addAll(corpse);
    }

    private Vector2d direction = new Vector2d(0, 1f);
    private double deltaLenght = .5;
    
    int iterator = 0;
    public void move(double animationRate){
        
        iterator++;
        double angleChange = 0;
        if(iterator > 100){
            angleChange = ((randomGenerator.nextDouble() - .5) ) * 20;
            randomlyModifyDirection(angleChange);
            iterator = 0;
        }
        Vector2d deltaInDir = direction.multiply(deltaLenght);
        
        this.baseY -= deltaInDir.getY();
        this.baseX -= deltaInDir.getX();
        
        
        double centerY = (getBaseY() + (getBaseY() - (  SCALE * 32 ))) / 2.0;
        corpse.getTransforms().add(new Rotate(angleChange, baseX , centerY));
        
                
        if(baseX >  w){
            baseX = 0;
        }
        if(baseY > h){
            baseY = 0;
        }
        if(baseX < 0){
            baseX = (float)w;
        }
        if(baseY < 0){
            baseY = (float)h;
        }
        
        body.getPoints().clear();
        body.getPoints().addAll(
          (getBaseX() - ( SCALE * 10)),   (getBaseY() - (  SCALE * 0  )),
          (getBaseX() + ( SCALE * 10)),   (getBaseY() - (  SCALE * 0  )),
          (getBaseX() + ( SCALE * 10)),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() + ( SCALE * 2 )),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() + ( SCALE * 2 )),   (getBaseY() - (  SCALE * 32 )),
          (getBaseX() - ( SCALE * 2 )),   (getBaseY() - (  SCALE * 32 )),
          (getBaseX() - ( SCALE * 2 )),   (getBaseY() - (  SCALE * 25 )),
          (getBaseX() - ( SCALE * 10)),   (getBaseY() - (  SCALE * 25 ))
        );
        
        
        generateShot(animationRate);
    }
    
    int maxIterationNumBtwShots = 120;
    public void generateShot(double animationRate){
    
        
}
    
    public boolean isShoted(double x, double y){
        
        Point2d point = new Point2d((float)x,(float)y);
        ArrayList<Point2d> thisBodyPolygon = new ArrayList<Point2d> ();
        thisBodyPolygon.add(   new Point2d(baseX - (float)( SCALE * 10),     baseY - (float)(  SCALE * 0  )));
        thisBodyPolygon.add(   new Point2d(baseX + (float)( SCALE * 10),     baseY - (float)(  SCALE * 0  )));
        thisBodyPolygon.add(   new Point2d(baseX + (float)( SCALE * 10),     baseY - (float)(  SCALE * 25 )));
        thisBodyPolygon.add(   new Point2d(baseX + (float)( SCALE * 2 ),     baseY - (float)(  SCALE * 25 )));
        thisBodyPolygon.add(   new Point2d(baseX + (float)( SCALE * 2 ),     baseY - (float)(  SCALE * 32 )));
        thisBodyPolygon.add(   new Point2d(baseX - (float)( SCALE * 2 ),     baseY - (float)(  SCALE * 32 )));
        thisBodyPolygon.add(   new Point2d(baseX - (float)( SCALE * 2 ),     baseY - (float)(  SCALE * 25 )));
        thisBodyPolygon.add(   new Point2d(baseX - (float)( SCALE * 10),     baseY - (float)(  SCALE * 25 )));
        return MathHelper.isPointInPolygon(point, thisBodyPolygon);
        
    }
    
    
    /**
     * @return the baseX
     */
    public double getBaseX() {
        return baseX;
    }

    /**
     * @param baseX the baseX to set
     */
    public void setBaseX(float baseX) {
        this.baseX = baseX;
    }

    /**
     * @return the baseY
     */
    public float getBaseY() {
        return baseY;
    }

    /**
     * @param baseY the baseY to set
     */
    public void setBaseY(float baseY) {
        this.baseY = baseY;
    }

    Random randomGenerator  = new Random();
    private void randomlyModifyDirection(double angleChange) {
        double deltaX = Math.sin(angleChange / 180.0 * Math.PI);
        double deltaY = Math.cos(angleChange / 180.0 * Math.PI);
        this.direction.setX(direction.getX() + (float)deltaX);
        this.direction.setY(direction.getY() + (float)deltaY);
        this.direction.normalize();
    }
    
    
    
}
