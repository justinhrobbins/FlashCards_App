<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionmessage theme="jquery"/>
<br/>
<b><s:text name="label.flashcard.question" /></b>:
<br/>
<s:property value="flashCard.question" />

<s:url var="url" action="form">
	<s:param name="id">
		<s:property value="flashCard.id" />
	</s:param>
</s:url>
<s:a href="%{url}">
	<img src="../images/edit.gif" />
</s:a>

<s:url var="url" action="delete">
	<s:param name="id">
		<s:property value="flashCard.id" />
	</s:param>
</s:url>
<s:a href="%{url}">
	<img src="../images/delete.gif" />
</s:a>

<br/><br/>

<b><s:text name="label.flashcard.answer" /></b>:
<s:property value="flashCard.answer" escape="false" />

<br/><br/>

<b><s:text name="label.flashcard.tags" /></b>:
<br/>
<s:if test="flashCard.tags.size() > 0">
	<s:iterator value="flashCard.tags" status="tagStatus">
		<s:url var="url" action="display" namespace="/tag">
			<s:param name="id">
				<s:property value="id" />
			</s:param>
		</s:url>
		<s:a href="%{url}"><s:property value="name" /></s:a>
		<s:if test="!#tagStatus.last">,</s:if>
	</s:iterator>
</s:if>
<s:else>
	<s:text name="label.flashcard.no.tags" />
</s:else>

<br/><br/>

<b><s:text name="label.flashcard.links" /></b>:
<br/>
<s:if test="flashCard.links.size() > 0">
	<s:iterator value="flashCard.links" status="rowstatus" var="idx" >
		<a href="<s:property/>" target="_blank"><s:property/></a>
		<br/>
	</s:iterator>
</s:if>
<s:else>
	<s:text name="label.flashcard.no.links" />
</s:else>

<br/><br/><br/>
<s:text name="label.flashcard.created" />:&nbsp;<s:date name="flashCard.getCreatedDateAsDate()" format="dd-MMM-yyyy 'at' hh:mm a" />
<br/>
<s:text name="label.flashcard.modified" />:&nbsp;<s:date name="flashCard.getLastModifiedDateAsDate()" format="dd-MMM-yyyy 'at' hh:mm a" />