package com.ocdsoft.bacta.swg.precu.message.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerMap {
	private Map<String, SoeUdpClient> players = new ConcurrentHashMap<String, SoeUdpClient>();
	
	@Inject
	public PlayerMap() {
	}
	
	public void add(String name, SoeUdpClient client) {
		players.put(name, client);
	}
	
	public SoeUdpClient remove(String name) {
		return players.remove(name);
	}
	
	public boolean containsKey(String name) {
		return players.containsKey(name);
	}
	
	public SoeUdpClient get(String name) {
		return players.get(name);
	}
	
	public int size() {
		return players.size();
	}
}
