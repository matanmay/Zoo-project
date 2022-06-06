package Exceptions;

public class SectionIsFullException extends Exception{
	public SectionIsFullException() 
	{
		super("The Section Can Not Add This Amount \n Of Visitors, Because It Will OverFlow");
	}

}
