package com.mycompany.sf.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * An entity class for storing Saleforce credentials, This class will also store the auth token
 * that is returned by the salesforce oauth process
 */
@Entity
public class SalesforceCredentials {
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "SalesforceCredentials [id=" + id + ", key=" + key
				+ ", username=" + username + ", password=" + password
				+ ", url=" + url + ", clientId=" + clientId + ", clientSecret="
				+ clientSecret + ", authToken=" + authToken + "]";
	}
	protected String key;
	protected String username;
	protected String password;
	protected String url;
	protected String clientId;
	protected String clientSecret;
	protected String authToken;
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
