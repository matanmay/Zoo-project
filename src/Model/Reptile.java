package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import Utils.AnimalFood;
import Utils.Gender;

public class Reptile extends Animal implements Serializable {
	private static int idCounter;
	
	private boolean isDangerous;
	private boolean seasonSleep;
	
	public Reptile(String name, LocalDate date, AnimalFood food, Gender gender, Section section
			, boolean isDangerous,boolean seasonSleep) {
		super(Zoo.getInstance().getMaxId(Zoo.getInstance().getReptiles()), name, date, food, gender, section);
		this.isDangerous = isDangerous;
		this.seasonSleep= seasonSleep;
	}
	
	public Reptile(int id) {
		super(id);
	}

	public boolean isDangerous() {
		return isDangerous;
	}

	public void setDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
	
	@Override
	public String toString() {
		return  super.toString()+ " , Dangerous? "+isDangerous+", sleep at winter? "+this.isSeasonSleep();
	}

	public boolean isSeasonSleep() {
		return seasonSleep;
	}

	public void setSeasonSleep(boolean seasonSleep) {
		this.seasonSleep = seasonSleep;
	}
}
