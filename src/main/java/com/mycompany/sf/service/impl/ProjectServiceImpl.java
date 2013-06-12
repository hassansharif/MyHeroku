package com.mycompany.sf.service.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.sf.dao.SalesforceCredentials;
import com.mycompany.sf.exceptions.AuthTokenExpiredException;
import com.mycompany.sf.exceptions.InvalidUserException;
import com.mycompany.sf.service.ProjectService;
import com.mycompany.sf.service.SalesforceAuthService;



/*
 * This class makes the REST calls to the ProjectsRestSVC and the ProjectTasksRestSVC APEX classes on salesforce 
 */

@Component
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	SalesforceAuthService auth;
	
	@Value("#{props['sf.apex.service.projects.url']}")
	public String projectURL;
	
	@Value("#{props['sf.apex.service.projects.task.url']}")
	public String projectTasksURL;
	
	/*
	 * Get Project associated with the key provided
	 */
	@Override
	public String getProjects(String key) throws InvalidUserException{
		String jsonResponse = null;
		// get Salesforce credentials object based on key
		try{
			SalesforceCredentials cred = auth.getSalesforceCredentials(key, false);
			if(cred == null){
				// no user found against this key
				throw new InvalidUserException("No user found with this key  " + key);
			}
			// generate the APEX Service URL for this user
			String projectsURL = cred.getUrl() + projectURL;
			// make REST call to APEX service
			try{
				jsonResponse = makeRESTCall(projectsURL,cred.getAuthToken());
			}
			catch(AuthTokenExpiredException ae){
				// the users auth token has expired
				System.out.println("AuthTokenExpiredException IS "+ae.getMessage());
			
				// make OAuth call to SF to get auth token
				cred = auth.getSalesforceCredentials(key, true);
				if(cred == null){
					// CRITICAL: no user was found with the credentials
					throw new InvalidUserException("No user found with this key  " + key);
				}
			// now that we have the new auth token make the REST call to the service
			jsonResponse = makeRESTCall(projectsURL,cred.getAuthToken());
			}
		}
		catch(Exception e){
			System.out.println("Exception OUT"+e.getMessage());
			jsonResponse = "{" + e.getMessage() + "}";
		}
		return jsonResponse;
	}

	@Override
	public String getProjectTasks(String key, String projectId) throws InvalidUserException{
		String jsonResponse = null;
		// get salesforce credentials object for this user
		try{
			SalesforceCredentials cred = auth.getSalesforceCredentials(key, false);
			if(cred == null){
				// no user found against this key
				throw new InvalidUserException("No user found with this key  " + key);
			}
		
			// generate the project task APEX service URL
			String projectTaskURL = cred.getUrl() + projectTasksURL + projectId;			
		
			try{
				// make the REST call with the auth token
				jsonResponse = makeRESTCall(projectTaskURL,cred.getAuthToken());
			}
			catch(AuthTokenExpiredException ae){
				// Token has expired
				System.out.println(ae.getMessage());
				// do OAuth with  SF to get the token
				cred = auth.getSalesforceCredentials(key, true);
				if(cred == null){
					// no user found with the credentials
					throw new InvalidUserException("No user found with this key  " + key);
				}			
				System.out.println("NEW AUTH TOKEN IS "+cred.getAuthToken());				
				jsonResponse = makeRESTCall(projectTaskURL,cred.getAuthToken());
				System.out.println("JSON RESPONSE IS "+jsonResponse.toString());
			}
		}
		catch(Exception e){
			jsonResponse = "{" + e.getMessage() + "}";
		}
		return jsonResponse;
		
	}
	
	private String makeRESTCall(String url, String token) throws AuthTokenExpiredException, ClientProtocolException, IOException{
		System.out.println("URL is " + url + " and token is " + token);
		String responseJSON = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		
		// set OAuth header
		get.setHeader("Authorization", "OAuth " + token);
		get.setHeader("Content-Type", "application/json");
		
		/// make GET call to the service
		HttpResponse response = httpclient.execute(get);
		System.out.println("Response code is " + response.getStatusLine().getReasonPhrase());
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
			// response no OK, token has probably expired
			throw new AuthTokenExpiredException("Token has expired, Try authenticating with SF");
		}
		else{
			// response received, convert to string
			responseJSON = EntityUtils.toString(response.getEntity());
			System.out.println("response JSON is " + responseJSON);
		}
		return responseJSON;
	}

}
