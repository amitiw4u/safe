package com.sf.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.User;

@Repository
public interface AuthDao extends PagingAndSortingRepository<User, Long>{

	@Query(value="select PASSWORD(':pwd') as pwd", nativeQuery = true)
	String getPassWord( @Param("pwd") String pwd);

}
