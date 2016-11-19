package com.appdirect.codechallenge.resource;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.appdirect.codechallenge.model.CreateSubsciptionSuccess;
import com.appdirect.codechallenge.service.OAuthService;
import com.appdirect.codechallenge.service.SubscriptionService;
import com.appdirect.codechallenge.util.Utility;




@Path("/subscription")
public class AppDirectSubscription {

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CreateSubscription(@QueryParam("url") String eventURL){

		System.out.println("***** CREATE SUBSCRIPTION EVENT NOTIFICATION STARTED *****");
	//	System.out.println("Create Event URL : "+eventURL);
		String accountIdentifier= null;
		String result = null;
		HttpURLConnection  request = null;
		try {	
			request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
			accountIdentifier = Utility.generateAccountID(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(request!=null){
				request.disconnect();
			}
		}
		Boolean isadded  = SubscriptionService.getSubscriptionServiceInstance().addSubscription(result,accountIdentifier);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isadded){
			createSubsciptionSuccess.setSuccess(true);
			createSubsciptionSuccess.setAccountIdentifier(accountIdentifier);
			System.out.println("Final Response Pass:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}else{
			createSubsciptionSuccess.setSuccess(false);
			System.out.println("Final Response Failed:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}
		System.out.println("***** CREATE SUBSCRIPTION EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}

	@GET
	@Path("/cancel")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelSubscription(@QueryParam("url") String eventURL){
		System.out.println("***** CANCEL SUBSCRIPTION EVENT NOTIFICATION STARTED *****");
		//System.out.println("Cancel Event URL : "+eventURL);
		String result = null;
		try {
			HttpURLConnection  request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			result = Utility.readApiResponse(request);
			
			System.out.println("Result : "+result);
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean isadded  = SubscriptionService.getSubscriptionServiceInstance().removeSubscription(result);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isadded){
			createSubsciptionSuccess.setSuccess(true);
			System.out.println("Final Response Pass:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}else{
			createSubsciptionSuccess.setSuccess(false);
			System.out.println("Final Response Failed:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}
		System.out.println("***** CANCEL SUBSCRIPTION EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}

	@GET
	@Path("/change")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeSubscription(@QueryParam("url") String eventURL){
		System.out.println("***** CHANGE SUBSCRIPTION EVENT NOTIFICATION STARTED *****");
		//System.out.println("Change Event URL : "+eventURL);
		HttpURLConnection request  = OAuthService.getoAuthSignedConnection(eventURL);
		String result =null;
		try {
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			request.disconnect();
		}
		Boolean isUpdated  = SubscriptionService.getSubscriptionServiceInstance().updateSubscription(result);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isUpdated){
			createSubsciptionSuccess.setSuccess(true);
			System.out.println("Final Response Pass:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}else{
			createSubsciptionSuccess.setSuccess(false);
			System.out.println("Final Response Failed:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}
		System.out.println("***** CHANGE SUBSCRIPTION EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SubscriptionStatus(@QueryParam("url") String eventURL){
		System.out.println("***** CHANGE SUBSCRIPTION EVENT NOTIFICATION STARTED *****");
		System.out.println("Status Event URL : "+eventURL);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		System.out.println("***** STATUS SUBSCRIPTION EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	
	
	
		
	
}
