<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionmessage theme="jquery"/>
<br/>
<b><s:text name="label.tag.title" /></b>:
<br/>
<s:property value="tag.name" />

<s:url var="url" action="form">
	<s:param name="id">
		<s:property value="tag.id" />
	</s:param>
</s:url>
<s:a href="%{url}">
	<img src="../images/edit.gif" />
</s:a>

<s:if test="tag.flashcards.size() > 0">
	<img src="../images/delete_disabled.gif" title="<s:text name="error.tag.delete.failed.extra.info" />" />
</s:if>
<s:else>
	<s:url var="url" action="delete">
		<s:param name="id">
			<s:property value="tag.id" />
		</s:param>
	</s:url>
	<s:a href="%{url}">
		<img src="../images/delete.gif" />
	</s:a>
</s:else>

<br/><br/>

<b><s:text name="label.flashcards.title" /></b>:

<br/>

<s:if test="tag.flashcards.size() > 0">
	<s:iterator value="tag.flashcards" status="flashcardStatus">
		<s:url var="url" action="display" namespace="/flashcard">
			<s:param name="id">
				<s:property value="id" />
			</s:param>
		</s:url>
		<s:a href="%{url}"><s:property value="question" /></s:a>
		<s:if test="!#flashcardStatus.last"><br/></s:if>
	</s:iterator>
</s:if>
<s:else>
	<s:text name="label.tag.no.flashcards" />
</s:else>

<br/><br/><br/>
<s:text name="label.tag.created" />:&nbsp;<s:date name="tag.getCreatedDateAsDate()" format="dd-MMM-yyyy 'at' hh:mm a" />
<br/>
<s:text name="label.tag.modified" />:&nbsp;<s:date name="tag.getLastModifiedDateAsDate()" format="dd-MMM-yyyy 'at' hh:mm a" />