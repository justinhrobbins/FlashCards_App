
package org.robbins.openid.authentication;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegResponse;
import org.robbins.flashcards.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OpenId4JavaAuthenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenId4JavaAuthenticator.class);

    private static final int MAX_AGE = 5000;

    private OpenId4JavaAuthenticator() {
    };

    private static ConsumerManager getConsumerManager(
            final Map<String, Object> application) {
        ConsumerManager manager;

        // try to get the ConsumerManager from the Application scope
        manager = (ConsumerManager) application.get("consumermanager");

        if (manager == null) {
            // create a new ConsumerManager
            try {
                manager = new ConsumerManager();
                manager.setAssociations(new InMemoryConsumerAssociationStore());
                manager.setNonceVerifier(new InMemoryNonceVerifier(MAX_AGE));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            // add the Consumer Manager to the application scope
            application.put("consumermanager", manager);
        }

        return manager;
    }

    @SuppressWarnings("unchecked")
    public static String getValidateOpenIdUrl(final String returnUrl,
            final String openIdIdentifier, final Map<String, Object> httpSession,
            final Map<String, Object> application)
            throws DiscoveryException, MessageException, ConsumerException {

        // get a reference to the Consumer Manager
        ConsumerManager manager = getConsumerManager(application);

        // perform discovery on the user-supplied identifier
        List<DiscoveryInformation> discoveries = manager.discover(openIdIdentifier);

        // attempt to associate with the OpenID provider
        // and retrieve one service endpoint for authentication
        DiscoveryInformation discovered = manager.associate(discoveries);

        // store the discovery information in the user's session for later use
        // leave out for stateless operation / if there is no session
        httpSession.put("discovered", discovered);

        // obtain a AuthRequest message to be sent to the OpenID provider
        AuthRequest authReq = manager.authenticate(discovered, returnUrl);

        // Attribute Exchange
        FetchRequest fetch = FetchRequest.createFetchRequest();

        // different Open Id providers accept different attributes
        if (openIdIdentifier.contains("google.com")) {
            LOGGER.debug("Open Id Identifier is: google.com");

            fetch.addAttribute("first", "http://axschema.org/namePerson/first", true);
            fetch.addAttribute("last", "http://axschema.org/namePerson/last", true);
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("language", "http://axschema.org/pref/language", true);
        } else if (openIdIdentifier.contains("yahoo.com")) {
            LOGGER.debug("Open Id Identifier is: yahoo.com");

            fetch.addAttribute("fullname", "http://axschema.org/namePerson", true);
            fetch.addAttribute("nickname", "http://axschema.org/namePerson/friendly",
                    true);
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("language", "http://axschema.org/pref/language", true);
        } else if (openIdIdentifier.contains("aol.com")) {
            LOGGER.debug("Open Id Identifier is: aol.com");

            fetch.addAttribute("first", "http://axschema.org/namePerson/first", true);
            fetch.addAttribute("last", "http://axschema.org/namePerson/last", true);
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("language", "http://axschema.org/pref/language", true);
        } else {
            LOGGER.debug("Open Id Identifier is: something else");

            fetch.addAttribute("fullname", "http://schema.openid.net/namePerson", true);
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
            fetch.addAttribute("country", "http://axschema.org/contact/country/home",
                    true);
        }

        // attach the extension to the authentication request
        authReq.addExtension(fetch);

        LOGGER.info("The request string is: "
                + authReq.getDestinationUrl(true).replaceAll("&", "\n"));

        return authReq.getDestinationUrl(true);
    }

    public static UserDto getAuthenticatedUser(final Map<String, String[]> parmList,
            final StringBuffer receivingURL, final Map<String, Object> httpSession,
            final Map<String, Object> application) throws MessageException,
            DiscoveryException,
            AssociationException {

        // extract the parameters from the authentication response
        // (which comes in as a HTTP request from the OpenID provider)
        ParameterList openidResp = new ParameterList(parmList);

        // retrieve the previously stored discovery information
        final DiscoveryInformation discovered = (DiscoveryInformation) httpSession.get("discovered");

        // get a reference to the Consumer Manager
        ConsumerManager manager = getConsumerManager(application);

        // verify the response
        final VerificationResult verification = manager.verify(receivingURL.toString(),
                openidResp, discovered);

        // examine the verification result and extract the verified identifier
        Identifier verified = verification.getVerifiedId();
        if (verified == null) {
            return null;
        }

        AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

		UserDto user = new UserDto();
        user.setOpenid(authSuccess.getIdentity());

        if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
            LOGGER.info("Processed as OPENID_NS_AX");

            FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

            // populate the User object with attributes from the FetchResponse
            user.setNickname(fetchResp.getAttributeValue("nickname"));
            user.setEmail(fetchResp.getAttributeValue("email"));
            user.setFullName(fetchResp.getAttributeValue("fullname"));
            user.setFirstName(fetchResp.getAttributeValue("first"));
            user.setLastName(fetchResp.getAttributeValue("last"));
            user.setLanguage(fetchResp.getAttributeValue("language"));
            user.setCountry(fetchResp.getAttributeValue("country"));

            LOGGER.info("User: " + user.toString());
        }

        if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
            LOGGER.info("Processed as OPENID_NS_SREG");

            SRegResponse sregResp = (SRegResponse) authSuccess.getExtension(SRegMessage.OPENID_NS_SREG);

            // if we didn't get the user's email addy from the FetchResponse, try to get
            // it from the SRegResponse
            if (StringUtils.isBlank(user.getEmail())) {
                user.setEmail(sregResp.getAttributeValue("email"));
            }
        }
        return user;
    }
}
