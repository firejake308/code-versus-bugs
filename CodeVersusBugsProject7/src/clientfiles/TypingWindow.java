package clientfiles;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TypingWindow extends JFrame implements KeyListener, ActionListener
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
			+ "like this.*/"};
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent event) 
	{
		try 
		{
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
			}
			
			//move on to next target String
			codeSegment++;
			charsTyped = 0;
			Game.addMoney(outputMultiplier);
			typeZone.setText("\n\n---------------------------\n\n" + typeZone.getText());
			typeZone.setCaretPosition(0);
		}
		//if runs out of code segments
		catch(ArrayIndexOutOfBoundsException exc)
		{
			codeSegment = 0;
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	}
}
