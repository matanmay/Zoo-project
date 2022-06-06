package Model;

import Exceptions.AnimalsVisitsException;

public interface AnimalsVisits {
	
	public void visitcount(Person p) throws AnimalsVisitsException ;
	public boolean hasVistedAnimal(Person p);

}
