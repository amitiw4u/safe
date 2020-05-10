package com.sf.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sf.bean.School;

@Repository
public interface SchoolDao extends CrudRepository<School, Integer> {

}
