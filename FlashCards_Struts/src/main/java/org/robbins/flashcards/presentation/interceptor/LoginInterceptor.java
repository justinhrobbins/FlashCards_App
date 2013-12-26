package org.robbins.flashcards.presentation.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.robbins.flashcards.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor implements StrutsStatics{
    
    @Inject
    private ApplicationContext applicationContext;

	private static final long serialVersionUID = -759435156742745257L;
	static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	public String intercept(final ActionInvocation invocation) throws Exception {
    	String invocatedAction = invocation.getAction().getClass().getName();
    	logger.debug("Invocated Action: " + invocatedAction);

        // get references to the App Context, Session, and Request objects
    	final ActionContext context = invocation.getInvocationContext ();
        HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
        HttpSession session =  request.getSession (true);

        // Is there a "user" object stored in the user's HttpSession?
        User user = (User) applicationContext.getBean("loggedInUser");
        
        if ( (user.getId() == null) || (user.getId() == 0)) {
            // The user has not logged in yet.
        	logger.debug("User NOT found in the Session");

        	// Is the user attempting to log in right now?
            String loginIdentifier = request.getParameter("openid_identifier");
            String openIdEndpoint =  request.getParameter("openid.op_endpoint");

        	// we can know the user is trying to login right now if the "openid_identifier" is a Request parm
            if (! StringUtils.isBlank (loginIdentifier) ) {
            	// The user is attempting to log in.
            	logger.debug("The user is attempting to log in");

                // Process the user's login attempt.
            	return invocation.invoke ();
            }
            // we know the user has just auth'd with the OpenID provider if the "openid.op_endpoint" is a Request parm
            else if(! StringUtils.isBlank (openIdEndpoint) ) {
            	// The user has logged in with an OpenId provider
            	logger.debug("The user has logged in with an OpenId provider");

                // Process the user's login attempt.
            	return invocation.invoke ();
            }
            else {
        		// save the original URL, we'll need it later
            	saveReceivingURL(request, session);
            	
            	logger.debug("Forwarding to the Login form");
            }

            // it we get this far then the user hasn't tried to login yet, 
            // and we need to send to the login form.
            return "login";
        } 
       	else {
       		logger.debug("User " + user.toString() + " found in the Session");
       		
       		// user is already logged in
       		return invocation.invoke ();
        }
    }

	private void saveReceivingURL(HttpServletRequest request, HttpSession session) {
		// extract the receiving URL from the HTTP request
		final StringBuffer receivingURL = request.getRequestURL();
		final String queryString = request.getQueryString();

		// if there is a query string then we'll need that too
		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(request.getQueryString());
		}
		
		logger.debug("Original URL: " + receivingURL.toString());
		
		// save the original URL in the Session
		// we're going to need to redirect the user back to this URL after login is completed
		session.setAttribute("originalURL", receivingURL.toString());
	}
}