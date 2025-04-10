package com.streetfit.daojdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


import com.streetfit.dao.AddStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Stage;

public class AddStageProcedureDAO implements AddStageDao{
	
	public void addStage(Stage s) throws DAOException{
		
		String title = s.getTitle();
		String itinerary = s.getItinerary();
		String category = s.getCategory();
		Date date = s.getDate(); // java.util.Date
		String location = s.getLocation();
		String intensity = s.getIntensity();
		int maxparticipant = s.getMaxParticipants();

		// Conversione corretta della data
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());


		try (
		Connection conn = ConnectionFactory.getConnection();
		CallableStatement cs = conn.prepareCall("{call addStage(?,?,?,?,?,?,?)}")){ 
			
		    cs.setString(1, title);
		    cs.setString(2, itinerary);
		    cs.setString(3, category);
		    cs.setDate(4, sqlDate); // <-- usi la data convertita
		    cs.setString(5, location);
		    cs.setString(6, intensity);
		    cs.setInt(7, maxparticipant);

		    cs.execute();
		} catch (SQLException e) {
		    throw new DAOException("Error inserting stage: " + e.getMessage(), e);
		}

	}

}

