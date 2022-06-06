package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

import Utils.Gender;
import Utils.Job;

public class ZooEmployee extends Person implements Serializable {
	private static int idCounter;
	
	private Job job;
	private String userName;
	private String password;
	private String myStatus;
	
	
	public ZooEmployee(String firstName, String lastName, LocalDate date, Gender gender, Section section, Job job) {
		super(Zoo.getInstance().getMaxId(Zoo.getInstance().getEmployees()),firstName, lastName, date, gender, section);
		this.job = job;
		this.userName = this.getFirstName()+this.getId();
		this.password = this.getLastName() + this.getId();
		this.myStatus="that's my status, i wish someone would update me already";
	}
	
	public ZooEmployee(int id) {
		super(id);
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public String getUser() 
	{
		return this.userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getPassword() {
		return password;
	}

	
	public String getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" id: "+this.getId()+", name: "+getFirstName()+" "+getLastName()+", "+this.getBirthDay()+", Job: "+job +", section "+super.getSection().getSectionName();
	}

}
