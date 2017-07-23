package com.prac3.ABug;

import javafx.scene.paint.Color;

public abstract class APlant extends ALife	// Umbrella class for plants 
{
	public int energy = 10;
	
	public abstract void move();

	public String toText()
	{
		String Attrs = "UID: " + UID + ", Position: " + xStart + ", " + yStart;
		return Attrs;
	}

	public abstract Color getColour();
}
