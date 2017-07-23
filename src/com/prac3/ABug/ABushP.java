package com.prac3.ABug;

import javafx.scene.paint.Color;

public class ABushP extends APlant // Poisoned version of the bush, if eaten causes etreme damage to predator
{
	ABushP(int xNew, int yNew, String ID)
	{
		poisoned = true;
		species = "BushP";
		setStart(xNew, yNew, ID);
	}
	
	ABushP(int xNew, int yNew, int ID)
	{	
		poisoned = true;
		species = "BushP";
		setStart(xNew, yNew, ID);
	}

	public void move()
	{
		
	}
	
	public void reset() // Separate reset because the bushp must start out poisoned
	{
		dead = false;
		poisoned = true;
	}

	public Color getColour()
	{
		Color colour = Color.MEDIUMPURPLE;
		return colour;
	}

}
