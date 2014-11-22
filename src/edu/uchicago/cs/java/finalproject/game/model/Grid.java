package edu.uchicago.cs.java.finalproject.game.model;

/**
 * Created by pmnehls on 11/19/14.
 */
public class Grid extends Sprite
{
    private TargetSpace[][] spacegrid;
    private Gridable inside;

    public Grid()
    {
        //iterate over rows
        for (int nC = 1; nC <= 36; nC++)
        {
            //iterate over columns
            for (int nD = 1; nD <= 28; nD++)
            {
                spacegrid[nC][nD] = new TargetSpace(nC, nD);
                if (spacegrid[nC][nD].getIsEnergizer())
                {

                    inside = new Energizer(nC, nD);

                }
                else if (spacegrid[nC][nD].getIsDot())
                {
                    inside = new Dot(nC, nD);
                }
                else if (spacegrid[nC][nD].getIsWall())
                {
                    inside = new Wall(nC, nD);
                }
            }
        }
    }

    public TargetSpace[][] getSpaceGrid()
    {
        return spacegrid;
    }

    public Gridable getInside()
    {
        return inside;
    }

}
