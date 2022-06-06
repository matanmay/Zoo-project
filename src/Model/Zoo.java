package Model;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


import Exceptions.AnimalsVisitsException;
import Exceptions.DuplicatedValues;
import Exceptions.MaximumCapcityException;
import Exceptions.SectionIsFullException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Zoo<E> implements Serializable {

	private static Zoo zoo = null;

	private HashMap<Integer,ZooEmployee> employees;
	private HashMap<Integer, Visitor> visitors;
	private HashMap<Integer, Mammal> mammals;
	private HashMap<Integer, Reptile> reptiles;
	private HashMap<Integer, Bird> birds;
	private HashMap<Integer, Section> sections;
	private HashMap<Integer,Snack>snacks;
	private HashMap<Integer, SnackBar> bars;
	private HashMap<ZooEmployee, HashSet<Animal>> animalTreatedByZooEmployee;
	private HashMap<Visitor, HashSet<Animal>> AnimalVisitsByPeople;
	private HashMap<Animal, Integer> counterVisitsMammal; //counter the visits to mammal
	private HashMap<Animal, Integer> counterVisitsBird; //counter the visits to birds
	private ZooEmployee saveMyUser; //this will save the user that login the system
	private HashMap<String, Integer> counterSnacks; // this will save String - name of snack and Integer - counter of buying Snack

	public static Zoo getInstance()
	{
		if(zoo==null) 
		{
			zoo = new Zoo();
		}
		return zoo;
	}

	private Zoo() {
		this.employees = new HashMap<Integer,ZooEmployee>();
		this.visitors = new HashMap<Integer, Visitor>();
		this.mammals =new HashMap<Integer, Mammal>();
		this.reptiles = new HashMap<Integer, Reptile>();
		this.birds = new HashMap<Integer, Bird>();
		this.sections = new HashMap<Integer, Section>();
		this.snacks = new HashMap<Integer,Snack>();
		this.bars = new HashMap<Integer, SnackBar>();
		this.animalTreatedByZooEmployee= new HashMap<ZooEmployee, HashSet<Animal>>();
		this.AnimalVisitsByPeople = new HashMap<Visitor, HashSet<Animal>>();
		//classID = new HashMap<String, Integer>();
		this.saveMyUser = new ZooEmployee(0);
		this.counterSnacks = new HashMap<String, Integer>();
		this.counterVisitsMammal = new HashMap<Animal, Integer>();
		this.counterVisitsBird=new HashMap<Animal, Integer>();
	}
	public ZooEmployee getSaveMyUser() {
		return saveMyUser;
	}

	public void setSaveMyUser(ZooEmployee saveMyUser) {
		this.saveMyUser = saveMyUser;
	}

	public void deserialize() {	
		try {
			FileInputStream fileIn = new FileInputStream("Zoo.ser");  //the name of the file
			ObjectInputStream in = new ObjectInputStream(fileIn);  //stream to open
			zoo = (Zoo) in.readObject();

			in.close();  //close stream
			fileIn.close();  //close file
			} 
		catch (IOException i) 
		{
   		    Alert alert = new Alert(AlertType.ERROR, "we have some error with desrialize");
			alert.setHeaderText(i.getMessage());
			alert.setTitle("error");
			alert.showAndWait();
			return;
		} 
		catch (ClassNotFoundException c) 
		{
   		    Alert alert = new Alert(AlertType.ERROR, "we have some error with desrialize");
			alert.setHeaderText(c.getMessage());
			alert.setTitle("error");
			alert.showAndWait();
			return;
		}
	}
	
	public void serialize() 
	{
		try {
	        FileOutputStream fileOut = new FileOutputStream("Zoo.ser"); //file name
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);  //outputstream
	        out.writeObject(Zoo.getInstance());  
	        out.close();  //close
	        fileOut.close();  //close file
	     }
		catch (IOException i) {
   		    Alert alert = new Alert(AlertType.ERROR, "we have some error with srialize");
			alert.setHeaderText(i.getMessage());
			alert.setTitle("error");
			alert.showAndWait();
	     }
	}

	


	public HashMap<Animal, Integer> getCounterVisitsMammal() {
		return counterVisitsMammal;
	}

	public void setCounterVisitsMammal(HashMap<Animal, Integer> counterVisitsMammal) {
		this.counterVisitsMammal = counterVisitsMammal;
	}

	public HashMap<Animal, Integer> getCounterVisitsBird() {
		return counterVisitsBird;
	}

	public void setCounterVisitsBird(HashMap<Animal, Integer> counterVisitsBird) {
		this.counterVisitsBird = counterVisitsBird;
	}

	public HashMap<Integer,ZooEmployee> getEmployees() {
		return employees;
	}

	public HashMap<String, Integer> getCounterSnacks() {
		return counterSnacks;
	}

	public void setCounterSnacks(HashMap<String, Integer> counterSnacks) {
		this.counterSnacks = counterSnacks;
	}

	public void setEmployees(HashMap<Integer,ZooEmployee> employees) {
		this.employees = employees;
	}

	public HashMap<Integer, Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitorsById(HashMap<Integer, Visitor> visitors) {
		this.visitors = visitors;
	}

	public HashMap<Integer, Mammal> getMammals() {
		return mammals;
	}

	public void setMammalsById(HashMap<Integer, Mammal> mammals) {
		this.mammals= mammals;
	}

	public HashMap<Integer, Reptile> getReptiles() {
		return reptiles;
	}
	public HashMap<Integer, Bird> getBirds() {
		return birds;
	}

	public void setReptilesById(HashMap<Integer, Reptile> reptiles) {
		this.reptiles= reptiles;
	}

	public void setBirdsById(HashMap<Integer, Bird> birds) {
		this.birds = birds;
	}

	public HashMap<Integer, Section> getSections() {
		return sections;
	}
	public ArrayList<String> getSectionsNames() {
		if(sections!=null)
		{
			ArrayList<String>arr = new ArrayList<String>();
			for(Section s: sections.values()) {
				String str="id: "+s.getId();
				str+=", "+s.getSectionName();
				str+=", Capacity: "+s.getVisitors().size()+"/"+s.getMaxCapacity();
				arr.add(str);
			}
			return arr;
		}
		return null;
	}

	public void setSections(HashMap<Integer, Section> sections) {
		this.sections = sections;
	}

	public HashMap<Integer, Snack> getSnacks() {
		return snacks;
	}

	public void setSnacksById(HashMap<Integer, Snack> snacks) {
		this.snacks = snacks;
	}

	public HashMap<Integer, SnackBar> getBars() {
		return bars;
	}
	public ArrayList<String> getSnackBarsNames() //method to get all the bar details we need, with some filter of the less relevant data 
	{
		if(bars!=null)
		{
			ArrayList<String> arr= new ArrayList<String>();
			for(SnackBar sn: bars.values())
			{
				String str=sn.getBarName()+" section is "+sn.getSection();
				str+= ", id: "+sn.getId();
				arr.add(str);
			}
			return arr;
		}
		return null;
	}
	
	///method to get all the birds details we need, with some filter of the less relevant data 
	public ArrayList<String> getBirdsNames() 
	{
		if(birds!=null)
		{
			ArrayList<String> arr= new ArrayList<String>();
			for(Bird sn: birds.values())
			{
				String str=sn.getName();
				str+= ", id: "+sn.getId()+"  food: "+sn.getFood();
				arr.add(str);
			}
			return arr;
		}
		return null;
	}
	
	///method to get all the drinks details we need, with some filter of the less relevant data 
	public ArrayList<String> getDrink() {
		if(snacks!=null)
		{
			ArrayList<String> arr= new ArrayList<String>();
			for(Snack sn: snacks.values())
			{
				if(sn instanceof Drink) 
				{
					Drink d = (Drink)sn;
					String str=d.getSnackName();
					str+= ", id: "+d.getId()+", price: "+d.getPrice()+"  bar name: "+d.getBar().getBarName()+", is fattening "+d.isFattening()+", iceCube "+d.isIceCube()+", sprinkel " +d.isSprinkel()+", straw "+d.isStraw();
					arr.add(str);
				}
			}
			return arr;
		}
		return null;
	}
	//this method return collection of drinks with all the data
	public ArrayList<Drink> getAllDrinkObjects()
	{
		if(snacks!=null)
		{
			ArrayList<Drink> arr= new ArrayList<Drink>();
			for(Snack sn: snacks.values())
			{
				if(sn instanceof Drink) 
				{
					Drink d = (Drink)sn;
					arr.add(d);
				}
			}
			return arr;
		}
		return null;
	}
	
	//this method return collection of Food with all the data
	public ArrayList<Food> getAllFoodObjects()
	{
		if(snacks!=null)
		{
			ArrayList<Food> arr= new ArrayList<Food>();
			for(Snack sn: snacks.values())
			{
				if(sn instanceof Food) 
				{
					Food f = (Food)sn;
					arr.add(f);
				}
			}
			return arr;
		}
		return null;
	}
	///method to get all the food details we need, with some filter of the less relevant data 
	public ArrayList<String> getFood() {
		if(snacks!=null)
		{
			ArrayList<String> arr= new ArrayList<String>();
			for(Snack sn: snacks.values())
			{
				if(sn instanceof Food) 
				{
					Food d= (Food)sn;
					String str=d.getSnackName();
					str+= ", id: "+d.getId()+", price: "+d.getPrice()+"  bar name: "+d.getBar().getBarName()+", is fattening "+d.isFattening()+", plate "+d.isPlate()+", gluten free "+d.isGlutenFree()+", spicy "+d.isSpaciy();
					arr.add(str);
				}
			}
			return arr;
		}
		return null;
	}

	public void setBars(HashMap<Integer, SnackBar> bars) {
		this.bars = bars;
	}


	/**
	 * @return the animalTreatedByZooEmployee
	 */
	public HashMap<ZooEmployee, HashSet<Animal>> getAnimalTreatedByZooEmployee() {
		return animalTreatedByZooEmployee;
	}

	/**
	 * @param animalTreatedByZooEmployee the animalTreatedByZooEmployee to set
	 */
	public void setAnimalTreatedByZooEmployee(HashMap<ZooEmployee, HashSet<Animal>> animalTreatedByZooEmployee) {
		this.animalTreatedByZooEmployee = animalTreatedByZooEmployee;
	}

	/**
	 * @return the animalVisitsByPeople
	 */
	public HashMap<Visitor, HashSet<Animal>> getAnimalVisitsByPeople() {
		return AnimalVisitsByPeople;
	}

	/**
	 * @param animalVisitsByPeople the animalVisitsByPeople to set
	 */
	public void setAnimalVisitsByPeople(HashMap<Visitor, HashSet<Animal>> animalVisitsByPeople) {
		AnimalVisitsByPeople = animalVisitsByPeople;
	}


	public boolean addEmployee(ZooEmployee employee){
		if(employee == null)
			return false;

		if(!getEmployees().containsValue(employee)) {
			getEmployees().put(employee.getId(),employee)  ; 
			employee.getSection().getEmployees().add(employee);
			}
		else
			return false;
		return true;
	}

	public boolean addVisitor(Visitor v)  {
		if(v == null )
		{
			return false;
		}
			

		if(!getVisitors().containsValue(v))
		{
			getVisitors().put(v.getId(),v);
		}
				
		else
		{
			return false;
		}
			
		return true;
	}

	public boolean addMammalById(Mammal m) {
		if(m == null)
			return false;

		if(!getMammals().containsValue(m)) {
			getMammals().put(m.getId(),m); 
			m.getSection().getMammals().add(m);
			}
		else
			return false;
		return true;
	}

	public boolean addReptileById(Reptile r) {
		if(r == null)
			return false;

		if(!getReptiles().containsValue(r)) {
			getReptiles().put(r.getId(),r);
			r.getSection().getReptiles().add(r);
			}
		else
			return false;
		return true;
	}

	public boolean addBirdById(Bird b) {
		if(b == null)
			return false;

		if(!getBirds().containsValue(b)) {
			getBirds().put(b.getId(),b) ;

			b.getSection().getBirds().add(b);
			}
		else
			return false;
		return true;
	}

	public boolean addSection(Section s) 
	{
		//getting null Section
		if(s == null)
		{
			return false;
		}
		

		//if name is exist
		for(Section se : getSections().values())
		{
			if(se.getSectionName().equals(s.getSectionName()))
			{
				//getClassID().put("Section", getClassID().get("Section")-1);
				return false;
			}
		}
		
		//check if id is exist
		if(!getSections().containsValue(s))
		{
			getSections().put(s.getId() , s) ;
		}
		else 
		{
			return false;
		}
	
		return true;
	}

	public boolean addSnack(Snack s) {
		if(s == null)
			return false;

		if(!getSnacks().containsValue(s))
			getSnacks().put(s.getId(),s);
		else
			return false;
		s.getBar().getSnacks().add(s);
	
		return true;
	}

	public boolean addSnackBar(SnackBar sb, Section s) {
		if(sb == null || s == null)
			return false;

		if(!getBars().containsValue(sb)&&s.getBar()==null)
			getBars().put(sb.getId(),sb);
		else
			return false;

		s.setBar(sb);
		return true;
	}

	public boolean removeEmployee(ZooEmployee employee) {
		if(employee == null)
			return false;

		getEmployees().remove(employee.getId());
		employee.getSection().getEmployees().remove(employee);
		return true;
	}

	public boolean removeVisitor(Visitor v) {
		if(v == null)
			return false;

		getVisitors().remove(v.getId());
		v.getSection().getVisitors().remove(v);

		return true;
	}

	public boolean removeMammal(Mammal m) {
		if(m == null)
			return false;

		getMammals().remove(m.getId());
		m.getSection().getMammals().remove(m);
		return true;
	}

	public boolean removeReptile(Reptile r) {
		if(r == null)
			return false;

		getReptiles().remove(r.getId());
		r.getSection().getReptiles().remove(r);
		return true;
	}

	public boolean removeBird(Bird b) {
		if(b == null)
			return false;

		getBirds().remove(b.getId());
		b.getSection().getBirds().remove(b);
		return true;
	}

	public boolean removeSection(Section oldSection, Section newSection) {
		
		try {
			if(oldSection == null || newSection == null)
				return false;
			if(newSection.getMaxCapacity()<=newSection.getVisitors().size()+oldSection.getVisitors().size()) {
				throw new SectionIsFullException();
			}
				newSection.getEmployees().addAll(oldSection.getEmployees());
				newSection.getVisitors().addAll(oldSection.getVisitors());
				newSection.getMammals().addAll(oldSection.getMammals());
				newSection.getReptiles().addAll(oldSection.getReptiles());
				newSection.getBirds().addAll(oldSection.getBirds());
				removeSnackBar(oldSection.getBar());
				getSections().remove(oldSection.getId());
				return true;
		}
		catch (SectionIsFullException e)
		{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("Failed adding!");
			alert.setTitle("Add Visitor");
			alert.showAndWait();
		}
		return false;
		
	}

	public boolean removeSnackBar(SnackBar sb) {
		if(sb == null)
			return false;

		sb.getSection().setBar(null);
		getBars().remove(sb.getId());
		return true;
	}

	public boolean removeSnack(Snack s) {
		if(s == null)
			return false;

		for(SnackBar sb : getBars().values()) {
			sb.getSnacks().remove(s);
		}

		getSnacks().remove(s.getId());
		return true;
	}

	public ZooEmployee getRealEmployee(int id) {
		return getEmployees().get(id);
	}

	public Visitor getRealVisitor(int id) {

		return getVisitors().get(id);
	}

	public Mammal getRealMammal(int id) {
		return getMammals().get(id);
	}

	public Reptile getRealReptile(int id) {

		return getReptiles().get(id);
	}

	public Bird getRealBird(int id) {

		return getBirds().get(id);
	}

	public Section getRealSection(int id) {
		return getSections().get(id);
	}

	public SnackBar getRealSnackBar(int id) {
		return getBars().get(id);
	}

	public Snack getRealSnack(int id) {

		return getSnacks().get(id);
	}

	public double checkTotalRevenue() 
	{
		double revenue = 0;
		for(Section s : getSections().values()) 
		{
			revenue += s.getTodayRevenue();
			if(s.getBar()!=null)
				revenue += s.getBar().getProfit()*SnackBar.getZooPercentage();
		}
		return revenue;
	}

	public boolean newVisitorInZoo(Visitor v, Section s)  {
	
		try 
		{
			boolean isValid = true;
			if(v == null || s == null)
			{
				return false;
			}


			if(s.getMaxCapacity()<=s.getVisitors().size()) {
				throw new SectionIsFullException();
			}

			if(!addVisitor(v)){
				return false;
			}

			if(isValid && !s.getVisitors().contains(v)) {
				s.getVisitors().add(v);
				v.setSection(s);
				double price = v.checkTicketPrice();
				s.setTodayRevenue(s.getTodayRevenue() + price);
				return true;
			}
			

		}
		catch (SectionIsFullException e)
		{
			//alert to let the user know there is an error in adding
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("Failed adding!");
			alert.setTitle("Add Visitor");
			alert.showAndWait();
		}
		return false;
	}


	public ArrayList<Animal> getAllAnimalsBySectionMaxVisits(Section s) {
		if(s == null) 
		{
			return null;
		}
		ArrayList<Animal> animals= new ArrayList<Animal>();
		for(Animal a : s.getBirds()) {
			animals.add(a);
		}

		for(Animal a : s.getReptiles()) {
			animals.add(a);

		}

		for(Animal a : s.getMammals()) {
			animals.add(a);
		}

		animals.sort(new Comparator<Animal>() {
			@Override
			public int compare(Animal o1, Animal o2) {
				String o1Class = o1.getClass().getSimpleName();
				String o2Class = o2.getClass().getSimpleName();
				if(o1Class.equals(o2Class)) {
					Integer count1= o1.getVisitCounter();
					Integer count2 = o2.getVisitCounter();
					return count1.compareTo(count2);
				}
				else
					return o1Class.compareTo(o2Class);
			}

		});
		return animals;

	}

	@SuppressWarnings("null")
	public ArrayList<Animal> allAnimalsByWorker(ZooEmployee employee){
		ArrayList<Animal> treatsBy = new ArrayList<Animal>();
		if(getEmployees().containsValue(employee)) {
			HashSet<Animal> animalsOfEmployee = getAnimalTreatedByZooEmployee().get(employee);
			if(animalsOfEmployee == null) 
			{
				return treatsBy;
			}
			for(Bird b : getBirds().values()) {
				if(!animalsOfEmployee.contains(b) && (!b.isCanFly()))
					treatsBy.add(b);
			}
			for(Mammal m : getMammals().values() ) {
				if(!animalsOfEmployee.contains(m) && (!m.isMeatEater()))
					treatsBy.add(m);
			}
			for(Reptile r : getReptiles().values() ) {
				if(!animalsOfEmployee.contains(r) && (r.isDangerous()))
					treatsBy.add(r);
			}
			treatsBy.sort(null);

		}
		return treatsBy;
	}


	public ArrayList<Snack>  findAllSnackByWorker(SnackBar sb){
		ArrayList<Snack> snacks= new ArrayList<Snack>();

		for(Snack s : sb.getSnacks()) {
			if(s instanceof Drink) {
				Drink d = (Drink)s;
				if(!d.isSprinkel()&& d.isIceCube()) {

					snacks.add(d);
				}
			}
			else if(s instanceof Food) {
				Food f= (Food)s;
				if(f.isSpaciy() && !f.isPlate()) {
					snacks.add(f);
				}
			}
		}
		snacks.sort(new Comparator<Snack>() {
			@Override
			public int compare(Snack o1, Snack o2) {
				return o1.getSnackName().compareTo(o2.getSnackName());
			}
		});

		return snacks;
	}

	public ArrayList<Reptile> reptilesSleepAtSeasson(){
		ArrayList<Reptile> reptiles = new ArrayList<Reptile>();
		if(getReptiles() !=null) {

			for(Reptile r : getReptiles().values() ) {
				if(r.isSeasonSleep() && (!r.isDangerous()) ) {
					reptiles.add(r);
				}
			}
		}

		reptiles.sort(new Comparator<Reptile>() {

			@Override
			public int compare(Reptile o1, Reptile o2) {
				if(o1.getName().equals(o2.getName())) {
					return o1.getGender().compareTo(o2.getGender());
				}
				return o1.getName().compareTo(o2.getName());
			}


		});
		return reptiles;
	}

	public TreeMap<Visitor, Double> geAllDiscountAmount(){
		TreeMap<Visitor, Double> Discount = new TreeMap<Visitor, Double>(new Comparator<Visitor>() {
			@Override
			public int compare(Visitor o1, Visitor o2) {
				Double o1Price = o1.getTicket().getValue()-o1.checkTicketPrice();
				Double o2Price = o2.getTicket().getValue()-o2.checkTicketPrice();
				return o1Price.compareTo(o2Price);
			}
		});
		double discountPrice;
		for(Visitor v : getVisitors().values()) 
		{
			discountPrice = v.getTicket().getValue()-v.checkTicketPrice();
			if(discountPrice>0.0) // because if not we show all the visitors with no discount
				Discount.put(v, discountPrice );

		}

		return Discount;
	}

	public Section getMaxVisitorsVSMaxWorkers() {
		PriorityQueue<Section> pqSection = new PriorityQueue<Section>(new Comparator<Section>() {
			@Override
			public int compare(Section o1, Section o2) {
				int sub1= o1.getVisitors().size()-o1.getEmployees().size();
				int sub2 = o2.getVisitors().size()-o2.getEmployees().size();
				return sub2-sub1;
			}
		});

		for(Section s: getSections().values()) {
			pqSection.add(s);
		}
		return pqSection.poll();
	}
	
	//This method gets array of objects which contains all the declared fields of each class, and then it returns arrayList of the fields that we want to show the user in update/delete
	public ArrayList<String> getMyClassFieldsNames(Object[] brr)
	{
		String str ="";
		ArrayList<String>arr = new ArrayList<String>(); //here we will save the casting of the array to string
		for(Object ob: brr)
		{
			arr.add(ob.toString()); //casting from object to string
		}
		
		ArrayList<String> fieldNames = new ArrayList<String>(); //here ill save the strings to return
		for(String s: arr) 
		{
			str=""; //cleaning the string
			for(int i=s.length()-1;i>0&&s.charAt(i)!='.';i--)  // taking the needed indexes to subString the field names i need
			{
				str=s.substring(i,s.length()); // subString the field name
			}
			fieldNames.add(str); //adding the field name to the array ill return
		}
		fieldNames.remove("id"); //removing fields i dont want the user to see
		fieldNames.remove("idCounter");
		fieldNames.remove("visitCounter");
		fieldNames.remove("profit");
		fieldNames.remove("snacks");
		return fieldNames; //return
	}
	

	// add two arraylists of strings to one arrayList
	public ArrayList<String> subTwoArrayLists(ArrayList<String>arr1, ArrayList<String>arr2)
	{
		arr1.addAll(arr2);
		return arr1;
	}
	
	/*
	 * This method gets hashMap and return the maximum key
	 */
	public int getMaxId(HashMap<Integer, E> map)
	{
		int max=0;
		for(int i : map.keySet())
		{
			if(max<i)
			{
				max = i;
			}
		}
		return (max+1);
	}
	
	//This Method gets userName and password and check if can enter to system
	public int checkUser(String userName, String password)
	{		
		for(ZooEmployee emp : getEmployees().values())
		{
			if(emp.getUser().equals(userName) && emp.getPassword().equals(password))
			{
				return emp.getId();
			}
		}
		return -1;
	}
	
	//this method gets Person and return true if duplicate visior and zooEmployee
	public boolean isDuplicate(Person p)
	{
		ArrayList<Person> people = new ArrayList<Person>();
		people.addAll(getVisitors().values());
		people.addAll(getEmployees().values());
		for(Person pe: people)
		{
			if(pe.equalsPerson(p))
			{
				return true;
			}			
		}
		
		return false;
	
	}
}
