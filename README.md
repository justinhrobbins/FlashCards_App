[![Build Status](https://justinhrobbins.ci.cloudbees.com/job/Flashcards/badge/icon)](https://justinhrobbins.ci.cloudbees.com/job/Flashcards/)

## Overview

The purpose of this project is to demonstrate my Java and web development competencies.

A working copy of this project is running on an Amazon EC2 cloud instance.  There are two published versions of the application:

1. The [Google Web Toolkit client version](http://www.socialflashcards.com/gwt/) is a rich JavaScript/AJAX client that interacts with the server through a stateless REST API.
2. The [Struts version](http://www.socialflashcards.com/struts/) implements a more traditional model-view-controller (MVC) architecture.  (This version features authentication using OpenId.  Your password is not saved or even sent to the FlashCards application)

Both versions of the Flashcards App reuse the same service, domain, and persistence layers.

## Project modules
The documentation below outlines the technologies used in each module of the Flashcards App:

### WebServices
JAX-RS web services using the following:
* [Apache CXF](http://cxf.apache.org/docs/jax-rs.html) JSR 311: JAX-RS
* [Apache Shiro](http://shiro.apache.org/authentication-features.html) Authentication
* [Spring](http://www.springsource.org/spring-framework)
* [Jackson](http://wiki.fasterxml.com/JacksonHome) JSON processor

I modeled the web services after the RESTful API best practices chronicled in the [apigee blog](http://blog.apigee.com/).  The API has the following functionality:
* Each JAX-RS resources supports CRUD using the relevant HTTP methods (@POST, @GET, @PUT, @DELETE)
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
* [Maven](http://maven.apache.org/) for dependency management and project structure
* [JUnit](http://www.junit.org/) and [Mockito](http://code.google.com/p/mockito/) are used for unit and integration tests
* Spring RestTemplate is used as the client for web service integration testing.
* [Eclipse](http://www.eclipse.org/) IDE

## Continuous Integration
Github commits to this project trigger a Jenkins build on [Cloudbees](http://www.cloudbees.com/).

Check out the current [build status](https://justinhrobbins.ci.cloudbees.com/job/Flashcards/)

## Software quality
[Sonar](http://www.sonarsource.org/) is employed for analyzing the code quality and test coverage of the Flashcards project modules

Check out the current [sonar results](http://www.socialflashcards.com/sonar/).  

## Deployment
The deployed app is hosted on the [Amazon EC2 cloud](http://aws.amazon.com/ec2/) using [Tomcat](http://tomcat.apache.org/) servlet container, [MySQL](http://www.mysql.com/)

Take a look at an example deployment of both the GWT and Struts versions of the app:
* [Google Web Toolkit version](http://www.socialflashcards.com/gwt/FlashCards_App.html)
* [Struts version](http://www.socialflashcards.com/struts/)
