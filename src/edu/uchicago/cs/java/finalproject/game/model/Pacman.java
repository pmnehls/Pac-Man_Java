package edu.uchicago.cs.java.finalproject.game.model;

import edu.uchicago.cs.java.finalproject.controller.Game;
import edu.uchicago.cs.java.finalproject.sounds.Sound;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pmnehls on 11/19/14.
 */
public class Pacman extends Sprite
{
    // ==============================================================
    // FIELDS
    // ==============================================================

    private static Clip clpWakawaka;

    private static int spaceX;
    private static int spaceY;
    private int nPacManSpeed = 3;

    private boolean bMoving;

    //private final double[] FLAME = { 23 * Math.PI / 24 + Math.PI / 2,
    //        Math.PI + Math.PI / 2, 25 * Math.PI / 24 + Math.PI / 2 };



    // ==============================================================
    // CONSTRUCTOR
    // ==============================================================

    public Pacman() {
        super();

        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(3, 7));
        pntCs.add(new Point(3, 6));
        pntCs.add(new Point(5, 6));
        pntCs.add(new Point(5, 5));
        pntCs.add(new Point(6, 5));
        pntCs.add(new Point(6, 3));
        pntCs.add(new Point(4, 3));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(1, 2));
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(-2, 1));
        pntCs.add(new Point(-2, 0));
        pntCs.add(new Point(1, 0));
        pntCs.add(new Point(1, -1));
        pntCs.add(new Point(4, -1));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(6, -2));
        pntCs.add(new Point(6, -4));
        pntCs.add(new Point(5, -4));
        pntCs.add(new Point(5, -5));
        pntCs.add(new Point(3, -5));
        pntCs.add(new Point(3, -6));
        pntCs.add(new Point(-2, -6));
        pntCs.add(new Point(-2, -5));
        pntCs.add(new Point(-4, -5));
        pntCs.add(new Point(-4, -4));
        pntCs.add(new Point(-5, -4));
        pntCs.add(new Point(-5, -2));
        pntCs.add(new Point(-6, -2));
        pntCs.add(new Point(-6, 3));
        pntCs.add(new Point(-5, 3));
        pntCs.add(new Point(-5, 5));
        pntCs.add(new Point(-4, 5));
        pntCs.add(new Point(-4, 6));
        pntCs.add(new Point(-2, 6));
        pntCs.add(new Point(-2, 7));

        assignPolarPoints(pntCs);

        setColor(Color.yellow);

        //put pacman in his start location
        setCenter(new Point(TargetSpace.TS_WIDTH*(14),
                TargetSpace.TS_HEIGHT*(26) + TargetSpace.TS_HEIGHT/2));

        //facing left
        setOrientation(270);

        //this is the size of Pacman
        setRadius(12);

        //set up dot eating sound
        clpWakawaka = Sound.clipForLoopFactory("waka.wav");

    }


    // ==============================================================
    // METHODS
    // ==============================================================

    public void move() {
        super.move();

        Point currPnt = getPacmanSpaceCoord();
        spaceX = currPnt.x;
        spaceY = currPnt.y;

        if (Game.getnTick() % 2 == 0) //&& bMoving)
        {
            ArrayList<Point> pntCs = new ArrayList<Point>();

            pntCs.add(new Point(3, 7));
            pntCs.add(new Point(3, 6));
            pntCs.add(new Point(5, 6));
            pntCs.add(new Point(5, 5));
            pntCs.add(new Point(6, 5));
            pntCs.add(new Point(6, 3));
            pntCs.add(new Point(7, 3));
            pntCs.add(new Point(7, -1));
            pntCs.add(new Point(6, -1));
            pntCs.add(new Point(6, -3));
            pntCs.add(new Point(5, -3));
            pntCs.add(new Point(5, -4));
            pntCs.add(new Point(5, -5));
            pntCs.add(new Point(3, -5));
            pntCs.add(new Point(3, -6));
            pntCs.add(new Point(-2, -6));
            pntCs.add(new Point(-2, -5));
            pntCs.add(new Point(-4, -5));
            pntCs.add(new Point(-4, -4));
            pntCs.add(new Point(-5, -4));
            pntCs.add(new Point(-5, -2));
            pntCs.add(new Point(-6, -2));
            pntCs.add(new Point(-6, 3));
            pntCs.add(new Point(-5, 3));
            pntCs.add(new Point(-5, 5));
            pntCs.add(new Point(-4, 5));
            pntCs.add(new Point(-4, 6));
            pntCs.add(new Point(-2, 6));
            pntCs.add(new Point(-2, 7));

            assignPolarPoints(pntCs);
        }
        else
        {
            ArrayList<Point> pntCs = new ArrayList<Point>();

            pntCs.add(new Point(3, 7));
            pntCs.add(new Point(3, 6));
            pntCs.add(new Point(5, 6));
            pntCs.add(new Point(5, 5));
            pntCs.add(new Point(6, 5));
            pntCs.add(new Point(6, 3));
            pntCs.add(new Point(4, 3));
            pntCs.add(new Point(4, 2));
            pntCs.add(new Point(1, 2));
            pntCs.add(new Point(1, 1));
            pntCs.add(new Point(-2, 1));
            pntCs.add(new Point(-2, 0));
            pntCs.add(new Point(1, 0));
            pntCs.add(new Point(1, -1));
            pntCs.add(new Point(4, -1));
            pntCs.add(new Point(4, -2));
            pntCs.add(new Point(6, -2));
            pntCs.add(new Point(6, -4));
            pntCs.add(new Point(5, -4));
            pntCs.add(new Point(5, -5));
            pntCs.add(new Point(3, -5));
            pntCs.add(new Point(3, -6));
            pntCs.add(new Point(-2, -6));
            pntCs.add(new Point(-2, -5));
            pntCs.add(new Point(-4, -5));
            pntCs.add(new Point(-4, -4));
            pntCs.add(new Point(-5, -4));
            pntCs.add(new Point(-5, -2));
            pntCs.add(new Point(-6, -2));
            pntCs.add(new Point(-6, 3));
            pntCs.add(new Point(-5, 3));
            pntCs.add(new Point(-5, 5));
            pntCs.add(new Point(-4, 5));
            pntCs.add(new Point(-4, 6));
            pntCs.add(new Point(-2, 6));
            pntCs.add(new Point(-2, 7));

            assignPolarPoints(pntCs);
        }

        switch(this.getOrientation())
        {
            case 270: moveLeft();
                break;
            case 0: moveUp();
                break;
            case 90: moveRight();
                break;
            case 180: moveDown();
                break;
        }

        if (CommandCenter.grid[spaceX-1][spaceY-1].getIsDot())
        {
            clpWakawaka.loop(2);
            CommandCenter.grid[spaceX-1][spaceY-1].setIsDot(false);
        }

        if (CommandCenter.grid[spaceX-1][spaceY-1].getIsEnergizer())
        {
            Sound.playSound("pacman_eatfruit.wav");
            CommandCenter.grid[spaceX-1][spaceY-1].setIsEnergizer(false);
        }



    } //end move

    public void moveLeft()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace nextSpace = new TargetSpace(targetSpace.x - 1, targetSpace.y);

        if (!nextSpace.getIsWall())
        {
            pnt.x = pnt.x - nPacManSpeed;
            setCenter(pnt);
        }
        else
        {
            if(pnt.x > ((nextSpace.getSpaceX())*TargetSpace.TS_WIDTH + TargetSpace.TS_WIDTH/2))
            {
                pnt.x = pnt.x - nPacManSpeed;
                setCenter(pnt);
            }
        }

    }

    public void moveUp()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace nextSpace = new TargetSpace(targetSpace.x, targetSpace.y - 1);

        if (!nextSpace.getIsWall())
        {
            pnt.y = pnt.y - nPacManSpeed;
            setCenter(pnt);
        }
        else
        {
            if(pnt.y > ((nextSpace.getSpaceY())*TargetSpace.TS_HEIGHT + TargetSpace.TS_HEIGHT/2))
            {
                pnt.y = pnt.y - nPacManSpeed;
                setCenter(pnt);
            }
        }
    }

    public void moveRight()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace nextSpace = new TargetSpace(targetSpace.x + 1, targetSpace.y);

        if (!nextSpace.getIsWall())
        {
            pnt.x = pnt.x + nPacManSpeed;
            setCenter(pnt);
        }
        else
        {
            if(pnt.x < ((nextSpace.getSpaceX()-1)*TargetSpace.TS_WIDTH - TargetSpace.TS_WIDTH/2))
            {
                pnt.x = pnt.x + nPacManSpeed;
                setCenter(pnt);
            }
        }
    }

    public void moveDown()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace nextSpace = new TargetSpace(targetSpace.x, targetSpace.y + 1);

        if (!nextSpace.getIsWall())
        {
            pnt.y = pnt.y + nPacManSpeed;
            setCenter(pnt);
        }
        else
        {
            if(pnt.y < ((nextSpace.getSpaceY() - 1)*TargetSpace.TS_HEIGHT - TargetSpace.TS_HEIGHT/2))
            {
                pnt.y = pnt.y + nPacManSpeed;
                setCenter(pnt);
            }
        }
    }

    public void turnLeft()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace turnLeft = new TargetSpace(targetSpace.x -1, targetSpace.y);

        if(!turnLeft.getIsWall())
        {
            setOrientation(270);
            setCenter(new Point(pnt.x, (targetSpace.y * TargetSpace.TS_HEIGHT - TargetSpace.TS_HEIGHT/2)));
        }
    }

    public void turnUp()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace turnUp = new TargetSpace(targetSpace.x, targetSpace.y -1);

        if(!turnUp.getIsWall())
        {
            setOrientation(0);
            setCenter(new Point((targetSpace.x * TargetSpace.TS_WIDTH - TargetSpace.TS_WIDTH/2), pnt.y));
        }
    }

    public void turnRight()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace turnRight = new TargetSpace(targetSpace.x + 1, targetSpace.y);

        if(!turnRight.getIsWall())
        {
            setOrientation(90);
            setCenter(new Point(pnt.x, ((targetSpace.y * TargetSpace.TS_HEIGHT) - (TargetSpace.TS_HEIGHT/2 + 1)))); // adjusted for shape of pacman
        }
    }

    public void turnDown()
    {
        Point pnt = getCenter();
        Point targetSpace = getPacmanSpaceCoord();
        TargetSpace turnDown = new TargetSpace(targetSpace.x, targetSpace.y + 1);

        if(!turnDown.getIsWall())
        {
            setOrientation(180);
            setCenter(new Point((targetSpace.x * TargetSpace.TS_WIDTH - TargetSpace.TS_WIDTH/2), pnt.y));

        }
    }

