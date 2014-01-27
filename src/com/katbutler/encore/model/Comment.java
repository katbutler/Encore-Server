package com.katbutler.encore.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENTS")
public class Comment {

	private Integer eventItemID;
	private EventItem eventItem;
	private String commentText;
	private Date commentDate;
	
	public Comment() {
	
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "COMMENT_ID", unique = true, nullable = false)
	public Integer getEventItemID() {
		return eventItemID;
	}
	
	public void setEventItemID(Integer eventItemID) {
		this.eventItemID = eventItemID;
	}
	
	@ManyToOne
	@JoinColumn(name = "EVENT_ITEM_FK")
	public EventItem getEventItem() {
		return eventItem;
	}
	
	public void setEventItem(EventItem eventItem) {
		this.eventItem = eventItem;
	}
	
	@Column(name = "COMMENT_TEXT")
	public String getCommentText() {
		return commentText;
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Column(name = "COMMENT_DATE")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	
}
