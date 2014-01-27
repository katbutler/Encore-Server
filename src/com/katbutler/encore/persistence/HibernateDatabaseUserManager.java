package com.katbutler.encore.persistence;

import java.util.List;
//import org.apache.struts.action.ActionError;
//import org.apache.struts.action.ActionErrors;
//import org.apache.struts.action.ActionMessage;
//import org.apache.struts.action.ActionMessages;


import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;

import com.katbutler.encore.common.Messages;
import com.katbutler.encore.logging.EncoreLogger;
import com.katbutler.encore.model.Event;
import com.katbutler.encore.model.User;

public class HibernateDatabaseUserManager extends AbstractHibernateDatabaseManager {

	private static String USER_TABLE_NAME = "USERS";
	private static String USER_JOIN_TABLE_NAME = "FRIENDS";
	private static String USER_CLASS_NAME = "User";

	private static String SELECT_ALL_USERS = "from "
			+ USER_CLASS_NAME + " as user";
	private static String SELECT_USER_WITH_EMAIL_ADDRESS = "from "
			+ USER_CLASS_NAME + " as user where user.email = :email";
	
	private static String SELECT_USER_WITH_ID = "from "
			+ USER_CLASS_NAME + " as user where user.userId = :id order by userId";
	
	private static String SELECT_USER_WITH_EMAIL_PASSWORD = "from "
			+ USER_CLASS_NAME
			+ " as user where user.email = :email and user.password = :password";

	private static final String DROP_TABLE_SQL = "drop table "
			+ USER_TABLE_NAME + ";";
	
	private static final String DROP_JOIN_TABLE_SQL = "drop table "
			+ USER_JOIN_TABLE_NAME + ";";
	
	private static String SELECT_NUMBER_USERS = "select count (*) from "
		+ USER_CLASS_NAME;
	
	private static final String CREATE_TABLE_SQL = "create table " + USER_TABLE_NAME + "(USER_ID int primary key,"
			+ "FIRST_NAME varchar(45), LAST_NAME varchar(45), EMAIL varchar(255), PICTURE_URL varchar(2000), "
			+ "BIO varchar(200));";
	
	private static final String CREATE_JOIN_TABLE_SQL = "create table " + USER_JOIN_TABLE_NAME + "(USER_ID_FK int, FRIEND_ID_FK);";
	
	private static final String METHOD_GET_N_USERS = "getNUsersStartingAtIndex";

	private static HibernateDatabaseUserManager manager;

	HibernateDatabaseUserManager() {
		super();
	}

	/**
	 * Returns default instance.
	 * 
	 * @return
	 */
	public static HibernateDatabaseUserManager getDefault() {
		
		if (manager == null) {
			manager = new HibernateDatabaseUserManager();
		}
		return manager;
	}

	public String getClassName() {
		return USER_CLASS_NAME;
	}