//    public void thrustOn() {
//        bThrusting = true;
//    }
//
//    public void thrustOff() {
//        bThrusting = false;
//        bFlame = false;
//    }

//    private int adjustColor(int nCol, int nAdj) {
//        if (nCol - nAdj <= 0) {
//            return 0;
//        } else {
//            return nCol - nAdj;
//        }
//    }

//    //public void draw(Graphics g) {
//
//        //does the fading at the beginning or after hyperspace
//        Color colShip;
//        if (getFadeValue() == 255) {
//            colShip = Color.white;
//        } else {
//            colShip = new Color(adjustColor(getFadeValue(), 200), adjustColor(
//                    getFadeValue(), 175), getFadeValue());
//        }

//		//shield on
//		if (bShield && nShield > 0) {
//
//			setShield(getShield() - 1);
//
//			g.setColor(Color.cyan);
//			g.drawOval(getCenter().x - getRadius(),
//					getCenter().y - getRadius(), getRadius() * 2,
//					getRadius() * 2);
//
//		} //end if shield

//        //thrusting
//        if (bFlame) {
//            g.setColor(colShip);
//            //the flame
//            for (int nC = 0; nC < FLAME.length; nC++) {
//                if (nC % 2 != 0) //odd
//                {
//                    pntFlames[nC] = new Point((int) (getCenter().x + 2
//                            * getRadius()
//                            * Math.sin(Math.toRadians(getOrientation())
//                            + FLAME[nC])), (int) (getCenter().y - 2
//                            * getRadius()
//                            * Math.cos(Math.toRadians(getOrientation())
//                            + FLAME[nC])));
//
//                } else //even
//                {
//                    pntFlames[nC] = new Point((int) (getCenter().x + getRadius()
//                            * 1.1
//                            * Math.sin(Math.toRadians(getOrientation())
//                            + FLAME[nC])),
//                            (int) (getCenter().y - getRadius()
//                                    * 1.1
//                                    * Math.cos(Math.toRadians(getOrientation())
//                                    + FLAME[nC])));
//
//                } //end even/odd else
//
//            } //end for loop
//
//            for (int nC = 0; nC < FLAME.length; nC++) {
//                nXFlames[nC] = pntFlames[nC].x;
//                nYFlames[nC] = pntFlames[nC].y;
//
//            } //end assign flame points
//
//            //g.setColor( Color.white );
//            g.fillPolygon(nXFlames, nYFlames, FLAME.length);
//
//        } //end if flame
//
//        drawShipWithColor(g, colShip);
//
//    } //end draw()

