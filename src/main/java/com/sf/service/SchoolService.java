package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.School;
import com.sf.dao.SchoolDao;

@Service
public class SchoolService {

	@Autowired
	private SchoolDao schoolDao;

	public void saveEntry(School school) {
		schoolDao.save(school);
	}

	public School getRecord(int id) {
		return schoolDao.findById(id).get();
	}

	public Iterable<School> getRecords() {
		return schoolDao.findAll();
	}

}
