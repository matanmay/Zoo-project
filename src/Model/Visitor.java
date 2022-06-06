package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.PrimitiveIterator;

import Exceptions.DiscountCheckException;
import Exceptions.MaximumCapcityException;
import Utils.Discount;
import Utils.Gender;
import Utils.MyFileLogWriter;
import Utils.TicketType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Visitor extends Person implements Serializable 
{
	private static int idCounter;

	private TicketType ticket;
	private Discount discount;

	public Visitor(String firstName, String lastName, LocalDate date, Gender gender
			, TicketType ticket, Discount discount) throws DiscountCheckException 
	{
		super(Zoo.getInstance().getMaxId(Zoo.getInstance().getVisitors()),firstName, lastName, date, gender, null);
		this.ticket = ticket;
		setDiscount(discount);
	}

	public Visitor(int id) {
		super(id);
	}

	public TicketType getTicket() {
		return ticket;
	}

	public void setTicket(TicketType ticket) {
		this.ticket = ticket;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) 
	{
		try 
		{
			this.discount = discount; // Gaston said on Shat Kabala in 02-10-20 if more then 25 except but give exception
			if(discount.getPercentage()>25.0) //because the discount needs to be between 0% to 25% only
			{
				throw new DiscountCheckException();
			}	
		}
		catch(DiscountCheckException e)
		{
			Alert alert = new Alert(AlertType.INFORMATION,"Discounts need to be between 0% to 25%\nbut because you chose " + this.discount + " it is allowed" );
			alert.setHeaderText("Discount Alert");
			alert.setTitle("Discount Alert");
			alert.showAndWait();	
			
		}
			
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" id: " +getId() + ", name: "+getFirstName()+" "+getLastName()+", "+this.getBirthDay()+",  "+ticket +", section: "+super.getSection().getSectionName();
	}

	public void moveVisitorToSection(Section newSection)
	{
		try 
		{
			if(newSection == null || (this.getSection().getMaxCapacity()/2) >= this.getSection().getVisitors().size()-1) {
				throw new MaximumCapcityException();
			}
			if(this.getSection().getMaxCapacity()<=this.getSection().getVisitors().size()-1)
			{
				throw new MaximumCapcityException();
			}
	
			this.getSection().getVisitors().remove(this);
			Section s= this.getSection();
			this.setSection(newSection);
			newSection.getVisitors().add(this);
			alertSucssedMessage(this+" moved from Section "+s.getSectionName()+" to " + "Section "+newSection);
		}
		catch (MaximumCapcityException e) {
			failMessage(e.getMessage());	
		}
		
		
	}
	
	public double checkTicketPrice() 
	{
		double perc = 100-getDiscount().getPercentage();
		double price = perc*ticket.getValue()/100;
		return price;
	}

	public boolean purchaseSnack(Snack s) {
		boolean isValid = true;
		if(s == null)
		{
			isValid = false;
		}
			
		
	if( s instanceof Drink) {
		
		Drink tmp = (Drink)s;
		 
		if(isValid && tmp.isSprinkel() && this.getSection().getBar().getSnacks().contains(tmp)){
			this.getSection().getBar().setProfit(this.getSection().getBar().getProfit() 
					+ s.getPrice()+5);
			this.getSection().getBar().getSnacks().remove(tmp);
			String str ="Visitor: "+this.getFirstName()+" Purchased Drink: "+tmp;
			alertSucssedMessage(str);
			saveCounterForBuy(s);
			return true;

			
		}
		
		else if(isValid && !tmp.isSprinkel() && this.getSection().getBar().getSnacks().contains(tmp)){
			this.getSection().getBar().setProfit(this.getSection().getBar().getProfit() 
					+ s.getPrice());
			this.getSection().getBar().getSnacks().remove(tmp);
			String str ="Visitor: "+this.getFirstName()+" Purchased Drink: "+tmp;
			alertSucssedMessage(str);
			saveCounterForBuy(s);
			return true;
			
			
		}
		else
		{	String str ="Visitor: "+this.getFirstName()+" Did Not Purchased any DRINK";
			failMessage(str);
			return false;
		}
	}
	
	else if( s instanceof Food) {
		
		Food tmp = (Food)s;
		 
		if(isValid && tmp.isPlate() && this.getSection().getBar().getSnacks().contains(tmp)){
			this.getSection().getBar().setProfit(this.getSection().getBar().getProfit() 
					+ s.getPrice()+20);
			this.getSection().getBar().getSnacks().remove(tmp);
			String str ="Visitor: "+this.getFirstName()+" Purchased Food: "+tmp.getSnackName() +" Price: " + tmp.getPrice();
			alertSucssedMessage(str);
			saveCounterForBuy(s);
			return true;
			
			
		}
		
		else if(isValid && !tmp.isPlate() && this.getSection().getBar().getSnacks().contains(tmp)){
			this.getSection().getBar().setProfit(this.getSection().getBar().getProfit() 
					+ s.getPrice());
			this.getSection().getBar().getSnacks().remove(tmp);
			String str ="Visitor: "+this.getFirstName()+" Purchased Food: "+tmp.getSnackName() +" Price: " + tmp.getPrice();
			alertSucssedMessage(str);
			saveCounterForBuy(s);
			return true;
		}
		else {
			String str ="Visitor: "+this.getFirstName()+" Did Not Purchased any FOOD";
			failMessage(str);
			return false;
		}
		
	}
	
		else
		{
			String str ="Visitor: "+this.getFirstName()+" Did Not Purchased Snack";
			failMessage(str);
			return false;
		}
			
	}
	
	//this method will show message on alert - Information
	 private void alertSucssedMessage(String message)
	 {
			Alert alert = new Alert(AlertType.INFORMATION, message);
			alert.showAndWait();
			Zoo.getInstance().serialize();
 
	 }
	 
	 
	 private void failMessage(String message)
	 {
			Alert alert = new Alert(AlertType.ERROR, message);
			alert.setHeaderText("Failed");
			alert.setTitle("Alert");
			alert.showAndWait();
	 }

	 //this method will add to collections that save counter of buying snacks
	 private void saveCounterForBuy(Snack s)
	 {
		 	HashMap hm = Zoo.getInstance().getCounterSnacks();
			if(!hm.containsKey(s.getSnackName()))
			{
				hm.put(s.getSnackName(), 1);
			}
			
			else
			{
				hm.put(s.getSnackName(), 1 + (int)(hm.get(s.getSnackName())));
			}
			
		 
	 }

}
