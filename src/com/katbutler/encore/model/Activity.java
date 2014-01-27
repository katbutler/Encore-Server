package com.katbutler.encore.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.katbutler.encore.model.common.ActivityType;
import com.katbutler.encore.xml.XmlDocument;
import com.katbutler.encore.xml.XmlElement;

@Entity
@Table(name="ACTIVITIES")
@XmlDocument(listRootName="Activities", rootName="Activity")
public class Activity implements Comparable<Activity> {
	
	@XmlElement(name="ActivityId")
	private Integer id;
	
	@XmlElement(name="Description")
	private String description;
	
	@XmlElement(name="ActivityDate")
	private Date activityDate;
	
	private ActivityType activityType;
	
	@XmlElement(name="ActivityType")
	private String activityTypeStr = "";
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ACTIVITY_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "ACTIVITY_DATE", nullable = false)
	public Date getActivityDate() {
		return activityDate;
	}
	
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	@Column(name = "ACTIVITY_TYPE", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
		activityTypeStr = this.activityType.toString();
	}

	@Override
	public int compareTo(Activity o) {
		return this.getActivityDate().compareTo(o.getActivityDate());
	}
	
}
