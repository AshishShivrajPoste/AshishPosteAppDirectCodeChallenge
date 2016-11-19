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
		System.out.println("***** ASSIGN USER EVENT NOTIFICATION STARTED *****");
		//System.out.println("Assign Event URL : "+eventURL);
		String result=null;
		try {
			HttpURLConnection request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " Request : "+ request.getResponseMessage());
			result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		Boolean isadded = usernotificationservice.addUser(result);
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isadded){
			createSubsciptionSuccess.setSuccess(true);
			System.out.println("Final Response Pass:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}else{
			createSubsciptionSuccess.setSuccess(false);
			createSubsciptionSuccess.setErrorCode("USER_ALREADY_EXISTS");
			System.out.println("Final Response Failed:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}
		System.out.println("***** ASSIGN USER EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}

	@GET
	@Path("/unassign")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unAssignUser(@QueryParam("url") String eventURL){

		System.out.println("***** UNASSIGN USER EVENT NOTIFICATION STARTED *****");
//		System.out.println("UnAssign Event URL : "+eventURL);
		Boolean isRemoved = false;
		try {
			HttpURLConnection  request = OAuthService.getoAuthSignedConnection(eventURL);
			request.connect();
			String result = Utility.readApiResponse(request);
			System.out.println("Result : "+result);
			isRemoved = usernotificationservice.removeUsers(result);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateSubsciptionSuccess createSubsciptionSuccess = new CreateSubsciptionSuccess();
		if(isRemoved){
			createSubsciptionSuccess.setSuccess(true);
			System.out.println("Final Response Pass:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}else{
			createSubsciptionSuccess.setSuccess(false);
			createSubsciptionSuccess.setErrorCode("USER_NOT_FOUND");
			System.out.println("Final Response Failed:"+Utility.getJsonfromObject(createSubsciptionSuccess));
		}
		System.out.println("***** UNASSIGN USER EVENT NOTIFICATION ENDED *****");
		return Response.ok(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS").entity(createSubsciptionSuccess).build();
	}

}