//    public void drawShipWithColor(Graphics g, Color col) {
//        super.draw(g);
//        g.setColor(col);
//        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
//    }

    //public void fadeInOut() {
//        if (getProtected()) {
//            setFadeValue(getFadeValue() + 3);
//        }
//        if (getFadeValue() == 255) {
//            setProtected(false);
//        }
    //}

    public void setProtected(boolean bParam) {
    //    if (bParam) {
    //        setFadeValue(0);
    //    }
    //    bProtected = bParam;
    }

    public void setProtected(boolean bParam, int n) {
    //    if (bParam && n % 3 == 0) {
    //        setFadeValue(n);
    //    } else if (bParam) {
    //        setFadeValue(0);
    //    }
    //    bProtected = bParam;
    }

    public void draw(Graphics g)
    {
        super.draw(g);
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }

    //public boolean getProtected() {//return bProtected;
    //}

    public void setPacManSpeed(int nPacManSpeed)
    {
        this.nPacManSpeed = nPacManSpeed;
    }

    public Point getPacmanSpaceCoord()
    {
        Point pnt = getCenter();
        int x = (pnt.x / TargetSpace.TS_WIDTH) + 1;
        int y = (pnt.y / TargetSpace.TS_HEIGHT) + 1;
        return new Point(x, y);
    }

    public static int getPacManSpaceX()
    {
        return spaceX;
    }

    public static int getPacManSpaceY()
    {
        return  spaceY;
    }

    public TargetSpace getPacManTarget()
    {
        Point pnt = getCenter();
        return new TargetSpace((pnt.x /TargetSpace.TS_WIDTH) + 1, (pnt.y / TargetSpace.TS_HEIGHT) +1 );

    }

    public int getDirection()
    {
        if (getOrientation() == 0)
        {
            return 1;
        }
        else if (getOrientation() == 90)
        {
            return 2;
        }
        else if (getOrientation() == 180)
        {
            return 3;
        }
        else
        {
            return 0;
        }
    }

    public static Clip getWaka()
    {
        return Pacman.clpWakawaka;
    }

    public static void protonPack()
    {
        Sound.playSound("ghostbusters_theme.wav");
    }
}
