package com.prac3.ABug;

import javafx.scene.paint.Color;

public class AGiraffe extends AAnimal
{
	
	AGiraffe(int xNew, int yNew, String ID)
	{
		xTar = xNew;
		yTar = yNew;
		smell = 10;
		species = "Giraffe";
		setStart(xNew, yNew, ID);
	}
	
	AGiraffe(int xNew, int yNew, int ID)
	{	
		xTar = xNew;
		yTar = yNew;
		smell = 10;
		species = "Giraffe";
		setStart(xNew, yNew, ID);
		System.out.println("Species: " + species);	
	}
	
	protected boolean isFood(String f)
	{
		try
		{
			f = f.substring(0, 4);
		} catch (Exception e)
		{
			
		}
		if(f.equals("Tree"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Color getColour()
	{
		Color colour = Color.GOLD;
		return colour;
	}

}
