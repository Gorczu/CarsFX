/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtherElements;

import DroneElements.GunShot;
import DroneElements.MathHelper;
import DroneElements.Point2d;
import DroneElements.Vector2d;
import java.util.ArrayList;
import java.util.Random;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

/**
 *
 * @author Krzysiek
 */
public class Enemy extends  Pane{
    
    public Pane corpse = new Pane();
    public Polygon body = null;
    private float baseX = 0;
    private float baseY = 0;
    private final double SCALE = 3.0; 
    private double w, h;
    String path = null;
    javafx.scene.image.Image imagePatt = null;
    ImagePattern corpseOfDronePat = null;
    ArrayList<Point2d> pointsWithoutAnyTransform= new ArrayList<Point2d>();
    VBox vBox = new VBox();
    Pane descrioptionBox = new Pane();
    
    
    public Enemy(double x, double y, double w, double h){
        
        this.baseX = (float)x;
        this.baseY = (float)y;
        this.w = w/2;
        this.h = h/2;
        
        
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
        path = new java.io.File("Textures//tankTexture.png").getAbsolutePath();
        imagePatt = new javafx.scene.image.Image("file:" + path);
        corpseOfDronePat = new ImagePattern(imagePatt);
        body.setFill(corpseOfDronePat);
        
        corpse.getChildren().addAll(body);
        descrioptionBox = new Pane();
        descrioptionBox.setMinSize(100, 30);
        vBox.setPrefSize(100, 30);
        Label label = new Label("TANK");
        label.setPrefWidth(100);
        ProgressBar liveBar = new ProgressBar(1.0);
        liveBar.setPrefWidth(100);
        liveBar.progressProperty().bindBidirectional(liveProperty);
        vBox.getChildren().addAll(label, liveBar);
        descrioptionBox.getChildren().add(vBox);
        descrioptionBox.getTransforms().add(new Translate(x, y));
        
        this.getChildren().addAll(corpse, descrioptionBox);
    }
    
    private SimpleDoubleProperty liveProperty = new SimpleDoubleProperty(1.0);
    
    private void setLive(double value){
        liveProperty.set(value);
    }
    public double getLive(){
        return liveProperty.get();
    }
    
    private Vector2d direction = new Vector2d(0, 1f);
    private double deltaLenght = 2;
    
    private double x1 = -10,  y1 = 16 ;
    private double x2 =  10,  y2 = 16 ;
    private double x3 =  10,  y3 = -9;
    private double x4 =  2 ,  y4 = -9;
    private double x5 =  2 ,  y5 = -16;
    private double x6 = -2 ,  y6 = -16;
    private double x7 = -2 ,  y7 = -9;
    private double x8 = -10,  y8 = -9;
    
