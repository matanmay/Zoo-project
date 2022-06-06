package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import Utils.MyFileLogWriter;

public class Section implements Serializable
{
	private static int idCounter;
	
	private int id;
	private String sectionName;
	private SnackBar bar;
	private final int maxCapacity;
	private double todayRevenue;
	private HashSet<ZooEmployee> employees;
	private HashSet<Visitor> visitors;
	private HashSet<Mammal> mammals;
	private HashSet<Reptile> reptiles;
	private HashSet<Bird> birds;
	
	public Section(String sectionName, int maxCapacity) 
	{
		super();
		idCounter = Zoo.getInstance().getMaxId(Zoo.getInstance().getSections());
		this.id = idCounter ++ ;
		this.sectionName = sectionName;
		this.maxCapacity = maxCapacity;
		todayRevenue = 0;
		employees = new HashSet<>();
		visitors = new HashSet<>();
		mammals = new HashSet<>();
		reptiles = new HashSet<>();
		birds = new HashSet<>();
	}
	
	public Section(int id) {
		super();
		this.id = id;
		this.maxCapacity = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public SnackBar getBar() {
		return bar;
	}

	public void setBar(SnackBar bar) {
		this.bar = bar;
	}

	public double getTodayRevenue() {
		return todayRevenue;
	}

	public void setTodayRevenue(double todayRevenue) {
		this.todayRevenue = todayRevenue;
	}

	public HashSet<ZooEmployee> getEmployees() {
		return employees;
	}

	public void setEmployees(HashSet<ZooEmployee> employees) {
		this.employees = employees;
	}

	public HashSet<Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(HashSet<Visitor> visitors) {
		this.visitors = visitors;
	}

	public HashSet<Mammal> getMammals() {
		return mammals;
	}

	public void setMammals(HashSet<Mammal> mammals) {
		this.mammals = mammals;
	}

	public HashSet<Reptile> getReptiles() {
		return reptiles;
	}

	public void setReptiles(HashSet<Reptile> reptiles) {
		this.reptiles = reptiles;
	}

	public HashSet<Bird> getBirds() {
		return birds;
	}

	public void setBirds(HashSet<Bird> birds) {
		this.birds = birds;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	@Override
	public String toString() {
		return "id: " + id + ", name: " + sectionName + ", max capacity: "+ maxCapacity +", today revenue: "
				+ todayRevenue + ", visitors: " + getVisitors().size() + ", employees: " + getEmployees().size();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void getSectionDetails() {
		int animalNum = mammals.size()+reptiles.size()+birds.size();
		MyFileLogWriter.println("Section "+getSectionName()+" ,Number Of Employees: "+
	getEmployees().size() +" ,Number Of Visitors: "+getVisitors().size()+ " ,Number Of"
			+ " Animals: "+animalNum+" ,Profit Today: "+getTodayRevenue());
	}
	
}
