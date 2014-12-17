/**Minion.java
 * Handles minion-specific stuff.
 * 
 * UPDATE LOG
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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Minion extends Malware
{
	
	public static Image sprite = MyImages.minion;
	private int w = Game.widthOfGamePanel;
	public int speed = (int) (w * 0.025);
	
	/**
	 * Creates a virus.
	 * Lanes are numbered from left to right.
	 * 
	 * @param lane
	 * @param y
	 */
	public Minion(int lane, int y)
	{
		super(lane, y);
		
		//initialize instance variables
		health = 100;
		reward = 4;
	}
}