	@Override
	public boolean setupTable() {
		HibernateUtil.executeSQLQuery(DROP_TABLE_SQL);
		HibernateUtil.executeSQLQuery(DROP_JOIN_TABLE_SQL);
		HibernateUtil.executeSQLQuery(CREATE_JOIN_TABLE_SQL);
		return HibernateUtil.executeSQLQuery(CREATE_TABLE_SQL);
	}
	
	
	public synchronized boolean followNewUser(int userId, int userToFollowId) {
		User user = HibernateDatabaseUserManager.getDefault().getUserById(userId);
		User userToFollow = HibernateDatabaseUserManager.getDefault().getUserById(userToFollowId);

		user.getFollowers().add(userToFollow);
		
		return HibernateDatabaseUserManager.getDefault().update(user);
	}
	

//
//	/**
//	 * Adds given object (user) to the database 
//	 */
//	public synchronized boolean add(Object object) {
//		Transaction transaction = null;
//		Session session = null;
//		User user = (User) object;
//		
//		try {
//			session = HibernateUtil.getCurrentSession();
//			transaction = session.beginTransaction();
//			Query query = session.createQuery(SELECT_USER_WITH_EMAIL_ADDRESS);
//			query.setParameter("email", user.getEmail());
//			@SuppressWarnings("unchecked")
//			List<User> users = query.list();
//
//			if (!users.isEmpty()) {
//				return false;
//			}
//				
//			session.save(user);
//			transaction.commit();
//			return true;
//
//		} catch (HibernateException exception) {
//			EncoreLogger.getDefault().severe(this, Messages.METHOD_ADD_USER,
//					"error.addUserToDatabase", exception);
//
//			rollback(transaction);
//			return false;
//		} finally {
//			closeSession();
//		}
//	}

	
	/**
	 * Updates given object (user).
	 * 
	 * @param object
	 * @return
	 */
	public synchronized boolean update(User user) {
		boolean result = super.update(user);	
		return result;
	}

	
	/**
	 * Deletes given user from the database.
	 * Returns true if successful, otherwise returns false.
	 * 
	 * @param object
	 * @return
	 */
	public synchronized boolean delete(User user){
		
		Session session = null;
		Transaction transaction = null;
		boolean errorResult = false;
		
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			session.delete(user);
			transaction.commit();
			return true;
		}
		catch (HibernateException exception) {
			rollback(transaction);
			EncoreLogger.getDefault().severe(this, Messages.METHOD_DELETE_USER, Messages.HIBERNATE_FAILED, exception);
			return errorResult;
		}	
		catch (RuntimeException exception) {
			rollback(transaction);
			EncoreLogger.getDefault().severe(this, Messages.METHOD_DELETE_USER, Messages.GENERIC_FAILED, exception);
			return errorResult;
		}
		finally {
			closeSession();
		}
	}
	
	
	/**
	 * Deletes user with given id from the database.
	 * Returns true if user deleted, otherwise return false.
	 * Upon error adds specific error to given actionErrors.
	 * 
	 * @param id
	 * @param actionErrors
	 * @return
	 
	public synchronized boolean delete(String id, ActionErrors actionErrors) {
		
		Transaction transaction = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(DELETE_USER_WITH_PRIMARY_KEY);
			query.setParameter(0, id);
			int rowCount = query.executeUpdate();
			transaction.commit();
			if (rowCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (HibernateException exception) {
			actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.addDeletingUserInDatabase"));
			BookingLogger.getDefault().severe(this,
					Messages.METHOD_DELETE_USER,
					"error.addDeletingUserInDatabase", exception);

			rollback(transaction);
			return false;
		} finally {
			closeSession();
		}
	}
	 */
	
	/**
	 * Returns user from database based on given email address.
	 * If not found returns null.
	 * Upon error returns null.
	 * 
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized User getUserByEmailAddress(String email) {
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(SELECT_USER_WITH_EMAIL_ADDRESS);
			query.setParameter("email", email);
			List<User> users = query.list();
			transaction.commit();

			if (users.isEmpty()) {
				return null;
			} else {
				User user = users.get(0);
				return user;
			}
		} catch (HibernateException exception) {
			exception.printStackTrace();
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_USER_BY_EMAIL_ADDRESS,
					"error.getUserByEmailAddressInDatabase", exception);
			return null;
		} finally {
			closeSession();
		}
	}

	/**
	 * Returns user from database based on given email and password.
	 * If not found returns null.
	 * Upon error returns null.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized User getUserByEmailAddressPasssword(String email, String password) {
		
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session
					.createQuery(SELECT_USER_WITH_EMAIL_PASSWORD);
			query.setParameter("email", email);
			query.setParameter("password", password);
			List<User> users = query.list();
			transaction.commit();

			if (users.isEmpty()) {
				return null;
			} else {
				User user = users.get(0);
				return user;
			}
		} catch (HibernateException exception) {
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_USER_BY_EMAIL_ADDRESS_PASSWORD,
					"error.getUserByEmailAddressInDatabase", exception);
			return null;
		} finally {
			closeSession();
		}
	}

	/**
	 * Returns user from the database with given id.
	 * Upon exception returns null.
	 * 
	 * @param id
	 * @return
	 */
	public synchronized User getUserById(Integer id) {
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(SELECT_USER_WITH_ID);
			query.setParameter("id", id);
			List<User> users = query.list();
			transaction.commit();

			if (users.isEmpty()) {
				return null;
			} else {
				User user = users.get(0);
				return user;
			}
		} catch (HibernateException exception) {
			exception.printStackTrace();
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_USER_BY_EMAIL_ADDRESS,
					"error.getUserByEmailAddressInDatabase", exception);
			return null;
		} finally {
			closeSession();
		}
	}
	
	
	
	
	
