package com.katbutler.encore.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import com.katbutler.encore.common.Messages;
import com.katbutler.encore.logging.EncoreLogger;
import com.katbutler.encore.model.Event;
import com.katbutler.encore.model.User;

public class HibernateDatabaseEventManager extends AbstractHibernateDatabaseManager {

	private static String EVENTS_TABLE_NAME = "EVENTS";
	private static String EVENTS_JOIN_TABLE_NAME = "USERS_has_EVENTS";
	private static String EVENTS_CLASS_NAME = "Event";
	
	
	
	private static String SELECT_ALL_EVENTS = "from " + EVENTS_CLASS_NAME + " as event";
	private static String SEARCH_ALL_EVENTS_TITLE_LIKE = SELECT_ALL_EVENTS + "where event.title like '%s%'";
	
	private static String SELECT_EVENT_WITH_ID = "from "
			+ EVENTS_CLASS_NAME + " as event where event.eventID = :id order by eventID";
	
	private static String SELECT_EVENTS_WITH_ID = "from "
			+ EVENTS_CLASS_NAME + " as event where event.id = :id";
	
	
	private static String SELECT_USER_WITH_EMAIL_PASSWORD = "from "
			+ EVENTS_CLASS_NAME
			+ " as event where event.email = :email and event.password = :password";

	private static final String DROP_TABLE_SQL = "drop table "
			+ EVENTS_TABLE_NAME + ";";
	
	private static final String DROP_JOIN_TABLE_SQL = "drop table "
			+ EVENTS_JOIN_TABLE_NAME + ";";
	
	private static String SELECT_NUMBER_USERS = "select count (*) from "
		+ EVENTS_CLASS_NAME;
	
	private static final String CREATE_TABLE_SQL = "create table " + EVENTS_TABLE_NAME + "(USER_ID int primary key,"
			+ "FIRST_NAME varchar(45), LAST_NAME varchar(45), EMAIL varchar(255), PICTURE_URL varchar(2000), "
			+ "BIO varchar(200));";
	
	private static final String CREATE_JOIN_TABLE_SQL = "create table " + EVENTS_JOIN_TABLE_NAME + "(USER_ID_FK int, FRIEND_ID_FK);";
	
	private static final String METHOD_GET_N_USERS = "getNEventsStartingAtIndex";
	
	private static HibernateDatabaseEventManager manager;

	HibernateDatabaseEventManager() {
		super();
	}
	

	/**
	 * Returns default instance.
	 * 
	 * @return
	 */
	public static HibernateDatabaseEventManager getDefault() {
		
		if (manager == null) {
			manager = new HibernateDatabaseEventManager();
		}
		return manager;
	}

	public String getClassName() {
		return EVENTS_CLASS_NAME;
	}


	@Override
	public boolean setupTable() {
		HibernateUtil.executeSQLQuery(DROP_TABLE_SQL);
		HibernateUtil.executeSQLQuery(DROP_JOIN_TABLE_SQL);
		HibernateUtil.executeSQLQuery(CREATE_JOIN_TABLE_SQL);
		return HibernateUtil.executeSQLQuery(CREATE_TABLE_SQL);
	}


	/**
	 * Adds given object (user) to the database 
	 */
	public synchronized boolean add(Object object) {
		Transaction transaction = null;
		Session session = null;
		Event event = (Event) object;
		
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(SELECT_EVENTS_WITH_ID);
			query.setParameter("id", event.getEventID());
			@SuppressWarnings("unchecked")
			List<Event> events = query.list();

			if (!events.isEmpty()) {
				return false;
			}
				
			session.save(event);
			transaction.commit();
			return true;

		} catch (HibernateException exception) {
			EncoreLogger.getDefault().severe(this, Messages.METHOD_ADD_EVENT,
					"error.addEventToDatabase", exception);

			rollback(transaction);
			return false;
		} finally {
			closeSession();
		}
	}

	
	/**
	 * Updates given object (user).
	 * 
	 * @param object
	 * @return
	 */
	public synchronized boolean update(Event user) {
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
	public synchronized boolean delete(Event user){
		
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
			EncoreLogger.getDefault().severe(this, Messages.METHOD_DELETE_EVENT, Messages.HIBERNATE_FAILED, exception);
			return errorResult;
		}	
		catch (RuntimeException exception) {
			rollback(transaction);
			EncoreLogger.getDefault().severe(this, Messages.METHOD_DELETE_EVENT, Messages.GENERIC_FAILED, exception);
			return errorResult;
		}
		finally {
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
	public synchronized Event getEventById(Integer id) {
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(SELECT_EVENT_WITH_ID);
			query.setParameter("id", id);
			List<Event> events = query.list();
			transaction.commit();

			if (events.isEmpty()) {
				return null;
			} else {
				Event event = events.get(0);
				return event;
			}
		} catch (HibernateException exception) {
			exception.printStackTrace();
			EncoreLogger.getDefault().severe(this,
					Messages.METHOD_GET_EVENT_BY_EMAIL_ADDRESS,
					"error.getEventByEmailAddressInDatabase", exception);
			return null;
		} finally {
			closeSession();
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
	public synchronized  List<Event> getNEventsStartingAtIndex(int index, int max) {
		List<Event> errorResult = null;
		Session session = null;
		try {
			session = HibernateUtil.getCurrentSession();
			Query query = session.createQuery(SELECT_ALL_EVENTS);
			query.setFirstResult(index);
			query.setMaxResults(max);
			List<Event> users = query.list();

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
	
	public synchronized boolean addEventForUser(int userId, int eventId) {
		User user = HibernateDatabaseUserManager.getDefault().getUserById(userId);
		Event event = getEventById(eventId);
		
		user.getEvents().add(event);
		
		return HibernateDatabaseUserManager.getDefault().update(user);
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
	public synchronized  List<Event> searchEventsWithTitleSimilar(String search, int max) {
		List<Event> errorResult = null;
		Session session = null;
		try {
			session = HibernateUtil.getCurrentSession();
//			Query query = session.createQuery(SEARCH_ALL_EVENTS_TITLE_LIKE);
//			query.setFirstResult(0);
//			query.setMaxResults(max);
//			query.setParameter(0, search);
			Criteria criteria = session.createCriteria(Event.class).add(Restrictions.like("title", "%" + search + "%"));
			
			List<Event> users = criteria.list();

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
		return EVENTS_TABLE_NAME;
	}
	
	
	/**
	 * Returns number of users.
	 * 
	 * Upon error returns empty list.
	 * 
	 * @param a charge status
	 * @return
	 */
	public synchronized int getNumberOfEvents() {
		
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
