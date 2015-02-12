package clientfiles;

import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

import static java.lang.System.*;

public class TypingWindow extends JFrame implements KeyListener
{
	public static TypingWindow game;
	
	private JTextArea typeZone;
	private JTextArea console;
	private JTextArea instructions;
	
	private int charsTyped;
	private int codeSegment;
	private int outputMultiplier;
	
	private String[] target = {"//Let's begin by saying hello\n"
			+ "System.out.println(\"Hello world!\");", 
			
			"//Variables store data, like so:\n"
			+ "int x = 0;\n"
			+ "System.out.println(x);", 
			
			"//You can change the value of a variable\n"
			+ "int y = 0;\n"
			+ "System.out.println(y);\n"
			+ "y = 5; //y now stores the value 5\n"
			+ "System.out.println(y);",
			
			"//There are other types of variables\n"
			+ "double d = 2.3;\n"
			+ "char c = 'c';\n"
			+ "String s = \"text\";",
			
			"//You can do math with variables\n"
			+ "int a = 10;\n"
			+ "int b = 3;\n"
			+ "System.out.println(\"a + b = \"+(a+b));\n"
			+ "System.out.println(\"a - b = \"+(a-b));\n"
			+ "System.out.println(\"a * b = \"+(a*b));\n"
			+ "System.out.println(\"a / b = \"+(a/b));\n"
			+ "/*Dividing int variables rounds down to\n"
			+ "the nearest whole number*/",
			
			"//By the way, this is a comment. It\n"
			+ "//doesn't get processed as actual code.\n\n"
			+ "/*If you want, you can also do block\n"
			+ "comments that span multiple lines\n"
			+ "like this.*/",
			
			"//How does this apply to our game? Watch:\n"
			+ "double health = 100;\n"
			+ "double damage = 25;\n"
			+ "health = health - damage;\n"
			+ "System.out.println(health);",
			
			"//But what if we're not sure if we want to\n"
			+ "//do something? Well, there's another\n"
			+ "//variable type called a boolean\n\n"
			+ "boolean unsure = false;\n"
			+ "boolean definitely = true;\n"
			+ "System.out.println(unsure);\n"
			+ "System.out.println(definitely);",
			
			"/*As you can see, booleans can either be\n"
			+ "true or false. With the help of if\n"
			+ "loops, booleans can control program flow.*/\n\n"
			+ "boolean happy = false;\n"
			+ "if(happy == true){\n"
			+ "\tSystem.out.println(\"I'm happy\");\n"
			+ "}\n"
			+ "else{\n"
			+ "\tSystem.out.println(\"I'm sad.\");\n"
			+ "}",
			
			"//Some expressions evaluate to booleans.\n"
			+ "boolean math = 4 > 5;\n"
			+ "System.out.println(math);",
			
			"if(4 < 5)\n"
			+ "   System.out.println(4 is less than 5);\n"
			+ "else\n"
			+ "   System.out.println(4 isn't less than 5);"
			
	};
	
	public TypingWindow() 
	{
		super("Typer");
		
		charsTyped = 0;
		codeSegment = 0;
		outputMultiplier = 5;
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);
		
		typeZone = new JTextArea();
		typeZone.setBounds(0, 0, 400, 400);
		typeZone.setFont(new Font("Monospaced", Font.PLAIN, 15));
		typeZone.setBackground(Color.BLACK);
		typeZone.setForeground(Color.WHITE);
		typeZone.setFocusable(false);
		typeZone.setEditable(false);
		c.add(typeZone);
		
		console = new JTextArea();
		console.setFocusable(false);
		console.setBounds(0, 400, 400, 100);
		c.add(console);
		
		instructions = new JTextArea();
		instructions.setBounds(400, 0, 110, 500);
		instructions.setWrapStyleWord(true);
		instructions.setLineWrap(true);
		instructions.setText("INSTRUCTIONS: \nIf you're bored of staring at the minions marching up and down and left and right or"
				+ " waiting to make enough money for the next upgrade, this is the place for you. In the typer, you"
				+ " can learn how to write code segments in Java, the language this game itself is written in."
				+ " Granted, you won't become as awesome as ourselves, but at least you'll kill time while earning $5"
				+ " per code segment that you finish. Start pressing random buttons to begin.");
		instructions.setEditable(false);
		instructions.setFocusable(false);
		c.add(instructions);
		
		addKeyListener(this);
		
		setSize(520, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent event) 
	{
		try 
		{
			if(Character.isWhitespace(target[codeSegment].charAt(charsTyped)))
				Game.playSound("spaceKeypress.wav");
			else
				Game.playSound("enterKeypress.wav");
			
			typeZone.insert(""+target[codeSegment].charAt(charsTyped), charsTyped);
			charsTyped++;
		}
		//if finished with string
		catch (StringIndexOutOfBoundsException exc) 
		{
			//clear console and print output
			console.setText("");
			switch(codeSegment)
			{
				case 0:
					console.append("Hello world!");
					break;
				case 1:
					console.append("0");
					break;
				case 2:
					console.append("0\n5");
					break;
				case 4:
					console.append("13\n7\n30\n3");
					break;
				case 6:
					console.append("75");
					break;
				case 7:
					console.append("false\ntrue");
					break;
				case 8:
					console.append("I'm happy.");
					break;
				case 9:
					console.append("false");
					break;
				case 10:
					console.append("4 is less than 5");
					break;
			}
			
			//move on to next target String
			codeSegment++;
			charsTyped = 0;
			Game.addMoney(outputMultiplier);
			typeZone.setText("\n\n---------------------------\n\n" + typeZone.getText());
			typeZone.setCaretPosition(0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			typeZone.setText("");
			codeSegment = 0;
			charsTyped = 0;
		}
	}
}
