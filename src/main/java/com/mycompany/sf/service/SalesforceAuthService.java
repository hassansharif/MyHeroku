package com.mycompany.sf.service;

import com.mycompany.sf.dao.SalesforceCredentials;

public interface SalesforceAuthService {
	public SalesforceCredentials getSalesforceCredentials(String key, boolean expired) throws Exception;
}
