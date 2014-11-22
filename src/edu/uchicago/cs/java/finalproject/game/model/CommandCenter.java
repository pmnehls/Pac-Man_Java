package edu.uchicago.cs.java.finalproject.game.model;

import java.util.concurrent.CopyOnWriteArrayList;

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
	private static boolean bPaused;
    private int nSecondTimer;

	// These ArrayLists are thread-safe
	public static CopyOnWriteArrayList<Movable> movDebris = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFriends = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFoes = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movFloaters = new CopyOnWriteArrayList<Movable>();
	public static CopyOnWriteArrayList<Movable> movEnergizers = new CopyOnWriteArrayList<Movable>();

    //create board as a grid of target spaces
    public static TargetSpace[][] grid = new TargetSpace[29][37];
    //private static TargetSpace[][] grid = Grid.

	// Constructor made private - static Utility class only
	private CommandCenter() {}
	
	public static void initGame(){
        setMaze();
        spawnPacman(true);
        spawnBlinky(true);
		setLevel(1);
		setScore(0);
		setNumFalcons(103);
		spawnFalcon(true);
	}
	
    public static void setMaze()
    {
        //loop over TargetSpaces and initialize contents
        for (int nD = 1; nD <= 36; nD++)
        {
            for (int nC = 1; nC <= 28; nC++)
            {
                grid[nC][nD] = new TargetSpace(nC, nD);
                if(grid[nC][nD] != null)
                {
                    if(grid[nC][nD].getIsEnergizer())
                    {
                        movDebris.add(new Energizer(nC, nD));
                    }
                    else if (grid[nC][nD].getIsDot())
                    {
                        movDebris.add(new Dot(nC, nD));
                    }
                    else if (grid[nC][nD].getIsGhostBox())
                    {
                        movDebris.add(new GhostBox(nC, nD));
                    }
                    else if (grid[nC][nD].getIsWall())
                    {
                        movDebris.add(new Wall(nC, nD));
                    }
                }

            }
        }
    }

    public static void spawnPacman(boolean bFirst)
    {
        if (bFirst)
        {
            Sound.playSound("pacman_beginning.wav");
        }
        bFirst = false;
        pacman = new Pacman();
        movFriends.add(pacman);
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
            //energizerOne = new Energizer(1);
            //energizerTwo = new Energizer(2);
            //energizerThree = new Energizer(3);
            //energizerFour = new Energizer(4);
            //dot = new Dot();
			movFriends.add(falShip);
            //movFriends.add(energizerOne);
            //movFriends.add(energizerTwo);
            //movFriends.add(energizerThree);
            //movFriends.add(energizerFour);
            //movFriends.add(dot);
			if (!bFirst)
			    setNumFalcons(getNumFalcons() - 1);
		}
		
		Sound.playSound("shipspawn.wav");

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
