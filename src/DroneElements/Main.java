package DroneElements;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import sun.audio.AudioData;

public class Main extends Application {

    
    
    private static final String DRONE_ENGINES_SOUNDS_EFFECT = "Resources\\198235_eelke_dronesounds-robot-motor.wav";
    

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void music(String musicPath, boolean repeatLoop) throws Exception
    {
    // open the sound file as a Java input stream
    InputStream in = new FileInputStream(musicPath);
 
    // create an audiostream from the inputstream
    AudioStream audioStream = new AudioStream(in);
    
    // play the audio clip with the audioplayer class        
      if(repeatLoop){
            AudioData data=audioStream.getData();
            ContinuousAudioDataStream  cont = new ContinuousAudioDataStream(data);
            AudioPlayer.player.start(cont);
      }else{
            AudioPlayer.player.start(audioStream);
      }
    
    }
    private double animationRate = 200;
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws URISyntaxException, MalformedURLException {
        DroneBody drone = new DroneBody();

        // Create a scene and place it in the stage
        Scene scene = new Scene(drone, 2000, 2500);
        primaryStage.setTitle("Dron Atack !!!"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        
        
        try {
            
            music(DRONE_ENGINES_SOUNDS_EFFECT, true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(30), e -> drone.move(animationRate)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        drone.requestFocus();
        
        drone.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                animation.setRate(animation.getRate());
                drone.setTurnDirection(DroneBody.MoveDirection.RIGHT);
            }else if (e.getCode() == KeyCode.LEFT) {
                animation.setRate(animation.getRate());
                drone.setTurnDirection(DroneBody.MoveDirection.LEFT);
            }else if (e.getCode() == KeyCode.UP){
                animation.setRate(animation.getRate() );
                drone.setTurnDirection(DroneBody.MoveDirection.FRONT);
            }else if (e.getCode() == KeyCode.DOWN){
                animation.setRate(animation.getRate());
                drone.setTurnDirection(DroneBody.MoveDirection.REAR);
            }else if(e.getCode() == KeyCode.SPACE){
                drone.shot();
            }else if(e.getCode() == KeyCode.Z){
                drone.turnVelocityDirLeft();
            }else if(e.getCode() == KeyCode.X){
                drone.turnVelocityDirRight();
            }else{
                drone.setTurnDirection(DroneBody.MoveDirection.NONE);
                animation.setRate(animation.getRate() );
            }
        });
        
        drone.setOnKeyReleased(e -> {
            drone.setTurnDirection(DroneBody.MoveDirection.NONE);
            animation.setRate(animation.getRate() );
        });
    }

    
}

    

