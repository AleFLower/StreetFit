package com.streetfit.daojdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.streetfit.dao.AddStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Role;
import com.streetfit.model.TrainingStage;

public class AddStageProcedureDAO implements AddStageDao{
	
	public AddStageProcedureDAO() {
		ConnectionFactory.changeRole(Role.TRAINER);
	}
	
	public void addStage(TrainingStage s) throws DAOException{
		
		
		String title = s.getTitle();
		String itinerary = s.getItinerary();
		String category = s.getCategory();
		Date date = s.getDate(); // java.util.Date
		String location = s.getLocation();
		int maxparticipant = s.getMaxParticipants();

		// Conversione corretta della data
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());


		try {  //use try catch because try with resources give problem(close connection)
		Connection conn = ConnectionFactory.getConnection();
		CallableStatement cs = conn.prepareCall("{call addStage(?,?,?,?,?,?)}"); 
			
		    cs.setString(1, title);
		    cs.setString(2, itinerary);
		    cs.setString(3, category);
		    cs.setDate(4, sqlDate); // <-- usi la data convertita
		    cs.setString(5, location);
		    cs.setInt(6, maxparticipant);

		    cs.execute();
		} catch (SQLException e) {
		    throw new DAOException("Error inserting stage: " + e.getMessage(), e);
		}
	}

	
	public List<TrainingStage> getStages() throws DAOException {
		
		List <TrainingStage> stageList = new ArrayList<TrainingStage>();
		
		String sql = "SELECT * FROM STAGE";  //remember to give all grants (select grant) for the trainer user
		
		try
		{Connection conn = ConnectionFactory.getConnection(); {
			PreparedStatement prepare = conn.prepareStatement(sql);
			ResultSet rs = prepare.executeQuery();
			
			while(rs.next()) {
				TrainingStage stage = new TrainingStage(rs.getString("titolo"), 
						rs.getString("itinerario"),
						rs.getString("categoria"),
						rs.getDate("data"),
						rs.getString("luogo"), 
						rs.getInt("max_partecipanti"));
				
				stageList.add(stage);
			}
		}

		}catch (SQLException e) {
		    throw new DAOException("Error inserting stage: " + e.getMessage(), e);
		}
		
		return stageList;
	}

}
