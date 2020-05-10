package com.sf.config.db;

public interface Query {

	String BUSES_FOR_STATION = "SELECT smartBest_line.name, smartBest_line.name,  smartBest_line.arrival_station,"
			+ " smartBest_line.departure_station, smartBest_trip.departure_time, smartBest_trip.arrival_time,"
			+ " smartBest_station.stop_name, smartBest_schedule.arrival_time, smartBest_schedule.departure_time, "
			+ " smartBest_schedule.name FROM smartBest_schedule  "
			+ " INNER JOIN smartBest_station ON smartBest_schedule.station_id_id=smartBest_station.id  "
			+ " INNER JOIN smartBest_line ON smartBest_schedule.line_id=smartBest_line.id "
			+ " INNER JOIN smartBest_trip ON smartBest_schedule.line_id=smartBest_trip.line_id_id "
			+ " WHERE smartBest_schedule.station_id_id=?;";
	/*
	 * String ROUTE_DETAILS =
	 * "SELECT smartBest_line.id, smartBest_line.description, smartBest_line.NAME, "
	 * + " smartBest_line.arrival_station_id, smartBest_line.departure_station_id, "
	 * +
	 * " smartBest_operator.operator_name, s1.stop_name arivalStation, s2.stop_name departureStation"
	 * + " FROM smartBest_line" +
	 * " INNER JOIN smartBest_operator ON smartBest_operator.id=smartBest_line.operator_id_id"
	 * +
	 * " INNER JOIN smartBest_station s1 ON smartBest_line.arrival_station_id=s1.id "
	 * +
	 * " INNER JOIN smartBest_station s2 ON smartBest_line.departure_station_id=s2.id"
	 * + " WHERE description LIKE ?;";
	 */
	String ROUTE_DETAILS = "SELECT smartBest_line.id, smartBest_line.name, smartBest_line.description, smartBest_operator.operator_name, "
			+ " smartBest_line.arrival_station arivalStation, smartBest_line.departure_station departureStation"
			+ " FROM smartBest_line INNER JOIN smartBest_operator ON smartBest_operator.id=smartBest_line.operator_id_id "
			+ " WHERE name LIKE ? ORDER BY name;";
	
	String ROUTE_DETAILS_FOR_USER = "SELECT smartBest_lineBookmark.id as bookmarkId, smartBest_lineBookmark.smartBest_lineId as id, smartBest_line.name, smartBest_operator.operator_name, smartBest_line.description" + 
			" FROM smartBest_lineBookmark INNER JOIN smartBest_line ON smartBest_line.id=smartBest_lineBookmark.smartBest_lineId" + 
			" INNER JOIN smartBest_operator ON smartBest_operator.id = smartBest_line.operator_id_id" + 
			" WHERE smartBest_UserId=? ORDER BY smartBest_line.name";

	/*
	 * String NEXT_TRIP_DETAILS =
	 * "SELECT smartBest_trip.departure_time, smartBest_trip.id, smartBest_trip.departure_time_display,"
	 * +
	 * " smartBest_line.NAME, smartBest_line.operator_id_id, smartBest_line.description FROM smartBest_trip"
	 * +
	 * " INNER JOIN smartBest_line ON smartBest_trip.line_id_id=smartBest_line.id "
	 * + " WHERE smartBest_line.id=? " // + " AND smartBest_trip.departure_time >
	 * CURRENT_TIME() + " LIMIT 1;";
	 */
	String NEXT_TRIP_DETAILS = " SELECT smartBest_trip.departure_time, smartBest_trip.line_id_id, smartBest_trip.id, "
			+ " smartBest_line.operator_id_id, smartBest_line.name FROM smartBest_trip "
			+ " INNER JOIN smartBest_line ON smartBest_trip.line_id_id=smartBest_line.id  "
			+ " WHERE smartBest_line.id=?;";

	// String SCHEDULE_DETAILS_FOR_TRIP = "SELECT * FROM smartBest_schedule WHERE
	// smartBest_schedule.trip_id_id=?;";
	// String SCHEDULE_DETAILS_FOR_TRIP = "SELECT * FROM smartBest_schedule WHERE
	// smartBest_schedule.line_id=?;";
	String SCHEDULE_DETAILS_FOR_TRIP = "SELECT smartBest_schedule.*,smartBest_location.location_name FROM smartNearBy.smartBest_schedule "
			+ " JOIN smartBest_station ON smartBest_station.stop_name=smartBest_schedule.station "
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id "
			+ " WHERE smartBest_schedule.line_id=? ORDER by smartBest_schedule.id;";

