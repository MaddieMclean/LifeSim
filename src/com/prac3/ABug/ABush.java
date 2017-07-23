package com.prac3.ABug;

import javafx.scene.paint.Color;

public class ABush extends APlant	// Bush class, generic plant
{
	ABush(int xNew, int yNew, String ID)
	{
		species = "Bush";
		setStart(xNew, yNew, ID);
	}
	
	ABush(int xNew, int yNew, int ID)
	{	
		species = "Bush";
		setStart(xNew, yNew, ID);
	}

	public void move()
	{		
		
	}
	
	public Color getColour()
	{
		Color colour = Color.FORESTGREEN;
		return colour;
	}

}
