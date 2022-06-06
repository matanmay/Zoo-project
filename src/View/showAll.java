package View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import Exceptions.NegativeNumberException;
import Exceptions.WrongFieldException;
import javafx.scene.control.TextField;

import Model.Animal;
import Model.Food;
import Model.Person;
import Model.Section;
import Model.Snack;
import Model.SnackBar;
import Model.Visitor;
import Model.Zoo;
import Model.ZooEmployee;
import Utils.SnackType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class showAll {
	
	@FXML
	private MenuBar menuId;
	@FXML
	private MenuBar menuId2;
	@FXML
	private ComboBox myComboBox;

	@FXML
	private ListView myList;
	@FXML
	private ComboBox comAnimal;
	@FXML
	private ImageView img1;
	@FXML
	private HBox hbox;
	@FXML
	private ComboBox comboFilter;  // this will use for filter the search
	
	@FXML
	private TextField minTxt; // for filter minimum value
	@FXML
	private TextField  maxField; // for filter minimum value
	@FXML
	private Button btnFilter;
	@FXML
	private Label toLbl;
	@FXML
	private Label lblSearch;
	
	@FXML
	private ImageView logOutIcon;
	@FXML
	private ImageView homeIcon;

	
	
	@FXML
	public void initialize() 
	{
		//for menu show icons of home and logout and tooltip
		logOutIcon.setVisible(true);
		Tooltip.install(logOutIcon, new Tooltip("LogOut"));	
		homeIcon.setVisible(true);
		Tooltip.install(homeIcon, new Tooltip("Home Page"));
		//for menuBar if admin or user
		if(Zoo.getInstance().getSaveMyUser().getId()==0)
		{
			//this is admin
			menuId.setVisible(true);
			menuId2.setVisible(false);
		}
		else
		{
			menuId.setVisible(false);
			menuId2.setVisible(true);
		}
		myList.setVisible(false);
		ArrayList<String>arr = new ArrayList<String>();
		//what the admin will be able to see
		arr.add("Sections"); arr.add("Birds"); arr.add("Mammals"); arr.add("Reptiles"); arr.add("SnackBars"); arr.add("Visitors");arr.add("Employees");
		arr.add("Drink");arr.add("Food");arr.add("animal Treated By Zoo Employee");arr.add("Animal Visits By People");
		myComboBox.setItems(FXCollections.observableArrayList(arr));
		comAnimal.setVisible(false); 
		minTxt.setVisible(false);
		maxField.setVisible(false);
		toLbl.setVisible(false);
		btnFilter.setVisible(false);
		//add image
		try {
			Image img= new Image("images/help.png");
			img1 = new ImageView(img);
			img1.setFitHeight(35);
			img1.setFitWidth(35);
			hbox.getChildren().add(img1);
			Tooltip.install(hbox, new Tooltip("choose here which fields you would like to see"));	
		}
		catch(Exception e)
		{
   		    Alert alert = new Alert(AlertType.ERROR, "we have some error");
			alert.setHeaderText("erorr");
			alert.setTitle("error");
			alert.showAndWait();
		}
	}
	
	
	@FXML
    void showSection(ActionEvent event) 
	{
		myList.setVisible(true);
		try 
		{	//we will show detailes by what the user chose
			if(myComboBox.getValue().equals("Sections"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
			if(myComboBox.getValue().equals("Birds"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBirds().values()));
			if(myComboBox.getValue().equals("Mammals"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getMammals().values()));
			if(myComboBox.getValue().equals("Reptiles"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getReptiles().values()));
			if(myComboBox.getValue().equals("SnackBars"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
			if(myComboBox.getValue().equals("Visitors"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
			if(myComboBox.getValue().equals("Employees"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
			if(myComboBox.getValue().equals("Drink"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAllDrinkObjects()));
			if(myComboBox.getValue().equals("Food"))
				myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAllFoodObjects()));
			if(myComboBox.getValue().equals("animal Treated By Zoo Employee"))
			{
				comAnimal.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAnimalTreatedByZooEmployee().keySet()));
				myList.setVisible(false);
				if(!comAnimal.getItems().isEmpty()) // if the list is not empty
				{
					comAnimal.setVisible(true);
				}
				else
				{
					noExistItems(); //will show alert the list is empty
				}
				
			}
			if(myComboBox.getValue().equals("Animal Visits By People"))
			{
				comAnimal.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAnimalVisitsByPeople().keySet()));
				myList.setVisible(false);
				if(!comAnimal.getItems().isEmpty())// if the list is not empty
				{
					comAnimal.setVisible(true);
				}
				else
				{
					noExistItems();//will show alert the list is empty
				}
				
			}
			filterList();
		
		}
			
		catch (Exception e) {
			 Alert alert = new Alert(AlertType.ERROR, "we have some error");
				alert.setHeaderText("erorr");
				alert.setTitle("error");
				alert.showAndWait();
		}
		
	  }
	
	//this method for animal Treated By Zoo Employee and Animal Visits By People"
	@FXML
	public void showAnimals(ActionEvent event)
	{
		if(myComboBox.getValue().equals("animal Treated By Zoo Employee"))
		{
			ZooEmployee a =(ZooEmployee) comAnimal.getValue();
			myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAnimalTreatedByZooEmployee().get(a)));	
		}
		if(myComboBox.getValue().equals("Animal Visits By People"))
		{
			Visitor a =(Visitor) comAnimal.getValue();
			myList.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAnimalVisitsByPeople().get(a)));
		}
		myList.setVisible(true);
		comAnimal.setVisible(false);
		Zoo.getInstance().serialize();
	}
	
	//adding to comboBox what the user can filter
	public void filterList()
	{
		ArrayList<String> typeFilter = new ArrayList<String>(); // arr to show in combox for filter
		if(myComboBox.getValue().equals("Sections"))
		{
			typeFilter.add("No filter");
			typeFilter.add("Name");
			typeFilter.add("Capacity");
			typeFilter.add("Revnues");
			typeFilter.add("Number of Employees now");
			typeFilter.add("Number of Visitors now");
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("Birds") || myComboBox.getValue().equals("Mammals") || myComboBox.getValue().equals("Reptiles"))
		{
			typeFilter.add("No filter");
			typeFilter.add("Name");
			typeFilter.add("Birthday");
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("SnackBars"))
		{
			typeFilter.add("No filter");
			typeFilter.add("Name");
			typeFilter.add("Profit");
			typeFilter.add("Number of Snacks");
		}
		else if(myComboBox.getValue().equals("Visitors") || myComboBox.getValue().equals("Employees"))
		{
			typeFilter.add("No filter");
			typeFilter.add("Name");
			typeFilter.add("Birthday");
			typeFilter.add("Section");
		}
		else if(myComboBox.getValue().equals("Drink") || myComboBox.getValue().equals("Food"))
		{
			typeFilter.add("No filter");
			typeFilter.add("Name");
			typeFilter.add("Price");
			typeFilter.add("Bar");
			typeFilter.add("type");
		}
		comboFilter.setItems(FXCollections.observableArrayList(typeFilter));
		

	}
	
	//doing the filtore
	public void updateListByFilter(ActionEvent event)
	{
		minTxt.setVisible(false);
		maxField.setVisible(false);
		toLbl.setVisible(false);
		btnFilter.setVisible(false);
		//if we dont do it the value exist on change
		minTxt.setText("");
		maxField.setText("");
		if(comboFilter.getValue() == null) // because it can be null 
		{
			comboFilter.setValue("No filter");
		}
		else if(myComboBox.getValue().equals("Sections"))
		{
			ArrayList<Section> sectionsSorted = new ArrayList<Section>();
			sectionsSorted.addAll(Zoo.getInstance().getSections().values()); // adding all sections to our arrayList
			if(comboFilter.getValue().equals("Name"))
			{
				sectionsSorted.sort(new Comparator<Section>() 
				{
					@Override
					public int compare(Section o1, Section o2) 
					{
						return o1.getSectionName().compareTo(o2.getSectionName());
					}
					
				});
			}
			else if(comboFilter.getValue().equals("Capacity"))
			{
				sectionsSorted.sort(new Comparator<Section>() 
				{
					//compare by capacity
					@Override
					public int compare(Section o1, Section o2) 
					{
						if(o1.getMaxCapacity()-o2.getMaxCapacity()==0)
						{
							return o1.getId()-o2.getId(); // because can equals max capacity
						}
						return o1.getMaxCapacity()-o2.getMaxCapacity();
						
					}
					
				});
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
			}
			
			else if(comboFilter.getValue().equals("Revnues"))
			{
				sectionsSorted.sort(new Comparator<Section>() 
				{
					//compare by todays Revenue
					@Override
					public int compare(Section o1, Section o2) 
					{
						if(o1.getTodayRevenue()-o2.getTodayRevenue()==0.0)
						{
							return o1.getId()-o2.getId(); // because can equals max capacity
						}
						Double revenuOfO1 = o1.getTodayRevenue();
						Double revenuOfO2 = o2.getTodayRevenue();
						return revenuOfO1.compareTo(revenuOfO2);
					}
					
				});
				
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
			}
			
			else if(comboFilter.getValue().equals("Number of Employees now"))
			{
				sectionsSorted.sort(new Comparator<Section>() 
				{
					//compare by number of Employees
					@Override
					public int compare(Section o1, Section o2) 
					{
						int def = o1.getEmployees().size() - o2.getEmployees().size();
						if(def == 0)
						{
							return o1.getId()-o2.getId(); // because can equals number of Employees
						}
						return def;
					}
					
				});
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
				
			}
			else if(comboFilter.getValue().equals("Number of Visitors now"))
			{
				sectionsSorted.sort(new Comparator<Section>() 
				{
					//compare by number of Visitors
					@Override
					public int compare(Section o1, Section o2) 
					{
						int def = o1.getVisitors().size() - o2.getVisitors().size();
						if(def == 0)
						{
							return o1.getId()-o2.getId(); // because can equals number of Visitors
						}
						return def;
					}
					
				});	
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
			}
			
				myList.setItems(FXCollections.observableArrayList(sectionsSorted));
		}		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("Birds") || myComboBox.getValue().equals("Mammals") || myComboBox.getValue().equals("Reptiles"))
		{
			ArrayList<Animal> animalArray= new ArrayList<Animal>();
			if(myComboBox.getValue().equals("Birds"))
			{
				if(Zoo.getInstance().getBirds()!=null)
					animalArray.addAll(filterAnimals(Zoo.getInstance().getBirds()));
			}
			else if(myComboBox.getValue().equals("Mammals"))
			{
				if(Zoo.getInstance().getMammals()!=null)
					animalArray.addAll(filterAnimals(Zoo.getInstance().getMammals()));
			}
			else if(myComboBox.getValue().equals("Reptiles"))
			{
				if(Zoo.getInstance().getReptiles()!=null)
					animalArray.addAll(filterAnimals(Zoo.getInstance().getReptiles()));
			}
			myList.setItems(FXCollections.observableArrayList(animalArray));
			
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("SnackBars"))
		{
			ArrayList<SnackBar> barsArray= new ArrayList<SnackBar>();
			barsArray.addAll(Zoo.getInstance().getBars().values());
			if(comboFilter.getValue().equals("Name"))
			{
				barsArray.sort(new Comparator<SnackBar>() 
				{
					@Override
					public int compare(SnackBar o1, SnackBar o2) 
					{
						int deff = o1.getBarName().compareTo(o2.getBarName());
						if(deff == 0)
						{
							return o1.getId()-o2.getId();
						}
						return deff;
					}
					
				});
			}
			else if(comboFilter.getValue().equals("Profit"))
			{
				barsArray.sort(new Comparator<SnackBar>() 
				{
					@Override
					public int compare(SnackBar o1, SnackBar o2) 
					{
						Double o1Profit = o1.getProfit();
						Double o2Profit = o2.getProfit();
						int deff = o1Profit.compareTo(o2Profit);
						if(deff == 0)
						{
							return o1.getId()-o2.getId();
						}
						return deff;
					}
					
				});
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
			}
			else if(comboFilter.getValue().equals("Number of Snacks"))
			{
				barsArray.sort(new Comparator<SnackBar>() 
				{
					@Override
					public int compare(SnackBar o1, SnackBar o2) 
					{
						int deff = o1.getSnacks().size() - o2.getSnacks().size();
						if(deff == 0)
						{
							return o1.getId()-o2.getId();
						}
						return deff;
					}
					
				});
				minTxt.setVisible(true);
				maxField.setVisible(true);
				toLbl.setVisible(true);
				btnFilter.setVisible(true);
			}
			myList.setItems(FXCollections.observableArrayList(barsArray));
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("Visitors") || myComboBox.getValue().equals("Employees"))
		{
			ArrayList<Person> personsList = new ArrayList<Person>();
			if(myComboBox.getValue().equals("Visitors"))
			{
				if(Zoo.getInstance().getVisitors()!=null)
					personsList.addAll(filterPersons(Zoo.getInstance().getVisitors()));
			}
			else if(myComboBox.getValue().equals("Employees"))
			{
				if(Zoo.getInstance().getEmployees()!=null)
					personsList.addAll(filterPersons(Zoo.getInstance().getEmployees()));
			}
			myList.setItems(FXCollections.observableArrayList(personsList)); // show the list choose
	
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(myComboBox.getValue().equals("Food") || myComboBox.getValue().equals("Drink"))
		{
			ArrayList<Snack> snacksList = new ArrayList<Snack>();
			if(myComboBox.getValue().equals("Food"))
			{
				if(Zoo.getInstance().getFood()!=null)
					snacksList.addAll(filterSnacks(Zoo.getInstance().getAllFoodObjects()));
			}
			else if(myComboBox.getValue().equals("Drink"))
			{
				if(Zoo.getInstance().getDrink()!=null)
					snacksList.addAll(filterSnacks(Zoo.getInstance().getAllDrinkObjects()));
			}
			myList.setItems(FXCollections.observableArrayList(snacksList)); // show the list choose
		}
	}
	
	
	private ArrayList<Animal> filterAnimals(HashMap<Integer,Animal> animals)
	{
		ArrayList<Animal> arr = new ArrayList<Animal>();
		if(animals== null)
		{
			return arr;
		}
		arr.addAll(animals.values());
		if(comboFilter.getValue() == null || comboFilter.getValue().equals("No filter"))
		{
			return arr;
		}
		
		else if(comboFilter.getValue().equals("Name"))
		{
			arr.sort(new Comparator<Animal>() 
			{
				@Override
				public int compare(Animal o1, Animal o2) 
				{
					if(o1.getName().equals(o2.getName())) // if the name is equals
					{
						return o1.getId()-o2.getId();
					}
					return o1.getName().compareTo(o2.getName()); // compere by name
				}
				
			});
		}
		
		else if(comboFilter.getValue().equals("Birthday"))
		{
			arr.sort(new Comparator<Animal>() 
			{
				@Override
				public int compare(Animal o1, Animal o2) 
				{
					if(o1.getBirthDay().equals(o2.getBirthDay())) // if the birthday is equals
					{
						return o1.getId()-o2.getId();
					}
					return o1.getBirthDay().compareTo(o2.getBirthDay());
				}
				
			});
		}
		
		return arr;
		
		
	}
	
	
	private ArrayList<Person> filterPersons(HashMap<Integer,Person> persons)
	{
		ArrayList<Person> arr = new ArrayList<Person>();
		if(persons== null)
		{
			return arr;
		}
		arr.addAll(persons.values());
		if(comboFilter.getValue() == null || comboFilter.getValue().equals("No filter"))
		{
			return arr;
		}
		
		else if(comboFilter.getValue().equals("Name"))
		{
			arr.sort(new Comparator<Person>() 
			{
				@Override
				public int compare(Person o1, Person o2) 
				{
					if(o1.getFirstName().equals(o2.getFirstName())) // if the name is equals
					{
						if(o1.getLastName().equals(o2.getLastName()))
						{
							return o1.getId()-o2.getId();
						}		
						else
						{
							o1.getLastName().compareTo(o2.getLastName()); // first name is equals
						}
					}
					return o1.getFirstName().compareTo(o2.getFirstName()); // compere by First name
				}
				
			});
		}
		
		else if(comboFilter.getValue().equals("Birthday"))
		{
			arr.sort(new Comparator<Person>() 
			{
				@Override
				public int compare(Person o1, Person o2) 
				{
					if(o1.getBirthDay().equals(o2.getBirthDay())) // if the birthday is equals
					{
						return o1.getId()-o2.getId();
					}
					return o1.getBirthDay().compareTo(o2.getBirthDay());
				}
				
			});
		}
		
		else if(comboFilter.getValue().equals("Section"))
		{
			arr.sort(new Comparator<Person>() 
			{
				@Override
				public int compare(Person o1, Person o2) 
				{
					if(o1.getSection().equals(o2.getSection())) // if the section is equals
					{
						return o1.getId()-o2.getId();
					}
					return o1.getSection().getId() - o2.getSection().getId(); // by section id
				}
				
			});
		}
		return arr;
		
		
	}
	
	//this method gets hashmap of integer and snak and show by filter the list
	private ArrayList<Snack> filterSnacks(ArrayList<Snack> snacks)
	{
		ArrayList<Snack> arr = new ArrayList<Snack>();
		if(snacks== null)
		{
			return arr;
		}
		arr.addAll(snacks);
		if(comboFilter.getValue() == null || comboFilter.getValue().equals("No filter"))
		{
			return arr;
		}
		
		else if(comboFilter.getValue().equals("Name"))
		{
			arr.sort(new Comparator<Snack>() 
			{
				@Override
				public int compare(Snack o1, Snack o2) 
				{
					if(o1.getSnackName().equals(o2.getSnackName()))
					{
						return o1.getId() - o2.getId(); // secound by id
					}
					return o1.getSnackName().compareTo(o2.getSnackName()); // by name
				}
				
			});
		}
		
		else if(comboFilter.getValue().equals("Price"))
		{
			arr.sort(new Comparator<Snack>() 
			{
				@Override
				public int compare(Snack o1, Snack o2) 
				{
					Double priceO1 = o1.getPrice();
					Double priceO2 = o2.getPrice();
					if(priceO1.equals(priceO2))
					{
						return o1.getId() - o2.getId();
					}
					return priceO1.compareTo(priceO2);
				}
				
			});
			
			minTxt.setVisible(true);
			maxField.setVisible(true);
			toLbl.setVisible(true);
			btnFilter.setVisible(true);
		}
		
		else if(comboFilter.getValue().equals("Bar"))
		{
			arr.sort(new Comparator<Snack>() 
			{
				@Override
				public int compare(Snack o1, Snack o2) 
				{
					if(o1.getBar().equals(o2.getBar()))
					{
						return o1.getId() - o2.getId();
					}
					return o1.getBar().getId() - o2.getBar().getId(); // compere by bar
				}
				
			});
		}
		else if(comboFilter.getValue().equals("type"))
		{
			arr.sort(new Comparator<Snack>() 
			{
				@Override
				public int compare(Snack o1, Snack o2) 
				{
					if(o1.getType().equals(o2.getType()))
					{
						return o1.getId() - o2.getId();
					}
					return o1.getType().compareTo(o2.getType()); // compere by Type
				}
				
			});
		}
		
		return arr;
		
		
	}
	
	
	public void priceFilter (MouseEvent event)
	{
		try 
		{
			
			double minimum = Double.parseDouble(minTxt.getText());
			double maximum = Double.parseDouble(maxField.getText());
			if(minimum>maximum)
			{
				throw new WrongFieldException();
			}
			if(minimum<0.0)
			{
				throw new NegativeNumberException();
			}
			
			if(myComboBox.getValue().equals("Sections"))
			{
				ArrayList<Section> allSections = new ArrayList<Section>();
				allSections.addAll(Zoo.getInstance().getSections().values());
				ArrayList<Section> filterSection = new ArrayList<Section>();
				if(comboFilter.getValue().equals("Capacity"))
				{
					for(Section se : allSections)
					{
						if(se.getMaxCapacity()>=minimum && se.getMaxCapacity()<=maximum) // if the max capcity between min and max
						{
							filterSection.add(se);
						}
					}
					filterSection.sort(new Comparator<Section>() 
					{
						@Override
						public int compare(Section o1, Section o2) 
						{
							int deff = o1.getMaxCapacity() - o2.getMaxCapacity();
							if(deff == 0)
							{
								return o1.getId()-o2.getId();
							}
							return deff;
						}
						
					}); // end comparator
					
				}//end if capacity
				
				else if(comboFilter.getValue().equals("Revnues"))
				{
					for(Section se : allSections)
					{
						if(se.getTodayRevenue()>=minimum && se.getTodayRevenue()<=maximum) // if the revenu between min and max
						{
							filterSection.add(se);
						}
					}
					
					filterSection.sort(new Comparator<Section>() 
					{
						//compare by todays Revenue
						@Override
						public int compare(Section o1, Section o2) 
						{
							if(o1.getTodayRevenue()-o2.getTodayRevenue()==0.0)
							{
								return o1.getId()-o2.getId(); // because can equals max capacity
							}
							Double revenuOfO1 = o1.getTodayRevenue();
							Double revenuOfO2 = o2.getTodayRevenue();
							return revenuOfO1.compareTo(revenuOfO2);
						}
						
					});

				}
				
				else if(comboFilter.getValue().equals("Number of Employees now"))
				{
					int employeesNumber;
					for(Section se : allSections)
					{
						employeesNumber = se.getEmployees().size();
						if(employeesNumber>=minimum && employeesNumber<=maximum) // if the number of emploees between min and max
						{
							filterSection.add(se);
						}
					}
					
					filterSection.sort(new Comparator<Section>() 
					{
						//compare by number of Employees
						@Override
						public int compare(Section o1, Section o2) 
						{
							int def = o1.getEmployees().size() - o2.getEmployees().size();
							if(def == 0)
							{
								return o1.getId()-o2.getId(); // because can equals number of Employees
							}
							return def;
						}
						
					});
;
					
				}
				else if(comboFilter.getValue().equals("Number of Visitors now"))
				{
					int visitorsNumber;
					for(Section se : allSections)
					{
						visitorsNumber = se.getVisitors().size();
						if(visitorsNumber>=minimum && visitorsNumber<=maximum) // if the number of emploees between min and max
						{
							filterSection.add(se);
						}
					}
					
					filterSection.sort(new Comparator<Section>() 
					{
						//compare by number of Visitors
						@Override
						public int compare(Section o1, Section o2) 
						{
							int def = o1.getVisitors().size() - o2.getVisitors().size();
							if(def == 0)
							{
								return o1.getId()-o2.getId(); // because can equals number of Visitors
							}
							return def;
						}
						
					});	

				}
				myList.setItems(FXCollections.observableArrayList(filterSection));
				minTxt.setVisible(false);
				maxField.setVisible(false);
				toLbl.setVisible(false);
				btnFilter.setVisible(false);	
					
			}//end if Section

			else if(myComboBox.getValue().equals("SnackBars"))
			{
				ArrayList<SnackBar> allBars = new ArrayList<SnackBar>();
				allBars.addAll(Zoo.getInstance().getBars().values());
				ArrayList<SnackBar> filterBars = new ArrayList<SnackBar>();
				if(comboFilter.getValue().equals("Profit"))
				{
					for(SnackBar sb : allBars)
					{
						if(sb.getProfit()>=minimum && sb.getProfit()<=maximum) // between min to max
						{
							filterBars.add(sb);
						}
					}
					filterBars.sort(new Comparator<SnackBar>() 
					{
						@Override
						public int compare(SnackBar o1, SnackBar o2) 
						{
							Double o1Profit = o1.getProfit();
							Double o2Profit = o2.getProfit();
							int deff = o1Profit.compareTo(o2Profit);
							if(deff == 0)
							{
								return o1.getId()-o2.getId();
							}
							return deff;
						}
						
					}); // end comparator
					
				}//end if profit
				
				////by number of snacks
				else if(comboFilter.getValue().equals("Number of Snacks"))
				{
					for(SnackBar sb : allBars)
					{
						int mountSnacks = sb.getSnacks().size(); // save the number of snacks
						//System.out.println(mountSnacks);
						if(mountSnacks>=minimum && mountSnacks<=maximum) // between min to max
						{
							filterBars.add(sb);
						}
					}
					
					filterBars.sort(new Comparator<SnackBar>() 
					{
						@Override
						public int compare(SnackBar o1, SnackBar o2) 
						{
							int deff = o1.getSnacks().size() - o2.getSnacks().size();
							if(deff == 0)
							{
								return o1.getId()-o2.getId();
							}
							return deff;
						}
						
					});
				}
				myList.setItems(FXCollections.observableArrayList(filterBars));
				minTxt.setVisible(false);
				maxField.setVisible(false);
				toLbl.setVisible(false);
				btnFilter.setVisible(false);
				
				
			}//end snackBar
			
			if(myComboBox.getValue().equals("Food") || myComboBox.getValue().equals("Drink"))
			{
				ArrayList<Snack> allSnacks = new ArrayList<Snack>(); // will save the all snacks
				ArrayList<Snack> filterSnacks = new ArrayList<Snack>(); // will save all snacks in filter
				if(comboFilter.getValue().equals("Price"))
				{
					if(myComboBox.getValue().equals("Food"))
					{
						if(Zoo.getInstance().getAllFoodObjects()!=null)
							allSnacks.addAll(Zoo.getInstance().getAllFoodObjects());
						
						for(Snack f : allSnacks)
						{
							if(f.getPrice()>=minimum&&f.getPrice()<=maximum)
							{
								filterSnacks.add(f);
							}
						}
						
						filterSnacks.sort(new Comparator<Snack>() 
						{
							@Override
							public int compare(Snack o1, Snack o2) 
							{
								Double priceO1 = o1.getPrice();
								Double priceO2 = o2.getPrice();
								if(priceO1.equals(priceO2))
								{
									return o1.getId() - o2.getId();
								}
								return priceO1.compareTo(priceO2);
							}
							
						});
					} // end food
					else if(myComboBox.getValue().equals("Drink"))
					{
						if(Zoo.getInstance().getAllDrinkObjects()!=null)
							allSnacks.addAll(Zoo.getInstance().getAllDrinkObjects());
						
						for(Snack d : allSnacks)
						{
							if(d.getPrice()>=minimum&&d.getPrice()<=maximum)
							{
								filterSnacks.add(d);
							}
						}
						
						filterSnacks.sort(new Comparator<Snack>() 
						{
							@Override
							public int compare(Snack o1, Snack o2) 
							{
								Double priceO1 = o1.getPrice();
								Double priceO2 = o2.getPrice();
								if(priceO1.equals(priceO2))
								{
									return o1.getId() - o2.getId();
								}
								return priceO1.compareTo(priceO2);
							}
							
						});
					}//end drink
					
					myList.setItems(FXCollections.observableArrayList(filterSnacks));
					minTxt.setVisible(false);
					maxField.setVisible(false);
					toLbl.setVisible(false);
					btnFilter.setVisible(false);
					
				}//end price
				
				
				
			}//end food and drink
			
		}
		
		catch(WrongFieldException e)
		{
			errorAlert("The left field should be smaller than the right field");
		}
		
		catch(NegativeNumberException e)
		{
			errorAlert(e.getMessage());
		}
		catch(NumberFormatException e)
		{
			errorAlert("The values needs to be numbers");
			
		}
		catch(NullPointerException e)
		{
			errorAlert("one or more field is empty");
		}
		catch(Exception e)
		{
			 Alert alert = new Alert(AlertType.ERROR, "we have some error");
				alert.setHeaderText("erorr");
				alert.setTitle("error");
				alert.showAndWait();
		}
		
	}
	
	
	//for menuBar
	
	@FXML
	public void movePage(ActionEvent event) throws Exception
	{
		String add = "add";
		add+=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveShow(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/showAll.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	@FXML
	public void moveAction(ActionEvent event) throws Exception
	{
		String add=(((MenuItem)event.getSource()).getText());
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/"+add+".fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//this method show failed alert on the screen with the string message
	private void errorAlert(String message)
	{
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setHeaderText("Failed show!");
		alert.setTitle("Error");
		alert.showAndWait();
	}
	
	//this method show alert if we dont have items
	private void noExistItems()
	{
		Alert alert = new Alert(AlertType.INFORMATION, "This List is empty");
		alert.setHeaderText("Empty List");
		alert.setTitle("empty list");
		alert.showAndWait();
	}

 	//on click on pic logOut you will do this method to go back to login 
	public void logOut(MouseEvent e) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//on click on pic home you will do this method to go back to home page
	public void goToHomePage(MouseEvent e) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/homePage.fxml"));
		Stage primaryStage = (Stage) menuId.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}