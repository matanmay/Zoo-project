package Exceptions;

public class DiscountCheckException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public DiscountCheckException() {

		super("can't be more than 25% discount you have reached the max amount");
	}
}
