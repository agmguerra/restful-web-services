package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();
	private static int userCount = 3;
	
	static {
		users.add(new User(1, new Date(), "Adam"));
		users.add(new User(2, new Date(), "Eve"));
		users.add(new User(3,  new Date(), "Jack"));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User user:users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		
		Iterator<User> it = users.iterator();
		
		while(it.hasNext()) {
			User user = it.next();
			if (user.getId() == id) {
				it.remove();
				return user;
			}
		}
		return null;
	}

	
}
