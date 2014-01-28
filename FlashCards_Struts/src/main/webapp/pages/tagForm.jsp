<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h3><s:text name="label.tag.form.title"/></h3>

<s:actionerror theme="jquery" />
<s:fielderror theme="jquery" />
<br/>

<s:form action="saveOrUpdate" method="post">
	<s:hidden name="tag.id" />
	<tr>
		<td class="tdLabel">
			<s:text name="label.tag.form.name" />&nbsp;<span class="required">*</span>
		</td>
		<td>
			<s:textfield name="tag.name" size="66" theme="simple"/>
		</td>
	</tr>
	<tr>
		<td class="tdLabel">
			<s:submit key="label.tag.submit" theme="simple" />
		</td>
		<td>
			<s:submit key="label.tag.cancel" name="redirectAction:list" theme="simple" />
		</td>
	</tr>
</s:form>

<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>