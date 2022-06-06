package Model;

import java.io.Serializable;

import Exceptions.NegativeNumberException;
import Utils.SnackType;

public class Snack implements Serializable {
	private static int idCounter;
	
	private int id;
	private SnackType type;
	private SnackBar bar;
	private String snackName;
	private boolean isFattening;
	private double price;
	
	public Snack(SnackType type, String snackName, SnackBar bar, boolean isFattening, double price) throws NegativeNumberException 
	{
		super();
		idCounter = Zoo.getInstance().getMaxId(Zoo.getInstance().getSnacks());
		this.id = idCounter++;
		this.type = type;
		this.bar = bar;
		this.snackName = snackName;
		this.isFattening = isFattening;
		setPrice(price);
	}
	
	public Snack(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SnackType getType() {
		return type;
	}

	public void setType(SnackType type) {
		this.type = type;
	}

	public String getSnackName() {
		return snackName;
	}

	public void setSnackName(String snackName) {
		this.snackName = snackName;
	}

	public boolean isFattening() {
		return isFattening;
	}

	public void setFattening(boolean isFattening) {
		this.isFattening = isFattening;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws NegativeNumberException 
	{
		if(price<=0.0)
		{
			throw new NegativeNumberException();
		}
		this.price = price;
	}
	

	public SnackBar getBar() {
		return bar;
	}

	public void setBar(SnackBar bar) {
		this.bar = bar;
	}

	@Override
	public String toString() {
		return  this.getClass().getSimpleName()+" id: " + id + ", type: " + type + ", name: " + snackName + ", is fattening? " + isFattening
				+ ", price: " + price+ ", bar: " + this.getBar().getBarName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Snack other = (Snack) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
