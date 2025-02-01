package com.apptrove.ledgerlyBackend.service;

import java.util.Map;

import com.apptrove.ledgerlyBackend.entities.User;

public interface MailService {

	public Map<String,Object> sendLoginMainNotification(User user);
	
}
