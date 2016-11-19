package com.appdirect.codechallenge.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.appdirect.codechallenge.model.CreateSubsciptionSuccess;
import com.appdirect.codechallenge.model.Event;
import com.google.gson.Gson;

public class Utility {

	public static Object getObjectfromJson(String jsonString, Class<Event> class1){
		Gson gson = new Gson();
		Event event = null;
		event  = gson.fromJson(jsonString, class1);
		return event;
		
	}
	public static String getJsonfromObject(CreateSubsciptionSuccess obj){
		
		Gson gson = new Gson();
		String event  = gson.toJson(obj);
		return event;
		
	}
	public static String readApiResponse(HttpURLConnection request){
		String result = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(request.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		result = sb.toString();
		return result;
	}
	public static void printHeader(Map<String, List<String>> header){
		Set<String> keyset = header.keySet();
		Iterator<String> ite = keyset.iterator();
		while(ite.hasNext()){
			String key = ite.next();
			List<String> list = header.get(key);
			for (String value : list) {
				System.out.print(value);
				System.out.print("  ");
			}
		}
	}
	
	public static String generateAccountID(String eventInformation){
		String accountIdentifier = null;
		Event subscribeEvent = (Event)Utility.getObjectfromJson(eventInformation, Event.class);
		accountIdentifier = subscribeEvent.hashCode()+"";
		return accountIdentifier;
	}

}
