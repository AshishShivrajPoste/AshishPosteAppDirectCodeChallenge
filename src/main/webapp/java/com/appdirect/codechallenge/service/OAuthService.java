package com.appdirect.codechallenge.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.glassfish.hk2.utilities.reflection.Constants;

import com.appdirect.codechallenge.AppDirectContants;
import com.appdirect.codechallenge.util.AppDirectCongifLoading;

import net.oauth.*;
import net.oauth.server.OAuthServlet;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class OAuthService {

	public static Boolean validate(HttpServletRequest request){
		
		Properties  prop = AppDirectCongifLoading.getInstance().getProp();
		
		String consumerKey = prop.getProperty(AppDirectContants.CONSUMER_KEY);
		String consumerSecret = prop.getProperty(AppDirectContants.CONSUMER_SECRET);
		  try {
	         //  System.out.println("Request Method = "+ request.getMethod() +" URL = "+request.getRequestURL().toString());
	          // System.out.println("Request consumerKey = "+consumerKey + " consumerSecret="+consumerSecret);

	            OAuthMessage oauthMessage= OAuthServlet.getMessage(request, null);

	            //OAuthServlet.getMessage(request, null);
	            OAuthConsumer consumer = new OAuthConsumer(null, consumerKey, consumerSecret, null);
	            OAuthAccessor accessor = new OAuthAccessor(consumer);
	            new SimpleOAuthValidator().validateMessage(oauthMessage, accessor);
	            return true;
	        } catch (OAuthException | URISyntaxException | IOException  e) {
	            System.out.println("Failed to validate {}"+e.getMessage());
	            e.printStackTrace();
	            return false;
	          //  throw new UnauthorizedException(e.getMessage());
	}
	}
	public static HttpURLConnection getoAuthSignedConnection(String connectionurl){
		
		Properties  prop = AppDirectCongifLoading.getInstance().getProp();
		
		String consumerKey = prop.getProperty(AppDirectContants.CONSUMER_KEY);
		String consumerSecret = prop.getProperty(AppDirectContants.CONSUMER_SECRET);
	
		DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		URL url = null;
		HttpURLConnection request = null;
		try {
			url = new URL(connectionurl);
			request = (HttpURLConnection) url.openConnection();
			request.setRequestProperty("Content-Type", "application/json");
			request.setRequestProperty("Accept", "application/json");
			consumer.sign(request);
			request.setRequestMethod("GET");	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return request;
	}
}
