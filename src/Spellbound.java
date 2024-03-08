import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Spellbound extends Applet implements Runnable, KeyListener
{
	
	//Background
	Image forest = Toolkit.getDefaultToolkit().getImage("forest_completed.png");
	
	//PLAYER
	String[] pose = {"Lidle", "Ridle", "Lwalk", "Rwalk"};
	
	Rect player_hitbox = new Rect(115, 753, 40, 90);
	Sprite player = new Sprite("wm", pose, 50, 654, 7, 10);
	
	//AI Enemy
	AI_control venustrap_hitbox = new AI_control(500, 804, 40, 40);
	
	//Boundaries
	Rect top_wall = new Rect(0, -50, 1500, 50);
	Rect left_wall = new Rect(-50, 0, 50, 800);
	Rect floor = new Rect(0, 845, 1500, 85);

	//Controls
	boolean UP_Pressed = false;
	boolean LT_Pressed = false;
	boolean RT_Pressed = false;
	boolean testing_Tool = false;
	
	//Paints Image to offScreenImg to reduce flicker
	Image offScreenImg;
	Graphics offScreenPen;
	
	
	public void run()
	{
		// Game Loop
		while(true)
		{
//			if(UP_Pressed)
//			{
//				player_hitbox.moveUP(2);
//				player.moveUP(2);
//			}

			if(LT_Pressed)
			{
				player_hitbox.moveLT(2);
				player.moveLT(2);
			}
			if(RT_Pressed)
			{
				player_hitbox.moveRT(2);
				player.moveRT(2);
			}
			
			//Prevents the player from going left to the screen
			if( player_hitbox.overlaps(left_wall))
			{
				if( player_hitbox.cameFromLeftOf(left_wall))
				{
					 player_hitbox.pushbackLeftFrom(left_wall);
					 player.pushbackLeftFrom(left_wall);
				}
				
				if( player_hitbox.cameFromRightOf(left_wall))
				{
					 player_hitbox.pushbackRightFrom(left_wall);
					 player.pushbackRightFrom(left_wall);
				}
			}
			
			//Prevents the player from going down the screen
			if( player_hitbox.overlaps(floor))
			{
				if( player_hitbox.cameFromAbove(floor))
				{
					 player_hitbox.pushbackUpFrom(floor);
				}
				
				if( player_hitbox.cameFromBelow(floor))
				{
					 player_hitbox.pushbackDownFrom(floor);
				}

				if( player_hitbox.cameFromLeftOf(floor))
				{
					 player_hitbox.pushbackLeftFrom(floor);
				}
				
				if( player_hitbox.cameFromRightOf(floor))
				{
					 player_hitbox.pushbackRightFrom(floor);
				}
			}
				
			//AI
			//venustrap_hitbox.chase(player_hitbox, 3);
			
			repaint();
			
			try
			{			
				Thread.sleep(16);
			}
			catch(Exception x) {};
		}
	}
	
	
	
	//Updates the image on the screen
	public void update(Graphics pen)
	{
		offScreenPen.clearRect(0, 0, 1920, 1080);
		
		paint(offScreenPen);
		
		pen.drawImage(offScreenImg, 0, 0, null);
	}

	public void paint(Graphics pen)
	{
	    // Sets background image
	    pen.drawImage(forest, 0, -280, 1500, 1200, null);
	   
	    player.draw(pen);
	    
	    //Testing Tool
	    if(testing_Tool == true) 
	    {
		    // Sets the color to green for the player_hitbox
		    pen.setColor(Color.GREEN);
		    player_hitbox.draw(pen);
	
		    // Sets the colors for other elements to Default
		    pen.setColor(Color.BLACK);
		    floor.draw(pen);
		    top_wall.draw(pen);
		    left_wall.draw(pen);
	
		    // Sets the colors for AI_enemies_hitbox
		    pen.setColor(Color.RED);
		    venustrap_hitbox.draw(pen);
	
		    // Sets the color to red if the player_hitbox overlaps with the AI_enemies_hitbox
		    if (player_hitbox.overlaps(venustrap_hitbox)) 
		    {
		        pen.setColor(Color.RED);
		        player_hitbox.draw(pen);
		        
		        // Takes damage
		    }
	    }
	    
	    // Reset the color to default
	    pen.setColor(Color.BLACK);
	}

	
	
	public void init()
	{
		offScreenImg = createImage(1920, 1080);
		offScreenPen = offScreenImg.getGraphics();
		
		addKeyListener(this);
		requestFocus();

		Thread t = new Thread(this);

		t.start();
	}
	
	public void keyPressed(KeyEvent e)
	{		
		int code = e.getKeyCode();
		
		if (code == e.VK_W)   UP_Pressed = true;   
		if (code == e.VK_A)   LT_Pressed = true;  
		if (code == e.VK_D)   RT_Pressed = true;  
		
		
		//Testing tool shows hitboxes and terrain boundaries
		if (code == e.VK_T ) 
		{
			if (testing_Tool == true) {
				testing_Tool = false; 
			}else {
				testing_Tool = true; 
			}
			
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();

		if (code == e.VK_W)   UP_Pressed = false;  
		if (code == e.VK_A)   LT_Pressed = false;  
		if (code == e.VK_D)   RT_Pressed = false;  
	}

	public void keyTyped(KeyEvent e) {}
	
}