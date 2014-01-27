package com.katbutler.encore.testing;

import org.junit.Test;

import com.katbutler.encore.model.User;
import com.katbutler.encore.xml.XmlWriter;

public class UserModelTest {

	@Test
	public void testUserToXML() {
		User user = new User();
		
		user.setFirstName("Billy");
		user.setLastName("Bob");
		user.setEmail("bbob@gmail.com");
		
		XmlWriter<User> userXmlWriter = new XmlWriter<User>(User.class);
		
		System.out.println(userXmlWriter.writeXml(user));
	}

}
