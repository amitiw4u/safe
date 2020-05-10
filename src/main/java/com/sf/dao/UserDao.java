package com.sf.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	@Query(value = "Select * from smartBus_users where userName=:userName AND password=:password", nativeQuery = true)
	User findbyUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

}
