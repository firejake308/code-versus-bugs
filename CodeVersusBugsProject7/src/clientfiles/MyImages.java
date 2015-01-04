package clientfiles;

//import java.awt.Toolkit;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**MyImages.java
 * Holds static BufferedImage variables for use in the other classes.
 * @author Ahmadul
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
public class MyImages 
{
	//frames and buttons
	public static BufferedImage miniMinion;
	public static BufferedImage miniMinion2;
	public static BufferedImage miniMinion3;
	public static BufferedImage play;
	public static BufferedImage pause;
	public static BufferedImage fastForwardOff;
	public static BufferedImage fastForwardOn;
	public static BufferedImage redArrow;
	public static BufferedImage arrowUp;
	public static BufferedImage arrowDown;
	public static BufferedImage dim;
	public static BufferedImage glow;
	public static BufferedImage startOpen;
	public static BufferedImage startClosed;
	public static BufferedImage creditsOpen;
	public static BufferedImage creditsClosed;
	public static BufferedImage optionsOpen;
	public static BufferedImage optionsClosed;
	public static BufferedImage directionsOpen;
	public static BufferedImage directionsClosed;
	public static BufferedImage exitOpen;
	public static BufferedImage exitClosed;
	public static BufferedImage backOpen;
	public static BufferedImage backClosed;
	public static BufferedImage buttonOpen;
	public static BufferedImage buttonClosed;
	public static BufferedImage delete;
	public static BufferedImage cure;
	public static BufferedImage connect;	
	//malware
	public static BufferedImage minion;
	public static BufferedImage tankMinion;
	public static BufferedImage rushMinion;
	public static BufferedImage wormHead;
	public static BufferedImage wormBody;
	public static BufferedImage trojan;
	public static BufferedImage dataFile;
	public static BufferedImage exeFile;
	public static BufferedImage secureFile;
	public static BufferedImage virus;
	public static BufferedImage spy;
	public static BufferedImage bot;
	//towers
	public static BufferedImage dt0;
	public static BufferedImage dt1;
	public static BufferedImage dt2;
	public static BufferedImage dt3;
	public static BufferedImage dt4;
	public static BufferedImage dt5;
	public static BufferedImage invalidDT;
	public static BufferedImage random;
	public static BufferedImage randomlethal;
	public static BufferedImage random0;
	public static BufferedImage random1;
	public static BufferedImage random2;
	public static BufferedImage random3;
	public static BufferedImage random4;
	public static BufferedImage random5;
	public static BufferedImage random6;
	public static BufferedImage random7;
	public static BufferedImage random8;
	public static BufferedImage random9;
	public static BufferedImage invalidRandom;
	public static BufferedImage scanner0;
	public static BufferedImage scanner1;
	public static BufferedImage scanner2;
	public static BufferedImage scanner3;
	public static BufferedImage scanner4;
	public static BufferedImage scanner5;
	public static BufferedImage encrypter0;
	public static BufferedImage encrypter1;
	public static BufferedImage encrypter2;
	public static BufferedImage encrypter3;
	public static BufferedImage encrypter4;
	public static BufferedImage encrypter5;
	public static BufferedImage invalidEncrypter;
	public static BufferedImage invalidScanner;
	public static BufferedImage firewall;
	public static BufferedImage firewallShopImage;
	public static BufferedImage firewallBroken;
	public static BufferedImage commTower;
	public static BufferedImage invalidCommTower;
	public static BufferedImage healthBar0;
	public static BufferedImage healthBar1;
	public static BufferedImage healthBar2;
	public static BufferedImage healthBar3;
	public static BufferedImage healthBar4;
	public static BufferedImage healthBar5;
	
