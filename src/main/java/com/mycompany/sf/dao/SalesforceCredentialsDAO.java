package com.mycompany.sf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
 * A Spring JPA Data repository interface, the Spring JPA data project does a behind-the-scenes 
 * query to the PGSQL database to get Salesforce user credentials based on provided key.
 */
@Repository
public interface SalesforceCredentialsDAO extends JpaRepository<SalesforceCredentials, Integer> {
	public SalesforceCredentials getByKey(String key);
	public List<SalesforceCredentials> getByUsername(String username);
}
