package server.requestDirector.impl;

import com.google.gson.Gson;
import model.DTOs.Request.ClientActionRequest;
import server.ConnectionData;
import server.endpoint.Endpoint;
import server.requestDirector.RequestDirector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThordonDirector
	implements RequestDirector {

	Gson g = new Gson();
	Map<String,Endpoint> endpoints = new HashMap<>();

	public ThordonDirector(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	public ThordonDirector(Endpoint... endpoints){
		Arrays.stream(endpoints).forEach(e->this.endpoints.put(e.getName(),e));
	}
	@Override
	public Endpoint redirect(ConnectionData data) {
		return endpoints.get(g.
				fromJson(new String(data.getRequest()), ClientActionRequest.class).getDirection());
	}
}
