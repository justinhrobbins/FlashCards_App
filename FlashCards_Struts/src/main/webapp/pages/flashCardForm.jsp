<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<script>
    $(function(){
		var allAvailableTags = [<s:property value="allExplodedTags" />];

	    $('#jQueryTags').tagit({
		    availableTags: allAvailableTags,
		    // This will make Tag-it submit a single form value, as a comma-delimited field.
		    singleField: true,
            singleFieldNode: $('#explodedTags')
	    });
    });
</script>

<h3><s:text name="label.flashcard.form.title"/></h3>

<s:actionerror theme="jquery" />
<s:actionmessage theme="jquery"/>
<s:fielderror theme="jquery"/>

<s:form action="saveOrUpdate" method="post">
	<s:hidden name="flashCard.id" />
	<tr>
		<td class="tdLabel">
			<s:text name="label.flashcard.form.question" />&nbsp;<span class="required">*</span>
		</td>
		<td>
			<s:textfield name="flashCard.question" size="66" theme="simple" />
		</td>
	</tr>
	<tr>
		<td class="tdLabel">
			<s:text name="label.flashcard.form.answer" />&nbsp;<span class="required">*</span>
		</td>
		<td>
			<sjr:tinymce
				id="flashCard.answer" 
				name="flashCard.answer" 
				rows="20" 
				cols="50" 
				editorTheme="simple"
				parentTheme="simple"
			/>
		</td>
	</tr>

	<tr>
		<td class="tdLabel">
			<s:text name="label.flashcard.form.tags" />
		</td>
		<td>
			<input 
				type="hidden"
				name="explodedTags"
				id="explodedTags"
				value="<s:property value="explodedTags" />" >

		    <ul id="jQueryTags"></ul>
		</td>
	</tr>

	<s:if test="flashCard.links.size() > 0">
		<s:iterator value="flashCard.links" status="rowstatus" var="idx" >
			<tr>
				<td class="tdLabel">
					<label class="label">
						<s:text name="label.flashcard.form.link" /> #${rowstatus.index+1}:
					</label>
				</td>
				<td>
					<s:textfield name="flashCard.links[%{#rowstatus.index}]" value="%{#idx}" size="66" theme="simple" />
				</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr>
			<td class="tdLabel">
				<label class="label">
					<s:text name="label.flashcard.form.link" /> #${rowstatus.index+1}:
				</label>
			</td>
			<td>
				<s:textfield name="flashCard.links[0]" value="" size="66" theme="simple" />
			</td>
		</tr>
	</s:else>

	<tr id="addRow">
		<td>&nbsp;</td>
		<td>
			<a href="#" onclick="return FlashCardLink.addRow()">
            <s:text name="label.flashcard.link.add" />
			</a>
		</td>
	</tr>

	<tr>
		<td>
			<s:submit label="label.flashcard.submit" align="center" theme="simple" />
		</td>
		<td>
			<s:submit key="label.flashcard.cancel" name="redirectAction:list" theme="simple"  />
		</td>
	</tr>
</s:form>

<script src="<s:url value="/js/flashcard.js"/>" type="text/javascript"></script>

<s:if test="flashCard.links.size() > 0">
	<script type="text/javascript">FlashCardLink.prepare('<s:text name="label.flashcard.link"/>', <s:property value="flashCard.links.size"/> );</script>
</s:if>
<s:else>
	<script type="text/javascript">FlashCardLink.prepare('<s:text name="label.flashcard.link"/>', 1);</script>
</s:else>

<%((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).clearErrorsAndMessages();%>