package com.appdirect.codechallenge.service;

import java.util.HashMap;
import java.util.Map;

import com.appdirect.codechallenge.model.Event;
import com.appdirect.codechallenge.util.Utility;

public class SubscriptionService {

	private static SubscriptionService subscriptionService;
	private Map<String, Event> subscriptionInfo;

	private SubscriptionService() {
		subscriptionInfo = new HashMap<>();
	}
	public static SubscriptionService getSubscriptionServiceInstance(){
		if(subscriptionService==null){
			synchronized (SubscriptionService.class) {
				if(subscriptionService==null){
					subscriptionService = new SubscriptionService();
				}
			}
		}
		return subscriptionService;
	}
	
	public Boolean addSubscription(String eventInformation, String accountIdentifier){
		
		Event subscribeEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		
		if(!this.subscriptionInfo.containsKey(accountIdentifier)){
			this.subscriptionInfo.put(accountIdentifier, subscribeEvent);
			return true;
		}else{
			return false;
		}
		
	}
	public Boolean removeSubscription(String eventInformation) {
		Event unSubscribeEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		if(unSubscribeEvent!=null && unSubscribeEvent.getPayload()!=null 
				&& unSubscribeEvent.getPayload().getAccount()!=null 
				&& unSubscribeEvent.getPayload().getAccount().getAccountIdentifier()!=null){
			String accountId = unSubscribeEvent.getPayload().getAccount().getAccountIdentifier();
			Event event = subscriptionInfo.remove(accountId);
			if(event!=null){
				return true;
			}
		}
		return false;
	}
	public Boolean updateSubscription(String eventInformation){
		Event updatedEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		if(updatedEvent!=null && updatedEvent.getPayload()!=null 
				&& updatedEvent.getPayload().getAccount()!=null 
				&& updatedEvent.getPayload().getAccount().getAccountIdentifier()!=null){
			String accountId = updatedEvent.getPayload().getAccount().getAccountIdentifier();
			Event event = subscriptionInfo.get(accountId);
			if(event!=null){
				subscriptionInfo.put(accountId, updatedEvent);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}
