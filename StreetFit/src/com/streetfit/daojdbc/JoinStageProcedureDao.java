package com.streetfit.daojdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.JoinStageDao;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Participation;
import com.streetfit.model.Role;

public class JoinStageProcedureDao implements JoinStageDao {

	@Override
	public void registrateParticipation(Participation p) throws DAOException {
		
		ConnectionFactory.changeRole(Role.PARTICIPANT);
		
		String username = p.getUsername();
		String stage = p.getStage();
	    int tickets = p.getTicket();
	    
	    try {
	    	Connection conn = ConnectionFactory.getConnection();
	    	CallableStatement cs = conn.prepareCall("{call add_member(?,?,?)}");
	    	
	    	cs.setString(1, username);
	    	cs.setString(2, stage);
	    	cs.setInt(3, tickets);
	    	
	    	cs.execute();
	    	
	    }
	    catch(SQLException e) {
	    	throw new DAOException("Error on registration: " + e.getMessage(), e);
	    }
		
	}

	@Override
	public List<Participation> showMembers() {
		ConnectionFactory.changeRole(Role.TRAINER);
		List <Participation> members = new ArrayList<Participation>();
		String sql = "SELECT * FROM MEMBERS";
		
		try
		{
			Connection conn = ConnectionFactory.getConnection(); 
		
			PreparedStatement prepare = conn.prepareStatement(sql);
			ResultSet rs = prepare.executeQuery();
			
			while(rs.next()) {
				Participation p = new Participation(		
						rs.getString("username"),
						rs.getString("titolo"),
						rs.getInt("tickets")	
						);
				
				members.add(p);
			}		
		
	}
		catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Error");
		}
		
		return members;
    }
}
