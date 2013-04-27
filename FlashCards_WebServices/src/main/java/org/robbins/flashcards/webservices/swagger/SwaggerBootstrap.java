package org.robbins.flashcards.webservices.swagger;

import javax.servlet.http.HttpServlet;

import com.wordnik.swagger.jaxrs.JaxrsApiReader;

public class SwaggerBootstrap extends HttpServlet {

	private static final long serialVersionUID = -7127649277794006525L;

	static {
		JaxrsApiReader.setFormatString("");
	}
}
