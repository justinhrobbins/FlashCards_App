<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionmessage theme="jquery"/>

<s:if test="tagList.size() > 0">
	<table border="0px" cellpadding="8px">
		<thead>
			<tr>
				<th><s:text name="label.tag.title" /></th>
				<th><s:text name="label.flashcard.title" /></th>
				<th><s:text name="label.edit" /></th>
				<th><s:text name="label.delete" /></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="tagList">
				<tr>
					<td>
						<s:url var="url" action="display">
							<s:param name="id">
								<s:property value="id" />
							</s:param>
						</s:url>
						<s:a href="%{url}"><s:property value="name" /></s:a>
					</td>
					<td>
						<s:if test="flashcards.size() > 0">
							<s:iterator value="flashcards"  status="flashcardStatus">
								<s:url var="url" namespace="/flashcard" action="display">
									<s:param name="id">
										<s:property value="id" />
									</s:param>
								</s:url>
								<s:a href="%{url}"><s:property value="question" /></s:a>
								<s:if test="!#flashcardStatus.last"><br/></s:if>
							</s:iterator>
						</s:if>
						&nbsp;
					</td>
					<td>
						<s:url var="url" action="form">
							<s:param name="id">
								<s:property value="id" />
							</s:param>
						</s:url>
						<s:a href="%{url}">
							<img src="../images/edit.gif" />
						</s:a>
					</td>
					<td>
						<s:if test="flashcards.size() > 0">
							<img src="../images/delete_disabled.gif" title="<s:text name="error.tag.delete.failed.extra.info" />"/>
						</s:if>
						<s:else>
							<s:url var="url" action="delete">
								<s:param name="id">
									<s:property value="id" />
								</s:param>
							</s:url>
							<s:a href="%{url}">
								<img src="../images/delete.gif" />
							</s:a>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
	<br/>
	<s:text name="label.no.tags" />
</s:else>
