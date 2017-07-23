package com.prac3.ABug;

public abstract class ALife extends AObject // Umbrella class for all living objects, plants have movement for special effects such as regrowing
{
	public int energy = 0;
	public boolean dead = false;
	public boolean poisoned = false;
	
	public abstract void move();
	
	public void reset()
	{
		dead = false;
		poisoned = false;
	}

}
