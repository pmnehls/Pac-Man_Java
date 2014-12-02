
/*
                                     ,2Ah5si3BMr             .3HA
                         r::       ,H@G:     .;&M;           s@#@@.        .3As
           .:rS22r.     ;@@@B     .#@,    ...    @i          s#. @@:     .3@#@@       :A&r
     :sXB#@@#Hh22XX#@:  h# :@@    X@   .....  .S@@2 S5XX2A:  iB   #@r  .2@#: @9      i@MM@:     ,:
     i@A;:,         2@r:#s  .@@:  @2 ..,,,.,iB@@S.  @@SS52@2 SH    G@2s@@;  .@r    ,A@i  @M     h@@A    2i:.
      @S      ri   ,sX@XA     A@i #2 ..,,,.r@@2     s@..,.A@i5G     s@@r    ;@,   r@M,   2@.   .#h3@S  ;@##@@#
      X#  ....@@.  ;Xr@@: .    r@5.M ......:ii29Xi;  .@@@@#Ai92 ..   .   .. i@  .9@5      @G   r@, M@: A#  .;2@
      ;@r .,.   ,:s5:M@A ..@@. rs#A5S  ....    s#;@;        :Bs ...    .. G,X@ ,##:  ,s.  h@   &A   @@;#;    ;@:
       #A ... 2hS23h@9M  . sS   3;9@AS:       ;5S@@.        i@,      .... @.HB;@X  . &@2 ..@S ;#r   .@@M  .. @@
       5@. .. 9X;@2::Xi ..    .:,#:;@Xr22srrriM##i           #3irr;:,.:XSsG @#9,  ..  .  3;3@ SM  .  ,@r .. ;@S
       ,@i .   #.@  iA     .,:sAG3hS@@5 .:sX9S;               X@@@@@@@@@@#hX@@: ..     ,..A.@93i ...  ;. ., M@
        M# :;;;& #9 M@SH@@@@@@BXir;,                                   .,;riS&M@@@@@AXsXXr& 2@H  ...     rH,@i
        r@i9HHBAH@@  2hi;,                                                       .:s3B@@@#HXh@A      ,.  @:9@
          sMH2r:.                                                                         :r5G@@@@AS;2Xsi& @S
                                                                                                :SH@@@@#H:9@
                                                                                                      ,;s5hr
*/

package edu.uchicago.cs.java.finalproject.controller;

import sun.audio.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.Clip;

import edu.uchicago.cs.java.finalproject.game.model.*;
import edu.uchicago.cs.java.finalproject.game.view.*;
import edu.uchicago.cs.java.finalproject.sounds.Sound;

// ===============================================
// == This Game class is the CONTROLLER
// ===============================================

public class Game implements Runnable, KeyListener {

	// ===============================================
	// FIELDS
	// ===============================================

	public static final Dimension DIM = new Dimension(TargetSpace.TS_WIDTH* 28, TargetSpace.TS_HEIGHT * 37); //the dimension of the game.
	private GamePanel gmpPanel;
	public static Random R = new Random();
	public final static int ANI_DELAY = 45; // milliseconds between screen
											// updates (animation)
	private Thread thrAnim;
	//private int nLevel = 1;
    private static int nLives = 4;
	private static int nTick = 0;
    private static int nTickStore;
    private static int nDotCounter;
    private static int nEnergizerCounter;
    private static int nScore = 0;
    private static int nGhostsEaten;
    private static int nScatterSeconds = 7; //hardcoded for testing
    private static int nChaseSeconds = 20; //hardcoded for testing
    private int nSirenTimer;
	private ArrayList<Tuple> tupMarkForRemovals;
	private ArrayList<Tuple> tupMarkForAdds;
	private boolean bMuted = true;
    private boolean bIntroDone = false;
    private boolean bInitial = true;
    private boolean bNewLevel;
    private boolean bRespawnAfterDeath;
    private static boolean bFruit = false;

    //energizer booleans and variables
    private static boolean isInvincible;
    private static int nScaredSeconds;

	private final int PAUSE = 80, // p key
			QUIT = 81, // q key
			LEFT = 37, // turn left; left arrow
			RIGHT = 39, // turn right; right arrow
			UP = 38, // move up
            DOWN = 40, // move down
			START = 83, // s key
			FIRE = 32, // space key
			MUTE = 77, // m-key mute

