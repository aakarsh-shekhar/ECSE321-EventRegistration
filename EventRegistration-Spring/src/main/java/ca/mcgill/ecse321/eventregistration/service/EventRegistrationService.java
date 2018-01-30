package ca.mcgill.ecse321.eventregistration.service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

@Service
public class EventRegistrationService {
	
	private RegistrationManager rm;

	public EventRegistrationService(RegistrationManager rm)
	{
	  this.rm = rm;
	}

	public Participant createParticipant(String name) throws InvalidInputException
	{
		 if (name == null || name.trim().length() == 0) {
			    throw new InvalidInputException("Participant name cannot be empty!");
			  }
			  Participant p = new Participant(name);
			  rm.addParticipant(p);
			  PersistenceXStream.saveToXMLwithXStream(rm);
			  return p;
	}
	
	public List<Participant> findAllParticipants() {
		  return rm.getParticipants();
	}

	public List<Event> getEventsForParticipant(Participant p) {
		List<Event> events = new ArrayList<Event>();

		for (Registration reg : rm.getRegistrations()) {
			if(reg.getParticipant().equals(p))
				events.add(reg.getEvent());
		}
		return events;
	}
	
	public Event createEvent(String name, Date date, Time startTime, Time endTime) throws InvalidInputException {

	    
	    if(name == null || date == null || startTime == null || endTime == null)
	    {
	    	throw new InvalidInputException("Null event");
	    }
	    
	    if(startTime.after(endTime))
	    {
	    	throw new InvalidInputException("Event end time cannot be before event start time!");
	    }
	    
	    if(name.trim().length() == 0)
	    {
	    	throw new InvalidInputException("Empty event name");
	    }
		
		Event e = new Event(name, date, startTime, endTime);
	    rm.addEvent(e);
	    PersistenceXStream.saveToXMLwithXStream(rm);
	    return e;
		}
	
	public Registration register(Participant participant, Event event) throws InvalidInputException {
		if(participant == null || event == null)
		{
			throw new InvalidInputException("Null registration");
		}
		boolean foundParticipant = false;
		boolean foundEvent = false;
		
		for(Participant particip : rm.getParticipants())
		{
			if(particip.equals(participant))
			{
				foundEvent = true;
			}
		}
		for(Event even : rm.getEvents())
		{
			if(even.equals(event))
			{
				foundEvent = true;
			}
		}
		
		if(foundParticipant == false || foundEvent == false)
		{
			throw new InvalidInputException ("Participant does not exist! Event does not exist!");
		}
		
		Registration reg = new Registration(participant, event);
		rm.addRegistration(reg);
		PersistenceXStream.saveToXMLwithXStream(rm);
		return reg;
		}

	public Participant getParticipantByName(String name) {
		for(Participant p : rm.getParticipants())
		{
			if(name.equals(p.getName()))
				return p;
		}
		return null;
	}

	public Event getEventByName(String name) {
		for(Event e : rm.getEvents())
		{
			if(name.equals(e.getName()))
				return e;
		}
		return null;
	}

	public List<Event> findAllEvents() {
		// TODO Auto-generated method stub
		return rm.getEvents();
	}
	
}