    int iterator = 0;
    public void move(double animationRate){
        
        iterator++;
        double angleChange = 0;
        if(iterator > 15){
            angleChange = ((randomGenerator.nextDouble() - .015) ) * 180.0 / Math.PI;
            /*randomlyModifyDirection(angleChange);*/
            double xDir = direction.getX();
            double yDir = direction.getY();
            
            float x_ = (float) ((xDir * Math.cos(angleChange)) - ( yDir * Math.sin(angleChange) ));
            float y_ = (float) ((xDir * Math.sin(angleChange)) + ( yDir * Math.cos(angleChange) ));
            direction.normalize();
            direction.setX(x_);
            direction.setY(y_);
            iterator = 0;
        }
        
        Vector2d deltaInDir = direction.multiply(getDeltaLenght());
        double centerY = (getBaseY() + (getBaseY() - (  SCALE * 32 ))) / 2.0;
        
        //descrioptionBox.getTransforms().add(new Rotate(angleChange, baseX , centerY));
        
        float oldX = baseX;
        float oldY = baseY;
        
        this.baseY -= deltaInDir.getY();
        this.baseX -= deltaInDir.getX();
        
        
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
        if(oldX!= baseX ){
            deltaInDir = baseY != oldY ? new Vector2d( ( baseX - oldX ), (baseY - oldY)) : new Vector2d((baseX - oldX), deltaInDir.getY());  
        }
        if(oldY != baseY ){
            deltaInDir = baseX != oldX ? new Vector2d( (baseX - oldX),(baseY - oldY)) : new Vector2d( deltaInDir.getX(), (baseY - oldY));   
        }
        
        
        descrioptionBox.getTransforms().add(new Translate(deltaInDir.getX(), deltaInDir.getY()));
        
        double x1_ = (x1 * Math.cos(angleChange)) - ( y1 * Math.sin(angleChange) );
        double x2_ = (x2 * Math.cos(angleChange)) - ( y2 * Math.sin(angleChange) ); 
        double x3_ = (x3 * Math.cos(angleChange)) - ( y3 * Math.sin(angleChange) );
        double x4_ = (x4 * Math.cos(angleChange)) - ( y4 * Math.sin(angleChange) );
        double x5_ = (x5 * Math.cos(angleChange)) - ( y5 * Math.sin(angleChange) );
        double x6_ = (x6 * Math.cos(angleChange)) - ( y6 * Math.sin(angleChange) );
        double x7_ = (x7 * Math.cos(angleChange)) - ( y7 * Math.sin(angleChange) );
        double x8_ = (x8 * Math.cos(angleChange)) - ( y8 * Math.sin(angleChange) ); 
        
        double y1_ = (x1 * Math.sin(angleChange)) + ( y1 * Math.cos(angleChange) );
        double y2_ = (x2 * Math.sin(angleChange)) + ( y2 * Math.cos(angleChange) ); 
        double y3_ = (x3 * Math.sin(angleChange)) + ( y3 * Math.cos(angleChange) );
        double y4_ = (x4 * Math.sin(angleChange)) + ( y4 * Math.cos(angleChange) );
        double y5_ = (x5 * Math.sin(angleChange)) + ( y5 * Math.cos(angleChange) );
        double y6_ = (x6 * Math.sin(angleChange)) + ( y6 * Math.cos(angleChange) );
        double y7_ = (x7 * Math.sin(angleChange)) + ( y7 * Math.cos(angleChange) );
        double y8_ = (x8 * Math.sin(angleChange)) + ( y8 * Math.cos(angleChange) ); 
        
        body.getPoints().clear();
        body.getPoints().addAll(
          (getBaseX() + ( SCALE * x1_)),   (getBaseY() + (  SCALE * y1_ )),
          (getBaseX() + ( SCALE * x2_)),   (getBaseY() + (  SCALE * y2_ )),
          (getBaseX() + ( SCALE * x3_)),   (getBaseY() + (  SCALE * y3_ )),
          (getBaseX() + ( SCALE * x4_)),   (getBaseY() + (  SCALE * y4_ )),
          (getBaseX() + ( SCALE * x5_)),   (getBaseY() + (  SCALE * y5_ )),
          (getBaseX() + ( SCALE * x6_)),   (getBaseY() + (  SCALE * y6_ )),
          (getBaseX() + ( SCALE * x7_)),   (getBaseY() + (  SCALE * y7_ )),
          (getBaseX() + ( SCALE * x8_)),   (getBaseY() + (  SCALE * y8_ ))
        );
        
        x1 = x1_;
        x2 = x2_;
        x3 = x3_;
        x4 = x4_;
        x5 = x5_;
        x6 = x6_;
        x7 = x7_;
        x8 = x8_;
        
        y1 = y1_;
        y2 = y2_;
        y3 = y3_;
        y4 = y4_;
        y5 = y5_;
        y6 = y6_;
        y7 = y7_;
        y8 = y8_;
        
        
        
        if(isShotSignlization){
            if(shotSignalizationIteration > 20){
                isShotSignlization = false;
                disableShotSignalization();
            }
            shotSignalizationIteration++;
            
        }
      
        generateShot(animationRate);
    }
    
    private Vector2d barelDirector = new Vector2d(0f, 0f);
    
    
    int maxIterationNumBtwShots = 120;
    
    public GunShot generateShot(double animationRate){
        GunShot result = null;
        iterator++;
        if(iterator > 5 + (randomGenerator.nextDouble() * 30)){
            result = shot(barelDirector);
            iterator = 0;
        }
        return result;
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
        double deltaX = Math.sin(angleChange );
        double deltaY = Math.cos(angleChange );
        direction.setX(direction.getX()+ (float)deltaX);
        direction.setY(direction.getY()+ (float)deltaY);
        this.direction.normalize();
    }

    boolean isShotSignlization = false;
    private  int shotSignalizationIteration = 0;
    public void signalizeThisShot() {
        isShotSignlization = true;
        
        setLive(getLive() - (randomGenerator.nextDouble() / 80.0));
        if(getLive() < 0){
            bum();
        }
            
        //TODO:Set Extra effects on enemy body
        this.body.setFill(Color.RED);
        BoxBlur bb = new BoxBlur();
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);
        this.body.effectProperty().set(bb);
    }
    public void disableShotSignalization(){
        isShotSignlization = false;
        //TODO:Disable Extra effects on enemy body
        corpseOfDronePat = new ImagePattern(imagePatt);
        body.setFill(corpseOfDronePat);
        
        this.body.effectProperty().set(null);
    }   

    private void bum() {
        
    }

    /**
     * @return the barelDirector
     */
    public Vector2d getBarelDirector() {
        return barelDirector;
    }

    /**
     * @param barelDirector the barelDirector to set
     */
    public void setBarelDirector(Vector2d barelDirector) {
        this.barelDirector = barelDirector;
    }

    private GunShot shot(Vector2d barelDirector) {
        return new GunShot(baseX, baseY, getDirection(), true);
        
    }

    /**
     * @return the direction
     */
    public Vector2d getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Vector2d direction) {
        this.direction = direction;
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
}
