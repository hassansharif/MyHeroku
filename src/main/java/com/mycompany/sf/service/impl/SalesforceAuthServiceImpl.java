package com.mycompany.sf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.sf.dao.SalesforceCredentials;
import com.mycompany.sf.dao.SalesforceCredentialsDAO;
import com.mycompany.sf.service.SalesforceAuthService;

/*
 * This class handles salesforce OAuth and queries database to get/update credentials
 */
@Component
public class SalesforceAuthServiceImpl implements SalesforceAuthService {

	private static final Logger logger = LoggerFactory.getLogger(SalesforceAuthServiceImpl.class);
	
	@Value("#{props['sf.service.auth.url']}")
	public String authURL;
	
	@Autowired
	SalesforceCredentialsDAO sfDAO;
	
	@Override
	public SalesforceCredentials getSalesforceCredentials(String key, boolean expired) throws Exception{
		// do a DB call to get the salesforce credentials object
		System.out.println("Passed key is " + key);
		SalesforceCredentials credentials = sfDAO.getByKey(key);
		String authToken = null;
		if(credentials != null){
			// object found for this key
			System.out.println("Credentials object is " + credentials);
			if(expired || credentials.getAuthToken() == null){
				// if the token has expired or no token was found in the database 
				// make a call to Salesforce OAuth to get an auth token
				System.out.println("No previous token info found or token has expired");
				// authenticate user and get a token
				try{
					authToken = authenticateUser(credentials);
					credentials.setAuthToken(authToken);
	
					// save this auth token in DB
					sfDAO.saveAndFlush(credentials);
				}
				catch(Exception e){
					logger.error(e.getMessage());
					throw e;
				}
			}
		}
		return credentials;
		
	}
	
	private String authenticateUser(SalesforceCredentials cred){
		String token = null;
		HttpClient httpclient = new DefaultHttpClient();
		
		// generate the SF OAuth URL
		String url = cred.getUrl() + authURL;
		System.out.println("Making a request to oauth2 url " + url);
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();

		// set the parameters needed to make the saleforce call
		parametersBody.add(new BasicNameValuePair("grant_type", "password"));
		parametersBody.add(new BasicNameValuePair("client_id", cred.getClientId()));
		parametersBody.add(new BasicNameValuePair("client_secret", cred.getClientSecret()));
		parametersBody.add(new BasicNameValuePair("username", cred.getUsername()));
		parametersBody.add(new BasicNameValuePair("password", cred.getPassword()));
		
		try{
			// set entity
			post.setEntity(new UrlEncodedFormEntity(parametersBody));
			
			// do a POST request
			HttpResponse res = httpclient.execute(post);
			
			// if this is a 200 OK parse the response
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				token = EntityUtils.toString(res.getEntity());
				System.out.println("POST Response is " + token);
				
				// convert response to a JSON Object
				JSONObject responseJSON = new JSONObject(token);
				
				// parse the access_token
				token = (String)responseJSON.get("access_token");
				
				System.out.println("Extracted token is " + token);
			}
			else{
				
				// response is not OK
				throw new Exception("Error getting response form Salesforce " + res.getStatusLine().getReasonPhrase());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return token;
	}

}
