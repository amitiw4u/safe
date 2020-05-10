package com.sf.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sf.bean.GPS;

@Repository
public interface GPSDao extends PagingAndSortingRepository<GPS, Integer>{

}
