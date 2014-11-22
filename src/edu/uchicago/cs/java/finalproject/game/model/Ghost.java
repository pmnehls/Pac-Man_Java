package edu.uchicago.cs.java.finalproject.game.model;

import edu.uchicago.cs.java.finalproject.controller.Game;

import java.awt.*;

/**
 * Created by pmnehls on 11/21/14.
 */
public class Ghost extends MoveAdapter
{

    private Point pntCenter;


    @Override
    public void move()
    {

    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(new Color (Game.R.nextInt(256)));
        g.fillOval(pntCenter.x, pntCenter.y, 21, 21);
    }

    @Override
    public int points()
    {
        return super.points();
    }

    @Override
    public Point getCenter()
    {
        return super.getCenter();
    }

    @Override
    public int getRadius()
    {
        return super.getRadius();
    }

    @Override
    public void expire()
    {
        super.expire();
    }
}
