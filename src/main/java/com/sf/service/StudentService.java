package com.sf.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Student;
import com.sf.bean.Trip;
import com.sf.dao.StudentDao;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;

	public void saveEntry(Student student) {
		studentDao.save(student);
	}

	public Iterable<Student> getRecords() {
		return studentDao.findAll();
	}
	
	public Student getRecord(int id) {
		return studentDao.findById(id).get();
	}
	
	public Student findObjectById(int id) {
		return studentDao.findObjectById(id);
	}
	
	public List<Map<String,Object>> getStudentMappingByTripId(int tripId){
		return studentDao.getStudentMappingByTripId(tripId);
		
	}

	public Map<String, Object> getPickupTimeByStudentandTripId(int studentId, int tripId) {
		// TODO Auto-generated method stub
		return studentDao.getPickupTimeByStudentandTripId(studentId,tripId);
	}
	
	public Map<String, Object> getDropTimeByStudentandTripId(int studentId, int tripId) {
		// TODO Auto-generated method stub
		return studentDao.getDropTimeByStudentandTripId(studentId,tripId);
	}

}