//	String stringQuery = 
//            "select " +
//                    "CU.Card as card, " +
//                    "CU.Fidely_Code as fidelyCode, "+
//                    "PI.Identity_Card as identityCard, " +
//                    "PI.Name as name, " +
//                    "PI.Surname as surname, " +
//                    "PI.Gender as gender, "+
//                    "AD.Zip as zip, " +
//                    "AD.Geo_Lat as geo_lat, " +
//                    "AD.Geo_Long as geo_long, "+
//                    "CO.City_Geo_Level as cityGeoLevel, "+
//                    "CA.Name as campaignName, "+
//                    "CU.Status as status, "+
//                    "Sum(MO.Charged_Points) as pointsCharged, "+
//                    "Sum(MO.Total_Money) as amountPurchase, "+
//                    "Count(MO.id) as amountTransWinner "+
//            "from Promotions_Winner WI "+ 
//            "join Customers CU "+
//              "on WI.Customer_id = CU.id "+
//            "join Personal_Info PI "+
//              "on CU.Personal_Info_Id = PI.id "+
//            "join Address AD "+
//              "on CU.Address_Id = AD.id "+
//            "join Countries CO "+
//              "on AD.country_id = CO.id "+
//            "join Campaigns CA "+
//              "on CU.Campaign_Id = CA.id "+
//            "join Movements MO "+
//              "on WI.Movement_Id = MO.id "+
//            "where WI.Promotion_Id = :pPromotionID "+
//            "group by "+
//              "WI.Customer_Id, CU.Card, CU.Fidely_Code, "+
//              "PI.Identity_Card, PI.Name, PI.Surname, PI.Gender, "+
//              "AD.Zip, AD.Geo_Lat, AD.Geo_Long, "+
//              "CO.City_Geo_Level, "+
//              "CU.Address_id, CA.Name, "+
//              "CU.Category_Id, "+
//              "CU.Status ";

