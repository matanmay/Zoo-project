package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import Exceptions.AnimalsVisitsException;
import Utils.AnimalFood;
import Utils.Gender;
import Utils.Job;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Mammal extends Animal implements AnimalsVisits, Serializable {
	private static int idCounter;
	
	private boolean meatEater;
	private boolean canTakePic;

	
	public Mammal(String name, LocalDate date, AnimalFood food, Gender gender, Section section
			, boolean meatEater, boolean canTakePic) {
		super(Zoo.getInstance().getMaxId(Zoo.getInstance().getMammals()), name, date, food, gender, section);
		this.meatEater = meatEater;
		this.setCanTakePic(canTakePic);
	}
	
	public Mammal(int id) {
		super(id);
	}

	public boolean isMeatEater() {
		return meatEater;
	}

	public void setMeatEater(boolean meatEater) {
		this.meatEater = meatEater;
	}

	@Override
	public String toString() {
		return super.toString()+", eat meat? "+meatEater +", pic? " +this.isCanTakePic();
	}

	public boolean isCanTakePic() {
		return canTakePic;
	}

	public void setCanTakePic(boolean canTakePic) {
		this.canTakePic = canTakePic;
	}

	@Override
	public void visitcount(Person p) throws AnimalsVisitsException 
	{
		if (getVisitCounter()<30) //if its less than 30 maybe a visit will be added
		{
			if(p instanceof Visitor)//if its a visitor we will count the visit
			{
				setVisitCounter(getVisitCounter()+1); 
			}
			hasVistedAnimal(p); //will send to add to the data structure 
		}
		else
		{
			hasVistedAnimal(p); //might need to send to data structure if employee
			throw new AnimalsVisitsException();
		}
	}


	@Override
	public boolean hasVistedAnimal(Person p) 
	{

			 if(p instanceof ZooEmployee && getVisitCounter()>29) //if bigger than 29, than an employee can be added
			 {
					ZooEmployee e= (ZooEmployee)p;
					if(!Zoo.getInstance().getAnimalTreatedByZooEmployee().containsKey(e)) 
					{
						Zoo.getInstance().getAnimalTreatedByZooEmployee().put(e, new HashSet<Animal>());
						((HashSet<Animal>) Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e)).add(this);
					}
					else
					{
						((HashSet<Animal>) Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e)).add(this);
					}
				}
				else if(p instanceof Visitor && getVisitCounter()<30) {
					Visitor v= (Visitor)p;
					if(!Zoo.getInstance().getAnimalVisitsByPeople().containsKey(v)) 
					{
						goToCounter(); //up the counter of the animal
						Zoo.getInstance().getAnimalVisitsByPeople().put(v,new HashSet<Animal>());
						((HashSet<Animal>) Zoo.getInstance().getAnimalVisitsByPeople().get(v)).add(this);		
					}
					else
					{
						//the visitor exist but animals not
						HashSet<Animal> animals = (HashSet<Animal>) (Zoo.getInstance().getAnimalVisitsByPeople().get(v));
						animals.add(this);
						goToCounter(); //up the counter of the animal
					}
	
				}
					
			return false;
		}
	
	//This method up the counter of the animal
	private void goToCounter()
	{
		if(Zoo.getInstance().getCounterVisitsMammal().containsKey(this))
		{
			Zoo.getInstance().getCounterVisitsMammal().put(this, (1 + (int) (Zoo.getInstance().getCounterVisitsMammal().get(this))));
		}
		else
		{
			Zoo.getInstance().getCounterVisitsMammal().put(this, 1);
		}
	}
	
	}

