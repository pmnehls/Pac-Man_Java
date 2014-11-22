package edu.uchicago.cs.java.finalproject.game.model;

import edu.uchicago.cs.java.finalproject.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pmnehls on 11/19/14.
 */
public class Energizer extends Sprite implements Gridable
{
    public Energizer(int x, int y) {
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

        setColor(Color.GRAY);

        int nHeight = TargetSpace.TS_HEIGHT;
        int nWidth = TargetSpace.TS_WIDTH;

        setCenter(new Point(nHeight * (x - 1) + nHeight/2, nWidth * (y - 1) + nWidth/2));

        setRadius(8);

    }

    public void draw(Graphics g) {
        super.draw(g);
        //fill this polygon (with whatever color it has)
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }
}