	// for possible future use
	// HYPER = 68, 					// d key
	// SHIELD = 65, 				// a key arrow
	// NUM_ENTER = 10, 				// hyp
	 SPECIAL = 70; 					// fire special weapon;  F key

	//private Clip clpThrust;
	//private Clip clpMusicBackground;
    private static Clip clpSiren;

	//private static final int SPAWN_NEW_SHIP_FLOATER = 1200;



	// ===============================================
	// ==CONSTRUCTOR
	// ===============================================

	public Game() {

		gmpPanel = new GamePanel(DIM);
		gmpPanel.addKeyListener(this);

		//clpThrust = Sound.clipForLoopFactory("whitenoise.wav");
		//clpMusicBackground = Sound.clipForLoopFactory("music-background.wav");
        clpSiren = Sound.clipForLoopFactory("siren.wav");

	}

	// ===============================================
	// ==METHODS
	// ===============================================

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
					public void run() {
						try {
							Game game = new Game(); // construct itself
							game.fireUpAnimThread();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void fireUpAnimThread() { // called initially
		if (thrAnim == null) {
			thrAnim = new Thread(this); // pass the thread a runnable object (this)
			thrAnim.start();
		}
	}

	// implements runnable - must have run method
	public void run() {

		// lower this thread's priority; let the "main" aka 'Event Dispatch'
		// thread do what it needs to do first
		thrAnim.setPriority(Thread.MIN_PRIORITY);

		// and get the current time
		long lStartTime = System.currentTimeMillis();

        //set nTick to zero when starting the program
        nTick = 0;

		// this thread animates the scene
		while (Thread.currentThread() == thrAnim) {
			tick();

            //spawn initial stuff after music finishes
            if (nTick == 100 && bInitial)
            {
                bInitial = false;
                CommandCenter.initialSpawn();
                nTick = 0;
            }

			spawnNewShipFloater();
			gmpPanel.update(gmpPanel.getGraphics()); // update takes the graphics context we must 
														// surround the sleep() in a try/catch block
														// this simply controls delay time between 
														// the frames of the animation

			//this might be a good place to check for collisions
			checkCollisions();


            //spawn pacman and ghosts after a pause after new level or death
            if(bNewLevel || bRespawnAfterDeath)
            {
                if (nTick > 67)
                {
                    CommandCenter.spawnAllAfterPause();
                    bNewLevel = false;
                    bRespawnAfterDeath = false;
                    nTick = 0;
                }
            }
			//this might be a god place to check if the level is clear (no more foes)
			//if the level is clear then spawn some big asteroids -- the number of asteroids 
			//should increase with the level. 

			checkNewLevel();

			try {
				// The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update) 
				// between frames takes longer than ANI_DELAY, then the difference between lStartTime - 
				// System.currentTimeMillis() will be negative, then zero will be the sleep time
				lStartTime += ANI_DELAY;
				Thread.sleep(Math.max(0,
						lStartTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				// just skip this frame -- no big deal
				continue;
			}
		} // end while
	} // end run

	private void checkCollisions() {

//        if (nDotCounter == 70 && !bFruit)
//        {
//            if (CommandCenter.getLevel() == 1)
//            {
//                CommandCenter.spawnCherry();
//                bFruit = true;
//            }
//        }

        System.out.println(nTick);

        //pause at new level or respawn
//        if(bNewLevel || bRespawnAfterDeath)
//        {
//            if (nTick > 67)
//            {
//                System.out.printf("respawning at %d", nTick);
//                CommandCenter.spawnAllAfterPause();
//                bNewLevel = false;
//                bRespawnAfterDeath = false;
//            }
//        }

        //@formatter:off
		//for each friend in movFriends
			//for each foe in movFoes
				//if the distance between the two centers is less than the sum of their radii
					//mark it for removal
		
		//for each mark-for-removal
			//remove it
		//for each mark-for-add
			//add it
		//@formatter:on
		
		//we use this ArrayList to keep pairs of movMovables/movTarget for either
		//removal or insertion into our arrayLists later on
		tupMarkForRemovals = new ArrayList<Tuple>();
		tupMarkForAdds = new ArrayList<Tuple>();

		Point pntFriendCenter, pntFoeCenter;
		int nFriendRadiux, nFoeRadiux;

        //for (Movable pacman : CommandCenter.movPacman)
        //{
        if (CommandCenter.movPacman.size() != 0)
        {
            for (Movable dot : CommandCenter.movDots)
            {
                Point pntPacman = CommandCenter.movPacman.get(0).getCenter();
                Point pntDot = dot.getCenter();

                if (Math.abs(pntPacman.x - pntDot.x) < 6)
                {
                    if (Math.abs(pntPacman.y - pntDot.y) < 6)
                    {
                        if (dot instanceof Cherry)
                        {
                            CommandCenter.setScore(CommandCenter.getScore() + 100);
                            tupMarkForRemovals.add(new Tuple(CommandCenter.movDots, dot));
                        }
                        else
                        {
                            stopLoopingSounds(clpSiren);
                            CommandCenter.setScore(CommandCenter.getScore() + 10);
                            tupMarkForRemovals.add(new Tuple(CommandCenter.movDots, dot));
                            nDotCounter = nDotCounter + 1; // this needs to be after so last dot is removed when level is clear
                            nSirenTimer = nTick;
                        }

                    }
                }

            }


            //play siren sound if dot sound is done
            if (nSirenTimer + 40 < nTick)
            {
                clpSiren.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        //}

        for (Movable pacman : CommandCenter.movPacman)
        {
            for (Movable energizer : CommandCenter.movEnergizers)
            {
                Point pntPacman = pacman.getCenter();
                Point pntEnergizer = energizer.getCenter();

                if (Math.abs(pntPacman.x - pntEnergizer.x) < 10)
                {
                    if (Math.abs(pntPacman.y - pntEnergizer.y) < 10)
                    {
                        tupMarkForRemovals.add(new Tuple(CommandCenter.movEnergizers, energizer));
                        nEnergizerCounter += 1;
                        isInvincible = true;
                        nTickStore = nTick;
                        CommandCenter.setScore(CommandCenter.getScore() + 50);

                        for (int nC = 0; nC < CommandCenter.movFoes.size(); nC++)
                        {
                            if (CommandCenter.movFoes.get(nC) instanceof Blinky)
                            {
                                CommandCenter.movFoes.get(nC).setRespawn(false);
                            }
                            else if (CommandCenter.movFoes.get(nC) instanceof Inky)
                            {
                                CommandCenter.movFoes.get(nC).setRespawn(false);
                            }
                            else if (CommandCenter.movFoes.get(nC) instanceof Pinky)
                            {
                                CommandCenter.movFoes.get(nC).setRespawn(false);
                            }
                            else if (CommandCenter.movFoes.get(nC) instanceof Clyde)
                            {
                                CommandCenter.movFoes.get(nC).setRespawn(false);
                            }
                        }
                    }
                }

            }
        }

        // eat fruit
//        for (Movable pacman : CommandCenter.movPacman)
//        {
//            for (Movable fruit : CommandCenter.movFruit)
//            {
//                Point pntPacman = pacman.getCenter();
//                Point pntFruit = fruit.getCenter();
//
//                if (Math.abs(pntPacman.x - pntFruit.x) < 5)
//                {
//                    if (Math.abs(pntPacman.y - pntFruit.y) < 5)
//                    {
//                        tupMarkForRemovals.add(new Tuple(CommandCenter.movFruit, fruit));
//                        //Sound.playSound("pacman_eatfruit.wav");
//
//
//                        if (fruit instanceof Cherry)
//                        {
//                            CommandCenter.setScore(CommandCenter.getScore() + 100);
//                        }
//
//                    }
//                }
//            }
//        }
        //handle pac-man and ghost collision
        for (Movable pacman : CommandCenter.movPacman)
        {
            for (Movable ghost : CommandCenter.movFoes)
            {
                Point pntPacman = pacman.getCenter();
                Point pacManSquare = new Point((pntPacman.x/TargetSpace.TS_WIDTH)+1, (pntPacman.y/TargetSpace.TS_HEIGHT)+1);
                Point pntGhost = ghost.getCenter();
                Point ghostSquare = new Point((pntGhost.x/TargetSpace.TS_WIDTH)+1, (pntGhost.y/TargetSpace.TS_HEIGHT)+1);

                //resets ghost eaten counter for proper bonus points
                if (!isInvincible)
                {
                    nGhostsEaten = 0;
                }

                if ((pacManSquare.x == ghostSquare.x) || Math.abs(pntPacman.x - pntGhost.x) < 3)
                {
                    if ((pacManSquare.y == ghostSquare.y) || Math.abs(pntPacman.y - pntGhost.y) < 3)
                    {
                        if (isInvincible && !ghost.getRespawn())
                        {
                            Sound.playSound("pacman_eatghost.wav");
                            nGhostsEaten += 1;
                            if (ghost instanceof Blinky)
                            {
                                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, ghost));
                                CommandCenter.spawnBlinky(false);

                            }
                            else if (ghost instanceof Pinky)
                            {
                                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, ghost));
                                CommandCenter.spawnPinky(false);
                            }
                            else if (ghost instanceof Inky)
                            {
                                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, ghost));
                                CommandCenter.spawnInky(false);
                            }
                            else if (ghost instanceof Clyde)
                            {
                                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, ghost));
                                CommandCenter.spawnClyde(false);
                            }

                            switch (nGhostsEaten)
                            {
                                case 1:
                                    CommandCenter.setScore(CommandCenter.getScore() + 200);
                                    break;
                                case 2:
                                    CommandCenter.setScore(CommandCenter.getScore() + 400);
                                    break;
                                case 3:
                                    CommandCenter.setScore(CommandCenter.getScore() + 800);
                                    break;
                                case 4:
                                    CommandCenter.setScore(CommandCenter.getScore() + 1600);
                                    break;
                            }
                        }
                        else
                        {
                            tupMarkForRemovals.add(new Tuple(CommandCenter.movPacman, pacman));
                            for (int nC = 0; nC < CommandCenter.movFoes.size(); nC ++)
                            {
                                tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes,
                                        CommandCenter.movFoes.get(nC)));
                            }

                            stopLoopingSounds(clpSiren, Pacman.getWaka());
                            try
                            {
                                Thread.sleep(150);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            Sound.playSound("pacman_death.wav");
                            nLives -= 1;
                            nTick = 0;
                            bRespawnAfterDeath = true;
                            CommandCenter.resetLevel();

                        }

                    }
                }

            }
        }

