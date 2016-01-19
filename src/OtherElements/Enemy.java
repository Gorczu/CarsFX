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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Krzysiek
 */
public class Enemy extends Pane{
    
    public Pane corpse = new Pane();
    Polygon body = null;
    private float baseX = 0;
    private float baseY = 0;
    private final double SCALE = 4.0; 
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
        
        descrioptionBox.translateXProperty().set(baseX + 20);
        descrioptionBox.translateYProperty().set(baseY - 50);
        //descrioptionBox.getTransforms().add(new Rotate(angleChange, baseX , centerY));
        
                
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
        if(iterator > 40 + (randomGenerator.nextDouble() * 30)){
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
        double deltaX = Math.sin(angleChange / 180.0 * Math.PI);
        double deltaY = Math.cos(angleChange / 180.0 * Math.PI);
        this.direction.setX(direction.getX() + (float)deltaX);
        this.direction.setY(direction.getY() + (float)deltaY);
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
        return new GunShot(baseX, baseY, direction, true);
        
    }
    
}
