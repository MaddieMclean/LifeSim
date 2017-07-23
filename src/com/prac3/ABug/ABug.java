package com.prac3.ABug;

import javafx.scene.paint.Color;
import java.lang.String;

public class ABug extends AAnimal	// Bug class, only eats bushes, is unable to differentiates between poisoned and non-poisoned ones 
{
	
	ABug(int xNew, int yNew, String ID)
	{
		xTar = xNew;
		yTar = yNew;
		species = "Bug";
		setStart(xNew, yNew, ID);
	}
	
	ABug(int xNew, int yNew, int ID)
	{	
		xTar = xNew;
		yTar = yNew;
		species = "Bug";
		setStart(xNew, yNew, ID);
		System.out.println("Species: " + species);	
	}
	
	public boolean isFood(String f)	// Method to determine whether or not an object is a bush and therefore edible
	{
		try
		{
			f = f.substring(0, 4);
		} catch (Exception e)
		{
			
		}
		if(f.equals("Bush"))
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
		Color colour = Color.LIGHTCORAL;
		return colour;
	}

}
