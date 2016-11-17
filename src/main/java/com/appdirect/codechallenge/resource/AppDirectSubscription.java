package com.appdirect.codechallenge.resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;



@Path("/subscription")
public class AppDirectSubscription {

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response CreateSubscription(@QueryParam("url") String eventURL){

		System.out.println("Create Event URL : "+eventURL);
		String accountIdentifier= null;
		String result = null;
		HttpURLConnection  request = null;
		try {	
			request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
			accountIdentifier = "fkgjk5656";
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(request!=null){
				request.disconnect();
			}
		}
		Boolean isadded  = SubscriptionService.getSubscriptionServiceInstance().addSubscription(result, accountIdentifier);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isadded){
			createSubsciptionSuccess.setSuccess(true);
			createSubsciptionSuccess.setAccountIdentifier(accountIdentifier);
			return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
		}else{
			createSubsciptionSuccess.setSuccess(false);
			return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
		}
	}

	@GET
	@Path("/cancel")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelSubscription(@QueryParam("url") String eventURL){

		System.out.println("Cancel Event URL : "+eventURL);

		try {
			HttpURLConnection  request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			String result = Utility.readApiResponse(request);
			
			System.out.println("Result : "+result);
			Boolean isadded  = SubscriptionService.getSubscriptionServiceInstance().removeSubscription(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		createSubsciptionSuccess.setAccountIdentifier("fkgjk5656");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}

	@GET
	@Path("/change")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeSubscription(@QueryParam("url") String eventURL){

		System.out.println("Change Event URL : "+eventURL);
		HttpURLConnection request  = OAuthService.getoAuthSignedConnection(eventURL);
		try {
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			String result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			request.disconnect();
		}
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SubscriptionStatus(@QueryParam("url") String eventURL){

		System.out.println("Status Event URL : "+eventURL);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	
	
	
		
	
}
