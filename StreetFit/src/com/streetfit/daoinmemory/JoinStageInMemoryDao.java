package com.streetfit.daoinmemory;

import java.util.ArrayList;
import java.util.List;

import com.streetfit.dao.JoinStageDao;
import com.streetfit.model.Participation;

public class JoinStageInMemoryDao implements JoinStageDao {

	private static List <Participation> members = new ArrayList<Participation>();  //se non static, non funziona, ovviamente...(vedi definizione di attributo static)
	@Override
	public void registrateParticipation(Participation p) {
		
		if(p == null) {
			throw new IllegalStateException("Error");
		}	
		members.add(p);
	}

	@Override
	public List<Participation> showMembers() {
		return members;
	}

}
