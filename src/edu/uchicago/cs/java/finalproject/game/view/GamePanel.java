package edu.uchicago.cs.java.finalproject.game.view;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;


import edu.uchicago.cs.java.finalproject.controller.Game;
import edu.uchicago.cs.java.finalproject.game.model.CommandCenter;
import edu.uchicago.cs.java.finalproject.game.model.Falcon;
import edu.uchicago.cs.java.finalproject.game.model.Movable;
import edu.uchicago.cs.java.finalproject.game.model.TargetSpace;


public class GamePanel extends Panel {
	
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-bufferred image. 
	private Dimension dimOff;
	private Image imgOff;
	private Graphics grpOff;
	
	private GameFrame gmf;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
    private Font fntlogo = new Font("Arial", Font.PLAIN, 2);

	private FontMetrics fmt; 
	private int nFontWidth;
	private int nFontHeight;
	private String strDisplay = "";

    private static final String strFile = System.getProperty("user.dir") + "/src/edu/uchicago/cs/java/finalproject/game/model/Joystix.TTF";

    public Font joystix = initJoystixFont();



	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public GamePanel(Dimension dim){
        initJoystixFont();
	    gmf = new GameFrame();
		gmf.getContentPane().add(this);
		gmf.pack();
		initView();
		
		gmf.setSize(dim);
		gmf.setTitle("PAC-MAN");
		gmf.setResizable(false);
		gmf.setVisible(true);
		this.setFocusable(true);


	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================
	
	private void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(fnt);
		if (CommandCenter.getScore() != 0) {
			g.drawString("" + CommandCenter.getScore(), nFontWidth, nFontHeight);
		} else {
			g.drawString("00", nFontWidth, nFontHeight);
		}
	}

    private void drawLevel(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.setFont(fnt);
        if(CommandCenter.getLevel() != 0)
        {
            g.drawString("Level " + CommandCenter.getLevel(), TargetSpace.TS_WIDTH * 24, TargetSpace.TS_HEIGHT * 35);
        }
    }
	
	@SuppressWarnings("unchecked")
	public void update(Graphics g) {
		if (grpOff == null || Game.DIM.width != dimOff.width
				|| Game.DIM.height != dimOff.height) {
			dimOff = Game.DIM;
			imgOff = createImage(Game.DIM.width, Game.DIM.height);
			grpOff = imgOff.getGraphics();
		}
		// Fill in background with black.
		grpOff.setColor(Color.black);
		grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);

		drawScore(grpOff);
        drawLevel(grpOff);

		if (!CommandCenter.isPlaying()) {
            //printLogo();
			displayTextOnScreen();
		} else if (CommandCenter.isPaused()) {
			strDisplay = "Game Paused";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
		}
		
