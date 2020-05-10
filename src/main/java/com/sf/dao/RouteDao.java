package com.sf.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sf.bean.Route;

@Repository
public interface RouteDao extends CrudRepository<Route, Integer> {

}
