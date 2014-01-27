package com.katbutler.encore.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="EVENT_ITEMS")
public class EventItem {
	private Integer eventItemID;
	private Event event;
	private String name;
	private String description;
	private String picURL;
	private Set<Comment> comments = new HashSet<Comment>(0);
	
    
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EVENT_ITEM_ID", unique = true, nullable = false)
	public Integer getEventItemID() {
		return eventItemID;
	}
	
	public void setEventItemID(Integer eventItemID) {
		this.eventItemID = eventItemID;
	}
	
	@ManyToOne
	@JoinColumn(name = "EVENTS_EVENT_ID")
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "PICTURE_URL", nullable = false)
	public String getPicURL() {
		return picURL;
	}
	
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	@OneToMany(mappedBy = "eventItem")
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	
}
