package hillbillies.world;

import be.kuleuven.cs.som.annotate.Value;

/**
 * A class to organize values about the game world.
 * 
 * @author  Pieter-Jan Van den Broecke: EltCw
 * 		    Emiel Vandeloo: WtkCw
 * @version Final version Part 1: 06/03/2016
 */
@Value
public class WorldStat {
	
	@Deprecated
	public static final int MAX_X = 50;
	@Deprecated
	public static final int MAX_Y = 50;
	@Deprecated
	public static final int MAX_Z = 50;
	
	@Deprecated
	public static final int MIN_X = 0;
	@Deprecated
	public static final int MIN_Y = 0;
	@Deprecated
	public static final int MIN_Z = 0;
	
	@Deprecated
	public static final int SIZE_X = MAX_X - MIN_X;
	@Deprecated
	public static final int SIZE_Y = MAX_Y - MIN_Y;
	@Deprecated
	public static final int SIZE_Z = MAX_Z - MIN_Z;
	
	@Deprecated
	public static final int CUBE_LENGTH = 1;
	
	@Deprecated
	public static final double[] FALL_VECTOR = new double[] {0, 0, -3};


}