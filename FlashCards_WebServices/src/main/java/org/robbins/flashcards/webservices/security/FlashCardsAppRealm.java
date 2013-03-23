package org.robbins.flashcards.webservices.security;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;

public class FlashCardsAppRealm extends AuthorizingRealm {

	private static Logger logger = Logger.getLogger(FlashCardsAppRealm.class);
	
	@Inject
	private UserService userService;
	
	public FlashCardsAppRealm() {
		// we are using MD5 encryption
        //setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));

        setCredentialsMatcher(new SimpleCredentialsMatcher());
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		logger.debug("Entering doGetAuthenticationInfo()");
		
		// retrieve a User using the info provided in the authentication attempt
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		logger.debug("Authenticating user name: '" + token.getUsername() + "'");

        User user;
		try {
			user = userService.findUserByOpenid(token.getUsername());

			if( user != null ) {
				return new SimpleAuthenticationInfo(user.getId(), "apiuserpassword", getName());
	        } else {
	            return null;
	        }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AuthenticationException();
		}
	}
}
