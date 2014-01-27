package com.katbutler.encore.actions;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.katbutler.encore.model.Activity;
import com.katbutler.encore.model.EncoreError;
import com.katbutler.encore.model.Event;
import com.katbutler.encore.model.User;
import com.katbutler.encore.model.common.ActivityType;
import com.katbutler.encore.persistence.HibernateDatabaseUserManager;
import com.katbutler.encore.xml.XmlWriter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * UsersAction handles all User based requests
 * 
 * @author Kat Butler
 *
 */
public class UsersAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = -7441783386128241976L;
	private static final String ID = "id";
	private static final String LAT = "lat";
	private static final String LNG = "lng";
	private static final String BIO = "bio";
	private static final String PIC_URL = "picurl";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String FIRST_NAME = "fname";
	private static final String LAST_NAME = "lname";
	HibernateDatabaseUserManager userManager = HibernateDatabaseUserManager.getDefault();
	
	private String xml = "";
	private String errorXml = "";
	private String id;
	private String email;
	private String fname;
	private String lname;
	private String password;
	private String bio;
	private String picurl;
	private String lat;
	private String lng;
	private String newUserEmail;
	
	
	private HttpServletRequest request;
	
	public String getUser() throws Exception {
		
		email = getServletRequest().getParameter(EMAIL); //get user email from parameter
		User user = userManager.getUserByEmailAddress(email); //passing in email parameter to get user
		
		if (user == null) {
			return ERROR;
		}
		
		setXml(new XmlWriter<User>(User.class).writeXml(user)); //converting user obj to xml
		
		return SUCCESS;
	}
	
	public String updateUser() throws Exception {
		email = getServletRequest().getParameter(EMAIL); 
		fname = getServletRequest().getParameter(FIRST_NAME);
		lname = getServletRequest().getParameter(LAST_NAME);
		picurl = getServletRequest().getParameter(PIC_URL);
		bio = getServletRequest().getParameter(BIO);
		
		User user = userManager.getUserByEmailAddress(email);
		
		//set all fields
		if (!isBlank(bio))
			user.setBio(bio);
		if (!isBlank(picurl))
			user.setPicURL(picurl);
		if (!isBlank(fname))
			user.setFirstName(fname);
		if (!isBlank(lname))
			user.setLastName(lname);
		
		if(!userManager.update(user)) {
			EncoreError error = new EncoreError("Could not update user");
			
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error)); //converting user obj to xml
			return ERROR;
		} 
		
		setXml(new XmlWriter<User>(User.class).writeXml(user)); //converting user obj to xml
		
		return SUCCESS;
	}
	
	private boolean isBlank(String value) {
		return (bio.equals("") || bio.equals("null"));
	}
	
	public String addUser() throws Exception {
		System.out.println("About to add new user");
		User user = new User();
		
		email = getServletRequest().getParameter(EMAIL); 
		password = getServletRequest().getParameter(PASSWORD);
		fname = getServletRequest().getParameter(FIRST_NAME);
		lname = getServletRequest().getParameter(LAST_NAME);
		
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(fname);
		user.setLastName(lname);
		
		if(userManager.add(user)) {
			System.out.println("Added New User");
			setXml(new XmlWriter<User>(User.class).writeXml(user)); //converting user obj to xml
			return SUCCESS;
		}
		
		EncoreError error = new EncoreError(email + " user already exists on the Encore Server.");
		setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
		
		System.out.println("Could not add user");
		return ERROR;
	}
	
	public String addPicUrlToUser() throws Exception {
		email = getServletRequest().getParameter(EMAIL); 
		picurl = getServletRequest().getParameter(PIC_URL);
		
		User user = userManager.getUserByEmailAddress(email);
		
		if (user != null) {
			user.setPicURL(picurl);
			userManager.update(user);
			setXml(new XmlWriter<User>(User.class).writeXml(user)); //converting user obj to xml
			return SUCCESS;
		} else {
			EncoreError error = new EncoreError("User with email " + email + " does not exists");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
		}
		
		return ERROR;
	}
	
	
	public String getAllUsers() throws Exception {
		
		List<User> users = userManager.getNUsersStartingAtIndex(0, 1000);
		
		if (users != null) {
			setXml(new XmlWriter<User>(User.class).writeArrayXml(users));
		} else {
			EncoreError error = new EncoreError("Could not get users from database.");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	public String getFollowersForUser() throws Exception {
		id = getServletRequest().getParameter(ID);
		
		User user = userManager.getUserById(Integer.parseInt(id));
		
		if (user != null) {
			setXml(new XmlWriter<User>(User.class).writeArrayXml(user.getFollowers()));
		} else {
			EncoreError error = new EncoreError("User with ID " + id + " does not exists");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	

	public String setHomeLocationForUser() throws Exception {
		id = getServletRequest().getParameter(ID);
		lat = getServletRequest().getParameter(LAT);
		lng = getServletRequest().getParameter(LNG);

		User user = userManager.getUserById(Integer.parseInt(id));
		
		if (user != null) {
			user.setHomeLatitude(Double.parseDouble(lat));
			user.setHomeLongitude(Double.parseDouble(lng));
			if (userManager.update(user)) {
				setXml(new XmlWriter<User>(User.class).writeXml(user));
			} else {
				EncoreError error = new EncoreError("Could not update user " + user.getEmail());
				setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
				return ERROR;
			}
		}
		
		return SUCCESS;
	}


	public String getEventsForUser() throws Exception {
		id = getServletRequest().getParameter(ID);
		
		User user = userManager.getUserById(Integer.parseInt(id));
		
		if (user != null) {
			setXml(new XmlWriter<Event>(Event.class).writeArrayXml(user.getEvents()));
		} else {
			EncoreError error = new EncoreError("User with ID " + id + " does not exists");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String followNewUser() throws Exception {
		id = getServletRequest().getParameter(ID);
		newUserEmail = getServletRequest().getParameter("newUserEmail");
		
		User user = userManager.getUserById(Integer.parseInt(id));
		User userToFollow = userManager.getUserByEmailAddress(newUserEmail);
		
		if(userManager.followNewUser(user.getUserId(), userToFollow.getUserId())) {
			Activity activity = new Activity();
			activity.setActivityType(ActivityType.NEW_FOLLOWER);
			activity.setActivityDate(new Date());
			activity.setDescription(user.getFirstName() + ActivityType.NEW_FOLLOWER.getDisplayString() + userToFollow.getFirstName());
			user.getActivities().add(activity);
			userManager.add(activity);
			userManager.update(user);
			
			setXml(new XmlWriter<User>(User.class).writeXml(userToFollow));
			return SUCCESS;
		} else {
			EncoreError error = new EncoreError("User with ID " + userToFollow.getUserId() + " could not be followed");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			
			return ERROR;
		}
	}
	
	
	public String getActivitiesForUser() throws Exception {
		id = getServletRequest().getParameter(ID);
		
		User user = userManager.getUserById(Integer.parseInt(id));
		
		if (user != null) {
			TreeSet<Activity> feedList = new TreeSet<Activity>();
			for (User u: user.getFollowers()) {
				feedList.addAll(u.getActivities());
			}
			
			
			setXml(new XmlWriter<Activity>(Activity.class).writeArrayXml(feedList));
		} else {
			EncoreError error = new EncoreError("User with ID " + id + " does not exists");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			
			return ERROR;
		}
		
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
	
	public String getErrorXml() {
		return errorXml;
	}

	public void setErrorXml(String errorXml) {
		this.errorXml = errorXml;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewUserEmail() {
		return newUserEmail;
	}

	public void setNewUserEmail(String newUserEmail) {
		this.newUserEmail = newUserEmail;
	}
	
	
}
