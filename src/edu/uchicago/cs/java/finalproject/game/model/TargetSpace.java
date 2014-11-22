package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;

/**
 * Created by pmnehls on 11/19/14.
 */
public class TargetSpace extends Sprite
{
    private boolean isWall;
    private boolean isDot;
    private boolean isEmpty;
    private boolean isEnergizer;
    private boolean isGhostBox;
    private boolean isReachable;
    public static final int TS_HEIGHT= 16;
    public static final int TS_WIDTH = 16;
    private Gridable inside;
    private Point pCenter;

    public TargetSpace(int y, int x)
    {

        if((y == 2 && x == 7) || (y == 2 && x == 27) || (y == 27 && x == 7) || (y == 27 && x == 27))
        {
            isEnergizer = true;
            isReachable = true;
            this.inside = new Energizer(y, x);
        }
        else if (((x == 5 || x == 24) && (y != 1 && y != 14 && y!= 15 && y != 28)) || ((x == 9 || x == 33) && (y != 1 && y != 28))
            || ((x == 12 || x == 30) && ((y != 1 && y != 8 && y != 9 && y != 14 && y!= 15 && y != 20 && y != 21 && y != 28)))
            || (x == 27 && ((y != 1 && y != 5 && y != 6 && y != 14 && y!= 15 && y != 23 && y != 24 && y != 28)))
            || ((x == 6 || x == 8 || x == 25 || x == 26) && (y == 2 || y == 7 || y == 13 || y == 16 || y == 22 || y == 27 ))
            || (x == 7 && (y == 7 || y == 13 || y == 16 || y == 22))
            || ((x == 10 || x == 11) && (y == 2 || y == 7 || y == 10 || y == 19 || y == 22 || y == 27 ))
            || (x > 12 && x < 24) && (y == 7 || y == 22)
            || (x == 28 || x == 29) && (y == 4 || y == 7 || y == 10 || y == 19 || y == 22 || y == 25)
            || (x == 31 || x == 32) && (y == 2 || y == 13 || y == 16 || y == 27))
        {
            isDot = true;
            isReachable = true;
            this.inside = new Dot(y,x);
        }
        else if (((x == 13 || x == 14) && (y == 13 || y == 16)) || ((x == 15 || x == 21) && ( y > 9 && y < 20))
            || ((x == 16 || x == 17 || x == 18 || x == 19 || x == 20 || x == 21 || x == 23 || x == 24) && (y == 10 || y == 19))
            || (x == 18 && (y != 7 && y != 22 && (y < 11 || y > 18))) || (x == 27 && (y == 14 || y == 15))
            || (x == 22 && (y == 10 || y == 19)))
        {
            isEmpty = true;
            isReachable = true;
        }
        else if(x == 1 || x == 2 || x == 3 || x == 35 || x == 36 || (x == 7 && ( y == 4 || y == 5 || y == 9 || y == 10 || y == 11
            || y == 18 || y == 19 || y == 20 || y == 24 || y == 25 )) || ((x == 14 || x == 15 || x == 16 || x == 20
            || x == 21 || x == 22) && ( y < 6 || y > 23)))
        {
            isEmpty = true;
        }
        else if ((x > 15 && x < 21 ) && (y > 10 && y < 19 ))
        {
            if (x == 16 || x == 20)
            {
                isGhostBox = true;
            }
            else if (y == 11 || y == 18)
            {
                isGhostBox = true;
            }
        }
        else
        {
            isWall = true;
            this.inside = new Wall(y ,x );
        }

        pCenter = new Point(TS_HEIGHT * (x-1) + TS_HEIGHT/2, TS_WIDTH * (y-1) + TS_WIDTH/2);
        setCenter(new Point(pCenter));

    }

    public boolean getIsWall()
    {
        return isWall;
    }

    public boolean getIsDot()
    {
        return isDot;
    }

    public boolean getIsEmpty()
    {
        return isEmpty;
    }

    public boolean getIsEnergizer()
    {
        return isEnergizer;
    }

    public boolean getIsGhostBox()
    {
        return isGhostBox;
    }
    public boolean getIsReachable()
    {
        return isReachable;
    }

    public Gridable getInside()
    {
        return inside;
    }

    public Point getCenter()
    {
        return pCenter;
    }

}