		//playing and not paused!
		else {
			
			//draw them in decreasing level of importance
			//friends will be on top layer and debris on the bottom
			iterateMovables(grpOff,
                       CommandCenter.movLives,
					   CommandCenter.movDebris,
                       CommandCenter.movDots,
                       CommandCenter.movEnergizers,
			           //CommandCenter.movFloaters,
			           CommandCenter.movFoes,
                       CommandCenter.movPacman,
			           CommandCenter.movFriends);
			
			
			//drawNumberShipsLeft(grpOff);
			if (CommandCenter.isGameOver()) {
				CommandCenter.setPlaying(false);
				//bPlaying = false;
			}
		}
		//draw the double-Buffered Image to the graphics context of the panel
		g.drawImage(imgOff, 0, 0, this);
	} 


	
	//for each movable array, process it.
	private void iterateMovables(Graphics g, CopyOnWriteArrayList<Movable>...movMovz){
		
		for (CopyOnWriteArrayList<Movable> movMovs : movMovz) {
			for (Movable mov : movMovs) {

				mov.move();
				mov.draw(g);
				mov.fadeInOut();
				mov.expire();
			}
		}
		
	}
	

	// Draw the number of falcons left on the bottom-right of the screen. 
	private void drawNumberShipsLeft(Graphics g) {
		Falcon fal = CommandCenter.getFalcon();
		double[] dLens = fal.getLengths();
		int nLen = fal.getDegrees().length;
		Point[] pntMs = new Point[nLen];
		int[] nXs = new int[nLen];
		int[] nYs = new int[nLen];
	
		//convert to cartesian points
		for (int nC = 0; nC < nLen; nC++) {
			pntMs[nC] = new Point((int) (10 * dLens[nC] * Math.sin(Math
					.toRadians(90) + fal.getDegrees()[nC])),
					(int) (10 * dLens[nC] * Math.cos(Math.toRadians(90)
							+ fal.getDegrees()[nC])));
		}
		
		//set the color to white
		g.setColor(Color.white);
		//for each falcon left (not including the one that is playing)
		for (int nD = 1; nD < CommandCenter.getNumFalcons(); nD++) {
			//create x and y values for the objects to the bottom right using cartesean points again
			for (int nC = 0; nC < fal.getDegrees().length; nC++) {
				nXs[nC] = pntMs[nC].x + Game.DIM.width - (20 * nD);
				nYs[nC] = pntMs[nC].y + Game.DIM.height - 40;
			}
			g.drawPolygon(nXs, nYs, nLen);
		} 
	}
	
	private void initView() {
		Graphics g = getGraphics();			// get the graphics context for the panel
		g.setFont(fnt);						// take care of some simple font stuff
		fmt = g.getFontMetrics();
		nFontWidth = fmt.getMaxAdvance();
		nFontHeight = fmt.getHeight();
		g.setFont(fntBig);					// set font info  CHANGED
	}
	
	// This method draws some text to the middle of the screen before/after a game
	private void displayTextOnScreen() {

		//strDisplay = "GAME OVER";
		//grpOff.drawString(strDisplay,
		//		(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

		strDisplay = "use the arrow keys to turn Pac-Man, don't hold them down";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 100);

		//strDisplay = "use the space bar to fire";
		//grpOff.drawString(strDisplay,
		//		(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
		//				+ nFontHeight + 80);

		strDisplay = "'S' to Start";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 160);

		strDisplay = "'P' to Pause";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 200);

		strDisplay = "'Q' to Quit";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 240);
		//strDisplay = "left pinkie on 'A' for Shield";
		//grpOff.drawString(strDisplay,
		//		(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
		//				+ nFontHeight + 240);

		//strDisplay = "left index finger on 'F' for Guided Missile";
		//grpOff.drawString(strDisplay,
		//		(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
		//				+ nFontHeight + 280);

		//strDisplay = "'Numeric-Enter' for Hyperspace";
		//grpOff.drawString(strDisplay,
		//		(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
		//				+ nFontHeight + 320);
	}
	
	public GameFrame getFrm() {return this.gmf;}
	public void setFrm(GameFrame frm) {this.gmf = frm;}

    public Font initJoystixFont()
    {
        try
        {
            Font fntTemp = Font.createFont(Font.TRUETYPE_FONT,new File(strFile));

            return fntTemp.deriveFont(18f);
        } catch (FontFormatException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void printLogo()
    {
        String strLogo =
                        "                                   .:rrs;.                                                                  \n" +
                        "                                 ,2Ah5si3BMr             .3HA                                               \n" +
                        "                     r::       ,H@G:     .;&M;           s@#@@.        .3As                                 \n" +
                        "       .:rS22r.     ;@@@B     .#@,    ...    @i          s#. @@:     .3@#@@       :A&r                      \n" +
                        " :sXB#@@#Hh22XX#@:  h# :@@    X@   .....  .S@@2 S5XX2A:  iB   #@r  .2@#: @9      i@MM@:     ,:              \n" +
                        " i@A;:,         2@r:#s  .@@:  @2 ..,,,.,iB@@S.  @@SS52@2 SH    G@2s@@;  .@r    ,A@i  @M     h@@A    2i:.    \n" +
                        "  @S      ri   ,sX@XA     A@i #2 ..,,,.r@@2     s@..,.A@i5G     s@@r    ;@,   r@M,   2@.   .#h3@S  ;@##@@#  \n" +
                        "  X#  ....@@.  ;Xr@@: .    r@5.M ......:ii29Xi;  .@@@@#Ai92 ..   .   .. i@  .9@5      @G   r@, M@: A#  .;2@ \n" +
                        "  ;@r .,.   ,:s5:M@A ..@@. rs#A5S  ....    s#;@;        :Bs ...    .. G,X@ ,##:  ,s.  h@   &A   @@;#;    ;@:\n" +
                        "   #A ... 2hS23h@9M  . sS   3;9@AS:       ;5S@@.        i@,      .... @.HB;@X  . &@2 ..@S ;#r   .@@M  .. @@ \n" +
                        "   5@. .. 9X;@2::Xi ..    .:,#:;@Xr22srrriM##i           #3irr;:,.:XSsG @#9,  ..  .  3;3@ SM  .  ,@r .. ;@S \n" +
                        "   ,@i .   #.@  iA     .,:sAG3hS@@5 .:sX9S;               X@@@@@@@@@@#hX@@: ..     ,..A.@93i ...  ;. ., M@  \n" +
                        "    M# :;;;& #9 M@SH@@@@@@BXir;,                                   .,;riS&M@@@@@AXsXXr& 2@H  ...     rH,@i  \n" +
                        "    r@i9HHBAH@@  2hi;,                                                       .:s3B@@@#HXh@A      ,.  @:9@   \n" +
                        "      sMH2r:.                                                                         :r5G@@@@AS;2Xsi& @S   \n" +
                        "                                                                                            :SH@@@@#H:9@    \n" +
                        "                                                                                                  ,;s5hr    \n";

        grpOff.drawString(strLogo, nFontHeight/2, nFontWidth/2);


                //(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                //        + nFontHeight + 40);
    }
}