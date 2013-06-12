package com.mycompany.sf.service;

import com.mycompany.sf.exceptions.AuthTokenExpiredException;
import com.mycompany.sf.exceptions.InvalidUserException;

public interface ProjectService {
	public String getProjects(String key) throws InvalidUserException;
	public String getProjectTasks(String key, String projectId) throws InvalidUserException, AuthTokenExpiredException;
}
