package main.java.com.streetfit.daojdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.dao.JoinStageDao;
import main.java.com.streetfit.exception.DAOException;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.Role;
import main.java.com.streetfit.model.TrainingStage;

public class JoinStageProcedureDao implements JoinStageDao {

	@Override
	public void registrateParticipation(Participation p) throws DAOException {
		
		ConnectionFactory.changeRole(Role.PARTICIPANT);
		
		String username = p.getUsername();
		String stage = p.getStage().getTitle();
	    int tickets = p.getTicket();
	    double total = p.getTotal();
	    Connection conn = ConnectionFactory.getConnection();
	    
	    try(CallableStatement cs = conn.prepareCall("{call add_member(?,?,?,?)}");) {

	    	cs.setString(1, username);
	    	cs.setString(2, stage);
	    	cs.setInt(3, tickets);
	    	cs.setDouble(4, total);	    	
	    	cs.execute();
	    	
	    }
	    catch(SQLException e) {
	    	throw new DAOException("Error on registration: " + e.getMessage(), e);
	    } 
		
	}

	@Override
	public List<Participation> showMembers() {
	    JoinStageController controller = new JoinStageController();
	    ConnectionFactory.changeRole(Role.TRAINER);
	    List<Participation> members = new ArrayList<>();

	    String sql = "SELECT username, titolo, tickets, total FROM MEMBERS";
	    Connection conn = ConnectionFactory.getConnection(); 

	    // Prima raccogliamo i dati grezzi, necessario perche chiamando findStageByTitle chiudo il result set alla fine
	    List<Object[]> rawList = new ArrayList<>();

	    try (PreparedStatement prepare = conn.prepareStatement(sql);
	         ResultSet rs = prepare.executeQuery()) {

	        while (rs.next()) {
	            rawList.add(new Object[]{
	                rs.getString("username"),
	                rs.getString("titolo"),
	                rs.getInt("tickets"),
	                rs.getDouble("total")
	            });
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IllegalStateException("Error retrieving data from DB");
	    }

	    // Poi creiamo le Participation una volta chiuso il ResultSet
	    for (Object[] data : rawList) {
	        String username = (String) data[0];
	        String stageTitle = (String) data[1];
	        int tickets = (Integer) data[2];
	        double total = (Double) data[3];
	        TrainingStageBean stage = controller.findStageByTitle(stageTitle);
            
            TrainingStage trainingStage = new TrainingStage(stage.getTitle(),stage.getItinerary(),stage.getCategory(),stage.getDate(),stage.getPlace(),stage.getMaxParticipants());
	        Participation p = new Participation(username, trainingStage, tickets, total);
	        members.add(p);
	    }

	    return members;
	}


	@Override
	public void depositMessage(Message m) throws DAOException {
	    ConnectionFactory.changeRole(Role.PARTICIPANT);

	    String sql = "{call insert_message(?, ?, ?)}";
	    Connection conn = ConnectionFactory.getConnection();
	    try(  CallableStatement cs = conn.prepareCall(sql) ;) {
	    	
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
	    String sql = "SELECT from_user,content,reply FROM Messages";
	    Connection conn = ConnectionFactory.getConnection();
	    
	    try( PreparedStatement ps = conn.prepareStatement(sql);) {
	        
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
	    Connection conn = ConnectionFactory.getConnection();
	    try( CallableStatement cs = conn.prepareCall(sql) ;) {
	        

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

	@Override
	public void removeParticipation(String username, String stage) throws DAOException {
	    ConnectionFactory.changeRole(Role.TRAINER); 

	    String sql = "{call remove_member(?, ?)}";
	    Connection conn = ConnectionFactory.getConnection();

	    try (CallableStatement cs = conn.prepareCall(sql)) {
	        cs.setString(1, username);
	        cs.setString(2, stage);

	        cs.execute();
	    } catch (SQLException e) {
	        throw new DAOException("Error removing participation: " + e.getMessage(), e);
	    }
	}

}
