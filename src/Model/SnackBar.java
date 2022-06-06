package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SnackBar implements Serializable {
	private static int idCounter; 
	
	private int id;
	private String barName;
	private Section section;
	private ArrayList<Snack> snacks;
	private double profit;
	private static double zooPercentage;
	
	public SnackBar(String barName, Section section) {
		super();
		idCounter = Zoo.getInstance().getMaxId(Zoo.getInstance().getBars());
		this.id = idCounter++;
		this.barName = barName;
		this.section = section;
		this.snacks = new ArrayList<>();
		this.profit = 0.0;
	}
	
	public SnackBar(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public ArrayList<Snack> getSnacks() {
		return snacks;
	}

	public void setSnacks(ArrayList<Snack> snacks) {
		this.snacks = snacks;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public static double getZooPercentage() {
		return zooPercentage;
	}

	public static void setZooPercentage(double zooPercentage) {
		SnackBar.zooPercentage = zooPercentage;
	}

	@Override
	public String toString() {
		return  this.getClass().getSimpleName()+" id: " + id + ", name: " + barName + ", section: " + section.getSectionName() 
				+ ", profit: " + profit + ", snack number: " + this.getSnacks().size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnackBar other = (SnackBar) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
