<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="login" namespace="/home" action="login.action"/>
<s:url id="logout" namespace="/home" action="logout.action"/>

<div id="login">
	<s:if test="%{#session.user.email.length() > 0}">
		<s:property value="%{#session.user.email}" />
		&nbsp;|&nbsp;
		<s:a href="%{logout}"><s:text name="menu.logout" /></s:a>
	</s:if>
	<s:else>
		<s:a href="%{login}"><s:text name="menu.login" /></s:a>
	</s:else>
</div>

<h1><a href="/flashcardsstruts">Flash Cards Application</a></h1>
<h3></h3>