	String STATION_DTEIALS = "SELECT smartBest_station.*, smartBest_location.location_name  FROM smartBest_station"
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id"
			+ " WHERE smartBest_station.stop_name LIKE ?";
	
	String STATION_DTEIALS_FOR_USER = "SELECT smartBest_stopBookmark.id, smartBest_station.*, smartBest_location.location_name  FROM smartBest_stopBookmark" + 
			" JOIN smartBest_station ON smartBest_stopBookmark.stopId=smartBest_station.id" + 
			" JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id" + 
			" WHERE smartBest_stopBookmark.userId=?;";

	String LOCATION_DTEIALS = "SELECT smartBest_location.* FROM smartBest_location"
			+ " WHERE smartBest_location.location_name LIKE ?";

	String STATION_DTEIALS_FOR_LOCATION = "SELECT smartBest_station.*, smartBest_location.location_name  FROM smartBest_station"
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id "
			+ " WHERE smartBest_location.id=?";

	// TODO change it stop num
	String STATION_DTEIALS_FOR_STOP_NO = "SELECT smartBest_station.*, smartBest_location.location_name  FROM smartBest_station"
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id "
			+ " WHERE smartBest_station.id like ?";
	
	String EXACT_STATION_DTEIALS_FOR_STOP_NO = "SELECT smartBest_station.*, smartBest_location.location_name  FROM smartBest_station"
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id "
			+ " WHERE smartBest_station.id = ?";
	
	String ROUTE_BOOKMARK_DETAILS = "INSERT INTO smartBest_lineBookmark(smartBest_lineId,smartBest_bookmarkDate,smartBest_UserId) VALUES(?,?,?)";
	
	String STOP_BOOKMARK_DETAILS = "INSERT INTO smartBest_stopBookmark(stopId,bookmarkDate,userId) VALUES(?,?,?)";

//	String STATIONS_FOR_LAT_LONG = "SELECT smartBest_station.fGeoLat,smartBest_station.fGeoLng,smartBest_operator.operator_name,smartBest_schedule.line_id,"
//			+ "smartBest_schedule.station,smartBest_line.noStops,smartBest_line.arrival_station,smartBest_line.name,smartBest_schedule.arrival_time,smartBest_schedule.departure_time "
//			+ "FROM smartBest_station"
//			+ " INNER JOIN smartBest_schedule ON smartBest_station.id = smartBest_schedule.station_id_id "
//			+ " INNER JOIN smartBest_line ON smartBest_schedule.line_id = smartBest_line.id "
//			+ " INNER JOIN smartBest_operator ON smartBest_operator.id = smartBest_line.operator_id_id "
//			+ " WHERE (fGeoLat BETWEEN ? AND ?)  AND (fGeoLng BETWEEN ? AND ?);";
	
	String STATIONS_FOR_LAT_LONG = "SELECT smartBest_station.fGeoLat,smartBest_station.fGeoLng,smartBest_station.stop_name as near_station,smartBest_schedule.line_id,smartBest_line.name,trip_id," + 
			" smartBest_operator.operator_name,smartBest_trip.departure_time,smartBest_line.noStops,smartBest_line.arrival_station as last_station" + 
			" FROM smartBest_station" + 
			" INNER JOIN smartBest_schedule ON smartBest_schedule.station = smartBest_station.stop_name" + 
			" INNER JOIN smartBest_line ON smartBest_line.id = smartBest_schedule.line_id" + 
			" INNER JOIN smartBest_operator ON smartBest_operator.id = smartBest_line.operator_id_id" + 
			" INNER JOIN smartBest_trip ON smartBest_trip.id = smartBest_schedule.trip_id" + 
			" WHERE (fGeoLat BETWEEN ? AND ?)  AND (fGeoLng BETWEEN ? AND ?);";
	
	String ALL_ROUTE_DETAILS = "SELECT smartBest_line.id, smartBest_line.name, smartBest_line.description, smartBest_operator.operator_name, "
			+ " smartBest_line.arrival_station arivalStation, smartBest_line.departure_station departureStation"
			+ " FROM smartBest_line INNER JOIN smartBest_operator ON smartBest_operator.id=smartBest_line.operator_id_id "
			+ " ORDER BY name;";
	String ALL_STATION_DTEIALS = "SELECT smartBest_station.*, smartBest_location.location_name  FROM smartBest_station"
			+ " JOIN smartBest_location ON smartBest_location.id=smartBest_station.location_id_id";
	
	String ALL_LOCATION_DTEIALS = "SELECT smartBest_location.* FROM smartBest_location";

}
