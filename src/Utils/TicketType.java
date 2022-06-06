package Utils;

public enum TicketType {
	Child(20), Adult(40), Old(30), Student(30), Soldiar(25), EmployeeRelative(10);
	private final double value;
	
	private TicketType(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	
	
	
}
