package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pmnehls on 11/21/14.
 */
public class Blinky extends Sprite
{
    private Point pBlinkyCenter;
    public Blinky()
    {
        super();

        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 1));
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(1, 0));
        pntCs.add(new Point(3, 0));
        pntCs.add(new Point(3, 1));
        pntCs.add(new Point(4, 1));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(5, 2));
        pntCs.add(new Point(5, 1));
        pntCs.add(new Point(6, 1));
        pntCs.add(new Point(6, 0));
        pntCs.add(new Point(8, 0));
        pntCs.add(new Point(8, 1));
        pntCs.add(new Point(9, 1));
        pntCs.add(new Point(9, 2));
        pntCs.add(new Point(10, 2));
        pntCs.add(new Point(10, 1));
        pntCs.add(new Point(11, 1));
        pntCs.add(new Point(11, 0));
        pntCs.add(new Point(13, 0));
        pntCs.add(new Point(13, 1));
        pntCs.add(new Point(14, 1));
        pntCs.add(new Point(14, 8));
        pntCs.add(new Point(13, 8));
        pntCs.add(new Point(13, 11));
        pntCs.add(new Point(12, 11));
        pntCs.add(new Point(12, 12));
        pntCs.add(new Point(11, 12));
        pntCs.add(new Point(11, 13));
        pntCs.add(new Point(9, 13));
        pntCs.add(new Point(9, 14));
        pntCs.add(new Point(5, 14));
        pntCs.add(new Point(5, 13));
        pntCs.add(new Point(3, 13));
        pntCs.add(new Point(3, 12));
        pntCs.add(new Point(2, 12));
        pntCs.add(new Point(2, 11));
        pntCs.add(new Point(1, 11));
        pntCs.add(new Point(1, 8));
        pntCs.add(new Point(0, 8));

        assignPolarPoints(pntCs);

        setColor(Color.RED);

        pBlinkyCenter = new Point(TargetSpace.TS_WIDTH*(14)+ TargetSpace.TS_WIDTH/2,
                TargetSpace.TS_HEIGHT*(15));

        //put blinky in his start location
        setCenter(pBlinkyCenter);


        setOrientation(270);

        setRadius(22);


    }

    public void draw(Graphics g)
    {
        super.draw(g);
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }

    public Point getBlinkyCenter()
    {
        return pBlinkyCenter;
    }

}
