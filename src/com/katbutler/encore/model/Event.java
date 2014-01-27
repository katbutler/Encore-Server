package com.katbutler.encore.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.katbutler.encore.xml.XmlDocument;
import com.katbutler.encore.xml.XmlElement;

@Entity
@Table(name = "EVENTS")
@XmlDocument(listRootName="Events", rootName="Event")
public class Event {
	
	@XmlElement(name="ID")
	private int eventID;
	
	@XmlElement(name="Title")
	private String title;
	
	@XmlElement(name="DateCreated")
	private Date dateCreated;
	
	@XmlElement(name="DateUpdated")
	private Date dateUpdated;
	
	@XmlElement(name="EventDate")
	private Date eventDate;
	
	@XmlElement(name="Description")
	private String description;
	
	@XmlElement(name="EventLatitude")
	Double eventLatitude;
	
	@XmlElement(name="EventLongitude")
	Double eventLongitude;
	
	private Set<User> users = new HashSet<User>(0);
	private Set<EventItem> eventItems = new HashSet<EventItem>(0);
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EVENT_ID", unique = true, nullable = false)
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DATE_CREATED", nullable = false)
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Column(name = "DATE_UPDATED", nullable = false)
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	@Column(name = "EVENT_DATE", nullable = false)
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "USERS_has_EVENTS", joinColumns = { @JoinColumn(name = "EVENTS_EVENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USERS_USER_ID") })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "EVENT_LAT")
	public Double getEventLatitude() {
		return eventLatitude;
	}

	public void setEventLatitude(Double eventLatitude) {
		this.eventLatitude = eventLatitude;
	}

	@Column(name = "EVENT_LONG")
	public Double getEventLongitude() {
		return eventLongitude;
	}

	public void setEventLongitude(Double eventLongitude) {
		this.eventLongitude = eventLongitude;
	}

	@OneToMany(mappedBy = "event", targetEntity = EventItem.class)
	public Set<EventItem> getEventItems() {
		return eventItems;
	}

	public void setEventItems(Set<EventItem> eventItems) {
		this.eventItems = eventItems;
	}
	
	
}
