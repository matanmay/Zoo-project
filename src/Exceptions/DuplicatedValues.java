package Exceptions;

public class DuplicatedValues extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicatedValues() {
		super("can not be a dupliacted value, \nemployee and visitor can't be the same person");
	}
	

}
