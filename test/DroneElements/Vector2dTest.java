/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneElements;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Krzysiek
 */
public class Vector2dTest {
    
    public Vector2dTest() {
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
     * Test of dot method, of class Vector2d.
     */
    @Test
    public void testDot() {
        System.out.println(java.util.ResourceBundle.getBundle("InternationalizationBundle").getString("DOT"));
        Vector2d v1 = null;
        Vector2d instance = null;
        double expResult = 0.0;
        double result = instance.dot(v1);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of length method, of class Vector2d.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Vector2d instance = null;
        float expResult = 0.0F;
        float result = instance.length();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lengthSquared method, of class Vector2d.
     */
    @Test
    public void testLengthSquared() {
        System.out.println("lengthSquared");
        Vector2d instance = null;
        double expResult = 0.0;
        double result = instance.lengthSquared();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class Vector2d.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Vector2d instance = null;
        instance.normalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angle method, of class Vector2d.
     */
    @Test
    public void testAngle() {
        System.out.println("angle");
        Vector2d v1 = null;
        Vector2d instance = null;
        double expResult = 0.0;
        double result = instance.angle(v1);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