//	
//	public synchronized List<User> getFriendsForUserId(Integer id) {
//		
//		Session session = null;
//		Transaction transaction = null;
//		try {
//			session = HibernateUtil.getCurrentSession();
//			transaction = session.beginTransaction();
//			
//			String strQuery = "select " +
//									"u2.userId," +
//									"u2.firstName," +
//									" u2.lastName, " + 
//									"u2.email, " +
//									"u2.picURL, " +
//									"u2.bio" +
//								"from " +
//									USER_CLASS_NAME + " u1, " +
//									USER_CLASS_NAME + " u2, " +
//									"(select " +
//										"f1.";
//									
////FROM
////	USERS u1,	
////	USERS u2,
////	(SELECT
////	  f1.USER_ID_FK,
////	  f1.FRIEND_ID_FK
////	FROM
////	  FRIENDS f1,
////	  FRIENDS f2
////	WHERE
////	  f1.USER_ID_FK = f2.FRIEND_ID_FK
////		AND
////	  f1.FRIEND_ID_FK = f2.USER_ID_FK) fr
////WHERE
////	u1.USER_ID = fr.USER_ID_FK
////		AND
////	u2.USER_ID = fr.FRIEND_ID_FK
////		AND
////	u1.USER_ID = 18;"
//			
//			Query query = session.createQuery(SELECT_USER_WITH_ID);
//			query.setParameter("id", id);
//			List<User> users = query.list();
//			transaction.commit();
//
//			if (users.isEmpty()) {
//				return null;
//			} else {
//				User user = users.get(0);
//				return user;
//			}
//		} catch (HibernateException exception) {
//			exception.printStackTrace();
//			EncoreLogger.getDefault().severe(this,
//					Messages.METHOD_GET_USER_BY_EMAIL_ADDRESS,
//					"error.getUserByEmailAddressInDatabase", exception);
//			return null;
//		} finally {
//			closeSession();
//		}
//	}

	/**
	 * Sets user in given form to the user found for form's email address and password.
	 * Returns true if successful, otherwise return false.
	 * 
	 * @param userForm
	 * @return
	 
	public synchronized boolean fillInFromEmailAddressAndPassword(
			UserForm userForm) {
		
		User user = getUserByEmailAddress(userForm.getEmailAddress());
		if ((user != null)
				&& (user.getPassword().equals(userForm.getPassword()))) {
			userForm.fillInFormWithUser(user);
			return true;
		} else {
			return false;
		}
	}
	 */
	
	/**
	 * Sets user in given form to the user found for form's phone number and password.
	 * Returns true if successful, otherwise return false.
	 * 
	 * @param userForm
	 * @return
	 
	public synchronized boolean fillInFromPhoneNumberAndPassword(
			UserForm userForm) {
		
		User user = getUserByEmail(userForm.getPhoneNumber());
		if ((user != null)
				&& (user.getPassword().equals(userForm.getPassword()))) {
			userForm.fillInFormWithUser(user);
			return true;
		} else {
			return false;
		}
	}
	 */
	
	/**
	 * Sets user in given form to the user found for form's selected phone number.
	 * Returns true if successful, otherwise return false.
	 * 
	 * @param userForm
	 * @return
	 
	public synchronized boolean fillInFromSelectedPhoneNumber(
			UserForm userForm) {
		
		User user = getUserByEmail(userForm
				.getSelectedPhoneNumber());
		if (user != null) {
			userForm.fillInFormWithUser(user);
			return true;
		} else {
			return false;
		}
	}
	 */
	
	/**
	 * Sets user in given form to the user found for form's selected email address.
	 * Returns true if successful, otherwise return false.
	 * 
	 * @param userForm
	 * @return
	 
	public synchronized boolean fillInFromEmailAddress(UserForm userForm) {
		
		User user = getUserByEmailAddress(userForm
				.getSelectedEmailAddress());
		if (user != null) {
			userForm.fillInFormWithUser(user);
			return true;
		} else {
			return false;
		}

	}
	 */
	
	/**
	 * Sets email addresses in the given user form to email addresses found
	 * in the user table.
	 * 
	 * @param userForm
	 
	@SuppressWarnings("unchecked")
	public synchronized void fillInWithEmailAddresses(UserForm userForm) {
		
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();

			Query query = session
					.createQuery(SELECT_ALL_USER_EMAIL_ADDRESSES);
			List<String> addresses = query.list();
			transaction.commit();

			Iterator<String> iterator = addresses.iterator();
			Collection<String> collection = new ArrayList<String>(addresses
					.size());
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
			userForm.setEmailAddresses(collection);
		} catch (HibernateException exception) {
			BookingLogger.getDefault().severe(this,
					Messages.METHOD_FILL_IN_WITH_EMAIL_ADDRESS,
					"error.fillInWithEmailAddresses", exception);
			rollback(transaction);
		} finally {
			closeSession();
		}
	}
	 */
	
	/**
	 * Returns user with given email and password from the database. 
	 * If not found returns null.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User getUser(String email, String password) {
		
		User user = getUserByEmailAddress(email);
		if ((user != null) && (user.getPassword().equals(password))) {
			return user;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns users, 
	 * from database.
	 * If not found returns null.
	 * Upon error returns null.
	 * 
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized  List<User> getNUsersStartingAtIndex(int index, int max) {
		List<User> errorResult = null;
		Session session = null;
		try {
			session = HibernateUtil.getCurrentSession();
			Query query = session.createQuery(SELECT_ALL_USERS);
			query.setFirstResult(index);
			query.setMaxResults(max);
			List<User> users = query.list();

			return users;
		} catch (ObjectNotFoundException exception) {
			EncoreLogger.getDefault().severe(this, METHOD_GET_N_USERS,
					Messages.OBJECT_NOT_FOUND_FAILED, exception);
			return errorResult;
		}  catch (JDBCConnectionException exception) {
			HibernateUtil.clearSessionFactory();
			EncoreLogger.getDefault().severe(this, METHOD_GET_N_USERS,
					Messages.HIBERNATE_CONNECTION_FAILED, exception);
			return errorResult;
		} catch (HibernateException exception) {
			EncoreLogger.getDefault().severe(this, METHOD_GET_N_USERS,
					Messages.HIBERNATE_FAILED, exception);
			return errorResult;
		} catch (RuntimeException exception) {
			EncoreLogger.getDefault().severe(this, METHOD_GET_N_USERS,
					Messages.GENERIC_FAILED, exception);
			return errorResult;
		} finally {
			closeSession();
		}
	}
	
	public String getTableName() {
		return USER_TABLE_NAME;
	}
	
	
	/**
	 * Returns number of users.
	 * 
	 * Upon error returns empty list.
	 * 
	 * @param a charge status
	 * @return
	 */
	public synchronized int getNumberOfUsers() {
		
		Session session = null;
		Long aLong;

		try {
			session = HibernateUtil.getCurrentSession();
			Query query = session
					.createQuery(SELECT_NUMBER_USERS);
			aLong = (Long) query.uniqueResult();
			return aLong.intValue();
		} catch (ObjectNotFoundException exception) {
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_NUMBER_OF_USERS,
					Messages.OBJECT_NOT_FOUND_FAILED, exception);
			return 0;
		} catch (HibernateException exception) {
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_NUMBER_OF_USERS,
					Messages.HIBERNATE_FAILED, exception);
			return 0;
		} catch (RuntimeException exception) {
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_NUMBER_OF_USERS,
					Messages.GENERIC_FAILED, exception);
			return 0;
		} finally {
			closeSession();
		}
	}

}

