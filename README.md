[![Build Status](https://travis-ci.org/justinhrobbins/FlashCards_App.png?branch=master)](https://travis-ci.org/justinhrobbins/FlashCards_App)
[![Coverage Status](https://coveralls.io/repos/justinhrobbins/FlashCards_App/badge.png?branch=master)](https://coveralls.io/r/justinhrobbins/FlashCards_App?branch=master)

View the [Maven site report](http://justinhrobbins.github.io/FlashCards_App/site/0.0.1-SNAPSHOT/index.html) for this project.

##Table of Contents
* [Overview](#overview)
* [Technologies](#technologies)
    * [Back-end Technologies](#backend-technologies)
    * [Front-end Technologies](#ui-technologies)
        * [GWT UI](#gwt)
        * [Struts UI](#struts)
* [REST API](#rest-api)
* [Continuous Integration](#continuous-integration)
* [Software Quality](#software-quality)
* [Maven Site Reports](#maven-site-reports)
* [Deployment](#deployment)
* [git workflow](#git-workflow)

## <a name="overview"></a>Overview
I use this project as a playground to learn new skills and demonstrate my Java and web development skills.

## <a name="technologies"></a>Technologies
Technologies and libraries used in this project:

### <a name="backend-technologies"></a>Back-end Technologies
* [Dozer](http://dozer.sourceforge.net/) Java bean mapping
* [DbUnit](http://www.dbunit.org/) and an embedded [H2](www.h2database.com/) database is used for integration tests
* [Jackson](http://wiki.fasterxml.com/JacksonHome) JSON processor
* [JAXB](https://jersey.java.net/) Java Architecture for XML Binding (JAXB)
* [Jersey](https://jersey.java.net/) JAX-RS implementation
* [Jetty](http://www.eclipse.org/jetty/) servlet container is used for API integration tests
* [JPA](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html) & [Hibernate](http://hibernate.org/orm/) JPA provider
* [JUnit](http://www.junit.org/), [Mockito](http://code.google.com/p/mockito/) and [Hamcrest](http://hamcrest.org/JavaHamcrest/) matchers are used for testing
* [Maven](http://maven.apache.org/)
    * Multi-module project
    * Dependency and plugin management
    * Maven profiles
    * Maven site reports
* Spring technologies
    * [Spring IOC](http://www.springsource.org/spring-framework) dependency injection
    * [Spring Security](http://static.springsource.org/spring-security/site/index.html) Authentication
    * [Spring Data - JPA](http://www.springsource.org/spring-data/jpa) If you are not familiar with Spring Data, you might like to take a look at the [Spring Data Example Showcase](https://github.com/SpringSource/spring-data-jpa-examples/tree/master/spring-data-jpa-showcase) on Github.  The project compares a a typical data access implementation with JPA 2 with a less boilerplate Spring Data version.
    * [Spring RestTemplate](http://spring.io/blog/2009/03/27/rest-in-spring-3-resttemplate/) for API integration tests
    * [Spring profiles](http://spring.io/blog/2011/02/14/spring-3-1-m1-introducing-profile/) to select embedded vs external database
* [Swagger](http://developers.helloreverb.com/swagger/) API documentation

### <a name="ui-technologies"></a>UI Technologies
There are two UI's for this project

#### <a name="gwt"></a>GWT
* [Google Web Toolkit](https://developers.google.com/web-toolkit/) (GWT)
* MVP Framework ([Activities and Places framework](https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces))
* Views, ClientFactory
* [UiBinder](https://developers.google.com/web-toolkit/doc/latest/DevGuideUiBinder)
* [RestyGWT](http://restygwt.fusesource.org/)

#### <a name="struts"></a>Struts
* [Struts MVC](http://struts.apache.org/)
    - Actions
    - [Tiles](http://struts.apache.org/2.x/docs/tiles-plugin.html)
    - [Interceptors](http://struts.apache.org/2.x/docs/interceptors.html)
    - Struts JSP tags
* [OpenId](http://openid.net/) Authentication
* JSP

## <a name="rest-api"></a>REST API
The web services are modeled after the RESTful API best practices chronicled in the [apigee blog](http://blog.apigee.com/).  The API has the following functionality:
* Each JAX-RS resource supports CRUD using the relevant HTTP methods (@POST, @GET, @PUT, @DELETE)
* [Partial updates](http://blog.apigee.com/detail/restful_api_design_can_your_api_give_developers_just_the_information/)
* [API Versioning](http://blog.apigee.com/detail/restful_api_design_tips_for_versioning)
* [Sorting and Pagination](http://blog.apigee.com/detail/restful_api_design_can_your_api_give_developers_just_the_information/)
* [Counts](http://blog.apigee.com/detail/restful_api_design_what_about_counts/)
* Authentication using Basic Auth
* [Custom JSON formatted exceptions](http://blog.apigee.com/detail/restful_api_design_what_about_errors/)
* [JSON request/responses payloads](http://blog.apigee.com/detail/why_you_should_build_your_next_api_using_json/)

## <a name="continuous-integration"></a>Continuous Integration
Github commits to this project trigger a CI build on [Travis CI](https://travis-ci.org/).

Check out the current [build status](https://travis-ci.org/justinhrobbins/FlashCards_App/builds)

## <a name="software-quality"></a>Software quality
* [Cobertura](http://cobertura.github.io/cobertura/) is used for analyzing unit test coverage
* [Findbugs](http://findbugs.sourceforge.net/) is used for code quality analysis

## <a name="maven-site-reports"></a>Maven Site Reports
The Maven [site report](http://maven.apache.org/guides/mini/guide-site.html) for this project is published to [Github Pages](http://pages.github.com/)
Reports generated for this project include:
* [JavaDocs](http://maven.apache.org/plugins/maven-javadoc-plugin/)
* [Surefire Report](https://maven.apache.org/surefire/maven-surefire-report-plugin/)
* [Failsafe Report](https://maven.apache.org/surefire/maven-failsafe-plugin/)
* [Github issues](http://maven.apache.org/plugins/maven-changes-plugin/github-report-mojo.html)
* [Cobertura Test Coverage](http://mojo.codehaus.org/cobertura-maven-plugin/)
* [Findbugs Report](http://mojo.codehaus.org/findbugs-maven-plugin/)
* [REST API Docs](https://github.com/kongchen/swagger-maven-plugin)

You can view the Maven site report for the [FlashCards app here](http://justinhrobbins.github.io/FlashCards_App/site/0.0.1-SNAPSHOT/index.html).

## <a name="deployment"></a>Deployment
The project wars (API, GWT UI, Stuts UI) can be deployed to any servlet container.

The simplest way to see the application in action is to:
* Clone the Flashcards git repository (or Fork then Clone)
* run 'mvn install' from the project root
* from the FlashCards_WebServices directory run 'mvn jetty:run'
 
Once Jetty has completed startup, you can browse the GWT UI, Struts UI, or interact directly with the API using the Swagger UI or using a REST client of your choice
* GWT UI:  http://localhost:8080/gwt/
* Struts UI: http://localhost:8080/struts/
* Swagger UI: http://localhost:8080/apidocs/
* API: http://localhost:8080/api/
 
## <a name="git-workflow"></a>git workflow
* [GitHub Flow](http://scottchacon.com/2011/08/31/github-flow.html)
