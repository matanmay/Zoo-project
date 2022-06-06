package Exceptions;

public class MaximumCapcityException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MaximumCapcityException() {
		super("you reach to less then 50% of capacity \ncan not move to other section");
	}

}
