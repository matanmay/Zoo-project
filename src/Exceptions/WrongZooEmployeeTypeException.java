package Exceptions;

public class WrongZooEmployeeTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WrongZooEmployeeTypeException() {
		super("only Vet can treat Animal");
	}

}
