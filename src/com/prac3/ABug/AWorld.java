package com.prac3.ABug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class AWorld
{	
	public static List<AObject> objectList = new ArrayList<AObject>();
	public static List<ABlock> blockList = new ArrayList<ABlock>();
	public static List<ALife> lifeList = new ArrayList<ALife>();
	public static String Grid[][];	
	public int rows, cols = 0;
	private Random r = new Random();
	
	AWorld(int r, int c)	// Newly generated world constructor
	{
		rows = r;
		cols = c;
		Grid = new String[rows][cols];	
		objectList = new ArrayList<AObject>();
		blockList = new ArrayList<ABlock>();
		lifeList = new ArrayList<ALife>();
		clearGrid();
		populate();	// Populates grid with default bushes and blocks
	}
	
	AWorld(int r, int c, Properties properties)	// Generates world based off of information from a save file
	{
		rows = r;
		cols = c;
		Grid = new String[rows][cols];	
		objectList = new ArrayList<AObject>();
		blockList = new ArrayList<ABlock>();
		lifeList = new ArrayList<ALife>();
		clearGrid();
		filePopulate(properties);	// Loads objects from file
	}
	
	private void populate()	// Populates grid with default bushes and blocks
	{		
		addBlocks(6);
		addBush(5);	
	}
	
	public void reset()	// Resets all current objects to their starting locations
	{
		for (int i = 0; i < objectList.size(); i++)	// Moves bback to starting positions
		{
			objectList.get(i).x = objectList.get(i).xStart;
			objectList.get(i).y = objectList.get(i).yStart;
		}
		for (int i = 0; i < lifeList.size(); i++)	// Resurrects the dead and cures poison
		{
			lifeList.get(i).reset();
		}
	}
	
	public void filePopulate(Properties properties)	// Reads java properties file and loads the stored values
	{
		Enumeration<?> e = properties.propertyNames();	// Creates enumerator to iterate through all key - value pairs in the file
		
		String UID;
		int x, y;
		
		while (e.hasMoreElements())	// While loop for file
		{			
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			List<String> AObject = Arrays.asList(value.split(",[ ]*"));	// Splits stored information for each entry into its unique identifier and position
			
			UID = AObject.get(0);	// UID
			x = Integer.parseInt(AObject.get(1));	// x position
			y = Integer.parseInt(AObject.get(2));	// y position
			
			if(UID.contains("Block"))	// Tests for each type of object, if an object fails to fit it's ignored
			{
				ABlock block = new ABlock(x, y, UID);
				blockList.add(block);
				objectList.add(block);
			}
			else if(UID.contains("BushP"))
			{
				ABushP bushp = new ABushP(x, y, UID);
				lifeList.add(bushp);
				objectList.add(bushp);
			}
			else if(UID.contains("Bush"))
			{
				ABush bush = new ABush(x, y, UID);
				lifeList.add(bush);
				objectList.add(bush);
			}
			else if(UID.contains("Tree"))
			{
				ATree tree = new ATree(x, y, UID);
				lifeList.add(tree);
				objectList.add(tree);
			}
			else if(UID.contains("Giraffe"))
			{
				AGiraffe gir = new AGiraffe(x, y, UID);
				lifeList.add(gir);
				objectList.add(gir);
			}
			else if(UID.contains("Bug"))
			{
				ABug bug = new ABug(x, y, UID);
				lifeList.add(bug);
				objectList.add(bug);
			}
		}
	}

	public void clearGrid()	// Wipes background grid
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				Grid[i][j] = "";				
			}			
		}
	}
	
	public void drawGrid()	// Fills in background grid, each object is represented by its UID as a string
	{
		for (int i = 0; i < blockList.size(); i++)	// creates blocks first as they are terrain and lowest priority
		{							
			int x = blockList.get(i).x;						
			int y = blockList.get(i).y;
			Grid[y][x] = blockList.get(i).UID;
		}
		
		for (int i = 0; i < lifeList.size(); i++)	// creates everything else
		{
			if(!lifeList.get(i).dead)
			{
				int x = lifeList.get(i).x;						
				int y = lifeList.get(i).y;
				Grid[y][x] = lifeList.get(i).UID;		
			}
		}
		
		for (int i = 0; i < lifeList.size(); i++)	// move actions for everything, this is separated to avoid a bug where some creatures would move before others had been drawn and the two would collide
		{
			lifeList.get(i).move();	
		}
	}
	
	private void addBlocks(int numBlocks)	// Generates a number of generic blocks
	{		
		for (int i = 0; i < numBlocks; i++)
		{		
			ABlock block = new ABlock(r.nextInt(cols), r.nextInt(rows), i);
			blockList.add(block); 
			objectList.add(block);
		}		
	}
	
	private void addBush(int numBush)	// Generates a number of generic bushes
	{
		for (int i = 0; i < numBush; i++)
		{		
			ABush bush = new ABush(r.nextInt(cols - 1), r.nextInt(rows - 1), i);
			lifeList.add(bush); 
			objectList.add(bush);
		}				
	}
	
	public void addObject(int i, String species)	// Switch statement for adding extra of each type of object to the grid 
	{	
		switch (species)
		{
			case "Bug":
			{
				ABug bug = new ABug(r.nextInt(cols), r.nextInt(rows), i);
				lifeList.add(bug);
				objectList.add(bug);
				break;
			}
			case "Bush":
			{
				ABush bush = new ABush(r.nextInt(cols - 1), r.nextInt(rows - 1), i);
				lifeList.add(bush); 
				objectList.add(bush);
				break;
			}
			case "BushP":
			{
				ABushP bush = new ABushP(r.nextInt(cols - 1), r.nextInt(rows - 1), i);
				lifeList.add(bush); 
				objectList.add(bush);
				break;
			}
			case "Tree":
			{
				ATree tree = new ATree(r.nextInt(cols - 1), r.nextInt(rows - 1), i);
				lifeList.add(tree); 
				objectList.add(tree);
				break;
			}
			case "Block":
			{
				ABlock block = new ABlock(r.nextInt(cols), r.nextInt(rows), i);
				blockList.add(block); 
				objectList.add(block);
				break;
			}
			case "Giraffe":
			{
				AGiraffe gir = new AGiraffe(r.nextInt(cols), r.nextInt(rows), i);
				lifeList.add(gir); 
				objectList.add(gir);
				break;
			}
	
			default:
				break;
		}

		
	}

}
