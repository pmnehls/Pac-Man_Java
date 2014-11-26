package edu.uchicago.cs.java.finalproject.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pmnehls on 11/21/14.
 *
 * Ghost Logic is true to the original Pac-Man game. This logic is available on various sites online,
 * most references in this code come from http://home.comcast.net/~jpittman2/pacman/pacmandossier.html. All ghost logic
 * code is original.
 */

public class Blinky extends Sprite
{
    private Point pBlinkyCenter;
    private Point pBlinkyCurrentSquare;
    private int nDirection; // 0- left, 1- up, 2- right, 3-down
    private int nBlinkySpeed = 3; //note to change this below in tunnel checker
    private boolean bTurnsQueued;
    private boolean toTurnDown;
    private boolean toTurnLeft;
    private boolean toTurnUp;
    private boolean toTurnRight;
    private boolean bFirstScatter = true;
    private int nXTurn;
    private int nYTurn;

    //scatter target square information
    private final Point scatterTargetPixel = new Point(TargetSpace.TS_HEIGHT*26 - TargetSpace.TS_HEIGHT/2,TargetSpace.TS_HEIGHT/2);
    private final Point scatterTargetSquare = new Point(26, 1);

    public Blinky()
    {
        super();

        ArrayList<Point> pntCs = new ArrayList<Point>();

//        pntCs.add(new Point(0, 1));
//        pntCs.add(new Point(1, 1));
//        pntCs.add(new Point(1, 0));
//        pntCs.add(new Point(3, 0));
//        pntCs.add(new Point(3, 1));
//        pntCs.add(new Point(4, 1));
//        pntCs.add(new Point(4, 2));
//        pntCs.add(new Point(5, 2));
//        pntCs.add(new Point(5, 1));
//        pntCs.add(new Point(6, 1));
//        pntCs.add(new Point(6, 0));
//        pntCs.add(new Point(8, 0));
//        pntCs.add(new Point(8, 1));
//        pntCs.add(new Point(9, 1));
//        pntCs.add(new Point(9, 2));
//        pntCs.add(new Point(10, 2));
//        pntCs.add(new Point(10, 1));
//        pntCs.add(new Point(11, 1));
//        pntCs.add(new Point(11, 0));
//        pntCs.add(new Point(13, 0));
//        pntCs.add(new Point(13, 1));
//        pntCs.add(new Point(14, 1));
//        pntCs.add(new Point(14, 8));
//        pntCs.add(new Point(13, 8));
//        pntCs.add(new Point(13, 11));
//        pntCs.add(new Point(12, 11));
//        pntCs.add(new Point(12, 12));
//        pntCs.add(new Point(11, 12));
//        pntCs.add(new Point(11, 13));
//        pntCs.add(new Point(9, 13));
//        pntCs.add(new Point(9, 14));
//        pntCs.add(new Point(5, 14));
//        pntCs.add(new Point(5, 13));
//        pntCs.add(new Point(3, 13));
//        pntCs.add(new Point(3, 12));
//        pntCs.add(new Point(2, 12));
//        pntCs.add(new Point(2, 11));
//        pntCs.add(new Point(1, 11));
//        pntCs.add(new Point(1, 8));
//        pntCs.add(new Point(0, 8));

        //test point
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(1, -1));
        pntCs.add(new Point(-1, -1));
        pntCs.add(new Point(-1, 1));

        assignPolarPoints(pntCs);

        setColor(Color.RED);

        pBlinkyCenter = new Point(TargetSpace.TS_WIDTH*(14) - 1,
                TargetSpace.TS_WIDTH*(14) + TargetSpace.TS_HEIGHT / 2);

        //put blinky in his start location
        setCenter(pBlinkyCenter);


        setOrientation(270);

        //set initial direction (left)
        nDirection = 0;

