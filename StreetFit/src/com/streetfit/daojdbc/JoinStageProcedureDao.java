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
import com.streetfit.model.Message;
import com.streetfit.model.Participation;
import com.streetfit.model.Role;

public class JoinStageProcedureDao implements JoinStageDao {

	@Override
	public void registrateParticipation(Participation p) throws DAOException {
		
		ConnectionFactory.changeRole(Role.PARTICIPANT);
		
		String username = p.getUsername();
		String stage = p.getStage();
	    int tickets = p.getTicket();
	    Connection conn = ConnectionFactory.getConnection();
	    
	    try(CallableStatement cs = conn.prepareCall("{call add_member(?,?,?)}");) {

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
		List <Participation> members = new ArrayList<>();
		String sql = "SELECT username,titolo,tickets FROM MEMBERS";
		Connection conn = ConnectionFactory.getConnection(); 
		
		try(PreparedStatement prepare = conn.prepareStatement(sql);)
		{	
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
			
			throw new IllegalStateException("Error");
		}
		
		return members;
    }

	@Override
	public void depositMessage(Message m) throws DAOException {
	    ConnectionFactory.changeRole(Role.PARTICIPANT);

	    String sql = "{call insert_message(?, ?, ?)}";
	    try {
	    	Connection conn = ConnectionFactory.getConnection();
	        CallableStatement cs = conn.prepareCall(sql) ;

	        cs.setString(1, m.getFromUser());
	        cs.setString(2, m.getContent());
	        cs.setString(3, m.getReply() != null ? m.getReply() : null);

	        cs.execute();

	    } catch (SQLException e) {
	        throw new DAOException("Error inserting message: " + e.getMessage(), e);
	    }
	}


	@Override
	public List<Message> retrieveMessage() throws DAOException {
	    ConnectionFactory.changeRole(Role.TRAINER);

	    List<Message> messages = new ArrayList<>();
	    String sql = "SELECT * FROM Messages";

	    try {Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Message m = new Message(
	                rs.getString("from_user"),
	                rs.getString("content"),
	                rs.getString("reply")
	            );
	            messages.add(m);
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error retrieving messages: " + e.getMessage(), e);
	    }

	    return messages;
	}

	@Override

	public void updateMessage(Message updatedMessage) throws DAOException {
	    String sql = "CALL update_message_reply(?, ?, ?)";  // Chiamata alla procedura

	    try {Connection conn = ConnectionFactory.getConnection();
	         CallableStatement cs = conn.prepareCall(sql) ;

	        // Impostiamo i parametri della procedura (p_from_user, p_content, p_reply)
	        cs.setString(1, updatedMessage.getFromUser());  // User che ha inviato il messaggio
	        cs.setString(2, updatedMessage.getContent());   // Contenuto del messaggio
	        cs.setString(3, updatedMessage.getReply());     // Risposta al messaggio

	        // Esegui la procedura
	        cs.execute();

	    } catch (SQLException e) {
	        // Gestione degli errori in caso di problemi con l'esecuzione della procedura
	        throw new DAOException("Error updating message reply: " + e.getMessage(), e);
	    }
	}


}
