/**CodeVersusBugs.java
 * This file launches the game and contains the game loop. It may not access the EDT.
 * 
 * @author Adel Hassan & Patrick Kenney
 * 
 * UPDATE LOG
 * 11/2/14:	
 * 		Now keeps track of fps and displays it in the info panel
 * 
 * ----------------------------------------------------------------------
 * 
 * Copyright 2014 Adel Hassan and Patrick Kenney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clientfiles;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.*;

public class CodeVersusBugs
{
	public static Game game;
	
	public static void main(String args[])
	{
		try
		{
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			MyImages.initializeImages();
			game = new Game();
			
			try 
			{
				SwingUtilities.invokeAndWait(new StartMenuCreator());
			}
			catch (InvocationTargetException e1) 
			{
				e1.printStackTrace();
			}
			catch (InterruptedException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Game.startMenu.setIconImage(MyImages.miniMinion);
			
			Game.gf = new GameFrame();
			
			//time variables for game loop
			long lastUpdateTime = System.nanoTime();
			final int TARGET_FPS = 60;
			final double ONE_SECOND= 1e9;
			final long TARGET_TIME = (long) (ONE_SECOND/TARGET_FPS);
			int framesThisSec=0;
			int lastFrameTime=0;
			
			//intentionally infinite loop
			while (Game.gameState != Game.OVER)
			{
				long now = System.nanoTime();
				long updateLength = now - lastUpdateTime;
				lastUpdateTime = now;
				Game.numFramesPassed = (double)updateLength/TARGET_TIME;
				
				if(Game.gameState == Game.START)
					SwingUtilities.invokeLater(Game.startMenu);
				
				//run game updates and render on EDT if not paused
				else if(Game.gameState == Game.PLAYING)
					SwingUtilities.invokeLater(game);
				else if(Game.gameState == Game.PAUSED)
					SwingUtilities.invokeLater(new PauseButtonListener());
				else if(Game.gameState == Game.OVER)
					break;
				
				//get fps
				framesThisSec++;
				lastFrameTime+=updateLength;
				if(lastFrameTime>ONE_SECOND)
				{
					Game.fps=framesThisSec;
					framesThisSec=0;
					lastFrameTime=0;
					
					Game.gf.fpsCounter.setText(Game.fps+" fps");
					Game.infoPanel.repaint();
				}
				
				//old debug code, to be removed
				//System.out.println("update length "+updateLength);
				//System.out.println(game.numFramesPassed+" frames");
				
				try
				{
					//remember to convert ns to ms
					Thread.sleep((TARGET_TIME-(System.nanoTime()-lastUpdateTime))/1000000);
				}
				catch(InterruptedException e)
				{
					System.out.println("couldn't sleep the thread");
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("last frame took too long");
				}
			}
			
			//end of game
			Game.gf.setVisible(false);
			JOptionPane.showMessageDialog(null, "You lost on round " + Game.level);
			System.exit(0);
		}
		catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null,"something was null");
			//JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		/*catch(UnsupportedLookAndFeelException e)
		{
			JOptionPane.showMessageDialog(null,"nimbus didn't work");
		}*/
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"something went wrong");
		}
	}
}
