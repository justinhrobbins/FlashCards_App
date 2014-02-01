
package org.robbins.flashcards.webservices;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Path("/")
@Component("flashCardsAppResource")
@Produces("text/plain")
@Consumes("text/plain")
public class FlashCardsAppResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardsAppResource.class);

    @Inject
    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    @GET
    @Path("/status")
    public String getStatus() {
        String version = getClass().getPackage().getImplementationVersion();
        if (version == null) {
            try {
                Resource resource = getContext().getResource("/META-INF/MANIFEST.MF");
                Properties prop = PropertiesLoaderUtils.loadProperties(resource);
                version = prop.getProperty("Implementation-Version");
                if (null == version) {
                    throw new GenericWebServiceException(
                            Response.Status.INTERNAL_SERVER_ERROR,
                            "Unable to determine status");
                }
                LOGGER.debug("Version: " + version);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                throw new GenericWebServiceException(
                        Response.Status.INTERNAL_SERVER_ERROR,
                        "Unable to determine status", e);
            }
        }
        return version;
    }
}
