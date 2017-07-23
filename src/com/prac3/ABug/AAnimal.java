package com.prac3.ABug;

import javafx.scene.paint.Color;
import java.lang.String;

public abstract class AAnimal extends ALife	// Umbrella class for animals, all animals move based on the same basic pattern - find a target, move to target, if not move randomly until a target can be found
{
	public int energy = 100;
	protected int xTar = 0, yTar = 0;//x Target, y Target
	protected int smell = 5;
	protected int rows = GridsCanvas.rows;
	protected int cols = GridsCanvas.cols;
	boolean search = true;
	

	public abstract Color getColour();
	
	protected abstract boolean isFood(String f);	// All animals must have a check for determining whether they will eat something, different for each animal

	
	public void move() // move prioritises targeted movement towards food, the fall back being random movement
	{	
		if(x == xTar && y == yTar) // If animal is already at a previous target it will search for a new one
		{
			search = true;
		}
		
		if(search)	// If in search mode it will sreach for a new target
		{
			searchTarget();
		}
		
		if(!search)	// if it has a target it will attemt to move to it
		{
			targetMove();
		}
		
		if(poisoned)	// if the animal has been poisoned it takes higher energy loss per turn
		{
			energy = energy - 10;
		}
		
		energy--;	// normal energy loss for movement
		
		if(energy <= 0)	// Dies when it runs out of energy
		{
			dead = true;
		}
	}
	
	private void searchTarget()	// Searces for a target in a redius based upon its smell range
	{
		int xDist, yDist; //dist x, dist y
		int total = rows * cols;
		
		int xLower = checkBounds(x - smell, cols);	// Creates radius based on current position, boundary checks ensure that the bounds do not overflow the edges of the grid and cause exceptions
		int yLower = checkBounds(y - smell, rows);
		int xHigher = checkBounds(x + smell, cols);
		int yHigher = checkBounds(y + smell, rows);
		
		for (int i = yLower; i < yHigher; i++)	// Loops through radius defined by its smell stat
		{
			for (int j = xLower; j < xHigher; j++)
			{
				if(isFood(AWorld.Grid[i][j]))	// Checks if target in each square is food, if true it calculates the direct distance to the target
				{
					xDist = Math.abs(j - x);
					yDist = Math.abs(i - y);
					
					if(total > xDist + yDist)	// Target value is kept until a one with a lower total distance is found
					{
						total = xDist + yDist;
						xTar = j;
						yTar = i;
					}					
				}
			}
		}
		
		if(total == rows * cols)	// If no food is found animal will move randomly this turn
		{
			randomMove();
		}
		else
		{
			search = false;	// if food is found search mode is activated
		}
		
	}
	
	private void targetMove()	// Moves towards the target by the most direct route possible 
	{
		int xDist = xTar - x;
		int yDist = yTar - y;
		
		int xTest = x, yTest = y;
		
		if(xDist <= -1)	// calculates difference between current position and target position, generates test move
			xTest = x - 1;
		if(xDist >= 1)
			xTest = x + 1;
		
		if(yDist <= -1)
			yTest = y - 1;
		if(yDist >= 1)
			yTest = y + 1;
		
		if(checkCollision(xTest, yTest))	// checks if test move collides with any objects, if not animals moves to that square
		{
			x = xTest;
			y = yTest;
		}
		else	// If a collision is detected, the animal panics and moves randomly
		{
			randomMove();
		}

	}
	
	public boolean checkCollision(int a, int b)	// Collision checks differs from AObject collision check in that it contains a test for whether the object the animal hits is food
	{
		String s;
		try
		{
			s = AWorld.Grid[b][a].substring(0, 5); // creates substring from longer Unique Identifier for each animal to test what it is
		} catch (Exception e)
		{
			s = AWorld.Grid[b][a];
		}
		
		if(s.equals(""))	// If empty space is returned path is clear
			return true;
		if(isFood(s))	// If food is returned path is clear and target is eaten
		{
			eat(a, b);
			return true;
		}
		
		return false;	// Otherwise space cannot be moved into
	}	
	
	private void eat(int a, int b)	// Finds life form in target square and kills it, harvesting its energy
	{
		String ID = AWorld.Grid[b][a];
		
		for (int i = 0; i < AWorld.lifeList.size(); i++)
		{
			if(ID.equals(AWorld.lifeList.get(i).UID))
			{
				if(AWorld.lifeList.get(i).poisoned)	// If target is poisoned the animal inherits the poison
				{
					poisoned = true;
				}
				energy = energy + AWorld.lifeList.get(i).energy;
				AWorld.lifeList.get(i).energy = 0;
				AWorld.lifeList.get(i).dead = true;
			}
		}
	}
	
	public String toText()
	{
		String Attrs = "UID: " + UID + ", Position: " + xStart + ", " + yStart + ", Energy: " + energy;
		return Attrs;
	}	
}
