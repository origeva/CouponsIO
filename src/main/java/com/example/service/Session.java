package com.example.service;

import com.example.facade.ClientFacade;

public class Session {
	
	private ClientFacade facade;
	private long lastAccessed;
	
	public Session(ClientFacade facade, long lastAccessed) {
		super();
		this.facade = facade;
		this.lastAccessed = lastAccessed;
	}

	public long getLastAction() {
		return lastAccessed;
	}

	public void setLastAction(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public ClientFacade getFacade() {
		return facade;
	}
	
}
