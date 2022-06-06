package Utils;

public enum Discount {
	Hever(30), Haifa_Student(10), Beyahad(20), Behatzdaa(20), Max(25), Kranot(25), No_Discount(0);
	private final double percentage;
	
	private Discount(double percentage) {
		this.percentage = percentage;
	}

	public double getPercentage() {
		return percentage;
	}
	
	
}
