package com.appdirect.codechallenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.appdirect.codechallenge.model.Event;
import com.appdirect.codechallenge.util.Utility;


public class UserResourceNotificationService {

	private static UserResourceNotificationService userResourceNotificationService;
	private Map<String, List<Event>> userResourcedatasource;

	public UserResourceNotificationService() {
		userResourcedatasource = new HashMap<>();
	}
	public static UserResourceNotificationService getSubscriptionServiceInstance(){
		if(userResourceNotificationService==null){
			synchronized (UserResourceNotificationService.class) {
				if(userResourceNotificationService==null){
					userResourceNotificationService = new UserResourceNotificationService();
				}
			}
		}
		return userResourceNotificationService;
	}
	
	public Boolean addUser(String eventInformation){
		
		Event subscribeEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		String accountIdentifier = subscribeEvent.getPayload().getAccount().getAccountIdentifier();
		if(!this.userResourcedatasource.containsKey(accountIdentifier)){
			List<Event> userAssigned = new ArrayList<>();
			userAssigned.add(subscribeEvent);
			this.userResourcedatasource.put(accountIdentifier, userAssigned);
			return true;
		}else{
			List<Event> userassigned = userResourcedatasource.get(accountIdentifier);
			Boolean userPresent = false;
			Iterator<Event> iterator = userassigned.iterator();
			while (iterator.hasNext()) {
				Event event = (Event) iterator.next();
				if (event.getPayload().getUser().getUuid().equalsIgnoreCase(subscribeEvent.getPayload().getUser().getUuid()) ) {
					userPresent = true;
					break;
				}
			}
			if (userPresent) {
				return false;
			}else{
				userassigned.add(subscribeEvent);
				return true;
			}
		}
		
	}
	public Boolean removeUsers(String eventInformation) {
		Event subscribeEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		String accountIdentifier = subscribeEvent.getPayload().getAccount().getAccountIdentifier();
		if(!this.userResourcedatasource.containsKey(accountIdentifier)){
			return false;
		}else{
			List<Event> userassigned = userResourcedatasource.get(accountIdentifier);
			Iterator<Event> iterator = userassigned.iterator();
			while (iterator.hasNext()) {
				Event event = (Event) iterator.next();
				if (event.getPayload().getUser().getUuid().equalsIgnoreCase(subscribeEvent.getPayload().getUser().getUuid()) ) {
					iterator.remove();
					return true;
				}
			}
		}
		return false;
	}
	
}
