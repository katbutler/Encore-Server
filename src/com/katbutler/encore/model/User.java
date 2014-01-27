package com.katbutler.encore.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.katbutler.encore.xml.XmlDocument;
import com.katbutler.encore.xml.XmlElement;

@Entity
@Table(name="USERS")
@XmlDocument(listRootName="Users", rootName="User")
public class User {

	@XmlElement(name="UserID")
	Integer userId;
	
	@XmlElement(name="FirstName")
	String firstName;
	
	@XmlElement(name="LastName")
	String lastName;
	
	@XmlElement(name="Email")
	String email;

	@XmlElement(name="PictureUrl")
	String picURL;
	
	@XmlElement(name="Bio")
	String bio;
	
	@XmlElement(name="HomeLatitude")
	Double homeLatitude;
	
	@XmlElement(name="HomeLongitude")
	Double homeLongitude;
	
	String password;
	
	private List<User> followers = new ArrayList<User>(0);
	private List<Event> events = new ArrayList<Event>(0);
	private List<Activity> activities = new ArrayList<Activity>(0);
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * Use to get Full name using this users first and last names.
	 * @return
	 */
//	public String getFullName() {
//		StringBuilder nameStringBuilder = new StringBuilder();
//		
//		if (firstName != null) {
//			nameStringBuilder.append(getFirstName() + " ");
//		}
//		
//		if (lastName != null) {
//			nameStringBuilder.append(getLastName());
//		}
//		return nameStringBuilder.toString();
//	}
//	
//	public void setFullName(String val) {
//		
//	}

	@Column(name = "FIRST_NAME", nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME", nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "EMAIL", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PICTURE_URL")
	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	@Column(name = "BIO")
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}

	@Column(name = "HOME_LAT")
	public Double getHomeLatitude() {
		return homeLatitude;
	}

	public void setHomeLatitude(Double homeLatitude) {
		this.homeLatitude = homeLatitude;
	}

	@Column(name = "HOME_LONG")
	public Double getHomeLongitude() {
		return homeLongitude;
	}

	public void setHomeLongitude(Double homeLongitude) {
		this.homeLongitude = homeLongitude;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "USERS_FOLLOWING", 
			   joinColumns = { @JoinColumn(name = "USER_ID") }, 
			   inverseJoinColumns = { @JoinColumn(name = "FOLLOWING_USER_ID") })
	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "USERS_has_EVENTS", 
			   joinColumns = { @JoinColumn(name = "USERS_USER_ID") }, 
			   inverseJoinColumns = { @JoinColumn(name = "EVENTS_EVENT_ID") })
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "USERS_has_ACTIVITIES", 
			   joinColumns = { @JoinColumn(name = "USERS_USER_ID") }, 
			   inverseJoinColumns = { @JoinColumn(name = "ACTIVITIES_ACTIVITY_ID") })
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User)obj;
			return getUserId() == user.getUserId();
		} 
		return false;
	}
}
