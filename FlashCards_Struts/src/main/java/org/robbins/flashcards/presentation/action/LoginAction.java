package org.robbins.flashcards.presentation.action;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.robbins.openid.authentication.OpenId4JavaAuthenticator;

import com.opensymphony.xwork2.Preparable;

public class LoginAction extends FlashCardsAppBaseAction implements Preparable, ServletRequestAware, ServletResponseAware, SessionAware, ApplicationAware {


	private static final long serialVersionUID = 9086659350295360444L;

	private static Logger logger = Logger.getLogger(LoginAction.class);

	@Inject
	private User loggedInUser;
	
	@Inject
	private UserService userService;
	
	// this is the only form field we will be looking for from OpenID Selector on the front end
	private String openidIdentifier;
	
    // we'll need access to the Servlet spec objects, rather than just their attribute or parm maps
	private HttpServletRequest request;
    private HttpServletResponse response;
    
    // we'll be storing the User object in the Session
    private Map<String, Object> httpSession;
	
    // the OpenIdAuthenticator class needs access to the application to store a OpenId4Java related object
    private Map<String, Object> application;
	
	// we'll need to send this to the OpenId provider so it knows where to send its response 
    private static final String RETURN_ACTION = "/home/authenticateOpenId.action";
    
    private static final int HTTP_PORT = 80;

    // the OpenID Selector form will submit to this Action method
    public String validateOpenId() throws Exception {

    	logger.debug("Entering validateOpenId()");
    	
    	// get rid of trailing slash
    	if (getOpenid_identifier().endsWith("/")) {
    		setOpenid_identifier(getOpenid_identifier().substring(0, getOpenid_identifier().length() - 1));
        }

    	logger.debug("The requested OpenId identifier is: " + getOpenid_identifier());

        // determine a return_to URL where the application will receive
        // the authentication responses from the OpenID provider
        String returnUrl = getServerContext(request) + RETURN_ACTION;
        logger.debug("return URL: " + returnUrl);
		
        // construct the destination Url to send to the Open Id provider
        String destinationUrl = OpenId4JavaAuthenticator.getValidateOpenIdUrl(returnUrl, this.getOpenid_identifier(), httpSession, application); 
		
		// redirect to the Auth Request
		response.sendRedirect(destinationUrl);
        
		// no need to return a view
		return NONE;
    }
    
	public String authenticateOpenId() throws Exception {
		logger.debug("Entering authenticateOpenId()");

		Map<String,String[]> parmList = request.getParameterMap();

		// extract the receiving URL from the HTTP request
		final StringBuffer receivingURL = request.getRequestURL();
		final String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(request.getQueryString());
		}
		
		logger.debug(receivingURL.toString().replaceAll("&", "\n"));

		// verify the user has authenticated with the Open Id provider and 
		// get a reference to the authenticated User
		User authenticatingUser = OpenId4JavaAuthenticator.getAuthenticatedUser(parmList, receivingURL, httpSession, application);

		// does the user already exist?
		User existingUser = userService.findUserByOpenid(authenticatingUser.getOpenid());
		if (existingUser != null) {
			authenticatingUser.setId(existingUser.getId());
			authenticatingUser.setCreatedBy(existingUser.getCreatedBy());
			authenticatingUser.setCreatedDate(existingUser.getCreatedDate());
			
			loggedInUser.setId(existingUser.getId());
		} else {
			// this is a new user and he doesn't have an id yet, temporarily set it to the service user (which is me)
			loggedInUser.setId(1L);
		}
		
		// save the user
		User persistedUser = userService.save(authenticatingUser);
		
		httpSession.put("user", persistedUser);
		
		// get the id of the user.  if he's new then this is our first opportunity to get his id
		Long loggedInUserId = persistedUser.getId();
		
		// set the id on the loggedInUser which we'll use for auditing
		loggedInUser.setId(loggedInUserId);
		
		// retrieve the original URL from the Session
		String desitinationURL = (String)httpSession.get("originalURL");

		// was a destination URL provided?
		if (desitinationURL == null) {
			logger.debug("No destination URL provided, will send to Home");
			return "home";
		}
		else {
			logger.debug("Redirecting to : " + desitinationURL);
			response.sendRedirect(desitinationURL);
			return NONE;
		}
	}

	@SuppressWarnings("rawtypes")
	public String logout() {
		logger.debug("Entering logout()");
		
		try {
			// invalidate the user's session
			httpSession.remove("user");

			if (httpSession instanceof org.apache.struts2.dispatcher.SessionMap) {
			    try {
			        ((org.apache.struts2.dispatcher.SessionMap) httpSession).invalidate();
			    } catch (IllegalStateException e) {
			        logger.error("Exception in logout()", e);
			    }
			}
			
			return "success";
		} catch (Exception e) {
			logger.error("Exception in logout():", e);
			
			return "error";
		}
	}

    private String getServerContext(final HttpServletRequest request) {
        // Get the base url.
		final StringBuilder serverPath = new StringBuilder();
		
		serverPath.append(request.getScheme() + "://");
		serverPath.append(request.getServerName());

		if (request.getServerPort() != HTTP_PORT) {
		    serverPath.append(":" + request.getServerPort());
		}
		serverPath.append(request.getContextPath());
		
		return serverPath.toString();
	}
	
	@Override
	public void prepare() throws Exception {
		logger.debug("Entering prepare()");
	}

	public String getOpenid_identifier() {
		return openidIdentifier;
	}

	public void setOpenid_identifier(String openid_identifier) {
		this.openidIdentifier = openid_identifier;
	}

	@Override
	public void setSession(Map<String, Object> httpSession) {
		this.httpSession = httpSession;
	}
	
	@Override
	public void setServletResponse(final HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(final HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
}