	//projectiles
	public static BufferedImage cd;
	public static BufferedImage r0;
	public static BufferedImage r1;
	public static BufferedImage r2;
	public static BufferedImage r3;
	public static BufferedImage r4;
	public static BufferedImage r5;
	public static BufferedImage r6;
	public static BufferedImage r7;
	public static BufferedImage r8;
	public static BufferedImage r9;
	public static BufferedImage r0lethal;
	public static BufferedImage r1lethal;
	public static BufferedImage r2lethal;
	public static BufferedImage r3lethal;
	public static BufferedImage r4lethal;
	public static BufferedImage r5lethal;
	public static BufferedImage r6lethal;
	public static BufferedImage r7lethal;
	public static BufferedImage r8lethal;
	public static BufferedImage r9lethal;
	//map
	public static BufferedImage cpu;
	public static BufferedImage decoyCPU;
	public static BufferedImage modem;
	public static BufferedImage router;
	
	/*
	 * I believe I need the method due to the need for a try-catch
	 * 
	 */
	public static void initializeImages()
	{
		try
		{
			//frames and buttons
			miniMinion = ImageIO.read(MyImages.class.getResourceAsStream("resources/cvb_icon.png"));
			miniMinion2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/cvb_icon2.png"));
			miniMinion3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/cvb_icon3.png"));
			pause = ImageIO.read(MyImages.class.getResourceAsStream("resources/pausePauseButton.png"));
			play 			= ImageIO.read(MyImages.class.getResourceAsStream("resources/playPauseButton.png"));
			fastForwardOff 	= ImageIO.read(MyImages.class.getResourceAsStream("resources/FastForwardOff.png"));
			fastForwardOn 	= ImageIO.read(MyImages.class.getResourceAsStream("resources/FastForwardOn.png"));
			redArrow = ImageIO.read(MyImages.class.getResourceAsStream("resources/redArrow.png"));
			arrowUp = ImageIO.read(MyImages.class.getResourceAsStream("resources/arrowUp.png"));
			arrowDown = ImageIO.read(MyImages.class.getResourceAsStream("resources/arrowDown.png"));
			dim = ImageIO.read(MyImages.class.getResourceAsStream("resources/dim.png"));
			glow = ImageIO.read(MyImages.class.getResourceAsStream("resources/glow.png"));
			startOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/startBtnOpen.png"));
			startClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/startBtnClosed.png"));
			creditsOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/creditsBtnOpen.png"));
			creditsClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/creditsBtnClosed.png"));
			optionsOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/optionsBtnOpen.png"));
			optionsClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/optionsBtnClosed.png"));
			directionsOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/directionsBtnOpen.png"));
			directionsClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/directionsBtnClosed.png"));
			exitOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/exitBtnOpen.png"));
			exitClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/exitBtnClosed.png"));
			backOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/backBtnOpen.png"));
			backClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/backBtnClosed.png"));
			buttonOpen = ImageIO.read(MyImages.class.getResourceAsStream("resources/btnOpen.png"));
			buttonClosed = ImageIO.read(MyImages.class.getResourceAsStream("resources/btnClosed.png"));
			delete = ImageIO.read(MyImages.class.getResourceAsStream("resources/delete.png"));
			cure = ImageIO.read(MyImages.class.getResourceAsStream("resources/cure.png"));
			connect = ImageIO.read(MyImages.class.getResourceAsStream("resources/connect.png"));
			
			//malware
			minion = ImageIO.read(MyImages.class.getResourceAsStream("resources/virus2.png"));
			tankMinion = ImageIO.read(MyImages.class.getResourceAsStream("resources/virusRed.png"));
			rushMinion = ImageIO.read(MyImages.class.getResourceAsStream("resources/virusGreen.png"));
			wormHead = ImageIO.read(MyImages.class.getResourceAsStream("resources/wormHead.png"));
			wormBody = ImageIO.read(MyImages.class.getResourceAsStream("resources/wormBody.png"));
			trojan = ImageIO.read(MyImages.class.getResourceAsStream("resources/trojan1.png"));
			dataFile = ImageIO.read(MyImages.class.getResourceAsStream("resources/datafile.png"));
			exeFile = ImageIO.read(MyImages.class.getResourceAsStream("resources/exefile.png"));
			secureFile = ImageIO.read(MyImages.class.getResourceAsStream("resources/securefile.png"));
			virus = ImageIO.read(MyImages.class.getResourceAsStream("resources/virus3.png"));
			spy = ImageIO.read(MyImages.class.getResourceAsStream("resources/spy.png"));
			bot = ImageIO.read(MyImages.class.getResourceAsStream("resources/bot.png"));
			
			//towers
			dt0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower0.png"));
			dt1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower1.png"));
			dt2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower2.png"));
			dt3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower3.png"));
			dt4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower4.png"));
			dt5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/discThrower5.png"));
			invalidDT = ImageIO.read(MyImages.class.getResourceAsStream("resources/invalidDiscThrower1.png"));
			random = ImageIO.read(MyImages.class.getResourceAsStream("resources/random.png"));
			randomlethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/randomlethal.png"));
			random0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random0.png"));
			random1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random1.png"));
			random2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random2.png"));
			random3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random3.png"));
			random4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random4.png"));
			random5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random5.png"));
			random6 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random6.png"));
			random7 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random7.png"));
			random8 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random8.png"));
			random9 = ImageIO.read(MyImages.class.getResourceAsStream("resources/random9.png"));
			invalidRandom = ImageIO.read(MyImages.class.getResourceAsStream("resources/invalidRandom.png"));
			scanner0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner0.png"));
			scanner1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner1.png"));
			scanner2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner2.png"));
			scanner3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner3.png"));
			scanner4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner4.png"));
			scanner5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/scanner5.png"));
			encrypter0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter0.png"));
			encrypter1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter1.png"));
			encrypter2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter2.png"));
			encrypter3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter3.png"));
			encrypter4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter4.png"));
			encrypter5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/encrypter5.png"));
			invalidEncrypter = ImageIO.read(MyImages.class.getResourceAsStream("resources/invalidEncrypter.png"));
			invalidScanner = ImageIO.read(MyImages.class.getResourceAsStream("resources/invalidScanner.png"));
			firewall = ImageIO.read(MyImages.class.getResourceAsStream("resources/firewall1.png"));
			firewallShopImage = ImageIO.read(MyImages.class.getResourceAsStream("resources/firewall2.png"));
			firewallBroken = ImageIO.read(MyImages.class.getResourceAsStream("resources/firewallBroken.png"));
			commTower = ImageIO.read(MyImages.class.getResourceAsStream("resources/commTower.png"));
			invalidCommTower = ImageIO.read(MyImages.class.getResourceAsStream("resources/invalidCommTower.png"));
			healthBar0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar0.png"));
			healthBar1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar1.png"));
			healthBar2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar2.png"));
			healthBar3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar3.png"));
			healthBar4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar4.png"));
			healthBar5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/healthBar5.png"));
			
			//projectiles
			cd = ImageIO.read(MyImages.class.getResourceAsStream("resources/cd.png"));
			r0 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r0.png"));
			r1 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r1.png"));
			r2 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r2.png"));
			r3 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r3.png"));
			r4 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r4.png"));
			r5 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r5.png"));
			r6 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r6.png"));
			r7 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r7.png"));
			r8 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r8.png"));
			r9 = ImageIO.read(MyImages.class.getResourceAsStream("resources/r9.png"));
			r0lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r0lethal.png"));
			r1lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r1lethal.png"));
			r2lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r2lethal.png"));
			r3lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r3lethal.png"));
			r4lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r4lethal.png"));
			r5lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r5lethal.png"));
			r6lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r6lethal.png"));
			r7lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r7lethal.png"));
			r8lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r8lethal.png"));
			r9lethal = ImageIO.read(MyImages.class.getResourceAsStream("resources/r9lethal.png"));
			
			//map
			cpu = ImageIO.read(MyImages.class.getResourceAsStream("resources/cpu.png"));
			decoyCPU = ImageIO.read(MyImages.class.getResourceAsStream("resources/decoyCPU.png"));
			modem = ImageIO.read(MyImages.class.getResourceAsStream("resources/modem1.png"));
			router = ImageIO.read(MyImages.class.getResourceAsStream("resources/router1.png"));
		}
		catch(Exception e)
		{
			System.out.println("there was an error in loading images");
			JOptionPane.showMessageDialog(null, "there was an error in loading images");
			e.printStackTrace();
		}
	}
}
