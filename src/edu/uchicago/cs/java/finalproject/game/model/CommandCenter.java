package edu.uchicago.cs.java.finalproject.game.model;

import java.util.concurrent.CopyOnWriteArrayList;

import edu.uchicago.cs.java.finalproject.controller.Game;
import edu.uchicago.cs.java.finalproject.sounds.Sound;

import javax.sound.sampled.Clip;

// I only want one Command Center and therefore this is a perfect candidate for static
// Able to get access to methods and my movMovables ArrayList from the static context.
public class CommandCenter {

	private static int nNumFalcon;
	private static int nLevel = 1;
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
    public static CopyOnWriteArrayList<Movable> movLives = new CopyOnWriteArrayList<Movable>();


    //create board as a grid of target spaces
    public static TargetSpace[][] grid = new TargetSpace[28][36];

	// Constructor made private - static Utility class only
	private CommandCenter() {}
	
	public static void initGame(){
        setMaze();
        setLevel(nLevel);
        setTimers();
        setScore(0);
        //setNumFalcons(1);
        //spawnFalcon(true);
        initPacmanLives();

//        //playIntro();
//        try
//        {
//            Thread.sleep(4500);
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }

//        spawnPacman(true);
//        spawnBlinky(true);
//        spawnPinky(true);
//        spawnInky(true);
//        spawnClyde(true);

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
                        movEnergizers.add(new Energizer(nC+1, nD+1));
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

    public static void setTimers()
    {
        if(nLevel == 1)
        {
            Game.setScaredSeconds(6);
        }
        else if (nLevel == 2)
        {
            Game.setScaredSeconds(5);
        }
        else if (nLevel == 3)
        {
            Game.setScaredSeconds(4);
        }
        else if (nLevel == 4)
        {
            Game.setScaredSeconds(3);
        }
        else if (nLevel == 5)
        {
            Game.setScaredSeconds(2);
        }
        else if (nLevel == 6)
        {
            Game.setScaredSeconds(5);
        }
        else if (nLevel >= 7 && nLevel < 10)
        {
            Game.setScaredSeconds(2);
        }
        else
        {
            Game.setScaredSeconds(1);
        }

    }

    public static void playIntro()
    {
        Game.setnTick(0);
        Sound.playSound("pacman_beginning.wav");
        bIntroDone = true;
    }

    public static void initPacmanLives()
    {
        for (int nC = 0; nC < Game.getLives() - 1; nC++)
        {
            movLives.add(new Life(TargetSpace.TS_WIDTH * (3 + (2 * nC)), TargetSpace.TS_HEIGHT*35));
        }
    }

    public static void spawnPacman(boolean bFirst)
    {
        if (bFirst)
        {
            pacman = new Pacman();
            movPacman.add(pacman);
        }
        else
        {
            pacman = new Pacman();
            movPacman.add(pacman);
        }

    }

    public static void spawnCherry()
    {
        Cherry cherry = new Cherry();
        movDots.add(cherry);
    }

    public static void spawnBlinky(boolean bFirstBlinky)
    {
        if (bFirstBlinky)
        {
            Blinky blinky = new Blinky(true);
            movFoes.add(blinky);
        }
        else
        {
            Blinky blinky = new Blinky(false);
            blinky.setRespawn(true);
            movFoes.add(blinky);
        }

    }

    public static void spawnPinky(boolean bFirstPinky)
    {
        if (bFirstPinky)
        {
            Pinky pinky = new Pinky();
            movFoes.add(pinky);
        }
        else
        {
            Pinky pinky = new Pinky();
            pinky.setRespawn(true);
            movFoes.add(pinky);
        }

    }

    public static void spawnInky(boolean bFirstInky)
    {
        if (bFirstInky)
        {
            Inky inky = new Inky();
            movFoes.add(inky);
        }
        else
        {
            Inky inky = new Inky();
            inky.setRespawn(true);
            movFoes.add(inky);
        }

    }

    public static void spawnClyde(boolean bFirstClyde)
    {
        if (bFirstClyde)
        {
            Clyde clyde = new Clyde();
            movFoes.add(clyde);
        }
        else
        {
            Clyde clyde = new Clyde();
            clyde.setRespawn(true);
            movFoes.add(clyde);
        }

    }

    public static void setScatterTimer()
    {

    }

    public static void setChaseTimer()
    {

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
        movDots.clear();
        movEnergizers.clear();
		movFloaters.clear();
        movPacman.clear();
	}

    public static void startNextLevel(int nLevel)
    {
        initPacmanLives();
        setMaze();
        setLevel(nLevel);
        Game.setnTick(0);
        Game.setDotCounter(0);
        Game.setEnergizerCounter(0);
        Game.setIsInvincible(false);
        Game.setFruitBool(false);

    }

    public static void spawnAllAfterPause()
    {
        Game.setnTick(0);
        setTimers();
        spawnPacman(true);
        spawnBlinky(true);
        spawnPinky(true);
        spawnInky(true);
        spawnClyde(true);
        Game.setIsInvincible(false);
        Game.getSiren().loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void initialSpawn()
    {
        setTimers();
        spawnPacman(true);
        spawnBlinky(true);
        spawnPinky(true);
        spawnInky(true);
        spawnClyde(true);
    }

    public static void resetLevel()
    {
        movLives.clear();
        Game.setnTick(0);
        initPacmanLives();
        setTimers();

    }

    public static void ouija(int nTick)
    {
        spawnBlinky(false);
//
//         switch(nTick % 4)
//        {
////            case 0: spawnBlinky(true);
////                break;
////            case 1: spawnInky(true);
////                break;
////            case 2: spawnPinky(true);
////                break;
//            default: spawnBlinky(false);
//                break;
//        }
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
	
	public static boolean isGameOver() {		//if pacman's lives = 0, then game over
		if (Game.getLives() == 0) {
			Game.setStarted(false);
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
