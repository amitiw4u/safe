package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.User;
import com.sf.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void saveEntry(User parent)
	{
		userDao.save(parent);
	}

	public User getRecord(int id) {
		return userDao.findById(id).get();
	}
	
	public Iterable<User> getRecords() {
		return userDao.findAll();
	}
	
	public User getUserAndPwd(String userName, String password) {
		return userDao.findbyUserNameAndPassword(userName, password);
	}
}
