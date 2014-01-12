[![Build Status](https://travis-ci.org/justinhrobbins/FlashCards_App.png?branch=master)](https://travis-ci.org/justinhrobbins/FlashCards_App)
[![Coverage Status](https://coveralls.io/repos/justinhrobbins/FlashCards_App/badge.png?branch=master)](https://coveralls.io/r/justinhrobbins/FlashCards_App?branch=master)

## Overview
The purpose of this project is to demonstrate my Java and web development competencies.

1. The [Google Web Toolkit client version](http://www.socialflashcards.com/gwt/) is a rich JavaScript/AJAX client that interacts with the server through a stateless REST API.
2. The [Struts version](http://www.socialflashcards.com/struts/) implements a more traditional model-view-controller (MVC) architecture.  (This version features authentication using OpenId.  Your password is not saved or even sent to the FlashCards application)

Both versions of the Flashcards App reuse the same service, domain, and persistence layers.

Maven site hosted on [Github pages](http://justinhrobbins.github.io/FlashCards_App/site/0.0.1-SNAPSHOT/index.html)

## Project modules
The documentation below outlines the technologies used in each module of the Flashcards App:

### WebServices
JAX-RS web services using the following:
* [Jersey](https://jersey.java.net/) JSR 311: JAX-RS
* [Spring Security](http://static.springsource.org/spring-security/site/index.html) Authentication
* [Spring IOC](http://www.springsource.org/spring-framework)
* [Jackson](http://wiki.fasterxml.com/JacksonHome) JSON processor
* [JAXB](https://jersey.java.net/) Java Architecture for XML Binding (JAXB)
* 
The web services are modeled after the RESTful API best practices chronicled in the [apigee blog](http://blog.apigee.com/).  The API has the following functionality:
* Each JAX-RS resource supports CRUD using the relevant HTTP methods (@POST, @GET, @PUT, @DELETE)
* [Partial updates](http://blog.apigee.com/detail/restful_api_design_can_your_api_give_developers_just_the_information/)
* [API Versioning](http://blog.apigee.com/detail/restful_api_design_tips_for_versioning)
* [Sorting and Pagination](http://blog.apigee.com/detail/restful_api_design_can_your_api_give_developers_just_the_information/)
* [Counts](http://blog.apigee.com/detail/restful_api_design_what_about_counts/)
* Authentication using Basic Auth
* [Custom JSON formatted exceptions](http://blog.apigee.com/detail/restful_api_design_what_about_errors/)
* [JSON request/responses payloads](http://blog.apigee.com/detail/why_you_should_build_your_next_api_using_json/)

### Service
* Spring Services and DI
* I've included two Service implementations which can be used interchangeably:
    - [Spring Data - JPA](http://www.springsource.org/spring-data/jpa) repositories. If you are not familiar with Spring Data, you might like to take a look at the [Spring Data Example Showcase](https://github.com/SpringSource/spring-data-jpa-examples/tree/master/spring-data-jpa-showcase) on Github.  The project compares a a typical data access implementation with JPA 2 with a less boilerplate Spring Data version.  Spring Data evolved out of the [Hades](https://github.com/synyx/hades) open source project.
    - JPA version using EntityManager, JPQL, and generic CRUD DAOs
* Transaction management
* Hibernate implementation of JPA

### Domain
* POJO entities
* JPA & [Jackson](https://github.com/FasterXML/jackson-annotations) annotations
    - Including: @ManyToMany, @ElementCollection, @CollectionTable

### GWT
* [Google Web Toolkit](https://developers.google.com/web-toolkit/) (GWT)
* MVP Framework ([Activities and Places framework](https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces))
* Views, ClientFactory
* [UiBinder](https://developers.google.com/web-toolkit/doc/latest/DevGuideUiBinder)
* [RestyGWT](http://restygwt.fusesource.org/)

### Struts
* [Struts MVC](http://struts.apache.org/)
    - Actions
    - [Tiles](http://struts.apache.org/2.x/docs/tiles-plugin.html)
    - [Interceptors](http://struts.apache.org/2.x/docs/interceptors.html)
    - Struts JSP tags
* Spring IOC
* [OpenId](http://openid.net/) Authentication
* JSP

### Misc.
In addition to the technology stack outlined above, this project also uses:
* [Maven](http://maven.apache.org/) for dependency management, project structure, and site reports
* [JUnit](http://www.junit.org/) and [Mockito](http://code.google.com/p/mockito/) are used for unit and integration tests
* [DbUnit](http://www.dbunit.org/) and an embedded [H2](www.h2database.com/) database is used for integration tests
* An embedded [Jetty](http://www.eclipse.org/jetty/) servlet container is used for API integration tests
* Spring RestTemplate is used as the client for web service integration testing.

## Continuous Integration
Github commits to this project trigger a CI build on [Travis CI](https://travis-ci.org/).

Check out the current [build status](https://travis-ci.org/justinhrobbins/FlashCards_App/builds)

## Software quality
* [Cobertura](http://cobertura.github.io/cobertura/) is used for analyzing unit test coverage
* [Findbugs](http://findbugs.sourceforge.net/) is used for code quality analysis

## Maven Site Reports
The Maven [site report](http://maven.apache.org/guides/mini/guide-site.html) for this project is published to [Github Pages](http://pages.github.com/)
Reports generated for this project include:
* [JavaDocs](http://maven.apache.org/plugins/maven-javadoc-plugin/)
* [Surefire Report](https://maven.apache.org/surefire/maven-surefire-report-plugin/)
* [Failsafe Report](https://maven.apache.org/surefire/maven-failsafe-plugin/)
* [Github issues](http://maven.apache.org/plugins/maven-changes-plugin/github-report-mojo.html)
* [Cobertura Test Coverage](http://mojo.codehaus.org/cobertura-maven-plugin/)
* [Findbugs Report](http://mojo.codehaus.org/findbugs-maven-plugin/)

## Deployment
The project wars (API, GWT UI, Stuts UI) can be deployed to any servlet container.
The simplest way to see the application in action is to:
* Clone the Flashcards git repository (or Fork then Clone)
* run 'mvn install' from the project root
* from the FlashCards_WebServices directory run 'mvn jetty:run'
* Open a browser and navigate to the GWT UI:  http://localhost:8080/gwt/
