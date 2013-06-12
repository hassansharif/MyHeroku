package com.mycompany.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.sf.exceptions.InvalidUserException;
import com.mycompany.sf.service.ProjectService;


/*
 * The ProjectsController is Facade to the SF APEX REST service
 */
@Controller
public class ProjectsController {
	
	
	
	
	@Autowired
	ProjectService projectService;
	
	@RequestMapping("/projects")
	@ResponseBody
	public String getProject(@RequestParam("key") String key) throws InvalidUserException{
		String response = null;
		try{
			// get JSON response for Projects associated with the key
			response = projectService.getProjects(key);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			response = e.getMessage();
		}
		return response;
	}
	
	@RequestMapping("/projects/{projectId}/tasks")
	@ResponseBody
	public String getProjectTasks(@PathVariable("projectId") String projectId, @RequestParam("key") String key) throws InvalidUserException{
		String response = null;
		try{
			// get JSON response for Project tasks associated with a project
			response = projectService.getProjectTasks(key, projectId);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			response = e.getMessage();
		}
		return response;
	}
}
