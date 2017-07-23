package com.prac3.ABug;

import javafx.scene.paint.Color;

public class ABlock extends AObject	// Block class
{
	
	ABlock(int xNew, int yNew, String ID)
	{
		species = "Block";
		setStart(xNew, yNew, ID);
	}

	ABlock(int xNew, int yNew, int ID)
	{
		species = "Block";
		setStart(xNew, yNew, ID);
	}

	public String toText()
	{
		String Attrs = "UID: " + UID + ", Position: " + xStart + ", " + yStart;
		return Attrs;
	}

	public Color getColour()
	{
		Color colour = Color.DARKGRAY;
		return colour;
	}

}
