package com.StreetFit.model;


public enum Role {
	TRAINER(1),
    PARTICIPANT(2);
	
	 private final int id;

	    private Role(int id) {
	        this.id = id;
	    }

	    public static Role fromInt(int id) {
	        for (Role type : values()) {
	            if (type.getId() == id) {
	                return type;
	            }
	        }
	        return null;
	    }

	    public int getId() {
	        return id;
	    }
	}

