/**
 * 
 */
package com.welflex.service;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.lacassandra.smooshyfaces.entity.Book;
import org.lacassandra.smooshyfaces.entity.User;
import org.springframework.stereotype.Controller;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.NamespaceContext;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author UPAPIFR
 *
 */
@Controller
@RequestMapping("/user")
public class UserServiceRestEndpoint {

	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private UserService userService;

	private String piSamlUrl = "https://sso.rumba.int.pearsoncmg.com/sso/samlValidate?service=http://floating-peak-4593.herokuapp.com/findBook&ticket=";
	
	@RequestMapping( method = RequestMethod.GET, value = "/upsert/{ticket}", produces="application/json")
	@ResponseBody
	public User validate(@PathVariable String ticket) {
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getmethod = new HttpGet(piSamlUrl+ticket);
		String response = "";
		try {
			response = EntityUtils.getContentCharSet(client.execute(getmethod).getEntity());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		String id = "";
		try {
			String attribute = "saml2:Attribute[@Name='" +
	    			"UserId"+ "']/saml2:AttributeValue";
	        HashMap<String, String> nsMgr = new HashMap<String, String>();
			nsMgr.put("saml2p","urn:oasis:names:tc:SAML:2.0:protocol");
	        nsMgr.put("xsi","http://www.w3.org/2001/XMLSchema-instance");
	        nsMgr.put("soap11","http://schemas.xmlsoap.org/soap/envelope/");
	        nsMgr.put("saml2","urn:oasis:names:tc:SAML:2.0:assertion");
	        nsMgr.put("xs","http://www.w3.org/2001/XMLSchema");	
	        NamespaceContext ctx = new SimpleNamespaceContext(nsMgr);
	        
	        String xpathExpres = "//" +
	    			attribute+ "/text()";
	        Document d = XMLUnit.buildControlDocument(response);
	        XpathEngine engine = XMLUnit.newXpathEngine();
	        engine.setNamespaceContext(ctx);

	        id = engine.evaluate(xpathExpres, d);
		} catch (XpathException e) {
			e.printStackTrace();
			throw new AssertionError("SAML ERROR: " + e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new AssertionError("SAML ERROR: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new AssertionError("SAML ERROR: " + e.getMessage());
		}
		User newUser = new User(id , "", "", "");
		User u = userService.create(newUser );
		return u;
	}
	
	@RequestMapping( method = RequestMethod.POST, value = "/create", produces="application/json")
	@ResponseBody
	public User create(@RequestBody User newUser) {
		
		User u = userService.create(newUser);
		return u;
	}

	@RequestMapping(value = "/{userid}/like/{isbn}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public User likeBook(@PathVariable String userid, @PathVariable String isbn)
	{
		User user = userService.like(userid,isbn); 
		return user;
	}


	@RequestMapping(value = "/{userid}/dislike/{isbn}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public User disLikeBook(@PathVariable String userid, @PathVariable String isbn)
	{
		User user = userService.dislike(userid,isbn); 
		return user;
	}
	@RequestMapping(value = "/{userid}/next", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Book next(@PathVariable String userid)
	{
		return userService.next(userid);
	}

}
