/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DroneElements.MathHelper;
import DroneElements.Point2d;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Krzysiek
 */
public class MathHelperTest {
    
    public MathHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isPointInPolygon method, of class MathHelper.
     */
    @Test
    public void testIsPointInPolygon() {
        
        System.out.println("isPointInPolygon");
        Point2d point2d = new Point2d(0f, 3f);
        
        ArrayList<Point2d> polygon = new ArrayList<Point2d>();
        polygon.add(new Point2d(0, 0));
        polygon.add(new Point2d(2, 0));
        polygon.add(new Point2d(2, 4));
        polygon.add(new Point2d(1, 5));
        polygon.add(new Point2d(0, 4));
        
        boolean expResult = false;
        boolean result = MathHelper.isPointInPolygon(point2d, polygon);
        assertEquals(expResult, result);
    }
    
}
