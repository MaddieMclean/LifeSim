package com.prac3.ABug;

import javafx.scene.paint.Color;

public class ATree extends APlant	// variant of the bush, only eaten by giraffes, regenerates after several turns
{
	private int i;
	
	ATree(int xNew, int yNew, String ID)
	{
		species = "Tree";
		setStart(xNew, yNew, ID);
	}
	
	ATree(int xNew, int yNew, int ID)
	{	
		species = "Tree";
		setStart(xNew, yNew, ID);
	}

	public void move()
	{	
		if(dead)	// if tree is dead (has been eaten) a counter starts
		{
			i++;
			if(i == 20)	// when counter reaches 20 the tree comes alive and restores its energy
			{
				dead = false;
				i = 0;
				energy = 10;
			}
		}
	}
	
	public Color getColour()
	{
		Color colour = Color.BROWN;
		return colour;
	}

}

