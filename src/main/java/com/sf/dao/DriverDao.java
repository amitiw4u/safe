package com.sf.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sf.bean.Driver;

@Repository
public interface DriverDao extends CrudRepository<Driver, Integer> {
	
}
