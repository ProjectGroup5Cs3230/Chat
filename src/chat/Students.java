package chat;

import java.util.ArrayList;
import java.util.List;

public class Students 
{
	String firstName;
	String lastName;
	double score;
	Students partner;
	List<String> response = new ArrayList<String>();
	
	public Students(String firstName, String lastName, double score, Students partner, List<String> response )
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.score = score;
		this.partner = partner;
		this.response = response;
	}
	
	public void setPartner(Students partner) {
		this.partner = partner;
	}

	public void Chat(ChatFrame chat)
	{
		for(int i = 0; i < response.size(); i++)
		{
			chat.output(firstName + " " + lastName + ": " + response.get(i));
			Sleep();
			chat.output(partner.firstName + " " + partner.lastName + ": " + partner.response.get(i));
			Sleep();
		}
	}
	
	public void Sleep()
	{
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(!(o instanceof Students))
			return false;
		
		Students s = (Students) o;
		return s.firstName.equals(firstName) && s.lastName.equals(lastName);
	}
	
	@Override 
	public int hashCode()
	{
		return 37 * firstName.hashCode() + lastName.hashCode();
	}
}


