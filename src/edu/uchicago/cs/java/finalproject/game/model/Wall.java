package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pmnehls on 11/19/14.
 */
public class Wall extends Sprite implements Gridable
{
    private int x;
    private int y;

    public Wall(int x, int y)
    {
        super();

        this.x = x;
        this.y = y;

        ArrayList<Point> pntCs = new ArrayList<Point>();

        //simple wall
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(1, -1));
        pntCs.add(new Point(-1, -1));
        pntCs.add(new Point(-1, 1));

        assignPolarPoints(pntCs);

        setColor(Color.BLUE);

        int nHeight = TargetSpace.TS_HEIGHT;
        int nWidth = TargetSpace.TS_WIDTH;

        setCenter(new Point(nHeight * (x - 1) + nHeight/2, nWidth * (y - 1) + nWidth/2));

        setRadius(8);
    }

    public void drawWall()
    {

    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }
}
