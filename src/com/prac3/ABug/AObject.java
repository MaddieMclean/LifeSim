package com.prac3.ABug;

import java.util.Random;

import javafx.scene.paint.Color;

public abstract class AObject // Umbrella class for all objects, contains methods that are common to everything
{
	public String species = "", UID = "";
	public int x = 0, y = 0;
	public int xStart = 0, yStart = 0; // separate stored start values so that all objects can be reverted back to their starting locations on reset
	
	protected void setStart(int xNew, int yNew, String ID) // Set start for loaded versions of objects where the UID has been pre-generated
	{
		x = xNew;
		y = yNew;
		xStart = x;
		yStart = y;
		UID = ID;
	}
		
	protected void setStart(int xNew, int yNew, int ID)	// Set start for newly generated versions of objects
	{
		if(checkCollision(xNew, yNew))	// checks for collisions based on the random starting locations of objects, moves them if problems are found 
		{
			x = xNew;
			y = yNew;
		}
		else
		{
			randomMove();
		}
		xStart = x;
		yStart = y;
		UID = species + ID; // UID = species plus a number from a counter
	}
	
	protected boolean checkCollision(int a, int b) // checks whether there is another object on the square where the object wants to be placed
	{
		String s;
		try
		{
			s = AWorld.Grid[b][a].substring(4);
		} catch (Exception e)
		{
			s = AWorld.Grid[b][a];
		}
		
		if(s.equals(""))
			return true;
				
		return false;
	}	
	
	protected void randomMove()	// random move function for sorting out collisions, loops until an empty square is found
	{
		Random r = new Random();
	
		int a = r.nextInt(4);
		
		int xTest = x, yTest = y;
		
		switch (a)
		{
		case 0:
			yTest = y + 1;;
			break;
		case 1:
			yTest = y - 1;;		
			break;
		case 2:
			xTest = x + 1;;
			break;
		case 3:
			xTest = x - 1;;
			break;
		}
		
		xTest = checkBounds(xTest, GridsCanvas.cols);
		yTest = checkBounds(yTest, GridsCanvas.rows);
		
		if(checkCollision(xTest, yTest))
		{
			x = xTest;
			y = yTest;
		}
		else
		{
			randomMove();
		}

	}
	
	protected int checkBounds(int a, int b) // checks whether movement falls within 5the boundaries of the map
	{
		
		if(a < 0)
		{
			a = 0;
		}
		if(a >= b)
		{
			a = b - 1;
		}
		
		return a;
	}
	
	public String toString()
	{
		String Attrs = UID + ", " + x + ", " + y;
		return Attrs;
	}
	
	public abstract String toText();
	
	public abstract Color getColour();
}
