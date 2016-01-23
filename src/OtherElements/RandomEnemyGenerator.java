/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OtherElements;

import java.util.Random;

/**
 *
 * @author Krzysiek
 */
public class RandomEnemyGenerator {
    private Random randomInstance = new Random();
    
    
    int iterationPause = 800;
    private static int currentIteration =0;
    public Enemy generate(int level, double w, double h){
        currentIteration++;
        if(currentIteration > (iterationPause - (level * 5))){
            currentIteration = 0;
            
            return new Enemy(w * randomInstance.nextDouble(),
                             -h * randomInstance.nextDouble(),
                             w,
                             h);
        }
        
        return null;
    }
}
