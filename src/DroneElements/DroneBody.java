/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneElements;

import OtherElements.Enemy;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Krzysiek
 */
class DroneBody extends Pane {


    private static final double w = 1500;
    private static final double h = 900;

    private static final String SHOT_SOUND_PATH = "Resources/126423__cabeeno-rossley__shoot-laser.wav";
    private Vector2d volocityDirection;
    private ImagePattern corpseOfDronePat;

    private double baseX = w / 2.0;
    private double baseY = h / 2.0;

    private Propeller c2 = new Propeller(15, getBaseX() + 65, getBaseY() - 5, 4, 55, 55);
    private Propeller c1 = new Propeller(15, getBaseX() - 25, getBaseY() - 5, 4, 55, 55);
    private Propeller c3 = new Propeller(15, getBaseX() + 65, getBaseY() - 65, 4, 55, 55);
    private Propeller c4 = new Propeller(15, getBaseX() - 25, getBaseY() - 65, 4, 55, 55);

    private Polygon bodyPart = new Polygon(getBaseX() + 15, getBaseY() - 10,
            getBaseX() + 25, getBaseY() - 10,
            getBaseX() + 30, getBaseY() - 20,
            getBaseX() + 30, getBaseY() - 50,
            getBaseX() + 25, getBaseY() - 60,
            getBaseX() + 15, getBaseY() - 60,
            getBaseX() + 10, getBaseY() - 50,
            getBaseX() + 10, getBaseY() - 20,
            getBaseX() + 15, getBaseY() - 10);

    private Polygon circle1Connection = new Polygon(
            getBaseX() - 25, getBaseY() - 5,
            getBaseX() - 15, getBaseY() - 35,
            getBaseX() - 15, getBaseY() - 20
    );
    
    
    private Polygon circle2Connection = new Polygon(
            getBaseX() + 65, getBaseY() - 5,
            getBaseX() - 15, getBaseY() - 35,
            getBaseX() - 15, getBaseY() - 20
    );
    
    
    private Polygon circle3Connection = new Polygon(
            getBaseX() + 65, getBaseY() - 65,
            getBaseX() - 15, getBaseY() - 35,
            getBaseX() - 15, getBaseY() - 20
    );
    private Polygon circle4Connection = new Polygon(
            getBaseX() - 25, getBaseY() - 65,
            getBaseX() - 15, getBaseY() - 35,
            getBaseX() - 15, getBaseY() - 20
    );

    Pane bodyParts = new Pane();
    private SimpleIntegerProperty pointsProperty = new SimpleIntegerProperty(0);
    
    private SimpleIntegerProperty levelProperty = new SimpleIntegerProperty(1);
    private SimpleDoubleProperty liveProperty = new SimpleDoubleProperty(1.0);
    
    private MoveDirection moveDirection;
    private final float DIRECTION_DELTA_CHANGE = 1f;
    private double life = 100;
    
    ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
    
