package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;

import Exceptions.DuplicatedValues;
import Utils.Gender;

public abstract class Person implements Comparable<Person>, Serializable {
	
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDay;
	private Gender gender;
	private Section section;
	
	public Person(int id, String firstName, String lastName, LocalDate date, Gender gender, Section section) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = date;
		this.gender = gender;
		this.section = section;
	}
	
	public Person(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws DuplicatedValues 
	{
		String oldName = this.getFirstName();
		this.firstName = firstName;
		if(Zoo.getInstance().isDuplicate(this)) // if this do duplicade
		{
			this.firstName = oldName;
			throw new DuplicatedValues();
		}
		
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws DuplicatedValues 
	{
		String oldName = this.getLastName();
		this.lastName = lastName;
		if(Zoo.getInstance().isDuplicate(this)) // if this do duplicade
		{
			this.lastName = oldName;
			throw new DuplicatedValues();
		}
		
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) throws DuplicatedValues 
	{ // due to the fact it will be duplicated
		LocalDate oldDate = this.getBirthDay();
		this.birthDay = birthDay;
		if(Zoo.getInstance().isDuplicate(this)) // if this do duplicade
		{
			this.birthDay = oldDate;
			throw new DuplicatedValues();
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) throws DuplicatedValues 
	{ //because it can be duplicated
		Gender oldGender = this.getGender();
		this.gender = gender;
		if(Zoo.getInstance().isDuplicate(this)) // if this do duplicade
		{
			this.gender = oldGender;
			throw new DuplicatedValues();
		}
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean equalsPerson(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Person other = (Person) obj;
		if (birthDay == null) {
			if (other.birthDay != null)
				return false;
		} else if (!birthDay.equals(other.birthDay))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Person o) {
		if(this.getFirstName().equals(o.getFirstName()))
			
			return this.getLastName().compareTo(o.getLastName()) ; 
		else
			return this.getFirstName().compareTo(o.getFirstName());
	}

}
