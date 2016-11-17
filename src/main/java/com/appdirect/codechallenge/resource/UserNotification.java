package com.appdirect.codechallenge.resource;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.appdirect.codechallenge.model.CreateSubsciptionSuccess;
import com.appdirect.codechallenge.service.OAuthService;
import com.appdirect.codechallenge.service.UserResourceNotificationService;
import com.appdirect.codechallenge.util.Utility;



@Path("/user")
public class UserNotification {

	UserResourceNotificationService usernotificationservice = new UserResourceNotificationService();
	@GET
	@Path("/assign")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignUser(@QueryParam("url") String eventURL){

		System.out.println("Assign Event URL : "+eventURL);
		try {
			HttpURLConnection request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			String result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
			Boolean isadded = usernotificationservice.addUser(result);
			if(isadded){
				CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
				createSubsciptionSuccess.setSuccess(true);
				createSubsciptionSuccess.setAccountIdentifier("fkgjk5656");
				return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
			}else{
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		createSubsciptionSuccess.setAccountIdentifier("fkgjk5656");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	
	@GET
	@Path("/unassign")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unAssignUser(@QueryParam("url") String eventURL){

		System.out.println("UnAssign Event URL : "+eventURL);
		try {
			HttpURLConnection  request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			String result = Utility.readApiResponse(request);
			//Utility.getObjectfromJson(result, Event.class);
		//	Event event  = (Event)Utility.getObjectfromJson(result,Event.class);
			System.out.println("Result : "+result);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		createSubsciptionSuccess.setSuccess(true);
		createSubsciptionSuccess.setAccountIdentifier("fkgjk5656");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}
	
}