    public DroneBody() throws URISyntaxException, MalformedURLException {
        
        String path = new java.io.File("Textures//DroneCorpse.png").getAbsolutePath();
        javafx.scene.image.Image imagePatt = new javafx.scene.image.Image("file:" + path);

        ImagePattern corpseOfDronePat = new ImagePattern(imagePatt);
        
        bodyPart.setFill(corpseOfDronePat);//setFill();//corpseOfDronePat);
        circle1Connection.setFill(Color.BLUE);
        circle2Connection.setFill(Color.BLUE);
        circle3Connection.setFill(Color.BLUE);
        circle4Connection.setFill(Color.BLUE);

        VBox  resultsPane = new VBox ();
        HBox pointsHb = new HBox();
        Label points =  new Label();
        points.textProperty().set("Points: ");
        
        Label pointValue =  new Label();
        pointValue.textProperty().bindBidirectional(pointsProperty, new NumberStringConverter());
        pointsHb.getChildren().addAll(
                points,
                pointValue);
        
        resultsPane.setAlignment(Pos.TOP_LEFT);
        resultsPane.setBackground( new Background(new BackgroundFill( Color.ALICEBLUE , CornerRadii.EMPTY, Insets.EMPTY)));
        resultsPane.setBorder(new Border(new BorderStroke( Color.BLACK ,  BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
        resultsPane.setPrefSize(150, 80);
        
        
        
        HBox levelsHb = new HBox();
        Label levelPref = new Label("Level: ");
        Label levelValue = new Label();
        levelValue.textProperty().bindBidirectional(levelProperty, new NumberStringConverter());
        levelsHb.getChildren().addAll(levelPref, levelValue);
        
        HBox liveHb = new HBox();
        Label livePref = new Label("Live: ");
        ProgressBar pb = new ProgressBar(1.0);
        
        pb.progressProperty().bindBidirectional(liveProperty);
        liveHb.getChildren().addAll(livePref, pb);
        
        
        
        resultsPane.getChildren().addAll(pointsHb, levelsHb, liveHb);
        resultsPane.setPadding(new Insets(5));
        
        Distant light = new Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);
        
        
        
        bodyParts.getChildren().addAll(
                c1,
                c2,
                c3,
                c4,
                circle1Connection,
                circle2Connection,
                circle3Connection,
                circle4Connection,
                bodyPart
                
        );
        
        bodyPart.setEffect(l);
        circle1Connection.setEffect(l);
        circle2Connection.setEffect(l);
        circle3Connection.setEffect(l);
        circle4Connection.setEffect(l);
        
        enemiesList.add( new Enemy(155, 150, w, h) );
        
        this.getChildren().addAll(
                resultsPane,
                bodyParts
                
        );
        
        for(Node enemy : enemiesList){
            enemy.setEffect(l);
        }
        this.getChildren().addAll(enemiesList);

        volocityDirection = new Vector2d(0f, 1.0f);
    }

    /**
     * @return the turnDirection
     */
    public MoveDirection getTurnDirection() {
        return moveDirection;
    }

    /**
     * @param turnDirection the turnDirection to set
     */
    public void setTurnDirection(MoveDirection turnDirection) {
        this.moveDirection = turnDirection;
    }


    public void turnVelocityDirRight() {

        float deltaX = (float) Math.sin((DIRECTION_DELTA_CHANGE / 180.0) * Math.PI);
        float deltaY = (float) Math.cos((DIRECTION_DELTA_CHANGE / 180.0) * Math.PI);

        volocityDirection.setX(volocityDirection.getX() +  deltaX);
        volocityDirection.setY(volocityDirection.getY() +  deltaY);
        volocityDirection.normalize();
        
        for (Node node : this.bodyParts.getChildren()) {
                node.getTransforms().add(new Rotate(DIRECTION_DELTA_CHANGE, baseX + 20, baseY - 60));
        }
    }

    public void turnVelocityDirLeft() {

        float deltaX = (float) Math.sin((-DIRECTION_DELTA_CHANGE / 180.0) * Math.PI );
        float deltaY = (float) Math.cos((-DIRECTION_DELTA_CHANGE / 180.0) * Math.PI);

        volocityDirection.setX(volocityDirection.getX() +  deltaX);
        volocityDirection.setY(volocityDirection.getY() +  deltaY);
        volocityDirection.normalize();
        
        for (Node node : this.bodyParts.getChildren()) {
                node.getTransforms().add(new Rotate(-DIRECTION_DELTA_CHANGE, baseX + 20, baseY - 60));
            }
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

    void shot() {
        try {
            Main.music(SHOT_SOUND_PATH, false);
        } catch (Exception ex) {
            Logger.getLogger(DroneBody.class.getName()).log(Level.SEVERE, null, ex);
        }

        GunShot shot = new GunShot(getCannonBaseX(), getCannonBaseY(), getVolocityDirection());
        shot.getTransforms().addAll(this.bodyPart.getTransforms());

        this.getChildren().add(shot);
    }
    
    
    private boolean isShoted(double x, double y){
        
        Point2d point = new Point2d((float)x,(float)y);
        ArrayList<Point2d> thisBodyPolygon = new ArrayList<Point2d> ();
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 30,(float)getBaseY() - 20));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 25,(float)getBaseY() - 10));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 30,(float)getBaseY() - 50));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 25,(float)getBaseY() - 60));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 15,(float)getBaseY() - 60));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 10,(float)getBaseY() - 50));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 10,(float)getBaseY() - 20));
        thisBodyPolygon.add(new Point2d((float)getBaseX() + 15,(float)getBaseY() - 10));
        return MathHelper.isPointInPolygon(point, thisBodyPolygon);
        
    }
    
    

    /**
     * @return the cannon baseX
     */
    public double getCannonBaseX() {

        return baseX + 20;
    }

    /**
     * @return the cannon baseX
     */
    public double getCannonBaseY() {
        return baseY - 2;
    }

    /**
     * @return the baseX
     */
    public double getBaseX() {
        return baseX;
    }

    /**
     * @return the baseY
     */
    public double getBaseY() {
        return baseY;
    }


    public void move(double animationRate) {

        MoveDirection directions = getTurnDirection();

        double planEdgeY = Math.abs( w * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) - h * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) ) ;
        double planEdgeX = Math.abs( w * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) + h * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) );
        
        double actualY = Math.abs( baseX * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) - baseY * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) ) ;
        double actualX = Math.abs( baseX * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) + baseY * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) );

        if (directions == MoveDirection.LEFT) {
            baseX -= 10;
        }
        if (directions == MoveDirection.RIGHT) {
            baseX += 10;
        }
        if (directions == MoveDirection.FRONT) {
            baseY -= 10;
        }
        if (directions == MoveDirection.REAR) {
            baseY += 10;
        }

        if (actualX > planEdgeX) {
            baseX = planEdgeX;
        }
        if (actualX < 0) {
            baseX = 0;
        }
        if (actualY > planEdgeY) {
            baseY = planEdgeY;
        }
        if (actualY < 0) {
            baseY = 0;
        }

        setValues((int) Math.round(animationRate));

        for(Enemy enemy: enemiesList){
            for (Node node : this.getChildren() ) {
                if (node instanceof GunShot) {
                    GunShot shot = ((GunShot) node);
                    double x = shot.centerXProperty().get();
                    double y = shot.centerYProperty().get();
                    boolean isShoted = enemy.isShoted(x, y);
                    if(isShoted){
                        
                    }
                    
                }
            }
        }
        
    }

    public void setValues(int animationRate) {

        if (animationRate > 0) {
            c2.move(getBaseX() + 65, getBaseY() - 5, animationRate / 100);
            c1.move(getBaseX() - 25, getBaseY() - 5, -animationRate / 100);
            c3.move(getBaseX() + 65, getBaseY() - 65, animationRate / 100);
            c4.move(getBaseX() - 25, getBaseY() - 65, -animationRate / 100);
        } else if (animationRate < 0) {
            c2.move(getBaseX() + 65, getBaseY() - 5, animationRate / 100);
            c1.move(getBaseX() - 25, getBaseY() - 5, -animationRate / 100);
            c3.move(getBaseX() + 65, getBaseY() - 65, animationRate / 100);
            c4.move(getBaseX() - 25, getBaseY() - 65, -animationRate / 100);
        } else {

        }
        bodyPart.getPoints().clear();
        bodyPart.getPoints().addAll(getBaseX() + 15, getBaseY() - 10,
                getBaseX() + 25, getBaseY() - 10,
                getBaseX() + 30, getBaseY() - 20,
                getBaseX() + 30, getBaseY() - 50,
                getBaseX() + 25, getBaseY() - 60,
                getBaseX() + 15, getBaseY() - 60,
                getBaseX() + 10, getBaseY() - 50,
                getBaseX() + 10, getBaseY() - 20,
                getBaseX() + 15, getBaseY() - 10);

        circle1Connection.getPoints().clear();
        circle2Connection.getPoints().clear();
        circle3Connection.getPoints().clear();
        circle4Connection.getPoints().clear();

        circle1Connection.getPoints().addAll(getBaseX() - 25, getBaseY() - 5,
                getBaseX() + 20, getBaseY() - 35,
                getBaseX() + 20, getBaseY() - 20
        );
        circle2Connection.getPoints().addAll(getBaseX() + 65, getBaseY() - 5,
                getBaseX() + 20, getBaseY() - 35,
                getBaseX() + 20, getBaseY() - 20
        );
        circle3Connection.getPoints().addAll(getBaseX() + 65, getBaseY() - 65,
                getBaseX() + 20, getBaseY() - 35,
                getBaseX() + 20, getBaseY() - 20
        );
        circle4Connection.getPoints().addAll(getBaseX() - 25, getBaseY() - 65,
                getBaseX() + 20, getBaseY() - 35,
                getBaseX() + 20, getBaseY() - 20
        );

        for(Enemy enemy : enemiesList){
            enemy.move(animationRate);
        }
        
        
        for (Node node : this.getChildren() ) {
            if (node instanceof GunShot) {
                
                GunShot shot = ((GunShot) node);
                double x = shot.centerXProperty().get();
                double y = shot.centerYProperty().get();
                
                double actualY = Math.abs( x * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) - y * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) ) ;
                double actualX = Math.abs( x * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) + y * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) );
        
                double edgeY = Math.abs( w * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) - h * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) ) ;
                double edgeX = Math.abs( w * Math.sin(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) + h * Math.cos(volocityDirection.angle(Vector2d.HORIZONTAL_VECTOR)) );
                
                if (actualX > edgeX || edgeX < 0 || actualY > edgeY || actualY < 0) {
                    shot = null;
                } else {
                    shot.move(animationRate);
                }
            }
        }
    }

    /**
     * @return the points
     */
    public SimpleIntegerProperty getPoints() {
        return pointsProperty;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(SimpleIntegerProperty points) {
        this.pointsProperty = points;
    }

    /**
     * @return the level
     */
    public SimpleIntegerProperty getLevel() {
        return levelProperty;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(SimpleIntegerProperty level) {
        this.levelProperty = level;
    }
    
    public enum MoveDirection {
        NONE,
        LEFT,
        RIGHT,
        FRONT,
        REAR
    }
}