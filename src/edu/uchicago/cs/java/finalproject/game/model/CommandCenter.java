package edu.uchicago.cs.java.finalproject.game.model;

import java.util.concurrent.CopyOnWriteArrayList;

import edu.uchicago.cs.java.finalproject.controller.Game;
import edu.uchicago.cs.java.finalproject.sounds.Sound;

// I only want one Command Center and therefore this is a perfect candidate for static
// Able to get access to methods and my movMovables ArrayList from the static context.
public class CommandCenter {

	private static int nNumFalcon;
	private static int nLevel;
	private static long lScore;
	private static Falcon falShip;
    private static Pacman pacman;
	private static boolean bPlaying;
    private static boolean bIntroDone = false;
	private static boolean bPaused;
    private int nSecondTimer;
    private int nScore;

	// These ArrayLists are thread-safe
	public static CopyOnWriteArrayList<Movable> movDebris = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFriends = new CopyOnWriteArrayList<Movable>();
    public static CopyOnWriteArrayList<Movable> movPacman = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFoes = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFloaters = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movEnergizers = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movDots = new CopyOnWriteArrayList<Movable>();

    //create board as a grid of target spaces
    public static TargetSpace[][] grid = new TargetSpace[28][36];

	// Constructor made private - static Utility class only
	private CommandCenter() {}
	
	public static void initGame(){
        setMaze();
        setLevel(1);
        setScore(0);
        setNumFalcons(1);
        spawnFalcon(true);
        initPacmanLives();
        playIntro();
        if (Game.getnTick() > 50)
        {
            spawnPacman(true);
            spawnBlinky(true);
        }
	}
	
    public static void setMaze()
    {
        //loop over TargetSpaces and initialize contents
        for (int nD = 0; nD < 36; nD++)
        {
            for (int nC = 0; nC < 28; nC++)
            {
                    grid[nC][nD] = new TargetSpace(nC+1, nD+1);

                    if(grid[nC][nD].getIsEnergizer())
                    {
                        movDebris.add(new Energizer(nC+1, nD+1));
                    }
                    else if (grid[nC][nD].getIsDot())
                    {
                        movDots.add(new Dot(nC+1, nD+1));
                    }
                    else if (grid[nC][nD].getIsGhostBox())
                    {
                        movDebris.add(new GhostBox(nC+1, nD+1));
                    }
                    else if (grid[nC][nD].getIsWall())
                    {
                        movDebris.add(new Wall(nC+1, nD+1));
                    }


            }
        }
    }

    public static void playIntro()
    {
        Sound.playSound("pacman_beginning.wav");
        try
        {
            Thread.sleep(4750);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        bIntroDone = true;
    }

    public static void initPacmanLives()
    {
        Life life1 = new Life(TargetSpace.TS_WIDTH*3, TargetSpace.TS_HEIGHT*35);
        Life life2 = new Life(TargetSpace.TS_WIDTH*5, TargetSpace.TS_HEIGHT*35);
        Life life3 = new Life(TargetSpace.TS_WIDTH*7, TargetSpace.TS_HEIGHT*35);
        movDebris.add(life1);
        movDebris.add(life2);
        movDebris.add(life3);
    }

    public static void spawnPacman(boolean bFirst)
    {
        bFirst = false;
        pacman = new Pacman();
        movPacman.add(pacman);
    }

    public static void spawnBlinky(boolean bFirstBlinky)
    {
        if (bFirstBlinky)
        {
            Blinky blinky = new Blinky();
            movFoes.add(blinky);
            bFirstBlinky = false;
        }
        else
        {
            Blinky blinky = new Blinky();
            movFoes.add(blinky);
        }

    }

	public static void spawnFalcon(boolean bFirst) {

		if (getNumFalcons() != 0) {
			falShip = new Falcon();
			movFriends.add(falShip);
			if (!bFirst)
			    setNumFalcons(getNumFalcons() - 1);
		}

	}
	
	public static void clearAll(){
		movDebris.clear();
		movFriends.clear();
		movFoes.clear();
		movFloaters.clear();
	}

	public static boolean isPlaying() {
		return bPlaying;
	}

	public static void setPlaying(boolean bPlaying) {
		CommandCenter.bPlaying = bPlaying;
	}

	public static boolean isPaused() {
		return bPaused;
	}

	public static void setPaused(boolean bPaused) {
		CommandCenter.bPaused = bPaused;
	}
	
	public static boolean isGameOver() {		//if the number of falcons is zero, then game over
		if (getNumFalcons() == 0) {
			return true;
		}
		return false;
	}

	public static int getLevel() {
		return nLevel;
	}

	public  static long getScore() {
		return lScore;
	}

	public static void setScore(long lParam) {
		lScore = lParam;
	}

	public static void setLevel(int n) {
		nLevel = n;
	}

	public static int getNumFalcons() {
		return nNumFalcon;
	}

	public static void setNumFalcons(int nParam) {
		nNumFalcon = nParam;
	}
	
	public static Falcon getFalcon(){
		return falShip;
	}

    public static Pacman getPacman() { return pacman;}
	
	public static void setFalcon(Falcon falParam){
		falShip = falParam;
	}

	public static CopyOnWriteArrayList<Movable> getMovDebris() {
		return movDebris;
	}

    public static CopyOnWriteArrayList<Movable> getMovDots() {return movDots;}

	public static CopyOnWriteArrayList<Movable> getMovFriends() {
		return movFriends;
	}



	public static CopyOnWriteArrayList<Movable> getMovFoes() {
		return movFoes;
	}


	public static CopyOnWriteArrayList<Movable> getMovFloaters() {
		return movFloaters;
	}


	
	
}
