package com.interactivebrokers.trader.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.interactivebrokers.trader.web.dao.UsersDao;
import com.interactivebrokers.trader.web.model.Instrument;
import com.interactivebrokers.trader.web.model.User;

@Service("usersService")
public class UsersService {
	
	private UsersDao usersDao;
	
	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	
	public void create(User user) {
		usersDao.create(user);
	}

	public User getUser(String userName){
		return usersDao.getUser(userName);
	}
	
	public boolean exists(String username) {
		return usersDao.exists(username);
	}

	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}


	public List<Instrument> getUserInstrumentList(int userId) {
		return usersDao.getUserInstrumentList(userId);
	}

}
