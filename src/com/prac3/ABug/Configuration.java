package com.prac3.ABug;

import java.io.*;
import java.util.Properties;

public class Configuration
{
	public Properties properties;
	
	Configuration(String filePath)	// Constructor loads properties from the specified filepath
	{
		load(filePath);
	}
	
	public void load(String filePath)	// Loads file or throws an error exception
	{
		properties = new Properties();
		try 
		{
		  properties.load(new FileInputStream(filePath));
		} 
		catch (IOException e) 
		{
		  System.out.println("file not found");
		}
	}
	
	public void save(AWorld world, String filePath)	// Saves file to sepcified filepath
	{
		properties = new Properties();
		OutputStream output = null;
		try {

			output = new FileOutputStream(filePath);

			for (int i = 0; i < AWorld.objectList.size(); i++)	// Takes list of all objects on the grid and stores position and type information as key - value pairs in a text file
			{
				properties.setProperty(AWorld.objectList.get(i).UID, AWorld.objectList.get(i).toString());
			}

			properties.store(output, null);

		} 
		catch (IOException io) 
		{
			io.printStackTrace();
		} 
		finally 
		{
			if (output != null) 
			{
				try 
				{
					output.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
