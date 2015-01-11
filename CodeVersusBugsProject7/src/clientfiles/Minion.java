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

import java.awt.Image;
public class Minion extends Malware
{
	public static Image sprite = MyImages.minion;
	public static Image tankSprite = MyImages.tankMinion;
	public static Image rushSprite = MyImages.rushMinion;
	private int w = Game.widthOfGamePanel;
	
	/**
	 * Creates a virus.
	 * Lanes are numbered from left to right.
	 * 
	 * @param lane
	 * @param y
	 */
	public Minion(int typeToSet, int lane, int y)
	{
		super(typeToSet, lane, y);
		
		//set type
		type = typeToSet;
		switch(type)
		{
			case NORMAL:
				//initialize instance variables
				health = 60;
				reward = 1;
				speed = (int) (w * 0.025);
				break;
			case TANK:
				//initialize instance variables
				health = 200;
				reward = 2;
				speed = (int) (w * 0.025 * 0.85);
				break;
			case RUSH:
				//initialize instance variables
				health = 150;
				reward = 2;
				speed = (int) (w * 0.025 * 1.2);
				break;
		}
	}
}