//		for (Movable movFriend : CommandCenter.movFriends) {
//			for (Movable movFoe : CommandCenter.movFoes) {
//
//				pntFriendCenter = movFriend.getCenter();
//				pntFoeCenter = movFoe.getCenter();
//				nFriendRadiux = movFriend.getRadius();
//				nFoeRadiux = movFoe.getRadius();
//
//				//detect collision
//				if (pntFriendCenter.distance(pntFoeCenter) < (nFriendRadiux + nFoeRadiux)) {
//
//					//falcon
//					if ((movFriend instanceof Falcon) ){
//						if (!CommandCenter.getFalcon().getProtected()){
//							tupMarkForRemovals.add(new Tuple(CommandCenter.movFriends, movFriend));
//							CommandCenter.spawnFalcon(false);
//							killFoe(movFoe);
//						}
//					}
//					//not the falcon
//					else {
//						tupMarkForRemovals.add(new Tuple(CommandCenter.movFriends, movFriend));
//						killFoe(movFoe);
//					}//end else
//
//					//explode/remove foe
//
//
//
//				}//end if
//			}//end inner for
//		}//end outer for


//		//check for collisions between falcon and floaters
//		if (CommandCenter.getFalcon() != null){
//			Point pntFalCenter = CommandCenter.getFalcon().getCenter();
//			int nFalRadiux = CommandCenter.getFalcon().getRadius();
//			Point pntFloaterCenter;
//			int nFloaterRadiux;
//
//			for (Movable movFloater : CommandCenter.movFloaters) {
//				pntFloaterCenter = movFloater.getCenter();
//				nFloaterRadiux = movFloater.getRadius();
//
//				//detect collision
//				if (pntFalCenter.distance(pntFloaterCenter) < (nFalRadiux + nFloaterRadiux)) {
//
//
//					tupMarkForRemovals.add(new Tuple(CommandCenter.movFloaters, movFloater));
//					Sound.playSound("pacman_eatghost.wav");
//
//				}//end if
//			}//end inner for
//		}//end if not null
		
		//remove these objects from their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForRemovals) 
			tup.removeMovable();
		
		//add these objects to their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForAdds) 
			tup.addMovable();

		//call garbage collection
		System.gc();
		
	}//end meth

	private void killFoe(Movable movFoe) {
		
		if (movFoe instanceof Asteroid){

			//we know this is an Asteroid, so we can cast without threat of ClassCastException
			Asteroid astExploded = (Asteroid)movFoe;
			//big asteroid 
			if(astExploded.getSize() == 0){
				//spawn two medium Asteroids
				tupMarkForAdds.add(new Tuple(CommandCenter.movFoes,new Asteroid(astExploded)));
				tupMarkForAdds.add(new Tuple(CommandCenter.movFoes,new Asteroid(astExploded)));
				
			} 
			//medium size aseroid exploded
			else if(astExploded.getSize() == 1){
				//spawn three small Asteroids
				tupMarkForAdds.add(new Tuple(CommandCenter.movFoes,new Asteroid(astExploded)));
				tupMarkForAdds.add(new Tuple(CommandCenter.movFoes,new Asteroid(astExploded)));
				tupMarkForAdds.add(new Tuple(CommandCenter.movFoes,new Asteroid(astExploded)));
			}
			//remove the original Foe	
			tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, movFoe));
		
			
		} 
		//not an asteroid
		else {
			//remove the original Foe
			tupMarkForRemovals.add(new Tuple(CommandCenter.movFoes, movFoe));
		}
		
		
		

		
		
		
		
	}

	//some methods for timing events in the game,
	//such as the appearance of UFOs, floaters (power-ups), etc. 
	public void tick() {
		if (nTick == Integer.MAX_VALUE)
			nTick = 0;
		else
			nTick++;
	}

	public int getTick() {
		return nTick;
	}

	private void spawnNewShipFloater() {
		//make the appearance of power-up dependent upon ticks and levels
		//the higher the level the more frequent the appearance
//		if (nTick % (SPAWN_NEW_SHIP_FLOATER - nLevel * 7) == 0) {
//			CommandCenter.movFloaters.add(new NewShipFloater());
//		}
	}

	// Called when user presses 's'
	private void startGame() {
		CommandCenter.clearAll();
        CommandCenter.playIntro();
		CommandCenter.initGame();
		CommandCenter.setPlaying(true);
		CommandCenter.setPaused(false);
		//if (!bMuted)
		   // clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
	}

	//this method spawns new asteroids
	private void spawnAsteroids(int nNum) {
		//for (int nC = 0; nC < nNum; nC++) {
		//	//Asteroids with size of zero are big
		//	CommandCenter.movFoes.add(new Asteroid(0));
		//}
	}
	
	
	private boolean isLevelClear(){
		//if there are no more Asteroids on the screen
		
		boolean bAsteroidFree = true;
		for (Movable movFoe : CommandCenter.movFoes) {
			if (movFoe instanceof Asteroid){
				bAsteroidFree = false;
				break;
			}
		}
		
		return bAsteroidFree;

		
	}
	
	private void checkNewLevel()
    {
        if (Game.getDotCounter() >= 240)
        {
            if(Game.getEnergizerCounter() >= 4)
            {
                CommandCenter.setLevel(CommandCenter.getLevel() + 1);
                CommandCenter.clearAll();
                stopLoopingSounds(clpSiren, Pacman.getWaka());
                CommandCenter.startNextLevel(CommandCenter.getLevel());
                nTick = 0;
                bNewLevel = true;

            }
        }
		//if (isLevelClear() ){
		//	if (CommandCenter.getFalcon() !=null)
		//		CommandCenter.getFalcon().setProtected(true);
        //
		//	spawnAsteroids(CommandCenter.getLevel() + 2);
		//	//CommandCenter.setLevel(CommandCenter.getLevel() + 1);
        //
        //		}
	}
	
	
	public static int getnTick()
    {
        return nTick;
    }

    public static void setnTick(int nTick)
    {
        Game.nTick = nTick;
    }

    public static int getEnergizerCounter()
    {
        return nEnergizerCounter;
    }

    public static void setEnergizerCounter(int nEnergizerCounter)
    {
        Game.nEnergizerCounter = nEnergizerCounter;
    }

    public static int getDotCounter()
    {
        return nDotCounter;
    }

    public static void setDotCounter(int nDotCounter)
    {
        Game.nDotCounter = nDotCounter;
    }

    public static int getScore()
    {
        return nScore;
    }

    public static void setScore(int nScore)
    {
        Game.nScore = nScore;
    }

    public static int getnScatterSeconds()
    {
        return nScatterSeconds;
    }

    public static void setScatterSeconds(int nSeconds)
    {
        Game.nScatterSeconds = nSeconds;
    }

    public static int getScaredSeconds()
    {
        return nScaredSeconds;
    }

    public static void setScaredSeconds(int nScaredSeconds)
    {
        Game.nScaredSeconds = nScaredSeconds;
    }

    public static int getChaseSeconds()
    {
        return nChaseSeconds;
    }

    public static void setChaseSeconds(int nChaseSeconds)
    {
        Game.nChaseSeconds = nChaseSeconds;
    }

    public static boolean getIsInvincible()
    {
        return isInvincible;
    }

    public static void setIsInvincible(boolean isInvincible)
    {
        Game.isInvincible = isInvincible;
    }

    public static int getTickStore()
    {
        return nTickStore;
    }

    public static void setTickStore(int nTickStore)
    {
        Game.nTickStore = nTickStore;
    }

    public static int getLives()
    {
        return  Game.nLives;
    }

    public static void setLives(int nLives)
    {
        Game.nLives = nLives;
    }

    public static void setFruitBool(boolean bFruit)
    {
        Game.bFruit = bFruit;
    }

    public static Clip getSiren()
    {
        return Game.clpSiren;
    }

    // Varargs for stopping looping-music-clips
	private static void stopLoopingSounds(Clip... clpClips) {
		for (Clip clp : clpClips) {
			clp.stop();
		}
	}

	// ===============================================
	// KEYLISTENER METHODS
	// ===============================================

	@Override
	public void keyPressed(KeyEvent e) {
		Pacman pacman = CommandCenter.getPacman();
		int nKey = e.getKeyCode();
		// System.out.println(nKey);

		if (nKey == START && !CommandCenter.isPlaying())
        {
            startGame();
            nTick = 0;
        }

		if (pacman != null) {

			switch (nKey) {
			case PAUSE:
				CommandCenter.setPaused(!CommandCenter.isPaused());
				if (CommandCenter.isPaused())
                {
                    stopLoopingSounds(clpSiren, Pacman.getWaka());
                }
				//else
					//clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
				break;
			case QUIT:
				System.exit(0);
				break;
			case UP:
				pacman.turnUp();
				break;
			case LEFT:
				pacman.turnLeft();
				break;
			case RIGHT:
				pacman.turnRight();
				break;
            case DOWN:
                pacman.turnDown();
                break;

			// possible future use
			// case KILL:
			// case SHIELD:
			// case NUM_ENTER:

			default:
				break;
			}
		}
	}

    public void keyReleased(KeyEvent e) {}
