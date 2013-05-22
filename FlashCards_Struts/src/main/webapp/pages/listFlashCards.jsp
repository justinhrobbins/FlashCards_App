<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionmessage theme="jquery"/>

<s:if test="flashCardList.size() > 0">
	<table border="0px" cellpadding="8px">
		<thead>
			<tr>
				<th><s:text name="label.flashcard.question" /></th>
				<th><s:text name="label.flashcard.tags" /></th>
				<th><s:text name="label.edit" /></th>
				<th><s:text name="label.delete" /></th>
			<tr>
		</thead>
		<tbody>
			<s:iterator value="flashCardList">
				<tr>
					<td class="wrapping">
						<s:url var="url" action="display">
							<s:param name="id">
								<s:property value="id" />
							</s:param>
						</s:url>
						<s:a href="%{url}"><s:property value="question" /></s:a>
					</td>
					<td>
						<s:if test="tags.size() > 0">
							<s:iterator value="tags" status="tagStatus">
								<s:url var="url" namespace="/tag" action="display">
									<s:param name="id">
										<s:property value="id" />
									</s:param>
								</s:url>
								<s:a href="%{url}"><s:property value="name" /></s:a>
								<s:if test="!#tagStatus.last">,</s:if>
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
						<s:url var="url" action="delete">
							<s:param name="id">
								<s:property value="id" />
							</s:param>
						</s:url>
						<s:a href="%{url}">
							<img src="../images/delete.gif" />
						</s:a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
	<br/>
	<s:text name="label.no.flashcards" />
</s:else>