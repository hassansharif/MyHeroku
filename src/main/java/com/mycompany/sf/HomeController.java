package com.mycompany.sf;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycompany.sf.dao.SalesforceCredentials;
import com.mycompany.sf.dao.SalesforceCredentialsDAO;

/**
 * Handles requests for the application home page.
 * 
 * This controller serves as the landing page for Saleforce REST POC
 */
@Controller
public class HomeController {
	
	@Autowired
	SalesforceCredentialsDAO dao;
	
	/**
	 * Home page returns a form where user will enter their salesforce credentials.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("sfcred", new SalesforceCredentials());
		return "home";
	}

	/*
	 * This is a POST call from the form submission, this code simply generates a key 
	 * and simply inserts the salesforce credentials in the PGSQL database along with the key information
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertData(@ModelAttribute("sfcred") SalesforceCredentials cred, Model model, BindingResult result){
		cred.setKey(UUID.randomUUID().toString());
		cred = dao.saveAndFlush(cred);
		model.addAttribute("sfcred", cred);
		return "home";
	}
	
	@RequestMapping(value="/get/{username:.+}",produces = {"application/json"})
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody List<SalesforceCredentials> getKey(@PathVariable String username){
		System.out.println("Username is " + username);
		List<SalesforceCredentials> sfcred = dao.getByUsername(username);
		System.out.println("Size is " + sfcred.size());
		return sfcred;
	}

}