//	@Override
//	public void keyReleased(KeyEvent e) {
//		Pacman pacman = CommandCenter.getPacman();
//		int nKey = e.getKeyCode();
//		 System.out.println(nKey);
//
//		if (pacman != null) {
//			switch (nKey) {
//			case FIRE:
//				//CommandCenter.movFriends.add(new Bullet(fal));
//				Sound.playSound("laser.wav");
//				break;
//
//			//special is a special weapon, current it just fires the cruise missile.
//			case SPECIAL:
//				//CommandCenter.movFriends.add(new Cruise(fal));
//				//Sound.playSound("laser.wav");
//				break;
//
//			case LEFT:
//				//fal.stopRotating();
//				break;
//			case RIGHT:
//				fal.stopRotating();
//				break;
//			case UP:
//				fal.thrustOff();
//				clpThrust.stop();
//				break;
//
//			case MUTE:
//				if (!bMuted){
//					stopLoopingSounds(clpMusicBackground);
//					bMuted = !bMuted;
//				}
//				else {
//					clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
//					bMuted = !bMuted;
//				}
//				break;
//
//
//			default:
//				break;
//			}
//		}
//	}

	@Override
	// Just need it b/c of KeyListener implementation
	public void keyTyped(KeyEvent e)
    {

	}

}

// ===============================================
// ==A tuple takes a reference to an ArrayList and a reference to a Movable
//This class is used in the collision detection method, to avoid mutating the array list while we are iterating
// it has two public methods that either remove or add the movable from the appropriate ArrayList 
// ===============================================

class Tuple{
	//this can be any one of several CopyOnWriteArrayList<Movable>
	private CopyOnWriteArrayList<Movable> movMovs;
	//this is the target movable object to remove
	private Movable movTarget;
	
	public Tuple(CopyOnWriteArrayList<Movable> movMovs, Movable movTarget) {
		this.movMovs = movMovs;
		this.movTarget = movTarget;
	}
	
	public void removeMovable(){
		movMovs.remove(movTarget);
	}
	
	public void addMovable(){
		movMovs.add(movTarget);
	}

}
 // this is a different class down here, chief!