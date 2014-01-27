package com.katbutler.encore.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.katbutler.encore.model.Activity;
import com.katbutler.encore.model.EncoreError;
import com.katbutler.encore.model.Event;
import com.katbutler.encore.model.User;
import com.katbutler.encore.model.common.ActivityType;
import com.katbutler.encore.persistence.HibernateDatabaseEventManager;
import com.katbutler.encore.persistence.HibernateDatabaseUserManager;
import com.katbutler.encore.xml.XmlWriter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * UsersAction handles all User based requests
 * 
 * @author Kat Butler
 *
 */
public class EventsAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = -7441783386128241976L;
	private static final String EVENT_ID = "id";
	private static final String TITLE = "title";
	private static final String DATE_CREATED = "date_created";
	private static final String DATE_UPDATED = "date_updated";
	private static final String EVENT_DATE = "event_date";
	private static final String EVENT_DESCRIPTION = "description";
	private static final String SEARCH = "search";
	
	private static final String USER_ID = "userid";
	HibernateDatabaseEventManager eventManager = HibernateDatabaseEventManager.getDefault();
	
	private String xml = "";
	private String errorXml = "";
	private String eventId;
	private String title;
	private String dateCreated;
	private String dateUpdated;
	private String eventDate;
	private String description;
	private String search;
	
	private String userId;
	
	private HttpServletRequest request;
	
	public String addEvent() throws Exception {
		
		eventId = getServletRequest().getParameter(EVENT_ID);
		title = getServletRequest().getParameter(TITLE);
		dateCreated = getServletRequest().getParameter(DATE_CREATED);
		dateUpdated = getServletRequest().getParameter(DATE_UPDATED);
		eventDate = getServletRequest().getParameter(EVENT_DATE);
		description = getServletRequest().getParameter(EVENT_DESCRIPTION);
		
		
		return SUCCESS;
	}
	
	public String addEventForUser() throws Exception {
		userId = getServletRequest().getParameter(USER_ID);
		eventId = getServletRequest().getParameter(EVENT_ID);

		try {
			if (eventManager.addEventForUser(Integer.parseInt(userId), Integer.parseInt(eventId))) {
				HibernateDatabaseUserManager userManager = HibernateDatabaseUserManager.getDefault();
				User user = userManager.getUserById(Integer.parseInt(userId));
				Event event = eventManager.getEventById(Integer.parseInt(eventId));
				Activity activity = new Activity();
				activity.setActivityType(ActivityType.ATTENDING_EVENT);
				activity.setActivityDate(new Date());
				activity.setDescription(user.getFirstName() + ActivityType.ATTENDING_EVENT.getDisplayString() + event.getTitle());
				user.getActivities().add(activity);
				userManager.add(activity);
				userManager.update(user);
				
				setXml(new XmlWriter<Event>(Event.class).writeXml(event));
			} else {
				setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(new EncoreError("Could not add Event to Users Events.")));
				return ERROR;	
			}
		} catch (NumberFormatException nfe) {
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(new EncoreError("Invalid Parameters supplied.")));
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String getEventForId() throws Exception {
		eventId = getServletRequest().getParameter(EVENT_ID);
		
		return SUCCESS;
	}
	
	public String getEventsForDate() throws Exception {
		eventDate = getServletRequest().getParameter(EVENT_DATE);
		
		return SUCCESS;
	}
	
	public String searchEventsTitle() throws Exception {
		search = getServletRequest().getParameter(SEARCH);
		
		List<Event> events = eventManager.searchEventsWithTitleSimilar(search, 100);
		
		setXml(new XmlWriter<Event>(Event.class).writeArrayXml(events));
		
		return SUCCESS;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpServletRequest getServletRequest() {
		return request;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getXmlRootStartTag() {
		return "<Events>";
	}
	
	public String getXmlRootEndTag() {
		return "</Events>";
	}

	public String getErrorXml() {
		return errorXml;
	}

	public void setErrorXml(String errorXml) {
		this.errorXml = errorXml;
	}

}