        setRadius(10);


    }

    public void move()
    {
        super.move();

        //get blinky Center
        Point pnt = getCenter();

        //slow down if ghost is in tunnel
        if (getBlinkySpaceCoord().y == 18 && (getBlinkySpaceCoord().x < 5 ||
                getBlinkySpaceCoord().x >24))
        {
            nBlinkySpeed = 2;
        }
        else
        {
            nBlinkySpeed = 3;
        }

        if (nDirection == 0) //moving left
        {
            setCenter(new Point(pnt.x-nBlinkySpeed, pnt.y));
        }

        if (nDirection == 1) //moving up
        {
            setCenter(new Point(pnt.x, pnt.y-nBlinkySpeed));
        }

        if (nDirection == 2) //moving right
        {
            setCenter(new Point(pnt.x+nBlinkySpeed, pnt.y));
        }

        if (nDirection == 3) //moving down
        {
            setCenter(new Point(pnt.x, pnt.y+nBlinkySpeed));
        }

        if(toTurnDown)
        {

            if (nDirection == 0)
            {
                if (pBlinkyCenter.x <= nXTurn)
                {
                    nDirection = 3;
                    setCenter(new Point(nXTurn, pBlinkyCenter.y));
                    toTurnDown = false;
                    bTurnsQueued = false;
                }
            }
            else if (nDirection == 2)
            {
                if (pBlinkyCenter.x >= nXTurn)
                {
                    nDirection = 3;
                    setCenter(new Point(nXTurn, pBlinkyCenter.y));
                    toTurnDown = false;
                    bTurnsQueued = false;
                }
            }
        }

        if(toTurnUp)
        {

            if (nDirection == 0)
            {
                if (pBlinkyCenter.x <= nXTurn)
                {
                    nDirection = 1;
                    setCenter(new Point(nXTurn, pBlinkyCenter.y));
                    toTurnUp = false;
                    bTurnsQueued = false;
                }
            }
            else if (nDirection == 2)
            {
                if (pBlinkyCenter.x >= nXTurn)
                {
                    nDirection = 1;
                    setCenter(new Point(nXTurn, pBlinkyCenter.y));
                    toTurnUp = false;
                    bTurnsQueued = false;
                }
            }
        }

        if(toTurnRight)
        {
            if (nDirection == 1)
            {
                if (pBlinkyCenter.y <= nYTurn)
                {
                    nDirection = 2;
                    setCenter(new Point(pBlinkyCenter.x, nYTurn));
                    toTurnRight = false;
                    bTurnsQueued = false;
                }
            }
            else if (nDirection == 3)
            {
                if (pBlinkyCenter.y >= nYTurn)
                {
                    nDirection = 2;
                    setCenter(new Point(pBlinkyCenter.x, nYTurn));
                    toTurnRight = false;
                    bTurnsQueued = false;
                }
            }
        }

        if(toTurnLeft)
        {
            if (nDirection == 1)
            {
                if (pBlinkyCenter.y <= nYTurn)
                {
                    nDirection = 0;
                    setCenter(new Point(pBlinkyCenter.x, nYTurn));
                    toTurnLeft = false;
                    bTurnsQueued = false;
                }
            }
            if (nDirection == 3)
            {
                if (pBlinkyCenter.y >= nYTurn)
                {
                    nDirection = 0;
                    setCenter(new Point(pBlinkyCenter.x, nYTurn));
                    toTurnLeft = false;
                    bTurnsQueued = false;
                }
            }
        }

        chase();

        pBlinkyCenter = getCenter();

        pBlinkyCurrentSquare = new Point((pBlinkyCenter.x / TargetSpace.TS_WIDTH) + 1,(pBlinkyCenter.y / TargetSpace.TS_HEIGHT) + 1);

    }

    public void scatter()
    {
        //reverses ghost direction if the scatter call is not the initial scatter
        if (!bFirstScatter)
        {
            nDirection = (nDirection + 2) % 4;
        }


    }

    public void chase()
    {
        Point pacman = new Point(CommandCenter.movPacman.get(0).getCenter());
        int pacmanSpaceX = pacman.x/TargetSpace.TS_WIDTH;
        int pacmanSpaceY = pacman.y/TargetSpace.TS_HEIGHT;
        Point pacTarget = new Point(pacmanSpaceX +1, pacmanSpaceY +1);
        TargetSpace pacSquare = new TargetSpace(pacTarget.x, pacTarget.y);
        Point currPnt = getBlinkySpaceCoord();

        //target spaces for tests for next move
        TargetSpace L = new TargetSpace(currPnt.x - 2, currPnt.y);
        TargetSpace LU = new TargetSpace(currPnt.x - 1, currPnt.y - 1);
        TargetSpace U = new TargetSpace(currPnt.x, currPnt.y - 2);
        TargetSpace UR = new TargetSpace(currPnt.x + 1, currPnt.y - 1);
        TargetSpace R = new TargetSpace(currPnt.x + 2, currPnt.y);
        TargetSpace RD = new TargetSpace(currPnt.x + 1, currPnt.y + 1);
        TargetSpace D = new TargetSpace(currPnt.x, currPnt.y + 2);
        TargetSpace DL = new TargetSpace(currPnt.x - 1, currPnt.y + 1);

        //Adjacent target spaces
        TargetSpace AL = new TargetSpace(currPnt.x -1, currPnt.y);
        TargetSpace AU = new TargetSpace(currPnt.x, currPnt.y - 1);
        TargetSpace AR = new TargetSpace(currPnt.x + 1, currPnt.y);
        TargetSpace AD = new TargetSpace(currPnt.x, currPnt.y + 1);


        if (nDirection == 0 && !bTurnsQueued)
        {
            //first handle cases with no choice to be made

            if (LU.getIsWall() && L.getIsWall() && !DL.getIsWall()) // turn down at corner
            {
                toTurnDown = true;
                bTurnsQueued = true;
                Point turn = AL.getTargetCenter();
                nXTurn = turn.x; //+ TargetSpace.TS_WIDTH / 2;  //DEBUG check why this needs to be added

            }
            if (DL.getIsWall() && L.getIsWall() && !LU.getIsWall()) // turn up at corner
            {
                toTurnUp = true;
                bTurnsQueued = true;
                Point turn = AL.getTargetCenter();
                nXTurn = turn.x;
            }
            if (L.getIsWall() && !LU.getIsWall() && !DL.getIsWall()) //handle two way intersection with wall in front
            {
                // get center point of both possible squares to go to
                Point option1 = LU.getTargetCenter();
                Point option2 = DL.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }
                else
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }

            }
            if (DL.getIsWall() && !L.getIsWall() && !LU.getIsWall()) //handle 3way junction up/left
            {
                // get center point of both possible squares to go to
                Point option1 = LU.getTargetCenter();
                Point option2 = L.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
            if (!DL.getIsWall() && !L.getIsWall() && LU.getIsWall()) //handle 3way junction down/left
            {
                // get center point of both possible squares to go to
                Point option1 = DL.getTargetCenter();
                Point option2 = L.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
            if (!L.getIsWall() && !LU.getIsWall() && !DL.getIsWall()) // 4 way intersection
            {
                Point option1 = LU.getTargetCenter();
                Point option2 = DL.getTargetCenter();
                Point option3 = L.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);
                double dist3 = getDistance(option3, target);

                if(dist1 < dist2 && dist1 < dist3)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }
                else if (dist2 < dist1 && dist2 < dist3)
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AL.getTargetCenter();
                    nXTurn = turn.x;
                }

            }

        }
        if (nDirection == 1 && !bTurnsQueued)
        {
            //first handle no choice turns
            if(LU.getIsWall() && U.getIsWall() && !UR.getIsWall()) // turn right at corner
            {
                toTurnRight = true;
                bTurnsQueued = true;
                Point turn = AU.getTargetCenter();
                nYTurn = turn.y;
            }
            if(U.getIsWall() && UR.getIsWall() && !LU.getIsWall()) // turn left at corner
            {
                toTurnLeft = true;
                bTurnsQueued = true;
                Point turn = AU.getTargetCenter();
                nYTurn = turn.y;
            }
            if(U.getIsWall() && !LU.getIsWall() && !UR.getIsWall())//T intersection
            {
                Point option1 = LU.getTargetCenter();
                Point option2 = UR.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
                else
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
            if(LU.getIsWall() && !U.getIsWall() && !UR.getIsWall())//3way intersection up, right
            {
                Point option1 = U.getTargetCenter();
                Point option2 = UR.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 > dist2)
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
            if(!LU.getIsWall() && !U.getIsWall() && UR.getIsWall())//3way intersection up, left
            {
                Point option1 = LU.getTargetCenter();
                Point option2 = U.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
            if (!LU.getIsWall() && !U.getIsWall() && !UR.getIsWall()) //4way intersection
            {
                Point option1 = LU.getTargetCenter();
                Point option2 = UR.getTargetCenter();
                Point option3 = U.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);
                double dist3 = getDistance(option3, target);

                if (dist1 < dist2 && dist1 < dist3)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
                else if (dist2 < dist1 && dist2 < dist3)
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AU.getTargetCenter();
                    nYTurn = turn.y;
                }
            }

        }
        if (nDirection == 2 && !bTurnsQueued)
        {
            if(RD.getIsWall() && R.getIsWall() && !UR.getIsWall())//turn up at corner
            {
                toTurnUp = true;
                bTurnsQueued = true;
                Point turn = AR.getTargetCenter();
                nXTurn = turn.x;
            }
            if(UR.getIsWall() && R.getIsWall() && !RD.getIsWall())//turn down at corner
            {
                toTurnDown = true;
                bTurnsQueued = true;
                Point turn = AR.getTargetCenter();
                nXTurn = turn.x;
            }
            if(R.getIsWall() && !UR.getIsWall() && !RD.getIsWall()) //T intersection
            {
                Point option1 = UR.getTargetCenter();
                Point option2 = RD.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
                else
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
            if (RD.getIsWall() && !R.getIsWall() && !UR.getIsWall())//3 way right up
            {
                Point option1 = UR.getTargetCenter();
                Point option2 = R.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
            if (UR.getIsWall() && !R.getIsWall() && !RD.getIsWall())//3 way right down
            {
                Point option1 = RD.getTargetCenter();
                Point option2 = R.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
            if (!UR.getIsWall() && !R.getIsWall() && !RD.getIsWall()) //4way intersection
            {
                Point option1 = UR.getTargetCenter();
                Point option2 = RD.getTargetCenter();
                Point option3 = R.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);
                double dist3 = getDistance(option3, target);

                if (dist1 < dist2 && dist1 < dist3)
                {
                    toTurnUp = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
                else if (dist2 < dist1 && dist2 < dist3)
                {
                    toTurnDown = true;
                    bTurnsQueued = true;
                    Point turn = AR.getTargetCenter();
                    nXTurn = turn.x;
                }
            }
        }
        if (nDirection == 3 && !bTurnsQueued)
        {
            if(RD.getIsWall() && D.getIsWall() && !DL.getIsWall()) //turn left at corner
            {
                toTurnLeft = true;
                bTurnsQueued = true;
                Point turn = AD.getTargetCenter();
                nYTurn = turn.y;  //CHANGED TO DEBUG
            }
            if (DL.getIsWall() && D.getIsWall() && !RD.getIsWall()) //turn right at corner
            {
                toTurnRight = true;
                bTurnsQueued = true;
                Point turn = AD.getTargetCenter();
                nYTurn = turn.y;
            }
            if (D.getIsWall() && !RD.getIsWall() && !DL.getIsWall()) // T intersection
            {
                Point option1 = DL.getTargetCenter();
                Point option2 = RD.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y; //DEBUG
                }
                else
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y; //debug
                }
            }
            if (DL.getIsWall() && !D.getIsWall() && !RD.getIsWall()) //3 way intersection down right
            {
                Point option1 = RD.getTargetCenter();
                Point option2 = D.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
            if (!DL.getIsWall() && !D.getIsWall() && RD.getIsWall()) //3 way intersection down left
            {
                Point option1 = DL.getTargetCenter();
                Point option2 = D.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);

                if (dist1 < dist2)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
            if (!DL.getIsWall() && !D.getIsWall() && !RD.getIsWall()) //4 way intersection
            {
                Point option1 = DL.getTargetCenter();
                Point option2 = RD.getTargetCenter();
                Point option3 = D.getTargetCenter();
                Point target = pacSquare.getTargetCenter();
                double dist1 = getDistance(option1, target);
                double dist2 = getDistance(option2, target);
                double dist3 = getDistance(option3, target);

                if (dist1 < dist2 && dist1 < dist3)
                {
                    toTurnLeft = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y;
                }
                else if (dist2 < dist1 && dist2 < dist3)
                {
                    toTurnRight = true;
                    bTurnsQueued = true;
                    Point turn = AD.getTargetCenter();
                    nYTurn = turn.y;
                }
            }
        }
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

    public Point getBlinkySpaceCoord()
    {
        Point pnt = getCenter();
        int x = (pnt.x / TargetSpace.TS_WIDTH) + 1;
        int y = (pnt.y / TargetSpace.TS_HEIGHT) + 1;
        return new Point(x, y);
    }

    public double getDistance(Point a, Point b)
    {
        double leg1 = Math.abs(a.x - b.x);
        double leg2 = Math.abs(a.y - b.y);
        return (Math.sqrt((leg1*leg1) + (leg2*leg2)));
    }

}
