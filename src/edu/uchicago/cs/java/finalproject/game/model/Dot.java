package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pmnehls on 11/19/14.
 */
public class Dot extends Sprite implements Gridable
{
    public Dot(int x, int y)
    {
        super();

        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(2, 4));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(2, -4));
        pntCs.add(new Point(-2, -4));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-4, 2));
        pntCs.add(new Point(-2, 4));

        assignPolarPoints(pntCs);

        int nHeight = TargetSpace.TS_HEIGHT;
        int nWidth = TargetSpace.TS_WIDTH;

        setCenter(new Point(nHeight * (x - 1) + nHeight/2, nWidth * (y - 1) + nWidth/2));

        setRadius(2);

    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        //fill this polygon (with whatever color it has)
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
        //now draw a white border
        //g.setColor(Color.WHITE);
        //g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }
}
