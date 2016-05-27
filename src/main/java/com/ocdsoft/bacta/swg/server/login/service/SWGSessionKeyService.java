package com.ocdsoft.bacta.swg.server.login.service;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.service.SessionKeyService;

import java.util.Random;

@Singleton
public class SWGSessionKeyService implements SessionKeyService {

	private final Random random = new Random();
	
	@Override
	public int getNextKey() {
		return random.nextInt();
	}

}
