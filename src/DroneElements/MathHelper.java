package DroneElements;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Krzysiek
 */
public final class MathHelper {

    public static boolean isPointInPolygon(Point2d point2d, ArrayList<Point2d> polygon) {

        int i, j = polygon.size() - 1;
        boolean oddNodes = false;

        for (i = 0; i < polygon.size(); i++) {
            if ((polygon.get(i).getY()) < point2d.getY() && polygon.get(j).getY() >= point2d.getY()
                    || (polygon.get(j).getY() < point2d.getY() && polygon.get(i).getY() >= point2d.getY())
                    && (polygon.get(i).getX() <= point2d.getX() || polygon.get(j).getX() <= point2d.getX())) {
                oddNodes ^= (polygon.get(i).getX() + point2d.getY() - polygon.get(i).getY()) / (polygon.get(j).getY() - polygon.get(i).getY()) * (polygon.get(j).getX() - polygon.get(i).getX()) < point2d.getX();
            }
            j = i;
        }
        return oddNodes;
    }

}
