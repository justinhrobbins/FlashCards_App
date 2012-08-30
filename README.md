## Overview

The purpose of this project is to demonstrate my Java development compentencies.  A working copy of this project is running on the Amazon EC cloud instance.

## WebServices
JAX-RS web services using the following:
* Apache CXF (JSR 311: JAX-RS)
* Apache Shiro (Authentication)
* Spring
* Jackson

I modeled the web services after the RESTful API best practices chronicled in the apigee blog.  The API has the following functionality:
# Each JAX-RS resources supports CRUD using the relevant HTTP methods (@POST, @GET, @PUT, @DELETE)
# Partial updates
# API Versioning
# Sorting and Pagination
# Counts
# Authentication using Basic Auth
# Custom JSON formatted exceptions
# JSON responses

## Domain
* POJOs
* JPA & Jackson annotations

## GWT
* Google Web Toolkit (GWT)
* MVP Framework (Activities and Places framework)
* Views, ClientFactory
* UiBinder
* RestyGWT

## Service
* Spring
    - Spring Data
    - Transactions
* Hibernate implementation of JPA

## Struts
* Struts MVC
    - Actions
    - Tiles
    - Struts JSP tags
* Spring
* JSP

## Deployment
Amazon EC2, Tomcat, MySQL