package com.sf.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sf.bean.RFID;
import com.sf.bean.Student;
import com.sf.bean.TripMap;
import com.sf.dao.CardMappingDao;
import com.sf.dao.RFIDDao;
import com.sf.dao.StudentDao;
import com.sf.dao.TripDao;
import com.sf.dao.TripMapDao;
import com.sf.exception.SafeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RFIDService {
	
	@Autowired
	private RFIDDao rfidDao;
	
	@Autowired
	private CardMappingDao cardMappingDao;
	
	@Autowired
	private TripMapDao tripMapDao;
	
	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private StudentDao studentDao;
	
	public RFID saveEntry(RFID rfid) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String cardIdCheckIn = rfid.getCardId();
		List<Map<String, Object>> cardMappingData = cardMappingDao.findAllByCardId(cardIdCheckIn);
		if (cardMappingData.size() == 0) {
			throw new SafeException("Please check your card Mapping for "+cardIdCheckIn+" card");
		}
		Map<String, Object> row2 = cardMappingData.get(0);
		int loginId = (int) row2.get("loginId");		
		String loginType = (String) row2.get("type");
		int busId = rfid.getBusId();

		if (loginType == null) {
			throw new SafeException("Login Type not Maintained for "+cardIdCheckIn+" card");
		}else if (loginType.equals("A")) {
			
			List<Map<String, Object>> checkInListforAtten = rfidDao.getTodayCheckInData(rfid.getCardId());
			List<Map<String, Object>> openTripListForAtten = tripMapDao.getOpenTripForBus(busId);	
			List<Map<String, Object>> tripForConductor = tripDao.getTripForConductorToday(loginId);
			
			if (tripForConductor.size() == 0) {
				throw new SafeException("Trip is not assigned to Conductor For "+timestamp);
			}
			
			RFID managedRFID = rfid;
			
			if(checkInListforAtten.size() == 0) {
				if (openTripListForAtten.size() == 0) {
					log.info("Driver has not Opened any trip Mapping..");
					log.info("No Entry for Attendant Check In For Today..");
					// from conductorId get trip
					// do the attendant check in and create the trip Map
					Map<String, Object> tripRow = tripForConductor.get(0);
					TripMap tripMapforAttendant = new TripMap((int)tripRow.get("id"),busId,loginId,-1,timestamp,1);
					rfid.setStatus(1);
					rfid.setTimeStamp(timestamp);
					managedRFID = rfidDao.save(rfid);
					if(managedRFID.getStatus() == 1) {		
						tripMapDao.save(tripMapforAttendant);
						return managedRFID;
					}
					return rfid;
				}else {
					log.info("Driver has Opened trip Mapping..");
					log.info("No Entry for Attendant Check In For Today..");
					// do the attendant check in and update the trip Map
					
					Map<String, Object> tripMapObject = openTripListForAtten.get(0);

					int id = (int)tripMapObject.get("id");
					Map<String, Object> tripRow = tripForConductor.get(0);
					int tripId = (int)tripRow.get("id");
					System.out.println(tripId);
					TripMap tripMapforAttendant = tripMapDao.findObjectById(id);
					rfid.setStatus(1);
					rfid.setTimeStamp(timestamp);
					managedRFID = rfidDao.save(rfid);
					if(managedRFID.getStatus() == 1) {
						tripMapforAttendant.setConductorId(loginId);
						tripMapforAttendant.setTripId(tripId);
						tripMapDao.save(tripMapforAttendant);
							return managedRFID;
						}				
				}
			}else {
				System.out.println("I am here..");
				Map<String, Object> attendanttLogin = checkInListforAtten.get(0);
				int status = (int)attendanttLogin.get("status");
				Timestamp timeStart = timestamp;
				Timestamp timeEnd = (Timestamp)attendanttLogin.get("timeStamp");
				if (compareTwoTimeStamps(timeStart,timeEnd) < 1.0) {
					//do Nothing
					throw new SafeException("Please wait for at least 1 minute..");
					//return rfid;
				}
				if (openTripListForAtten.size() == 0) {
					System.out.println("I am here..3");
					log.info("Previous Entry for Attendant Check In/Out For Today..");
					log.info("Driver has Not Opened trip Mapping..");
					if(status == 1) {
						log.info("It is safe to do check out..");
						rfid.setStatus(0);
						rfid.setTimeStamp(timestamp);
						managedRFID = rfidDao.save(rfid);	
						return managedRFID;
					}else {
						System.out.println("I am here..2");
						log.info("Do Attendant check in..");
						log.info("Create new Trip Mapping from Attendant..");
						rfid.setStatus(1);
						rfid.setTimeStamp(timestamp);
						Map<String, Object> tripRow = tripForConductor.get(0);
						int tripId = (int)tripRow.get("id");
						TripMap tripMapforAttendant = new TripMap(tripId,busId,loginId,-1,timestamp,1);
						managedRFID = rfidDao.save(rfid);	
						if(managedRFID.getStatus() == 1) {
							tripMapDao.save(tripMapforAttendant);
							return managedRFID;
						}
					}
					
				}else {
					log.info("Previous Entry for Attendant Check In/Out For Today..");
					log.info("Driver has Opened trip Mapping..");
					System.out.println("I am here..4");
					if(status == 1) {
						// case for check out
						// student should not be in bus
						// close the trip mapping
						boolean flag = false;
						Map<String, Object> tripRow = tripForConductor.get(0);
						int tripId = (int)tripRow.get("id");
						
						List<Map<String, Object>> studentsForTrip = studentDao.getStudentsWithTripForToday(tripId);
						if (studentsForTrip.size() != 0) {
							for(int i=0;i<studentsForTrip.size();i++) {
								Map<String, Object> studentV3Map= studentsForTrip.get(i);
								int studentId = (int) studentV3Map.get("id");
								Student studentObj = studentDao.findById(studentId).get();
								String cardId = studentObj.getCardId();
								
								List<Map<String, Object>> checkInDataForStudent = rfidDao.getTodayCheckInData(cardId);
								if (checkInDataForStudent.size() == 0) {
									//student has not checked In Today
									flag = false;
								}else {
									//Student Data Found
									Map<String, Object> studentCheckedInMap = checkInDataForStudent.get(0);
									int studentStatus = (int)studentCheckedInMap.get("status");
									if(studentStatus == 1) {
										flag = true;
										log.info("Student with card No "+studentCheckedInMap.get("cardId")+" Is in bus");
										log.info("Check Out Not Possible");
										break;
									}else {
										flag = false;
									}
								}
							}
							if(flag == false) {
								rfid.setStatus(0);
								rfid.setTimeStamp(timestamp);
								managedRFID = rfidDao.save(rfid);
								
								Map<String, Object> tripMapForAttend = openTripListForAtten.get(0);
								int tripMapId = (int)tripMapForAttend.get("id");
								TripMap tripMapObject = tripMapDao.findObjectById(tripMapId);
								tripMapObject.setStarted(0);
								tripMapDao.save(tripMapObject);
							}else {
								throw new SafeException("Student Is in bus.Check Out Not Possible");
							}
						}else {
								throw new SafeException("No Students are assigned to Trip "+tripId+" at "+timestamp);
						}
						
						
						
					}else {
						// case for check in
						// do the check in and update trip
						rfid.setStatus(1);
						rfid.setTimeStamp(timestamp);
						Map<String, Object> tripRow = tripForConductor.get(0);
						int tripId = (int)tripRow.get("id");
						Map<String, Object> tripMapForAttend = openTripListForAtten.get(0);
						int tripMapId = (int) tripMapForAttend.get("id");
						TripMap tripMapObject = tripMapDao.findObjectById(tripMapId);
						tripMapObject.setConductorId(loginId);
						tripMapObject.setTripId(tripId);
						managedRFID = rfidDao.save(rfid);	
						tripMapDao.save(tripMapObject);
					}
				}
//				Map<String, Object> attendanttLogin = checkInListforAtten.get(0);
//				int status = (int)attendanttLogin.get("status");
//				Timestamp timeStart = timestamp;
//				Timestamp timeEnd = (Timestamp)attendanttLogin.get("timeStamp");
//				if (compareTwoTimeStamps(timeStart,timeEnd) < 1.0) {
//					//do Nothing
//					throw new SafeException("Please wait for at least 1 minute..");
//					//return rfid;
//				}else {
//					if(status == 1) {
//						log.info("Previous Entry for Attendant Check In For Today..");
//						boolean flag = false;
//						
//						Map<String, Object> tripRow = tripForConductor.get(0);
//						int tripId = (int)tripRow.get("id");
//							
//							//get all students with this trip Id valid for today
//							List<Map<String, Object>> studentsForTrip = studentDao.getStudentsWithTripForToday(tripId);
//							if (studentsForTrip.size() != 0) {
//								for(int i=0;i<studentsForTrip.size();i++) {
//									Map<String, Object> studentV3Map= studentsForTrip.get(i);
//									int studentId = (int) studentV3Map.get("id");
//									StudentV3 studentObj = studentDao.findById(studentId).get();
//									String cardId = studentObj.getCardId();
//									
//									List<Map<String, Object>> checkInDataForStudent = rfidDao.getTodayCheckInData(cardId);
//									if (checkInDataForStudent.size() == 0) {
//										//student has not checked In Today
//										flag = false;
//									}else {
//										//Student Data Found
//										Map<String, Object> studentCheckedInMap = checkInDataForStudent.get(0);
//										int studentStatus = (int)studentCheckedInMap.get("status");
//										if(studentStatus == 1) {
//											flag = true;
//											log.info("Student with card No "+studentCheckedInMap.get("cardId")+" Is in bus");
//											log.info("Check Out Not Possible");
//											break;
//										}else {
//											flag = false;
//										}
//									}
//								}
//								if(flag == false) {
//									log.info("Attendant Check Out Possible.."+"Close the Trip Map..");
//									tripId = (int)tripRow.get("id");
//									//busId
//									//conductorId -- loginId
//									List<Map<String, Object>> closeTripForConduc = tripMapDao.getOpenTripForConductor(tripId, busId, loginId);
//									
//									if(closeTripForConduc.size() == 0) {
//										return rfid;
//									}
//									Map<String, Object> closeTripMap = closeTripForConduc.get(0);
//									
//									int tripMapId = (int) closeTripMap.get("id");
//									TripMapV3 tripMapV3 = tripMapDao.findObjectById(tripMapId);
//									tripMapV3.setStarted(0);
//									tripMapDao.save(tripMapV3);
//									
//									rfid.setStatus(0);
//									rfid.setTimeStamp(timestamp);
//									managedRFID = rfidDao.save(rfid);
//									
//									return managedRFID;
//									
//								}
//							}else {
//								throw new SafeException("No Students are assigned to Trip "+tripId+" at "+timestamp);
//								//return rfid;
//							}
//						
//					}else {
//						log.info("Previous Entry for Attendant Check In For Today..");
//						log.info("Possibility of doing check In for Attendant");
//						Map<String, Object> tripRow = tripForConductor.get(0);
//						int tripId = (int)tripRow.get("id");
//						TripMapV3 tripMapforAttendant = new TripMapV3((int)tripRow.get("id"),busId,loginId,-1,timestamp,1);
//						rfid.setStatus(1);
//						rfid.setTimeStamp(timestamp);
//						managedRFID = rfidDao.save(rfid);					
//							if(managedRFID.getStatus() == 1) {
//								tripMapDao.save(tripMapforAttendant);
//								return managedRFID;
//							}
//					}
//				}
			}
			return managedRFID;
			
		}else if(loginType.equals("D")) {
			List<Map<String, Object>> checkInListForDriver = rfidDao.getTodayCheckInData(cardIdCheckIn);
			List<Map<String, Object>> openTripListForDriver = tripMapDao.getOpenTripForBus(busId);
			
			RFID managedRFID = rfid;
			
			if(checkInListForDriver.size() == 0) {
				if (openTripListForDriver.size() != 0) {
					log.info("Driver has not done the check In/Out for today");
					log.info("Attendant has open the trip for driver");
					// do the driver check in and update the trip Map
					
					rfid.setStatus(1);
					rfid.setTimeStamp(timestamp);
					managedRFID = rfidDao.save(rfid);
					Map<String, Object> tripMapforDriver = openTripListForDriver.get(0);
					int tripMapId = (int)tripMapforDriver.get("id");
					if (managedRFID.getStatus() == 1) {
						log.info("Driver has Updated the Trip..");
						TripMap tripMapObject = (TripMap)tripMapDao.findObjectById(tripMapId);
						tripMapObject.setDriverId(loginId);
						tripMapDao.save(tripMapObject);
					}
					return managedRFID;
				}else {
					log.info("Driver has not done the check In/Out for today");
					log.info("Attendant has not open the trip for driver");
					// do the driver check in and create the trip Map
					
					rfid.setStatus(1);
					rfid.setTimeStamp(timestamp);
					managedRFID = rfidDao.save(rfid);
					if (managedRFID.getStatus() == 1) {
						log.info("Driver has Opened the Trip..");
						TripMap tripMapforDriver = new TripMap(-1,busId,-1,loginId,timestamp,1);
						tripMapDao.save(tripMapforDriver);
					}
					return managedRFID;
				}
			}else {
				log.info("Check Whether Driver has done the Check In or Out Today");
				Map<String, Object> driverCheckInMap = checkInListForDriver.get(0);
				
				Timestamp timeStart = timestamp;
				Timestamp timeEnd = (Timestamp)driverCheckInMap.get("timeStamp");
				if (compareTwoTimeStamps(timeStart,timeEnd) < 1.0) {
					//do Nothing
					throw new SafeException("Please wait for at least 1 minute...");
					//return rfid;
				}
				int status = (int)driverCheckInMap.get("status");
				if (status == 1) {
					log.info("Driver is trying to do Check Out..");
					//Check Whether driver has any Opened Trip Mapping..If yes then check out not allowed it..
					List<Map<String, Object>> driverOpenTrip = tripMapDao.getOpenTripForDriver(loginId, busId);
					if(driverOpenTrip.size() == 0) {
						System.out.println("Check Out Allowed for Driver");
						rfid.setStatus(0);
						rfid.setTimeStamp(timestamp);
						managedRFID = rfidDao.save(rfid);
						return managedRFID;
					}else {
						Map<String, Object> driverOpenMap = driverOpenTrip.get(0);
						int conductorId = (int)driverOpenMap.get("conductorId");
						if (conductorId == -1) {
							rfid.setStatus(0);
							rfid.setTimeStamp(timestamp);
							managedRFID = rfidDao.save(rfid);
							int tripMapid = (int)driverOpenMap.get("id");
							TripMap tripMapV3 = tripMapDao.findObjectById(tripMapid);
							tripMapV3.setStarted(0);
							tripMapDao.save(tripMapV3);
							log.info("Check Out done for Driver..");
							log.info("Trip Map also closed for Driver..");
							return managedRFID;
						}else {
							throw new SafeException("Ask Attendant to do Check Out First..");
							//return managedRFID;
						}

					}
				}else {
					log.info("Driver is trying to do Check In..");
					
					//Check Whether attendant has done check in same bus, if yes update Trip Map else open new Trip Map
					List<Map<String, Object>> tripForDriver = tripMapDao.getOpenTripForBus(busId);
					if (tripForDriver.size() == 0) {
						rfid.setStatus(1);
						rfid.setTimeStamp(timestamp);
						managedRFID = rfidDao.save(rfid);
						TripMap tripMapforDriver = new TripMap(-1,busId,-1,loginId,timestamp,1);
						tripMapDao.save(tripMapforDriver);
						return managedRFID;
					}else {
						rfid.setStatus(1);
						rfid.setTimeStamp(timestamp);
						managedRFID = rfidDao.save(rfid);
						Map<String, Object> tripMapObject = tripForDriver.get(0);
						int tripMapId = (int) tripMapObject.get("id");
						TripMap tripMap = tripMapDao.findObjectById(tripMapId);
						tripMap.setDriverId(loginId);
						tripMapDao.save(tripMap);
						return managedRFID;
					}
				}
			}
		}else if(loginType.equals("S")) {
			List<Map<String, Object>> tripForStudent = studentDao.getStudentTripForToday(loginId);
			
			if(tripForStudent.size() == 0) {
				throw new SafeException("No Trip Assigned to Student For Today "+timestamp);
			}
			
			Map<String, Object> studenttripMap = tripForStudent.get(0);
			int tripId = (int)studenttripMap.get("tripId");
			
			List<Map<String, Object>> checkInListForStudent = rfidDao.getTodayCheckInData(cardIdCheckIn);
			List<Map<String, Object>> bustripForStudent = tripMapDao.getOpenTripForStudent(busId,tripId);
		
			if(checkInListForStudent.size() == 0) {
				if (bustripForStudent.size()==0) {
					log.info("Student has not done the check In/Out for today");
					log.info("Attendant has not done the Check In..Student Check In not allowed.");
					
					throw new SafeException("Attendant has not done the Check In..Student Check In not allowed.");
				}else {
					log.info("Student has not done the check In/Out for today");
					log.info("Attendant has done the Check In..Student Check In allowed.");
					
					rfid.setStatus(1);
					rfid.setTimeStamp(timestamp);
					return rfidDao.save(rfid);
				}
			}else {
				
				Map<String, Object> studentCheckInMap = checkInListForStudent.get(0);
				Timestamp timeStart = timestamp;
				Timestamp timeEnd = (Timestamp)studentCheckInMap.get("timeStamp");
				if (compareTwoTimeStamps(timeStart,timeEnd) < 1.0) {
					//do Nothing
					throw new SafeException("Please wait for at least 1 minute..");
				}else {
					int status = (int) studentCheckInMap.get("status");
					if (status == 1) {
						rfid.setStatus(0);
						rfid.setTimeStamp(timestamp);
						log.info("Student Check Out Completed..");
						return rfidDao.save(rfid);
					}else {
						
						if (bustripForStudent.size()==0) {
						log.info("Student has done the check Out for today");
						log.info("Attendant has not done the Check In..Student Check In not allowed.");
						
						throw new SafeException("Attendant has not done the Check In..Student Check In not allowed.");
					}else {
						log.info("Student has done the check Out for today");
						log.info("Attendant has done the Check In..Student Check In/Out might be allowed.");
						
						rfid.setStatus(1);
						rfid.setTimeStamp(timestamp);
						log.info("Student Check In Completed..");
						return rfidDao.save(rfid);
					}
					}
				}
				
//				if (bustripForStudent.size()==0) {
//					log.info("Student has done the check In/Out for today");
//					log.info("Attendant has not done the Check In..Student Check In not allowed.");
//					
//					throw new SafeException("Attendant has not done the Check In..Student Check In not allowed.");
//				}else {
//					log.info("Student has done the check In/Out for today");
//					log.info("Attendant has done the Check In..Student Check In/Out might be allowed.");
//				}
			}
			
//			if(tripForStudent.size() == 0) {
//				throw new SafeException("No Trip Assigned to Student For Today "+timestamp);
//				//return rfid;
//			}else {
//				Map<String, Object> studenttripMap = tripForStudent.get(0);
//				int tripId = (int)studenttripMap.get("tripId");
//				
//				List<Map<String, Object>> bustripForStudent = tripMapDao.getOpenTripForStudent(busId,tripId);
//				if(bustripForStudent.size()==0) {
//					throw new SafeException("Attendant has not done the Check In..Student Check In not allowed.");
//					//return rfid;
//				}else {
//					log.info("Student can do Check In/Out Today...");
//					
//					if(checkInListForStudent.size() == 0) {
//						log.info("Student has not done the Check In/Out for Today "+timestamp);
//						rfid.setStatus(1);
//						rfid.setTimeStamp(timestamp);
//						return rfidDao.save(rfid);
//					}else {
//						Map<String, Object> studentLogin = checkInListForStudent.get(0);
//						Timestamp timeStart = timestamp;
//						Timestamp timeEnd = (Timestamp)studentLogin.get("timeStamp");
//						if (compareTwoTimeStamps(timeStart,timeEnd) < 1.0) {
//							//do Nothing
//							throw new SafeException("Please wait for at least 1 minute..");
//							//return rfid;
//						}
//						Map<String, Object> studentCheckInMap = checkInListForStudent.get(0);
//						int status = (int) studentCheckInMap.get("status");
//						if (status == 1) {
//							rfid.setStatus(0);
//							rfid.setTimeStamp(timestamp);
//							log.info("Student Check Out Completed..");
//							return rfidDao.save(rfid);
//						}else {
//							rfid.setStatus(1);
//							rfid.setTimeStamp(timestamp);
//							log.info("Student Check In Completed..");
//							return rfidDao.save(rfid);
//						}
//					}
//				}
//			}
		}
		return rfid;
	}
	
	public RFID getRecord(int id) {
		return rfidDao.findById(id).get();
	}
	
	private long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
	{
	  long milliseconds1 = oldTime.getTime();
	  long milliseconds2 = currentTime.getTime();

	  long diff = milliseconds2 - milliseconds1;
	  long diffMinutes = diff / (60 * 1000);
	  return diffMinutes;
	}

}
